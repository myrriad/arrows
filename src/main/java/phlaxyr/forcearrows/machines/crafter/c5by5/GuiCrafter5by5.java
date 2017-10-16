package phlaxyr.forcearrows.machines.crafter.c5by5;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phlaxyr.forcearrows.gui.GuiCommon;

@SideOnly(Side.CLIENT)
public class GuiCrafter5by5 extends GuiCommon<ContainerCrafter5by5, TileCrafter5by5>
{
    private static final ResourceLocation tex = new ResourceLocation("forcearrows:textures/gui/m_workbench_gui.png");
    
   // TileCommon tile;
    /*
    public GuiCrafter5by5(ContainerCrafter container, TileCommon tile,
    		int textureSizeX, int textureSizeY) {
		super(textureSizeX,textureSizeY,container,tex);
    	//this.tile = tile;
    }*/

	public GuiCrafter5by5(int i, int j, ContainerCrafter5by5 cont, TileCrafter5by5 tile)
	{
		super(i, j, cont, tex);
	}
}

