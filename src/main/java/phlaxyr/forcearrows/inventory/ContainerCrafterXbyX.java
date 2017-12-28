package phlaxyr.forcearrows.inventory;

import javax.annotation.Nullable;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.forcearrows.ForceArrows;
import phlaxyr.forcearrows.crafting.CraftXbyXManager;
import phlaxyr.forcearrows.tile.TileCrafter;
public abstract class ContainerCrafterXbyX<T extends TileCrafter> extends ContainerCommon<T>
{
	/** COPY-PASTA'D */
    /** The crafting matrix inventory, except adjustable. */
    private InventoryTileCrafting craftMatrix; 

    private IInventory craftResult;
    
    private final CraftXbyXManager MANAGER;
    
    /**measured at the upper left corner (inside the black border)*/
    public abstract int resultX();
    /**measured at the upper left corner (inside the black border)*/
    public abstract int resultY();
    /**measured at the upper left corner (inside the black border)*/
    public abstract int gridX();
    /**measured at the upper left corner (inside the black border)*/
    public abstract int gridY();
    
    public final int gridSlotLengthX;
    public final int gridSlotLengthY;
    
    /*
    public ContainerCrafterXbyX(InventoryPlayer playerInventory, World worldIn, 
    		BlockPos posIn, TileCommon tile, CraftXbyXManager manager, 
    		int gridX, int gridY)
    {
    	super(playerInventory, worldIn, posIn, tile);
    	MANAGER = manager;
    	craftResult = new InventoryTileCraftResult(tile);
    	craftMatrix = new InventoryTileCrafting(this, gridX, gridY, tile);
        
        //crafting output
        this.addSlotToContainer(new SlotCrafter(MANAGER, playerInventory.player, this.craftMatrix, this.craftResult,
        		0, resultX()+4, resultY()+4));
        
        //crafting grid
		for (int i = 0; i < gridX; ++i) {
			for (int j = 0; j < gridY; ++j) {
				this.addSlotToContainer(new Slot(this.craftMatrix, j + i * gridY,

						gridX() + j * 18, gridY() + i * 18));
			}
		}
        
        super.addInventories();
        this.onCraftMatrixChanged(this.craftMatrix);
    }*/
    /***
     * Required in clump: constructor args, manager
     * 
     * @param clump
     * @param gridX
     * @param gridY
     */
    public ContainerCrafterXbyX(InventoryPlayer inv, T tile, World world, BlockPos pos, CraftXbyXManager manager, int gridX, int gridY) {
    	super(inv, tile, world, pos);
        MANAGER = manager;
        
        gridSlotLengthX = gridX;
        gridSlotLengthY = gridY;
        /*
    	int gridX = gridSlotLengthX;
    	int gridY = gridSlotLengthY;
    	*/
    	craftResult = new InventoryTileCraftResult(this.getTile());
    	craftMatrix = new InventoryTileCrafting(this, gridX, gridY, this.getTile());


    	//crafting output
        this.addSlotToContainer(new SlotCrafter(MANAGER, this.player, 
        		this.craftMatrix, this.craftResult,
        		0, resultX()+4, resultY()+4));
        
        //crafting grid
		for (int i = 0; i < gridY; ++i) { //# of rows
			for (int j = 0; j < gridX; ++j) { //# of slots in each row
				this.addSlotToContainer(new Slot(this.craftMatrix, j + i * gridX,
						
						gridX() + j * 18, gridY() + i * 18));
				ForceArrows.lumberjack.debug("Added a slot");;
			}
		}
        addInventories();
        this.onCraftMatrixChanged(this.craftMatrix);
    	
    }
    
    
    protected void addSlots(){
    	// this should always work
    	int gridX = gridSlotLengthX;
    	int gridY = gridSlotLengthY;
    	craftResult = new InventoryTileCraftResult(this.getTile());
    	craftMatrix = new InventoryTileCrafting(this, gridX, gridY, this.getTile());


    	//crafting output
        this.addSlotToContainer(new SlotCrafter(MANAGER, this.player, 
        		this.craftMatrix, this.craftResult,
        		0, resultX()+4, resultY()+4));
        
        //crafting grid
		for (int i = 0; i < gridX; ++i) { //# of rows
			for (int j = 0; j < gridY; ++j) { //# of slots in each row
				this.addSlotToContainer(new Slot(this.craftMatrix, j + i * gridX,

						gridX() + j * 18, gridY() + i * 18));
			}
		}
    }
    
    
    //
    
    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the playerInv
     * inventory and the other inventory(s).
     */
    @Nullable
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
    	ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(index);
        
        int slotOffset = (craftMatrix.getWidth()*craftMatrix.getHeight())+1;
        
        if (slot != null && slot.getHasStack())
        {
            ItemStack slotstack = slot.getStack();
            itemstack = slotstack.copy();
            
            // if it's the result
            if (index == 0)
            {
				// it's been made!
				slotstack.getItem().onCreated(slotstack, this.world, playerIn);

				if (!this.mergeItemStack(slotstack, slotOffset, slotOffset + 36, true)) { // shift
																							// crafting
																							// output
																							// into
																							// inventory
					return ItemStack.EMPTY;
				}

				// if only some have been shifted, notify everything that
				// something has been crafted
				slot.onSlotChange(slotstack, itemstack);
            }
            else if (index >= slotOffset && index < slotOffset+27) //main, excluding hotbar
            {
                if (!this.mergeItemStack(slotstack, 1, slotOffset-1, false)) 
                	//shift from main inventory into crafting body
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (index >= slotOffset+27 && index < slotOffset+36) //hotbar
            {
                if (!this.mergeItemStack(slotstack, 1, slotOffset-1, false))
                	//shift from hotbar into crafting body
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(slotstack, slotOffset, slotOffset+36, false))	
            {
                //shifts from crafting body to main inventory
            	return ItemStack.EMPTY;
            }

            
            
            if (slotstack.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
                //putstack triggers Inventory.setinventoryslotcontents
                //which triggers Tile.setinventoryslotcontents
            }
            else
            {
                slot.onSlotChanged();
            }

            if (slotstack.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            ItemStack itemstack2 = slot.onTake(playerIn, slotstack);
            
            if (index == 0)
            {
                playerIn.dropItem(itemstack2, false);
            }
        }

        return itemstack;
    }

    public void onCraftMatrixChanged(IInventory inventoryIn)
    {   
    	
    	super.onCraftMatrixChanged(inventoryIn);
    	
    	ItemStack i = MANAGER.findMatchingResult(this.craftMatrix, this.world);
  //  	craftResult.setRecipeUsed(irecipe);
    	this.craftResult.setInventorySlotContents(0, i);
		if (!this.world.isRemote)
        {
			EntityPlayerMP entityplayermp = (EntityPlayerMP) this.player;
			entityplayermp.connection.sendPacket(new SPacketSetSlot(this.windowId, 0, i));
        }
    }
    public boolean canMergeSlot(ItemStack stack, Slot slotIn)
    {
        return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
    }
}