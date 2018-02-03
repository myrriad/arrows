package phlaxyr.arrows.inventory;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import phlaxyr.arrows.tile.TileCommon;


/**
 * 
 * "repurposed" from avaritia's dire crafting table
 * */
public class InventoryTileCrafting extends InventoryCrafting {

	private TileCommon tile;
	private Container container;

	
	//remember that slot is between 0 and gridsize-1, whereas the container index is between 1 and gridSize!
	public InventoryTileCrafting(Container cont, int x, int y, TileCommon table){
	        super(cont, x, y);
	        tile = table;
	        container = cont;
	        
	    }

	@Override
	public ItemStack getStackInSlot(int slot /*0-24 incl*/) {

		
		return slot >= this.getSizeInventory() ? ItemStack.EMPTY : tile.getStackInSlot(slot+1);
	}

	@Override
	public ItemStack getStackInRowAndColumn(int x, int y) {
		if (x >= 0 && x < this.getWidth() && y >=0 && y < this.getHeight()) {
			int intx = x + y * this.getWidth();
			return this.getStackInSlot(intx);
		} else {
			return ItemStack.EMPTY;
		}
	}

	/*
	 * @Override public ItemStack getStackInSlotOnClosing (int par1) { return
	 * ItemStack.EMPTY; }
	 */

	
	
	@Override
	public ItemStack decrStackSize(int slot, int decrement) {
		ItemStack stack = tile.getStackInSlot(slot+1);
		if (!stack.isEmpty()) {
			ItemStack itemstack;
			if (stack.getCount() <= decrement) {
				itemstack = stack.copy();
				stack = ItemStack.EMPTY;
				this.setInventorySlotContents(slot, ItemStack.EMPTY);
				
				return itemstack;
			} else {
				itemstack = stack.splitStack(decrement);
				if (stack.getCount() == 0) {
					stack = ItemStack.EMPTY;
                    this.setInventorySlotContents(slot+1, ItemStack.EMPTY);
				}
				this.container.onCraftMatrixChanged(this);
				return itemstack;
			}
		} else {
			return ItemStack.EMPTY;
		}
	}

	//TODO NO PROBLEMO -- PROBABLY
	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		//Remember -- this is the grid
		
		tile.setInventorySlotContents(slot+1, itemstack);
		this.container.onCraftMatrixChanged(this);
	}

}
