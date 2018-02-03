package phlaxyr.arrows.crafting;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;

@Deprecated
/***
 * Not in use
 *
 */
public abstract class ICraftingRecipe implements IRecipe{
	public abstract NonNullList<ItemStack> craft(InventoryCrafting crafting, int ct);
	
	
	public int amountCraftable(InventoryCrafting crafting, InventoryPlayer playerinv) {
		ItemStack result = this.getCraftingResult(crafting);
		int shouldcraft;
		
		
		int ingrid = matchesAmount(crafting);
		int maxspacecrafts = getMaxAmtToCraftByInventorySpace(playerinv);
		if(ingrid > maxspacecrafts) { //not enough space
			shouldcraft = maxspacecrafts;
		} else {
			shouldcraft = ingrid;
		}
		return (int)(shouldcraft / result.getCount());
	}
	/**Number of times that it matches*/
	public abstract int matchesAmount(InventoryCrafting crafting);
	
	protected int amtInventorySpace(InventoryPlayer playerinv) {
		ItemStack result = this.getRecipeOutput();
		int inventoryspace = 0;
		for(ItemStack i : playerinv.mainInventory) {
			if(i.isEmpty()) {
				inventoryspace += result.getMaxStackSize();
				break;
			}
			if(ItemStack.areItemsEqual(i, result)) {
				inventoryspace += (i.getMaxStackSize() - i.getCount());
				break;
			}
		}
		return inventoryspace;
	}
	protected int getMaxAmtToCraftByInventorySpace(InventoryPlayer invplayer) {
		return (int) (amtInventorySpace(invplayer) / this.getRecipeOutput().copy().getCount());
	}
	
	public NonNullList<ItemStack> onceClick(InventoryCrafting crafting) {
		return craft(crafting, 1);
	}
	
	
	public void shiftClickCraftAll(InventoryCrafting crafting, InventoryPlayer playerinv) {
		NonNullList<ItemStack> amount = craft(crafting, amountCraftable(crafting, playerinv));
		moveIntoInventory(amount, playerinv);
	}
	public void moveIntoInventory(NonNullList<ItemStack> items, InventoryPlayer playerinv) {
		
	}
}
