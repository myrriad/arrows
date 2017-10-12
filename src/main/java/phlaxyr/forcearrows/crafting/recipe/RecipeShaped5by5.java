package phlaxyr.forcearrows.crafting.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

public class RecipeShaped5by5 extends RecipeShapedExt{

	public RecipeShaped5by5(int width, int height, NonNullList<Ingredient> ingredients, ItemStack result) {
		super(width, height, ingredients, result, 5, 5);
	}
}
