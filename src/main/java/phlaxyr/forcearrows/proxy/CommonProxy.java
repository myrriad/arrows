package phlaxyr.forcearrows.proxy;


import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import phlaxyr.forcearrows.blocks.BlockRegistrar;
import phlaxyr.forcearrows.items.ItemRegistrar;
import phlaxyr.forcearrows.machines.crafter.c5by5.Manager5by5;
import phlaxyr.forcearrows.registrars.TileRegistrar;

@Mod.EventBusSubscriber
public abstract class CommonProxy {
	
	public void preInit() {
		BlockRegistrar.preInit();
		ItemRegistrar.preInit();

		TileRegistrar.preInit();
	}
	public void init() {
		BlockRegistrar.init();
		ItemRegistrar.init();
		TileRegistrar.init();
		
		Manager5by5.getInstance().init();
	}
	public void postInit() {
		BlockRegistrar.postInit();
		ItemRegistrar.postInit();

		TileRegistrar.postInit();
	}
	@SubscribeEvent
	public static void registerBlocks(Register<Block> event) {
		BlockRegistrar.registerBlocks(event);
	}
	@SubscribeEvent
	public static void registerItems(Register<Item> event) {
		ItemRegistrar.registerItems(event);
		BlockRegistrar.registerItems(event);
	}

	abstract public boolean playerIsInCreativeMode(EntityPlayer player);

	abstract public boolean isDedicatedServer();
}
