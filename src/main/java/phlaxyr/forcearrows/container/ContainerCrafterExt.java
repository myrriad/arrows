package phlaxyr.forcearrows.container;

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
import phlaxyr.forcearrows.crafting.manager.ManagerCraftCommon;
import phlaxyr.forcearrows.inventory.InventoryTileCraftResult;
import phlaxyr.forcearrows.inventory.InventoryTileCrafting;
import phlaxyr.forcearrows.inventory.SlotCrafter;
import phlaxyr.forcearrows.tile.TileCommon;
import phlaxyr.forcearrows.util.Argbus;

import static phlaxyr.forcearrows.util.ArgbusMappings.*;
public abstract class ContainerCrafterExt extends ContainerCommon
{
	/** COPY-PASTA'D */
    /** The crafting matrix inventory, except adjustable. */
    private InventoryTileCrafting craftMatrix; 

    public IInventory craftResult;
    
    private final ManagerCraftCommon MANAGER;
    
    /**measured at the upper left corner (inside the black border)*/
    public abstract int resultX();
    /**measured at the upper left corner (inside the black border)*/
    public abstract int resultY();
    /**measured at the upper left corner (inside the black border)*/
    public abstract int gridX();
    /**measured at the upper left corner (inside the black border)*/
    public abstract int gridY();
    
    public ContainerCrafterExt(InventoryPlayer playerInventory, World worldIn, 
    		BlockPos posIn, TileCommon tile, ManagerCraftCommon manager, 
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
        
        //main inventory
        for (int k = 0; k < 3; ++k) //rows
        {
            for (int i1 = 0; i1 < 9; ++i1) //slots in each row
            {
                this.addSlotToContainer(new Slot(playerInventory, /*slot in row*/i1 + /*row*/k * 9 + /*hotbar slots*/9, 
                		
                		bodyX() + i1 * 18, bodyY() + k * 18));
            }
        }

        //hotbar
        for (int l = 0; l < 9; ++l)
        {
            this.addSlotToContainer(new Slot(playerInventory, l, hotbarX() + l * 18, hotbarY()));
        }

        this.onCraftMatrixChanged(this.craftMatrix);
    }
    

    
    //
    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
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
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            
            
            if (index == 0)
            {
                itemstack1.getItem().onCreated(itemstack1, this.world, playerIn);
            	
            	if (!this.mergeItemStack(itemstack1, slotOffset, slotOffset+36, true))
            	{	//shift crafting output into inventory
                    return ItemStack.EMPTY;
                }

                //if only some have been shifted, notify everything that something has been crafted
                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index >= slotOffset && index < slotOffset+27) //main, excluding hotbar
            {
                if (!this.mergeItemStack(itemstack1, 1, slotOffset-1, false)) 
                	//shift from main inventory into crafting body
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (index >= slotOffset+27 && index < slotOffset+36) //hotbar
            {
                if (!this.mergeItemStack(itemstack1, 1, slotOffset-1, false))
                	//shift from hotbar into crafting body
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, slotOffset, slotOffset+36, false))	
            {
                //shifts from crafting body to main inventory
            	return ItemStack.EMPTY;
            }

            
            
            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
                //putstack triggers Inventory.setinventoryslotcontents
                //which triggers Tile.setinventoryslotcontents
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);
            
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