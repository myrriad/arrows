package phlaxyr.arrows.registrars;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent.Register;

public class Registrar {
	public static void preInit() {
		
	}
	public static void init() {
		
	}
	public static void postInit() {
		
	}
	protected static void registerItem(Register<Item> event, Block staticBlock) {
		event.getRegistry().register(new ItemBlock(staticBlock).setRegistryName(staticBlock.getRegistryName())); // here
	}
	protected static void registerItem(Register<Item> event, Item newItem) {
		event.getRegistry().register(newItem);
	}
	protected static void registerBlock(Register<Block> event, Block newBlock) {
		event.getRegistry().register(newBlock);
	}

}
