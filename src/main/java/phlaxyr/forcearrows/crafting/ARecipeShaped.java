package phlaxyr.forcearrows.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ARecipeShaped extends ShapedRecipes implements ICrafterExtendable{

	int gridWidth, gridHeight;
	
	public ARecipeShaped(int width, int height, NonNullList<Ingredient> ingredients, ItemStack result,
			int gridWidth, int gridHeight) {
		super("", width, height, ingredients, result);
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
	}

	/***
	 * Container Item is for buckets etc. when you craft with them
	 * This gets the buckets
	 * And also deletes items with weird meta-values
	 * 
	 */
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		NonNullList<ItemStack> remaining = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);
		for (int i = 0; i < remaining.size(); ++i) {
			// gets the stuff in the inventory
			ItemStack itemInInv = inv.getStackInSlot(i);
			// if it's something that should be left over, like a bucket
			remaining.set(i, net.minecraftforge.common.ForgeHooks.getContainerItem(itemInInv));
		}
		return remaining;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	public boolean matches(InventoryCrafting inv, World worldIn) {
		for (int i = 0; i <= gridWidth - this.recipeWidth; ++i) {
			for (int j = 0; j <= gridHeight - this.recipeHeight; ++j) {
				if (this.checkMatch(inv, i, j, true)) {
					return true;
				}
				if (this.checkMatch(inv, i, j, false)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if the region of a crafting inventory is match for the recipe.
	 */
	private boolean checkMatch(InventoryCrafting grid, int width, int height, boolean magicFlag) {
		for (int i = 0; i < gridWidth; ++i) {
			for (int j = 0; j < gridHeight; ++j) {
				int k = i - width;
				int l = j - height;
				Ingredient ingredient = Ingredient.EMPTY;

				if (k >= 0 && l >= 0 && k < this.recipeWidth && l < this.recipeHeight) {
					if (magicFlag) {
						ingredient = this.recipeItems.get(this.recipeWidth - k - 1 + l * this.recipeWidth);
					} else {
						ingredient = this.recipeItems.get(k + l * this.recipeWidth);
					}
				}

				if (!ingredient.apply(grid.getStackInRowAndColumn(i, j))) {
					return false;
				}
			}
		}

		return true;
	}
	/*
	public static ARecipeShaped deserialize(JsonObject jsonObject, int gridWidth, int gridHeight) {
		Map<String, Ingredient> map = deserializeKey(JsonUtils.getJsonObject(jsonObject, "key"));
		String[] astring = shrink(patternFromJson(
				JsonUtils.getJsonArray(jsonObject, "pattern"),
				gridWidth,
				gridHeight));
		int i = astring[0].length();
		int j = astring.length;
		NonNullList<Ingredient> nonnulllist = deserializeIngredients(astring, map, i, j);
		ItemStack itemstack = deserializeItem(JsonUtils.getJsonObject(jsonObject, "result"), true);
		return new ARecipeShaped(i, j, nonnulllist, itemstack, gridWidth, gridHeight);
	}

	//PRIVATE!
	private static NonNullList<Ingredient> deserializeIngredients(String[] p_192402_0_,
			Map<String, Ingredient> p_192402_1_, int p_192402_2_, int p_192402_3_) {
		NonNullList<Ingredient> nonnulllist = NonNullList.<Ingredient>withSize(p_192402_2_ * p_192402_3_,
				Ingredient.EMPTY);
		Set<String> set = Sets.newHashSet(p_192402_1_.keySet());
		set.remove(" ");

		for (int i = 0; i < p_192402_0_.length; ++i) {
			for (int j = 0; j < p_192402_0_[i].length(); ++j) {
				String s = p_192402_0_[i].substring(j, j + 1);
				Ingredient ingredient = p_192402_1_.get(s);

				if (ingredient == null) {
					throw new JsonSyntaxException(
							"Pattern references symbol '" + s + "' but it's not defined in the key");
				}

				set.remove(s);
				nonnulllist.set(j + p_192402_2_ * i, ingredient);
			}
		}

		if (!set.isEmpty()) {
			throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
		} else {
			return nonnulllist;
		}
	}

	
	private static String[] shrink(String... inputStrings) {
		int i = Integer.MAX_VALUE;
		int j = 0;
		int k = 0;
		int l = 0;

		for (int i1 = 0; i1 < inputStrings.length; ++i1) {
			String s = inputStrings[i1];
			i = Math.min(i, firstNonSpace(s));
			int j1 = lastNonSpace(s);
			j = Math.max(j, j1);
			// no spaces
			if (j1 < 0) {
				if (k == i1) {
					++k;
				}
				++l;
			} else {
				l = 0;
			}
		}

		if (inputStrings.length == l) {
			return new String[0];
		} else {
			String[] astring = new String[inputStrings.length - l - k];

			for (int k1 = 0; k1 < astring.length; ++k1) {
				astring[k1] = inputStrings[k1 + k].substring(i, j + 1);
			}
			return astring;
		}
	}
	private static String[] patternFromJson(JsonArray jsonArray, int gridWidth, int gridHeight) {
		String[] astring = new String[jsonArray.size()];
		if (astring.length > gridHeight) {
			throw new JsonSyntaxException("Invalid pattern: too many rows, "+ gridHeight + " is maximum");
		} else if (astring.length == 0) {
			throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
		} else {
			for (int i = 0; i < astring.length; ++i) {
				String s = JsonUtils.getString(jsonArray.get(i), "pattern[" + i + "]");
				if (s.length() > gridWidth) {
					throw new JsonSyntaxException("Invalid pattern: too many columns, "+ gridWidth +" is maximum");
				}
				if (i > 0 && astring[0].length() != s.length()) {
					throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
				}
				astring[i] = s;
			}
			return astring;
		}
	}
	
	private static Map<String, Ingredient> deserializeKey(JsonObject jsonObject) {
		Map<String, Ingredient> map = Maps.<String, Ingredient>newHashMap();
		for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
			if (((String) entry.getKey()).length() != 1) {
				throw new JsonSyntaxException("Invalid key entry: '" + (String) entry.getKey()
						+ "' is an invalid symbol (must be 1 character only).");
			}
			if (" ".equals(entry.getKey())) {
				throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
			}
			map.put(entry.getKey(), deserializeIngredient(entry.getValue()));
		}

		map.put(" ", Ingredient.EMPTY);
		return map;
	}
	private static int firstNonSpace(String str) {
		int i;
		for (i = 0; i < str.length() && str.charAt(i) == ' '; ++i) {
		}
		return i;
	}

	private static int lastNonSpace(String str) {
		int i;
		for (i = str.length() - 1; i >= 0 && str.charAt(i) == ' '; --i) {
		}
		return i;
	}
	*/

	@Override
	public int gridSlotWidth()
	{
		return gridWidth;
	}

	@Override
	public int gridSlotHeight()
	{
		return gridHeight;
	}
	
}