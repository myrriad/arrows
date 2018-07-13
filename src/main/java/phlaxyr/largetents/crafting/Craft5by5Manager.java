package phlaxyr.largetents.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import phlaxyr.largetents.items.ItemRegistrar;

public class Craft5by5Manager extends CraftXbyXManager<ShapedRecipe5by5> {

	
	private static Craft5by5Manager INSTANCE = new Craft5by5Manager();
	
	
	public static Craft5by5Manager getInstance() {
		return INSTANCE;
	}
	
	private Craft5by5Manager() {
		// super(5, 5);

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

	@Override
	public ShapedRecipe5by5 getNewRecipe(int width, int height, NonNullList<Ingredient> ingredients, ItemStack result) {
		return new ShapedRecipe5by5(ingredients, result, width, height);
	}
}
