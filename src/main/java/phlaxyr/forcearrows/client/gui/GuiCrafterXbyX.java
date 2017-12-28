package phlaxyr.forcearrows.client.gui;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phlaxyr.forcearrows.inventory.ContainerCrafterXbyX;
import phlaxyr.forcearrows.machines.Machine;
import phlaxyr.forcearrows.tile.TileCrafter;

@SideOnly(Side.CLIENT)
public class GuiCrafterXbyX<T extends TileCrafter, C extends ContainerCrafterXbyX<T>> extends GuiCommon<T, C>{

	public GuiCrafterXbyX(int i, int j, C cont, ResourceLocation tex)
	{
		super(i, j, cont, tex);
	}
	
	public GuiCrafterXbyX(C cont, Machine<?,?>m) {
		super(cont, m);
	}
	
}
