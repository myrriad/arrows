package phlaxyr.forcearrows.integration.jei.crafter;

import java.util.List;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import phlaxyr.forcearrows.crafting.ARecipeShaped;

public class CrafterRecipeShapelessWrapper extends CrafterRecipeWrapper<ARecipeShaped> {

	public CrafterRecipeShapelessWrapper(ARecipeShaped recipe)
	{
		super(recipe);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IIngredients ingredients)
	{
		recipeLayout.setShapeless();
		
	}

	@Override
	public List<List<ItemStack>> getRecipeInputs(ARecipeShaped recipe)
	{
		for(Ingredient ingr :recipe.getIngredients() ) {
			ingr.getMatchingStacks();
		}
	
		return null;
	}

}
