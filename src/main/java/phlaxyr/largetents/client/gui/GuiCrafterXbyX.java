package phlaxyr.largetents.client.gui;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phlaxyr.largetents.crafting.ShapedRecipeXbyX;
import phlaxyr.largetents.inventory.ContainerCrafterXbyX;
import phlaxyr.largetents.machines.Template;
import phlaxyr.largetents.tile.TileCrafter;

@SideOnly(Side.CLIENT)
public class GuiCrafterXbyX<T extends TileCrafter, R extends ShapedRecipeXbyX, C extends ContainerCrafterXbyX<T, R>> extends GuiCommon<T, C>{

	public GuiCrafterXbyX(int i, int j, C cont, ResourceLocation tex)
	{
		super(i, j, cont, tex);
	}
	
	public GuiCrafterXbyX(C cont, Template<?,?>m) {
		super(cont, m);
	}
	
}
