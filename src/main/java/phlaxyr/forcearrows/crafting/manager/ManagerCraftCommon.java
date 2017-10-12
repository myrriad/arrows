package phlaxyr.forcearrows.crafting.manager;

import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.world.World;
import phlaxyr.forcearrows.crafting.recipe.RecipeShapedExt;


public abstract class ManagerCraftCommon {
	/** A list of all the recipes added */
	public static final RegistryNamespaced<ResourceLocation, IRecipe> REGISTRY = new RegistryNamespaced<>();

	private static int nextAvailableId = 0;
	int gridWidth, gridHeight;
	public static ManagerCraftCommon getInstance() {
		throw new IllegalStateException("Hide this static method (?)");
	}
	
	protected ManagerCraftCommon(int width, int height) {
		gridWidth = width;
		gridHeight = height;
	}

    /**
     * Adds a shaped recipe to the games recipe list.
     * By mojang and/or forge
     */
    public ShapedRecipes oldAddRecipe(ItemStack stack, String recipeRegistryName, Object... recipeComponents)
    {
        String s = ""; //concatenated version of the strings in the actual recipe
        int i = 0; // index in the object array
        int j = 0; // number of items in a row (ie. 3 for a piston) (ie. 3 for a fence)
        int k = 0; // number of rows in a recipe (ie. 3 for a piston) (ie. 2 for a fence)

        //you can have a seperate string array in the object array
        if (recipeComponents[i] instanceof String[])
        {
        	String[] astring = (String[])recipeComponents[i];
            i++;

            for (String s2 : astring)
            {
                ++k;
                j = s2.length();
                s += s2;
            }
        }
        else
        {
        	while (recipeComponents[i] instanceof String)
            {
                String s1 = (String)recipeComponents[i];
                i++;
                ++k;
                j = s1.length();
                s += s1;
            }
        }

        Map<Character, Ingredient> map;

        for (map = Maps.<Character, Ingredient>newHashMap(); i < recipeComponents.length; i += 2)
        {
            Character character = (Character)recipeComponents[i];
            Ingredient ingredient = Ingredient.EMPTY;

            if (recipeComponents[i + 1] instanceof Item)
            {
            	ingredient = Ingredient.fromItem(((Item)recipeComponents[i + 1]));
            }
            else if (recipeComponents[i + 1] instanceof Block)
            {
            	ingredient = Ingredient.fromStacks(
                			new ItemStack((Block)recipeComponents[i + 1], 1, 32767)
                		);
            }
            else if (recipeComponents[i + 1] instanceof ItemStack)
            {
            	ingredient = Ingredient.fromStacks(
                		(ItemStack)recipeComponents[i + 1]
                				);
            } else {
            	try {
            		if(recipeComponents[i + 1] == null) {
            			System.out.println("recipeComp is null!");
					} else {
						Class<?> c = recipeComponents[i + 1].getClass();
						System.out.print(c.getSimpleName());
					}
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
            }
            
            map.put(character, ingredient);
        }
        //items * rows
        NonNullList<Ingredient> nnlIngr = NonNullList.create();

        //number of items in a recipe
        for (int l = 0; l < j * k; ++l)
        {
            char c0 = s.charAt(l);
            
            if (map.containsKey(Character.valueOf(c0)))
            {
                nnlIngr.add( 
                		map.get(Character.valueOf(c0))
                		);
            } else {
            	nnlIngr.add(Ingredient.EMPTY);
            }

        }

        System.err.println("WIDTH: "+j);
        System.err.println("HEIGHT: "+k);
        for(Ingredient ingr:nnlIngr) {
        	for(ItemStack testIStack:ingr.getMatchingStacks()) {
        		System.err.println("INGREDIENT: "+testIStack.getDisplayName());
        	}
        }
        System.err.println("RESULT: "+stack.getDisplayName());
        
        ShapedRecipes shapedrecipes = new RecipeShapedExt(j, k, nnlIngr, stack, gridWidth, gridHeight);
        register(recipeRegistryName,shapedrecipes);
        return shapedrecipes;
    }
    
    
    
    /**
     * Adds a shapeless crafting recipe to the the game.
     */
    public void addShapelessRecipe(String recipeRegistryName, ItemStack stack, Object... recipeComponents)
    {
        NonNullList<Ingredient> list = NonNullList.create();

        for (Object object : recipeComponents)
        {
            if (object instanceof ItemStack)
            {
                list.add(Ingredient.fromStacks((ItemStack)object));
            }
            else if (object instanceof Item)
            {
                list.add(Ingredient.fromItem((Item)object));
            }
            else
            {
                if (!(object instanceof Block))
                {
                    throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + object.getClass().getName() + "!");
                }

                list.add(Ingredient.fromStacks(new ItemStack((Block)object)));
            }
        }

        register(recipeRegistryName, new ShapelessRecipes("",stack, list));
    }
	public static void register(String name, IRecipe recipe) {
		register(new ResourceLocation(name), recipe);
	}

	public static void register(ResourceLocation name, IRecipe recipe) {
		if (REGISTRY.containsKey(name)) {
			throw new IllegalStateException("Duplicate recipe ignored with ID " + name);
		} else {
			REGISTRY.register(nextAvailableId++, name, recipe);
		}
	}

	/**
	 * Retrieves an ItemStack that has multiple recipes for it.
	 */
	public ItemStack findMatchingResult(InventoryCrafting matrix, World world) {
		for (IRecipe irecipe : REGISTRY) {
			if (irecipe.matches(matrix, world)) {
				return irecipe.getCraftingResult(matrix);
			}
		}

		return ItemStack.EMPTY;
	}

	/**
	 * Retrieves an ItemStack that has multiple recipes for it.
	 */
	@Nullable
	public IRecipe findMatchingRecipe(InventoryCrafting craftMatrix, World worldIn) {
		for (IRecipe irecipe : REGISTRY) {
			if (irecipe.matches(craftMatrix, worldIn)) {
				return irecipe;
			}
		}

		return null;
	}

	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting craftMatrix, World worldIn) {
		for (IRecipe irecipe : REGISTRY) {
			if (irecipe.matches(craftMatrix, worldIn)) {
				return irecipe.getRemainingItems(craftMatrix);
			}
		}

		NonNullList<ItemStack> aitemstack = NonNullList.<ItemStack>withSize(craftMatrix.getSizeInventory(),
				ItemStack.EMPTY);

		for (int i = 0; i < aitemstack.size(); ++i) {
			aitemstack.set(i, craftMatrix.getStackInSlot(i));
		}

		return aitemstack;
	}

	@Nullable
	public static IRecipe getRecipe(ResourceLocation name) {
		return REGISTRY.getObject(name);
	}
}