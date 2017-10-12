package phlaxyr.forcearrows.tile;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import phlaxyr.forcearrows.ForceArrows;
import phlaxyr.forcearrows.Registrar;
import phlaxyr.forcearrows.container.gui.GuiHandler5by5;
import phlaxyr.forcearrows.container.gui.GuiHandlerCommon;
import phlaxyr.forcearrows.container.gui.GuiHandlerRegistry;


public class TileRegistrar extends Registrar{
	public static void preInit() {
		GameRegistry.registerTileEntity(TileCrafter5by5.class, "m_workbench");
		NetworkRegistry.INSTANCE.registerGuiHandler(ForceArrows.instance,GuiHandlerRegistry.getInstance());
		registerTile(new GuiHandler5by5());
	}
	public static void registerTile(GuiHandlerCommon gui) {
				GuiHandlerRegistry.getInstance().registerGuiHandler(gui, gui.GUI_ID);			
	}
	
}
