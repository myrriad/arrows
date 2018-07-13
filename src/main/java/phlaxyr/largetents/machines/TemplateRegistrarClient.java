package phlaxyr.largetents.machines;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phlaxyr.largetents.LargeTents;
import phlaxyr.largetents.client.gui.GuiHandlerRegistrar;

@SideOnly(Side.CLIENT)
public class TemplateRegistrarClient {
	public static void preInit() {
    	
    	NetworkRegistry.INSTANCE.registerGuiHandler(LargeTents.instance,GuiHandlerRegistrar.getInstance());
    	
    	for(ITemplateMachine<?,?> m : TemplateRegistrar.templates) {
    		registerGui(m);
    	}

	}
    public static void initModels() {

    	TemplateRegistrar.block_mWorkbench.initModel();
    }
    
	protected static void registerGui(ITemplateMachine<?, ?> m) { // templates are guihandlers! :D
		GuiHandlerRegistrar.getInstance().registerGuiHandler(m, m.getGuiID());			
	}
}
