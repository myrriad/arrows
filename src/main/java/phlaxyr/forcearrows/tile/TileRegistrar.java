package phlaxyr.forcearrows.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import phlaxyr.forcearrows.ForceArrows;
import phlaxyr.forcearrows.gui.GuiHandlerRegistrar;
import phlaxyr.forcearrows.machines.crafter.c5by5.GuiHandler5by5;
import phlaxyr.forcearrows.machines.crafter.c5by5.TileCrafter5by5;
import phlaxyr.forcearrows.registrars.Registrar;


public class TileRegistrar extends Registrar{
	public static void preInit() {
		registerAll(TileCrafter5by5.class, "m_workbench", new GuiHandler5by5());
	}


	private static void registerAll(Class<? extends TileEntity> tileclass, String tileID, GuiHandler5by5 gui) {
		registerTile(tileclass, tileID);	
		GuiHandlerRegistrar.getInstance().registerGuiHandler(gui, gui.GUI_ID);			
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static void registerTile(Class<? extends TileEntity> tileclass, String tileID){
		GameRegistry.registerTileEntity(tileclass, tileID);
		NetworkRegistry.INSTANCE.registerGuiHandler(ForceArrows.instance,GuiHandlerRegistrar.getInstance());
	}
}
