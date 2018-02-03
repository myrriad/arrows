package phlaxyr.arrows.client.gui;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phlaxyr.arrows.crafting.ShapedRecipeXbyX;
import phlaxyr.arrows.inventory.ContainerCrafterXbyX;
import phlaxyr.arrows.machines.Template;
import phlaxyr.arrows.tile.TileCrafter;

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
