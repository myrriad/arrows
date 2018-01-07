package phlaxyr.forcearrows;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import phlaxyr.forcearrows.proxy.CommonProxy;

@Mod(modid = ForceArrows.MODID, name=ForceArrows.NAME, version = ForceArrows.VERSION)
public class ForceArrows
{
    public static final String MODID = "forcearrows";
    public static final String VERSION = "1.0";
    public static final String NAME = "Force Arrows";
    
    @Mod.Instance
    public static ForceArrows instance;
    
    @SidedProxy(clientSide="phlaxyr.forcearrows.proxy.ClientProxy",
    		serverSide="phlaxyr.forcearrows.proxy.ServerProxy")
    public static CommonProxy proxy;
    
    public static Logger lumberjack;
    static {
    	lumberjack = LogManager.getLogger(ForceArrows.MODID);
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
