package phlaxyr.largetents.integration.jei;

import com.google.common.collect.Lists;


import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import phlaxyr.largetents.LargeTents;
import phlaxyr.largetents.crafting.Craft5by5Manager;
import phlaxyr.largetents.crafting.ShapedRecipe5by5;
import phlaxyr.largetents.integration.jei.crafter.JeiCrafter5by5Category;
import phlaxyr.largetents.integration.jei.crafter.JeiShapedRecipe5by5;
import phlaxyr.largetents.machines.TemplateRegistrar;

@JEIPlugin
public class TentsJeiPlugin implements IModPlugin{

	@Override
	public void registerCategories(IRecipeCategoryRegistration registrar){
		LargeTents.lumberjack.info("registered categories!");
		registrar.addRecipeCategories(
				new JeiCrafter5by5Category(registrar.getJeiHelpers().getGuiHelper()));
	}
	
	@Override
	public void register(IModRegistry registrar) {
		LargeTents.lumberjack.info("registered thingies");
		// registrar.handleRecipes(ShapedRecipeXbyX.class, JeiShapedRecipeXbyX::new, largetentsUid.CRAFTER5_5);
		registrar.handleRecipes(ShapedRecipe5by5.class, JeiShapedRecipe5by5::new, TentsUid.CRAFTER5_5);
		registrar.addRecipeCatalyst(new ItemStack(TemplateRegistrar.block_mWorkbench), TentsUid.CRAFTER5_5);
		
		registrar.addRecipes(Lists.newArrayList(Craft5by5Manager.getInstance().REGISTRY.iterator()), TentsUid.CRAFTER5_5);
	}
}
