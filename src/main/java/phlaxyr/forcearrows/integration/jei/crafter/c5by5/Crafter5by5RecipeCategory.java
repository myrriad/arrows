package phlaxyr.forcearrows.integration.jei.crafter.c5by5;

import mezz.jei.api.IGuiHelper;
import net.minecraft.util.ResourceLocation;
import phlaxyr.forcearrows.ForceArrows;
import phlaxyr.forcearrows.integration.jei.crafter.CrafterRecipeCategory;

public class Crafter5by5RecipeCategory extends CrafterRecipeCategory{


	public Crafter5by5RecipeCategory(IGuiHelper guiHelper)
	{
		super(guiHelper, new ResourceLocation(ForceArrows.MODID, "textures/gui/jei/craftfivefive.png"), 5, 5);
	}



	@Override
	public String getBlockName()
	{
		return "craftfivefive";
	}

}
