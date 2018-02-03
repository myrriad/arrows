package phlaxyr.arrows;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import phlaxyr.arrows.proxy.CommonProxy;

@Mod(modid = Arrows.MODID, name=Arrows.NAME, version = Arrows.VERSION)
public class Arrows
{
    public static final String MODID = "arrows";
    public static final String VERSION = "1.0";
    public static final String NAME = "Force Arrows";
    
    @Mod.Instance
    public static Arrows instance;
    
    @SidedProxy(clientSide="phlaxyr.arrows.proxy.ClientProxy",
    		serverSide="phlaxyr.arrows.proxy.ServerProxy")
    public static CommonProxy proxy;
    
    public static Logger lumberjack;
    static {
    	lumberjack = LogManager.getLogger(Arrows.MODID);
    	lumberjack.info("Testing logger!");
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	//lumberjack = event.getModLog();
    	
    	proxy.preInit();
    }
    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	proxy.postInit();
    }
    public static String prependModID(String name) {
    	return MODID+":"+name;
    }
}
