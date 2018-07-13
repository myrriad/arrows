package phlaxyr.largetents.integration.jei.crafter;

import mezz.jei.api.IGuiHelper;
import net.minecraft.util.ResourceLocation;
import phlaxyr.largetents.LargeTents;

public class JeiCrafter5by5Category extends JeiCrafterXbyXCategory{


	public JeiCrafter5by5Category(IGuiHelper guiHelper)
	{
		super(guiHelper, new ResourceLocation(LargeTents.MODID, "textures/gui/jei/craftfivefive.png"), 5, 5);
	}



	@Override
	public String getBlockName()
	{
		return "craftfivefive";
	}

}
