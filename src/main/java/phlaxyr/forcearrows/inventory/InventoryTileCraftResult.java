package phlaxyr.forcearrows.inventory;


import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.item.ItemStack;
import phlaxyr.forcearrows.tile.TileCommon;

public class InventoryTileCraftResult extends InventoryCraftResult{
    private TileCommon craft;

    public InventoryTileCraftResult(TileCommon table){
        craft = table;
    }

    @Override
    public ItemStack getStackInSlot (int par1)
    {
        
    	return craft.getStackInSlot(0);
    }

    //triggers whenever slot is clicked
    //and whenever container.transferstackinslot is called
    @Override
    public ItemStack decrStackSize (int slot, int count)
    {
        System.out.println(slot);
    	ItemStack stack = craft.getStackInSlot(0);
        if (stack != null)
        {
            ItemStack itemstack = stack;
            craft.setInventorySlotContents(0, ItemStack.EMPTY);
            return itemstack;
        }
        else
        {
            return ItemStack.EMPTY;
        }
    }

    /*@Override
    public ItemStack getStackInSlotOnClosing (int par1)
    {
        return null;
    }*/

    @Override
    public void setInventorySlotContents (int par1, ItemStack par2ItemStack)
    {
        craft.setInventorySlotContents(0, par2ItemStack);
    }
}
