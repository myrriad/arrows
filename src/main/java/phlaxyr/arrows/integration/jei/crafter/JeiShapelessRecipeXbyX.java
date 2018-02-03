package phlaxyr.arrows.integration.jei.crafter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import phlaxyr.arrows.crafting.ShapedRecipeXbyX;

public class JeiShapelessRecipeXbyX extends JeiRecipeXbyX<ShapedRecipeXbyX> {

	public JeiShapelessRecipeXbyX(ShapedRecipeXbyX recipe)
	{
		super(recipe);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IIngredients ingredients)
	{
		recipeLayout.setShapeless();
		
	}

	@Override
	public List<List<ItemStack>> getRecipeInputs(ShapedRecipeXbyX recipe)
	{
		List<List<ItemStack>> retn = new ArrayList<>();
		for(Ingredient ingr : recipe.getIngredients()) {
			retn.add(Arrays.asList(ingr.getMatchingStacks()));
		}
		
		return retn;
	}

}
