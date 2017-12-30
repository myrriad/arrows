package phlaxyr.forcearrows.integration.jei.crafter;

import mezz.jei.api.IGuiHelper;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.wrapper.ICustomCraftingRecipeWrapper;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import phlaxyr.forcearrows.ForceArrows;


// see https://github.com/mezz/JustEnoughItems/blob/1.12/src/main/java/mezz/jei/plugins/vanilla/crafting/CraftingRecipeCategory.java
// mostly written by the jei team
public abstract class JeiCrafterXbyXCategory implements IRecipeCategory<IRecipeWrapper>{
	public static final int craftOutputSlot = 0;
	public static final int craftInputSlot1 = 1;

	// public static final int width = 116;
	// public static final int height = 54;

	public static final int width = 148;
	public static final int height = 90;
	private final IDrawable background;
	private final String title;
	private final int gridX;
	private final int gridY;
	
	
	
	public JeiCrafterXbyXCategory(IGuiHelper guiHelper, ResourceLocation gui, int gridSlotsWidth, int gridSlotsHeight) {
		ResourceLocation location = gui;
		background = guiHelper.createDrawable(location, 0, 0, width, height);
		title = I18n.format("jei." + ForceArrows.MODID + "." + getBlockName());
		this.gridX = gridSlotsWidth;
		this.gridY = gridSlotsHeight;
	}
	
	


	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getModName() {
		return ForceArrows.NAME;
	}

	public abstract String getBlockName(); // ie. craftingTable
	
	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public String getUid()
	{
		return ForceArrows.MODID + ":" + getBlockName();
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		// TODO adjust this
		guiItemStacks.init(craftOutputSlot, false, /*94, 18*/ 127, 36);

		int index = craftInputSlot1;
		for (int y = 0; y < gridY; ++y) {
			for (int x = 0; x < gridX; ++x) {
				// int index = craftInputSlot1 + x + (y * gridX); // current rows * number of slots in a row
				guiItemStacks.init(index++, true, x * 18, y * 18);
			}
		}
		

		// List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		// List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);

		if (recipeWrapper instanceof ICustomCraftingRecipeWrapper) {
			ICustomCraftingRecipeWrapper customWrapper = (ICustomCraftingRecipeWrapper) recipeWrapper;
			customWrapper.setRecipe(recipeLayout, ingredients);
			return;
		}
		// guiItemStacks.set(craftOutputSlot, outputs.get(0));
		
/*
		if (recipeWrapper instanceof IShapedCraftingRecipeWrapper) {
			IShapedCraftingRecipeWrapper wrapper = (IShapedCraftingRecipeWrapper) recipeWrapper;
			craftingGridHelper.setInputs(guiItemStacks, inputs, wrapper.getWidth(), wrapper.getHeight());
		} else {
			craftingGridHelper.setInputs(guiItemStacks, inputs);
			recipeLayout.setShapeless();
		}
		guiItemStacks.set(craftOutputSlot, outputs.get(0));
		*/
		
	}
}
