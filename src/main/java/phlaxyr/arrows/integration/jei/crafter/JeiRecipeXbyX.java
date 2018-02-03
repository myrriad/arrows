package phlaxyr.arrows.integration.jei.crafter;

import java.util.List;

import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.wrapper.ICustomCraftingRecipeWrapper;
import net.minecraft.item.ItemStack;
import phlaxyr.arrows.crafting.IRecipeXbyX;

/**
 * Created by covers1624 on 31/07/2017.
 * Originally for Avaritia
 */
public abstract class JeiRecipeXbyX<T extends IRecipeXbyX> implements ICustomCraftingRecipeWrapper{
	
	
	private static final int craftOutputSlot = 0;
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IIngredients ingredients)
	{
		IGuiItemStackGroup items = recipeLayout.getItemStacks();
		items.set(craftOutputSlot, ingredients.getOutputs(ItemStack.class).get(0));
	}
	
	public int gridSlotWidth() {
		return recipe.gridSlotWidth();
	}
	public int getSlotHeight() {
		return recipe.gridSlotHeight();
	}
	
	private final T recipe;

	public JeiRecipeXbyX(T recipe) {
		this.recipe = recipe;
	}
	ICustomCraftingRecipeWrapper wrap;
	
	@Override
	public void getIngredients(IIngredients ingredients)
	{
		ingredients.setInputLists(ItemStack.class, getRecipeInputs(recipe));
		ingredients.setOutput(ItemStack.class, getRecipeOutputs(recipe));
	}

	public abstract List<List<ItemStack>> getRecipeInputs(T recipe);
	
	protected ItemStack getRecipeOutputs(T recipe) {
		return recipe.getRecipeOutput().copy(); // just to be safe
	}

	

}
