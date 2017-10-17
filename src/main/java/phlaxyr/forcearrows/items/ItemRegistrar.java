package phlaxyr.forcearrows.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import phlaxyr.forcearrows.registrars.Registrar;

public class ItemRegistrar extends Registrar {
	@GameRegistry.ObjectHolder("forcearrows:mass_ingot")
	public static ItemCommon item_massIngot;
	
	@GameRegistry.ObjectHolder("forcearrows:mass_nugget")
	public static ItemCommon item_massNugget;
	
	@GameRegistry.ObjectHolder("forcearrows:energy_ingot")
	public static ItemCommon item_energyIngot;
	
	@GameRegistry.ObjectHolder("forcearrows:energy_nugget")
	public static ItemCommon item_energyNugget;
	
	@GameRegistry.ObjectHolder("forcearrows:crafting_arrow")
	public static ItemCommon item_craftingArrow;

	
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
	}

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        item_massIngot.initModel();
        item_massNugget.initModel();
        item_energyIngot.initModel();
        item_energyNugget.initModel();
        item_craftingArrow.initModel();
    }
	
	public static void init() {
		OreDictionary.registerOre("ingotMass", item_massIngot);
		OreDictionary.registerOre("nuggetMass", item_massNugget);
		
		OreDictionary.registerOre("ingotEnergy", item_massIngot);
		OreDictionary.registerOre("nuggetEnergy", item_energyNugget);
	}
}
