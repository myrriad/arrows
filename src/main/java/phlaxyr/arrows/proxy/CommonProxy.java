package phlaxyr.arrows.proxy;


import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import phlaxyr.arrows.Arrows;
import phlaxyr.arrows.blocks.BlockRegistrar;
import phlaxyr.arrows.crafting.Craft5by5Manager;
import phlaxyr.arrows.craftingarrow.ArrowManager;
import phlaxyr.arrows.craftingarrow.PacketHandler;
import phlaxyr.arrows.items.ItemRegistrar;
import phlaxyr.arrows.machines.TemplateRegistrar;

@Mod.EventBusSubscriber
public abstract class CommonProxy {
	
	public void preInit() {
		// test for crashs
    	// net.minecraft.client.gui.GuiScreen testclient = new net.minecraft.client.gui.GuiScreen(){};
		// good, it crashes
		
		BlockRegistrar.preInit();
		ItemRegistrar.preInit();

		// TileRegistrar.preInit();
		TemplateRegistrar.preInit();
		
		PacketHandler.preinit(Arrows.MODID);
		
	}
	public void init() {
		BlockRegistrar.init();
		ItemRegistrar.init();
		
		// TileRegistrar.init();
		TemplateRegistrar.preInit();
		
		Craft5by5Manager.getInstance().init();
		ArrowManager.init();
		MinecraftForge.EVENT_BUS.register(new ArrowManager.EventHandlerCommon());

	}
	public void postInit() {
		BlockRegistrar.postInit();
		ItemRegistrar.postInit();

		// TileRegistrar.postInit();
		TemplateRegistrar.postInit();
	}
	@SubscribeEvent
	public static void registerBlocks(Register<Block> event) {
		BlockRegistrar.registerBlocks(event);
		TemplateRegistrar.registerBlocks(event);
	}
	@SubscribeEvent
	public static void registerItems(Register<Item> event) {
		ItemRegistrar.registerItems(event);
		BlockRegistrar.registerItems(event);
		TemplateRegistrar.registerItems(event);
	}

	abstract public boolean playerIsInCreativeMode(EntityPlayer player);

	abstract public boolean isDedicatedServer();
}
