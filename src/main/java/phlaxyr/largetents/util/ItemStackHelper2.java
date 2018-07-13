package phlaxyr.largetents.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemStackHelper2 {
	public static ItemStack loadItem(NBTTagCompound tag, String target){
		NBTTagCompound resultNBT = tag.getCompoundTag(target);
        return new ItemStack(resultNBT);
	}
	public static NBTTagCompound saveItem(NBTTagCompound tag, String target, ItemStack stack){
		if (!stack.isEmpty()) {
			NBTTagCompound nbtsub = new NBTTagCompound();
			stack.writeToNBT(nbtsub);
			tag.setTag(target, nbtsub);
		} else {
			tag.removeTag(target);
		}
		return tag;
	}
	public static ItemStack getAndSplit(ItemStack itemstack, int count) {
		if(!itemstack.isEmpty() && count > 0)
			return itemstack.splitStack(count);
			else return ItemStack.EMPTY;
	}
	
}
