package phlaxyr.forcearrows.machines.transformer;

import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import phlaxyr.forcearrows.ForceArrows;
import phlaxyr.forcearrows.tile.TileCommon;
import phlaxyr.forcearrows.util.ItemStackHelper2;

public class TileTransformer extends TileCommon {

	NonNullList<ItemStack> items;
	ItemStack toInfuse;
	ItemStack result;

	public static final int RESULT = 0;
	public static final int INFUSE = 1;
	public static final int TRANSLATE = 2;
	public static final int ROTATE = 3;
	public static final int REFLECT = 4;
	public static final int DILATE = 5;
	
	public TileTransformer(String name)
	{
		super(name);
		// TODO Auto-generated constructor stub
	}

    public void readFromNBT(NBTTagCompound tag)
    {
/*
        NBTTagCompound resultNBT = tag.getCompoundTag("Result");
        result = new ItemStack(resultNBT);
  */
    	result = ItemStackHelper2.loadItem(tag, "Result");
    	toInfuse = ItemStackHelper2.loadItem(tag, "Infuse");
        
        ItemStackHelper.loadAllItems(tag, items);
        markDirty();
    }
    
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		/*
		if (!result.isEmpty()) {
			NBTTagCompound nbtResult = new NBTTagCompound();
			result.writeToNBT(nbtResult);
			tag.setTag("Result", nbtResult);
		} else {
			tag.removeTag("Result");
		}*/
		ItemStackHelper2.saveItem(tag, "Result", result);
		ItemStackHelper2.saveItem(tag, "Infuse", toInfuse);
		
		ItemStackHelper.saveAllItems(tag, items);
		return tag;
	}
	@Override
	public int getSizeInventory()
	{
		return TRANSLATE + items.size();
	}

	@Override
	public boolean isEmpty()
	{
		if(!result.isEmpty()) return false;
		if(!toInfuse.isEmpty()) return false;
		for (ItemStack itemstack : this.items) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		if(index == RESULT) {
			return result;
		}
		if(index == INFUSE) {
			return toInfuse;
		}
		
		
		return items.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		markDirty();
		if(index == RESULT) {
			return ItemStackHelper2.getAndSplit(result, count);
		}
		if(index == INFUSE) {
			return ItemStackHelper2.getAndSplit(result, count);
		}
		return ItemStackHelper.getAndSplit(this.items, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		markDirty();
		ItemStack retn;
		if(index == RESULT) {
			retn = result;
			result = ItemStack.EMPTY;
			return retn;
		}
		if(index == INFUSE) {
			retn = toInfuse;
			toInfuse = ItemStack.EMPTY;
			return retn;
		}
		return ItemStackHelper.getAndRemove(this.items, index - TRANSLATE);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		markDirty();
		if(index == RESULT) {
			result = stack;
		} else if(index == INFUSE) {
			toInfuse = stack;
		} else {
			items.set(index - TRANSLATE, stack);		
		
		}

		// over the limit
		if (!stack.isEmpty() && stack.getCount() > getInventoryStackLimit()) {									
			stack.setCount(this.getInventoryStackLimit());
		}

	}

	@Override
	public void clear()
	{
		result = ItemStack.EMPTY;
		toInfuse = ItemStack.EMPTY;
		
		items.clear();
		markDirty();

	}

}
