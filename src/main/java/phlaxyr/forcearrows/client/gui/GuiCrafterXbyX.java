package phlaxyr.forcearrows.client.gui;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phlaxyr.forcearrows.crafting.ShapedRecipeXbyX;
import phlaxyr.forcearrows.inventory.ContainerCrafterXbyX;
import phlaxyr.forcearrows.machines.Template;
import phlaxyr.forcearrows.tile.TileCrafter;

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
