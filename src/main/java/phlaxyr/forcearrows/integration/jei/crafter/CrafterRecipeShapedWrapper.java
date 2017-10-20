package phlaxyr.forcearrows.integration.jei.crafter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import phlaxyr.forcearrows.crafting.ARecipeShaped;

public class CrafterRecipeShapedWrapper extends CrafterRecipeWrapper<ARecipeShaped> {

	private static int craftOutputSlot = 0;
	private static int craftInputSlot1 = 1;
	public CrafterRecipeShapedWrapper(ARecipeShaped recipe)
	{
		super(recipe);
	}

	
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IIngredients ingredients)
	{

		super.setRecipe(recipeLayout, ingredients);
		IGuiItemStackGroup items = recipeLayout.getItemStacks();
		int c = craftInputSlot1;
		for(List<ItemStack> ingr : ingredients.getInputs(ItemStack.class)) {
			items.set(c++, ingr);
			
		}
	}

	@Override
	public List<List<ItemStack>> getRecipeInputs(ARecipeShaped recipe)
	{
		List<List<ItemStack>> retn = new ArrayList<>();
		for(Ingredient ingr : recipe.getIngredients()) {
			retn.add(Arrays.asList(ingr.getMatchingStacks()));
		}
		
		return retn;
	}
	

}
