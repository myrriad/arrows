package phlaxyr.forcearrows.machines.crafter.c5by5;

import net.minecraft.item.ItemStack;
import phlaxyr.forcearrows.crafting.ManagerCrafting;
import phlaxyr.forcearrows.items.ItemRegistrar;

public class Manager5by5 extends ManagerCrafting {

	
	private static Manager5by5 INSTANCE = new Manager5by5();
	
	
	public static Manager5by5 getInstance() {
		return INSTANCE;
	}
	
	private Manager5by5() {
		super(5, 5);

	}	
	public void init() {
		this.oldAddRecipe(new ItemStack(ItemRegistrar.item_massIngot),"formaelis_via_nugget", new Object[]{
					"..E..",
					".NNN.",
					".NNN.",
					".NNN.",
					"E...E",
					
	                  'E', ItemRegistrar.item_energyNugget,
	                  'N', ItemRegistrar.item_massNugget// note carefully - 'E' not "E" !
			});
		}
}
