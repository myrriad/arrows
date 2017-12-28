package phlaxyr.forcearrows.crafting;

import net.minecraft.item.ItemStack;
import phlaxyr.forcearrows.items.ItemRegistrar;

public class Craft5by5Manager extends CraftXbyXManager {

	
	private static Craft5by5Manager INSTANCE = new Craft5by5Manager();
	
	
	public static Craft5by5Manager getInstance() {
		return INSTANCE;
	}
	
	private Craft5by5Manager() {
		super(5, 5);

	}	
	public void preInit(){}public void postInit(){} // filler
	
	
	
	public void init() {
		this.oldAddRecipe(new ItemStack(ItemRegistrar.item_massIngot),"mass_via_nugget", new Object[]{
					".....",
					".NNN.",
					"ENNNE",
					".NNN.",
					".....",
					
	                  'E', ItemRegistrar.item_energyNugget,
	                  'N', ItemRegistrar.item_massNugget// note carefully - 'E' not "E" !
			});
		this.oldAddRecipe(new ItemStack(ItemRegistrar.item_energyIngot), "energy_via_nugget", new Object[]{
					".....",
					".EEE.",
					"MEEEM",
					".EEE.",
					".....",
					
					'M', ItemRegistrar.item_massNugget,
					'E', ItemRegistrar.item_energyNugget
		});
		}
}
