package phlaxyr.forcearrows.crafting;

import net.minecraft.item.crafting.IRecipe;

public interface ICrafterExtendable extends IRecipe{
	public int gridSlotWidth();
	public int gridSlotHeight();
}
