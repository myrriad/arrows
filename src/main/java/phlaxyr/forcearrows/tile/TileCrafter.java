package phlaxyr.forcearrows.tile;

import javax.annotation.Nullable;

import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import phlaxyr.forcearrows.util.ItemStackHelper2;

public class TileCrafter extends TileCommon{

	ItemStack result;
	NonNullList<ItemStack> items;

	public TileCrafter(int rows, int columns, String name) {
		super(name);
		items = NonNullList.withSize(rows * columns, ItemStack.EMPTY);
		clear();
	}
	
	/* <--STANDARD SH-->T */


	/* <--STANDARD SH-->T */
	public int getSizeInventory() {
		return items.size() + 1;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		// System.out.println("getStackInSlot "+index);
		if (index == 0) {
			return result;
		}
		return items.get(index - 1);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		System.out.println(index);
		markDirty();
		if (index == 0) {

			// if there is a crafting recipe available
			if (!result.isEmpty()) {

				/*
				 * //decrease everything in crafting grid for(int x = 1;x <=
				 * items.length;x++){ decrStackSize(x, 1); } //
				 */

				if (result.getCount() <= count) {
					// moves result into return value
					ItemStack craft = result;
					result = ItemStack.EMPTY;
					return craft;
				}
				ItemStack split = result.splitStack(count);
				if (result.getCount() <= 0)
					result = ItemStack.EMPTY;
				return split;
			} else
				return ItemStack.EMPTY;
		}

		return ItemStackHelper.getAndSplit(this.items, index - 1, count);
	}

	/***
	 * One single click, not a shift click.
	 */
	@Nullable
	public ItemStack removeStackFromSlot(int index) {
		System.out.println(index);
		if (index == 0) {
			if (!result.isEmpty()) {
				for (int x = 1; x <= items.size(); x++) {
					// since this is triggered by one click, this works
					decrStackSize(x, 1);
				}

				ItemStack craft = result;
				result = ItemStack.EMPTY;
				markDirty();
				return craft;

			} else
				return ItemStack.EMPTY;
		}
		markDirty();
		return ItemStackHelper.getAndRemove(this.items, index - 1);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index == 0) {
			result = stack;
		} else {
			items.set(index - 1, stack);
			// over the limit
			if (!stack.isEmpty() && stack.getCount() > getInventoryStackLimit()) {
																					
				stack.setCount(this.getInventoryStackLimit());
			}
		}
		markDirty();
	}

	/* <--NBT SH-->T */
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		
		//load item from nbt
		/*
		NBTTagCompound resultNBT = tag.getCompoundTag("Result");
        result = new ItemStack(resultNBT);
        */
		result = ItemStackHelper2.loadItem(tag, "Result");
        ItemStackHelper.loadAllItems(tag, items);
        markDirty();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		// result
		/*if (!result.isEmpty()) {
			NBTTagCompound nbtResult = new NBTTagCompound();
			result.writeToNBT(nbtResult);
			tag.setTag("Result", nbtResult);
		} else {
			tag.removeTag("Result");
		}*/
		ItemStackHelper2.saveItem(tag, "result", result);
		ItemStackHelper.saveAllItems(tag, items);
		return tag;
	}
	/* <--NBT SH-->T */
	
	public void clear()
	{
		result = ItemStack.EMPTY;
		items.clear();
		markDirty();
	}
	
	public boolean isEmpty() {
		for (ItemStack itemstack : this.items) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

}
