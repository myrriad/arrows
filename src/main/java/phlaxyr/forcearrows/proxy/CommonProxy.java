package phlaxyr.forcearrows.proxy;


import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import phlaxyr.forcearrows.blocks.BlockRegistrar;
import phlaxyr.forcearrows.crafting.Craft5by5Manager;
import phlaxyr.forcearrows.craftingarrow.ArrowManager;
import phlaxyr.forcearrows.items.ItemRegistrar;
import phlaxyr.forcearrows.machines.MachineRegistrar;

@Mod.EventBusSubscriber
public abstract class CommonProxy {
	
	public void preInit() {
		// test for crashs
    	// net.minecraft.client.gui.GuiScreen testclient = new net.minecraft.client.gui.GuiScreen(){};
		// good, it crashes
		
		BlockRegistrar.preInit();
		ItemRegistrar.preInit();

		// TileRegistrar.preInit();
		MachineRegistrar.preInit();
		
	}
	public void init() {
		BlockRegistrar.init();
		ItemRegistrar.init();
		
		// TileRegistrar.init();
		MachineRegistrar.preInit();
		
		Craft5by5Manager.getInstance().init();
		ArrowManager.init();

	}
	public void postInit() {
		BlockRegistrar.postInit();
		ItemRegistrar.postInit();

		// TileRegistrar.postInit();
		MachineRegistrar.postInit();
	}
	@SubscribeEvent
	public static void registerBlocks(Register<Block> event) {
		BlockRegistrar.registerBlocks(event);
		MachineRegistrar.registerBlocks(event);
	}
	@SubscribeEvent
	public static void registerItems(Register<Item> event) {
		ItemRegistrar.registerItems(event);
		BlockRegistrar.registerItems(event);
		MachineRegistrar.registerItems(event);
	}

	abstract public boolean playerIsInCreativeMode(EntityPlayer player);

	abstract public boolean isDedicatedServer();
}
