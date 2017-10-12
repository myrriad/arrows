package phlaxyr.forcearrows.crafting.manager;

import net.minecraft.item.ItemStack;
import phlaxyr.forcearrows.items.ItemRegistrar;

public class Manager5by5 extends ManagerCraftCommon {

	
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
