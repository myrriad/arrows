package phlaxyr.largetents.tile;

import java.util.Arrays;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class SlotTracker {
	
	NonNullList<ItemStack> items;
	int[] internal;
	public void initSlotItems(int... iarray) {
		this.internal = iarray.clone();
		items = NonNullList.withSize(slotCount(), ItemStack.EMPTY);
		
	}
	public int[] toIntArray() {
		return internal.clone();
	}
	public int slotCount() {
		return internal.length / 2;
	}
	public int itemCount() {
		return items.size();
	}
	public int getSlotX(int i) {
		return internal[i * 2];
	}
	public int getSlotY(int i) {
		return internal[i * 2 + 1];
	}
	public void setSlotX(int i, int x) {
		internal[i * 2] = x;
		System.out.println("set slot x" + i + " , " + x);
	}
	public void setSlotY(int i, int y) {
		internal[i * 2 + 1] = y;
		System.out.println("set slot y" + i + " , " + y);
	}
	public void resizeTo(int newsize) {
    	// int[] newarr = new int[newsize * 2];
    	internal = Arrays.copyOf(internal, newsize * 2);
    	// System.arraycopy(internal, 0, newarr, 0, internal.length);
    	padItemsToSize(newsize);
	}
		
		// System.out.println("Slotpos is " + Arrays.toString(st.internal));
	
	
	private void padItemsToSize(int size) {
		for(int i=items.size(); i < size; i ++ ) {
			items.add(ItemStack.EMPTY); // pad with empty
		}
	}
}
