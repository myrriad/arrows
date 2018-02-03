package phlaxyr.arrows.items;

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
import phlaxyr.arrows.Arrows;
import phlaxyr.arrows.registrars.Registrar;

public class ItemRegistrar extends Registrar {
	@GameRegistry.ObjectHolder(Arrows.MODID + ":mass_ingot")
	public static ItemCommon item_massIngot;
	
	@GameRegistry.ObjectHolder(Arrows.MODID + ":mass_nugget")
	public static ItemCommon item_massNugget;
	
	@GameRegistry.ObjectHolder(Arrows.MODID + ":energy_ingot")
	public static ItemCommon item_energyIngot;
	
	@GameRegistry.ObjectHolder(Arrows.MODID + ":energy_nugget")
	public static ItemCommon item_energyNugget;
	
	@GameRegistry.ObjectHolder(Arrows.MODID + ":crafting_arrow")
	public static ItemCommon item_craftingArrow;

	@GameRegistry.ObjectHolder(Arrows.MODID + ":triggerer_no_get")
	public static ItemCommon item_triggerer_no_get;
	
	@GameRegistry.ObjectHolder(Arrows.MODID + ":mass_shears")
	public static ItemShears item_mass_shears;
	
	public static void preInit() {
		}
	
	public static void registerItems(Register<Item> event) {
		registerItem(event, new ItemCommon(CreativeTabs.MATERIALS)
				.setUnlocalizedName("mass_ingot")
				.setRegistryName("mass_ingot"));
		registerItem(event, new ItemCommon(CreativeTabs.MATERIALS)
				.setUnlocalizedName("mass_nugget")
				.setRegistryName("mass_nugget"));
		
		registerItem(event, new ItemCommon(CreativeTabs.MATERIALS)
				.setUnlocalizedName("energy_nugget")
				.setRegistryName("energy_nugget"));
		registerItem(event, new ItemCommon(CreativeTabs.MATERIALS)
				.setUnlocalizedName("energy_ingot")
				.setRegistryName("energy_ingot"));

		registerItem(event, new ItemCommon(CreativeTabs.MATERIALS)
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
    	
    }
	
	public static void init() {
		OreDictionary.registerOre("ingotMass", item_massIngot);
		OreDictionary.registerOre("nuggetMass", item_massNugget);
		
		OreDictionary.registerOre("ingotEnergy", item_massIngot);
		OreDictionary.registerOre("nuggetEnergy", item_energyNugget);
	}
}
