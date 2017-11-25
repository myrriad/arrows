package phlaxyr.forcearrows.codepatcher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.forcearrows.event.ArrowManager;

public class ProposedChangedContainerWorkbench extends ContainerWorkbench{

	private final World world;
	private final BlockPos pos;
	public ProposedChangedContainerWorkbench(InventoryPlayer playerInventory, World worldIn, BlockPos posIn)
	{
		super(playerInventory, worldIn, posIn);
		world = null;
		pos = null;
		// TODO Auto-generated constructor stub
	}
	public void onCraftMatrixChanged(IInventory inventoryIn) {
        // ArrowManager.onChange(this, world);
        // super.onCraftMatrixChanged(inventoryIn);
    }

    protected void slotChangedCraftingGrid(World worldIn, EntityPlayer playerIn, InventoryCrafting inv, InventoryCraftResult res)
    {
        if (!worldIn.isRemote)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)playerIn;
            ItemStack itemstack = ItemStack.EMPTY;
            IRecipe irecipe = CraftingManager.findMatchingRecipe(inv, worldIn); //
            //* patch start
            irecipe = ArrowManager.recipe(this, worldIn, inv, irecipe);
            // patch end */
            if (irecipe != null && (irecipe.isHidden() || !worldIn.getGameRules().getBoolean("doLimitedCrafting") || entityplayermp.getRecipeBook().containsRecipe(irecipe)))
            {
                res.setRecipeUsed(irecipe);
                itemstack = irecipe.getCraftingResult(inv);
            }

            res.setInventorySlotContents(0, itemstack);
            entityplayermp.connection.sendPacket(new SPacketSetSlot(this.windowId, 0, itemstack));
        }
    }
    
    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        
    	
    	ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        // Start patch
        if(ArrowManager.onCraftArrow(this, slot,  pos, world)) return ItemStack.EMPTY;
        // End patch
        
        
        
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 0)
            {
                
            	itemstack1.getItem().onCreated(itemstack1, this.world, playerIn);

                if (!this.mergeItemStack(itemstack1, 10, 46, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index >= 10 && index < 37)
            {
                if (!this.mergeItemStack(itemstack1, 37, 46, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (index >= 37 && index < 46)
            {
                if (!this.mergeItemStack(itemstack1, 10, 37, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 10, 46, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
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
}
