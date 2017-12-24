package phlaxyr.forcearrows.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

public class RecipeShaped5by5 extends ARecipeShaped{

	public RecipeShaped5by5(NonNullList<Ingredient> ingredients, ItemStack result, int gridWidth,
			int gridHeight)
	{
		super(5, 5, ingredients, result, gridWidth, gridHeight);
	}

}
