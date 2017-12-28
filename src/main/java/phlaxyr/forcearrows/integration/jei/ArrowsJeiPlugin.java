package phlaxyr.forcearrows.integration.jei;

import com.google.common.collect.Lists;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import phlaxyr.forcearrows.ForceArrows;
import phlaxyr.forcearrows.crafting.Craft5by5Manager;
import phlaxyr.forcearrows.crafting.ShapedRecipeXbyX;
import phlaxyr.forcearrows.integration.jei.crafter.JeiCrafter5by5Category;
import phlaxyr.forcearrows.integration.jei.crafter.JeiShapedRecipeXbyX;

@JEIPlugin
public class ArrowsJeiPlugin implements IModPlugin{

	@Override
	public void registerCategories(IRecipeCategoryRegistration registrar){
		ForceArrows.lumberjack.info("registered categories!");
		registrar.addRecipeCategories(
				new JeiCrafter5by5Category(registrar.getJeiHelpers().getGuiHelper()));
	}
	
	@Override
	public void register(IModRegistry registrar) {
		ForceArrows.lumberjack.info("registered thingies");
		registrar.handleRecipes(ShapedRecipeXbyX.class, JeiShapedRecipeXbyX::new, ArrowsUid.CRAFTER);
		
		registrar.addRecipes(Lists.newArrayList(Craft5by5Manager.getInstance().REGISTRY.iterator()), ArrowsUid.CRAFTER);
	}
}
