package phlaxyr.arrows.integration.jei;

import com.google.common.collect.Lists;


import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import phlaxyr.arrows.Arrows;
import phlaxyr.arrows.crafting.Craft5by5Manager;
import phlaxyr.arrows.crafting.ShapedRecipe5by5;
import phlaxyr.arrows.integration.jei.crafter.JeiCrafter5by5Category;
import phlaxyr.arrows.integration.jei.crafter.JeiShapedRecipe5by5;
import phlaxyr.arrows.machines.TemplateRegistrar;

@JEIPlugin
public class ArrowsJeiPlugin implements IModPlugin{

	@Override
	public void registerCategories(IRecipeCategoryRegistration registrar){
		Arrows.lumberjack.info("registered categories!");
		registrar.addRecipeCategories(
				new JeiCrafter5by5Category(registrar.getJeiHelpers().getGuiHelper()));
	}
	
	@Override
	public void register(IModRegistry registrar) {
		Arrows.lumberjack.info("registered thingies");
		// registrar.handleRecipes(ShapedRecipeXbyX.class, JeiShapedRecipeXbyX::new, ArrowsUid.CRAFTER5_5);
		registrar.handleRecipes(ShapedRecipe5by5.class, JeiShapedRecipe5by5::new, ArrowsUid.CRAFTER5_5);
		registrar.addRecipeCatalyst(new ItemStack(TemplateRegistrar.block_mWorkbench), ArrowsUid.CRAFTER5_5);
		
		registrar.addRecipes(Lists.newArrayList(Craft5by5Manager.getInstance().REGISTRY.iterator()), ArrowsUid.CRAFTER5_5);
	}
}
