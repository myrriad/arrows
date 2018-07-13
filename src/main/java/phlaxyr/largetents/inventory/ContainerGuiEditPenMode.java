package phlaxyr.largetents.inventory;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.largetents.items.ItemRegistrar;
import phlaxyr.largetents.tile.TileGuiEditor;
import phlaxyr.largetents.util.ISlotTracker;

public class ContainerGuiEditPenMode extends ContainerGuiEditResult {

	public InventoryPenSlot peninventory;
	public ISlotTracker tracker;
	int penSlotCount;
	
    int customSlotCount;
    int size;
    int idxFirstPenSlot;
	
	public ContainerGuiEditPenMode(InventoryPlayer playerInv, TileGuiEditor tile, World world, BlockPos pos,
			ISlotTracker st, int xSize, int ySize, IPlayerSlotTracker ptracker, ItemStack pen) {
		super(playerInv, tile, world, pos, st, xSize, ySize, ptracker);
		

//		ItemStack s = this.getSlot(slot).getStack();
//		if(isPen(s)) {
//			this.putStackInSlot(slot, ItemStack.EMPTY);
//		}
        
		this.tracker = st;
		this.penSlotCount = 1;
		this.customSlotCount = tracker.slotCount() + penSlotCount; 
		this.size = this.inventorySlots.size();
		this.idxFirstPenSlot = size - penSlotCount;
		int slot = this.getSlotFor(pen);
		
		if(slot != -1) {
			System.out.println("slot is " + slot);

			transferStackInSlot(playerInv.player, slot);
	
		} else {
			slot = this.fromPlayerGetSlotFor(playerInv, pen);
			ItemStack penstack = playerInv.getStackInSlot(slot);
			if(slot != -1) {
				// attemptTransfer(playerInv, slot, this.inventorySlots.get(idxFirstPenSlot));
				mergeItemStack(penstack, idxFirstPenSlot, idxFirstPenSlot + 1, false);
			}
		}
	}
	public boolean isPen(ItemStack stack) {
		return stack.getItem().equals(ItemRegistrar.item_edit_pen);
	}
	@Override
	public void addAllSlots(TileGuiEditor tile, ISlotTracker st) {
		if(peninventory == null) {
			peninventory = new InventoryPenSlot();
		}

		
		super.addAllSlots(tile, st);
		this.addSlotToContainer(new Slot(peninventory, 0, this.xSize, 0) {
//		    public ItemStack getStack()
//		    {
//		        if(this.inventory == null) {
//		        	Lumber.jack.debug("Inventory is null!");
//		        	return ItemStack.EMPTY;
//		        }
//
//		    	return super.getStack();
//		    }
		    @Override
			/**
		     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
		     */
		    public boolean isItemValid(ItemStack stack)
		    {
		        return isPen(stack);
		    }
		});

		
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);


        
        // the order is:
        // Custom Slots, Inventory (if applicable), Pen Slots
        // such that for index i where i is not a pen slot, it will work with both containerpenmode and the other one
        
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            
            
            
            if((size - customSlotCount) < 1) {
            	
                return ItemStack.EMPTY; // no inventory 
                
            }
            
            if (index < tracker.slotCount() || index >= idxFirstPenSlot) // a custom slot or a penslot
            {

            	if (!this.mergeItemStack(itemstack1, tracker.slotCount(), idxFirstPenSlot, true)) // put into player inventory
                {
                    return ItemStack.EMPTY;
                }
            }
            else { // an inventory slot
            	boolean flag2 = true;
            	
            	if(isPen(itemstack1)) {
            		System.out.println("Attempting to transfer the pen");
            		// vv notice it is different
                	if (this.mergeItemStack(itemstack1, idxFirstPenSlot, idxFirstPenSlot + 1, false)) // if success
                	{
                		System.out.println("flag2 = false");
                		flag2 = false;
                
                	} // if not try to put it in the container
            	}
            	
            	if (flag2 && !this.mergeItemStack(itemstack1, tracker.slotCount(), idxFirstPenSlot, false)) // put it into the container
            	{
            		return ItemStack.EMPTY;
            
            	}

            }
            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
	}

	
    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);

        if (!this.world.isRemote)
        {
            this.clearContainer(playerIn, this.world, this.peninventory);
        }
    }
    public int getSlotFor(ItemStack stack)
    {
        NonNullList<ItemStack> items = this.inventoryItemStacks;
    	for (int i = 0; i < items.size(); ++i)
        {
            if (!((ItemStack)items.get(i)).isEmpty() && stackEqualExact(stack, items.get(i)))
            {
                return i;
            }
        }

        return -1;
    }
    public int fromPlayerGetSlotFor(InventoryPlayer ip, ItemStack stack) {
        NonNullList<ItemStack> items = ip.mainInventory;
    	for (int i = 0; i < items.size(); ++i)
        {
            if (!((ItemStack)items.get(i)).isEmpty() && stackEqualExact(stack, items.get(i)))
            {
                return i;
            }
        }

        return -1;
    }
    /**
     * Checks item, NBT, and meta if the item is not damageable
     */
    public static boolean stackEqualExact(ItemStack stack1, ItemStack stack2)
    {
        return stack1.getItem() == stack2.getItem() && (!stack1.getHasSubtypes() || stack1.getMetadata() == stack2.getMetadata()) && ItemStack.areItemStackTagsEqual(stack1, stack2);
    }
    /**
     * returns if something has been transfered
     * @param first
     * @param second
     * @return
     */
    public static boolean attemptTransfer(IInventory inv, int index, Slot second) {
    	if(second.getHasStack()) {
    		return false;
    	}
    	ItemStack stack = inv.getStackInSlot(index);
    	if(!stack.isEmpty()) {
    		second.putStack(stack);
    		inv.setInventorySlotContents(index, ItemStack.EMPTY);
    	}
    	return false;
    }

	

}
