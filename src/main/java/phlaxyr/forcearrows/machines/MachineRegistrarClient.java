package phlaxyr.forcearrows.machines;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phlaxyr.forcearrows.ForceArrows;
import phlaxyr.forcearrows.client.gui.GuiHandlerRegistrar;

@SideOnly(Side.CLIENT)
public class MachineRegistrarClient {
	public static void preInit() {
    	// FIRST AND FOREMOST
    	NetworkRegistry.INSTANCE.registerGuiHandler(ForceArrows.instance,GuiHandlerRegistrar.getInstance());
    	
    	for(Machine<?,?> m : MachineRegistrar.machines) {
    		registerGui(m);
    	}

	}
    public static void initModels() {

    	MachineRegistrar.block_mWorkbench.initModel();
    }
    
	protected static void registerGui(Machine<?,?> guihandler) { // machines are guihandlers! :D
		GuiHandlerRegistrar.getInstance().registerGuiHandler(guihandler, guihandler.getGuiID());			
	}
}
