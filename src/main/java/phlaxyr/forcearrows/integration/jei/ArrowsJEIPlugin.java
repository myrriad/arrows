package phlaxyr.forcearrows.integration.jei;

import com.google.common.collect.Lists;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import phlaxyr.forcearrows.ForceArrows;
import phlaxyr.forcearrows.crafting.ARecipeShaped;
import phlaxyr.forcearrows.integration.jei.crafter.CrafterRecipeShapedWrapper;
import phlaxyr.forcearrows.integration.jei.crafter.c5by5.Crafter5by5RecipeCategory;
import phlaxyr.forcearrows.machines.crafter.c5by5.Manager5by5;

@JEIPlugin
public class ArrowsJEIPlugin implements IModPlugin{

	@Override
	public void registerCategories(IRecipeCategoryRegistration registrar){
		ForceArrows.lumberjack.info("registered categories!");
		registrar.addRecipeCategories(
				new Crafter5by5RecipeCategory(registrar.getJeiHelpers().getGuiHelper()));
	}
	
	@Override
	public void register(IModRegistry registrar) {
		ForceArrows.lumberjack.info("registered thingies");
		registrar.handleRecipes(ARecipeShaped.class, CrafterRecipeShapedWrapper::new, ArrowsUID.CRAFTER);
		
		registrar.addRecipes(Lists.newArrayList(Manager5by5.getInstance().REGISTRY.iterator()), ArrowsUID.CRAFTER);
	}
}
