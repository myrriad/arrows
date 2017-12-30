package phlaxyr.forcearrows.craftingarrow;

import net.minecraft.block.Block;


import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.forcearrows.crafting.CraftXbyXManager;
// import phlaxyr.forcearrows.event.ArrowShearRenderer;
import phlaxyr.forcearrows.items.ItemRegistrar;
// import phlaxyr.forcearrows.util.NullableDelegate;

public class ArrowManager {
	// public static final Delegate renderer = new Delegate();
	// public static class Delegate extends NullableDelegate<ArrowShearRenderer>{}
	public static IRecipe arrow_recipe;
	
	
	
	public static final CraftXbyXManager CUSTOM_MANAGER = new CraftXbyXManager(3, 3){};
	public static void init() {
		
		arrow_recipe = 
		CUSTOM_MANAGER.oldAddRecipe(new ItemStack(ItemRegistrar.item_triggerer_no_get),"trigger_shears", new Object[]{
		        "EB.",
		        "BES",
		        "EB.",
				
                  'E', ItemRegistrar.item_energyIngot,
                  'B', Items.BLAZE_POWDER,
                  'S', ItemRegistrar.item_mass_shears// note carefully - 'E' not "E" !
		});
		// System.out.print(CUSTOM_MANAGER.REGISTRY);
		
		
	}
	/**
	 * COREMOD should make this so that this triggers when the ctable is changed
	 * @param res 
	 * @param inv 
	 * @param playerIn 
	 * @param worldIn 
	 * 
	 * @param ct
	 */
	// @SuppressWarnings("unused")
//	public static void onChange(ContainerWorkbench ct, World world) {
//		// long starttime = System.nanoTime();
//		//*
//		// if(false) return;
//		//*/
//		if(ct == null) {
//			System.out.println("gui isnull");
//			return;
//		}
//		if(world == null) {
//			System.out.println("world is null");
//			return;
//		}
//		InventoryCrafting mx = ct.craftMatrix;
//		System.out.println(mx.getSizeInventory());
//		System.out.println(recipe.getIngredients().size());
//		for(int i=0;i<recipe.getIngredients().size();i++){
//			Ingredient ingr = recipe.getIngredients().get(i);
//			for(ItemStack match : ingr.getMatchingStacks()) {
//				System.out.println(i+"RECIPE:"+match.getDisplayName());	
//			}
//
//			System.out.println(i+"REAL:"+mx.getStackInSlot(i).getDisplayName());
//			
//		}
//		
//        IRecipe irecipe = CUSTOM_MANAGER.findMatchingRecipe(mx, world);
//        // System.out.println(System.nanoTime() - starttime);
//        if(irecipe == null) {
//        	System.out.println("NOT A MATCH");
//        	return;
//        }
//        System.out.println("MATCH!");
//        // this ^ is normal: it just means that we haven't crafted a arrow
//        
//        // && (irecipe.isHidden() || !p_192389_1_.getGameRules().getBoolean("doLimitedCrafting") || entityplayermp.getRecipeBook().containsRecipe(irecipe)))
//
//		InventoryCraftResult rs = ct.craftResult;
//		rs.setRecipeUsed(irecipe);
//		ItemStack itemstack = irecipe.getCraftingResult(mx);
//		rs.setInventorySlotContents(0, itemstack);
//
//		// no need to send packet - the normal crafting table does this already
//            // entityplayermp.connection.sendPacket(new SPacketSetSlot(this.windowId, 0, itemstack));
//        
//		
//		
//	}
	/**
	 * Returns whether or not the stack will craft an arrow
	 * @param stack
	 * @return
	 */
	
	public static boolean onCraftArrow(ContainerWorkbench cw, BlockPos bp, World w) {
		// System.out.println("oncraftarrow");
		if(ItemStack.areItemsEqual(cw.craftResult.getStackInSlot(0), arrow_recipe.getRecipeOutput())) {
			if(cw.getClass().equals(ContainerWorkbench.class)) { // make sure it's not a subclass
				if(Block.isEqualTo(w.getBlockState(bp).getBlock(), Blocks.CRAFTING_TABLE)) {
					// success, all the conditions pass
					
					cw.craftMatrix.decrStackSize(0, 1);
					cw.craftMatrix.decrStackSize(1, 1);
					cw.craftMatrix.decrStackSize(3, 1);
					cw.craftMatrix.decrStackSize(4, 1);
					cw.craftMatrix.decrStackSize(6, 1);
					cw.craftMatrix.decrStackSize(7, 1);
					cw.detectAndSendChanges();
					// renderer.obj.addShear(1);
					return true;
				}
			}
			
		}
		return false;
	}
	
	public static ItemStack checkCustomRecipe(InventoryCrafting inv, World world, ItemStack in) {
		if(arrow_recipe.matches(inv, world)) return arrow_recipe.getRecipeOutput();
		return in;
	}
	
	
	
	
	/*
	public static IRecipe recipe(Container container, World worldIn, InventoryCrafting inv, IRecipe recipe) {
		if(recipe == null) {// only if there's not a recipe
			if(arrow_recipe.matches(inv, worldIn)) { // if it matches
				if(container.getClass().equals(ContainerWorkbench.class)) { // make sure that the container is a containerworkbench, and not some random crafter
					/*
					ContainerWorkbench cw = (ContainerWorkbench) container;
					
					
					try {
						Field f = cw.getClass().getDeclaredField(PatchTransformer.obfuscated ? "" : "pos");
						f.setAccessible(true);
						BlockPos pos = (BlockPos) f.get(cw);
						
					} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
							| SecurityException e) {
						
						e.printStackTrace();
						return recipe;
					}*//*
					
					
					ContainerWorkbench cw = (ContainerWorkbench) container;
					cw.craftResult.setInventorySlotContents(0, arrow_recipe.getRecipeOutput());
				}
			}
		}
		return recipe;
	}*/
}
