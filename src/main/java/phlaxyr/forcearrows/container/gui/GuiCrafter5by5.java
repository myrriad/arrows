package phlaxyr.forcearrows.container.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phlaxyr.forcearrows.container.ContainerCrafterExt;
import phlaxyr.forcearrows.crafting.manager.ManagerCraftCommon;
import phlaxyr.forcearrows.tile.TileCommon;
import phlaxyr.forcearrows.tile.TileCrafter;

@SideOnly(Side.CLIENT)
public class GuiCrafter5by5 extends GuiCommon
{
    private static final ResourceLocation tex = new ResourceLocation("forcearrows:textures/gui/m_workbench_gui.png");

    TileCrafter tile;
	public GuiCrafter5by5(int textureSizeX, int textureSizeY, InventoryPlayer ip, World worldIn, BlockPos pos, 
			TileCommon tile2, ManagerCraftCommon manager) {
		super(textureSizeX,textureSizeY,ip, worldIn, pos, tile2, manager,tex);
	}

    public GuiCrafter5by5(ContainerCrafterExt container, TileCrafter tile,
    		int textureSizeX, int textureSizeY) {
		super(textureSizeX,textureSizeY,container,tile,tex);
    	this.tile = tile;
    }
}

