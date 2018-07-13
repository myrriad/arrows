package phlaxyr.largetents.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

public class ShapedRecipe5by5 extends ShapedRecipeXbyX{

	public ShapedRecipe5by5(NonNullList<Ingredient> ingredients, ItemStack result, int gridWidth,
			int gridHeight)
	{
		super(5, 5, ingredients, result, gridWidth, gridHeight);
	}

}
