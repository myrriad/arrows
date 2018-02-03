package phlaxyr.arrows.machines;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phlaxyr.arrows.Arrows;
import phlaxyr.arrows.client.gui.GuiHandlerRegistrar;

@SideOnly(Side.CLIENT)
public class TemplateRegistrarClient {
	public static void preInit() {
    	// FIRST AND FOREMOST
    	NetworkRegistry.INSTANCE.registerGuiHandler(Arrows.instance,GuiHandlerRegistrar.getInstance());
    	
    	for(Template<?,?> m : TemplateRegistrar.templates) {
    		registerGui(m);
    	}

	}
    public static void initModels() {

    	TemplateRegistrar.block_mWorkbench.initModel();
    }
    
	protected static void registerGui(Template<?,?> guihandler) { // templates are guihandlers! :D
		GuiHandlerRegistrar.getInstance().registerGuiHandler(guihandler, guihandler.getGuiID());			
	}
}
