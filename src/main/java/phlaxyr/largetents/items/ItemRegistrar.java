package phlaxyr.largetents.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import phlaxyr.largetents.LargeTents;
import phlaxyr.largetents.registrars.Registrar;

public class ItemRegistrar extends Registrar {
	
	static final String prefix = LargeTents.MODID + ":";
	
	@GameRegistry.ObjectHolder(prefix + "mass_ingot")
	public static ItemCommon item_massIngot;
	
	@GameRegistry.ObjectHolder(prefix + "mass_nugget")
	public static ItemCommon item_massNugget;
	
	@GameRegistry.ObjectHolder(prefix + "energy_ingot")
	public static ItemCommon item_energyIngot;
	
	@GameRegistry.ObjectHolder(prefix + "energy_nugget")
	public static ItemCommon item_energyNugget;
	
	@GameRegistry.ObjectHolder(prefix + "crafting_arrow")
	public static ItemCommon item_craftingArrow;

	@GameRegistry.ObjectHolder(prefix + "triggerer_no_get")
	public static ItemCommon item_triggerer_no_get;
	
	@GameRegistry.ObjectHolder(prefix + "mass_shears")
	public static ItemShears item_mass_shears;
	
	@GameRegistry.ObjectHolder(prefix + "tent")
	public static ItemTent item_tent;

	@GameRegistry.ObjectHolder(prefix + "edit_pen")
	public static ItemCommon item_edit_pen;
	
	public static void preInit() {
	}
	
	public static void registerItems(Register<Item> event) {
		registerItem(event, new ItemCommon()
				.setUnlocalizedName("mass_ingot")
				.setRegistryName("mass_ingot"));
		registerItem(event, new ItemCommon()
				.setUnlocalizedName("mass_nugget")
				.setRegistryName("mass_nugget"));
		
		registerItem(event, new ItemCommon()
				.setUnlocalizedName("energy_nugget")
				.setRegistryName("energy_nugget"));
		registerItem(event, new ItemCommon()
				.setUnlocalizedName("energy_ingot")
				.setRegistryName("energy_ingot"));

		registerItem(event, new ItemCommon()
				.setUnlocalizedName("crafting_arrow")
				.setRegistryName("crafting_arrow"));
		registerItem(event, new ItemTriggerShears()//ArrowManager.renderer)
				.setUnlocalizedName("triggerer_no_get")
				.setRegistryName("triggerer_no_get"));
		
		ItemShears mass_shears = new ItemShears();
		
		mass_shears.setMaxDamage(2400);
		
		mass_shears.setCreativeTab(CreativeTabs.TOOLS);
		registerItem(event, mass_shears
				.setUnlocalizedName("mass_shears")
				.setRegistryName("mass_shears"));
		registerItem(event, new ItemTent()//ArrowManager.renderer)
				.setUnlocalizedName("tent")
				.setRegistryName("tent"));
		
		registerItem(event, new ItemCommon()
				.setUnlocalizedName("edit_pen")
				.setRegistryName("edit_pen")
				.setMaxStackSize(1));
	}

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        item_massIngot.initModel();
        item_massNugget.initModel();
        item_energyIngot.initModel();
        item_energyNugget.initModel();
        item_craftingArrow.initModel();
        item_triggerer_no_get.initModel();
		ModelLoader.setCustomModelResourceLocation(
				item_mass_shears, 0, new ModelResourceLocation(item_mass_shears.getRegistryName(), "inventory"));
    	item_tent.initModel();
    	item_edit_pen.initModel();
    }
	
	public static void init() {
		OreDictionary.registerOre("ingotMass", item_massIngot);
		OreDictionary.registerOre("nuggetMass", item_massNugget);
		
		OreDictionary.registerOre("ingotEnergy", item_massIngot);
		OreDictionary.registerOre("nuggetEnergy", item_energyNugget);
	}
}
