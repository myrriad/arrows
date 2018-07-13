package phlaxyr.largetents.tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import phlaxyr.largetents.inventory.IPlayerSlotTracker;
import phlaxyr.largetents.inventory.PlayerSlotTracker;
import phlaxyr.largetents.machines.TemplateRegistrar;
import phlaxyr.largetents.machines.guieditor.TemplateGuiEditor;
import phlaxyr.largetents.util.ISlotTracker;
import phlaxyr.largetents.util.Misc;

public class TileGuiEditor extends TileCommon implements ISlotTracker{
	
	// private static ArrayList<Integer> testVals = Misc.toArrayList(18, 18, 18, 36);
	// SlotTracker st;
	
	// ##start of iitemhandler move##
	IItemHandler handler = new ItemStackHandler();
	// ##end##
	
	public PlayerSlotTracker ptracker;
	private static int[] testVals = {18, 18, 18, 36};
	public int xSize, ySize;
//	public int xPInv, yPInv;
//	public int xHotbar, yHotbar;
//	{
//		xPInv = yPInv = -1;
//		xHotbar = yHotbar = -1;
//	}
	// private int[] slotpos; // = new // ArrayList<Integer>(); // probably doesn't have to be a list
	
	/**
	 * If a tile entity, then the `poss` will be overridden
	 */
	public TileGuiEditor() {
		this(testVals);
		
	}
	
	public TileGuiEditor(@Nullable int[] slotpos) {
		this(TemplateRegistrar.machine_guieditor.getTileName(), 100, 100, slotpos);
		
	}
	
	public TileGuiEditor(String name, int xSize, int ySize, @Nullable int[] slotpos) {
		super(name);
		
		this.xSize = xSize;
		this.ySize = ySize;
		
		if(slotpos != null) {
			initSlotItems(slotpos);
		} else {
			initSlotItems();
		}
	}

	
	
	

	
	
	@Override
	public int getSizeInventory() {
		return items.size();
	}

	@Override
	public boolean isEmpty() {
		for(ItemStack i : items) {
			if(!i.isEmpty()) return false;
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		// indexoob error only on server side
		return items.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		markDirty();
		return ItemStackHelper.getAndSplit(items, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		markDirty();
		return ItemStackHelper.getAndRemove(items, index - 1);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		markDirty();
		if (!stack.isEmpty() && stack.getCount() > getInventoryStackLimit()) {
			
			stack.setCount(this.getInventoryStackLimit());
		}
		items.set(index, stack);
		
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		
		
		
		
		
        NBTTagList slots = tag.getTagList("SlotItems", 10);
        
        int needed = slots.tagCount();
        
        System.out.println("Needed is "+ needed);
        
        if(needed <= 0) return;
        
        if(slotCount() <= needed) {
        	resizeTo(needed); // resize internal
        } else {
        	// ???
        	// TODO
        	System.out.println("This Place");
        }
        
        
        items = NonNullList.withSize(needed, ItemStack.EMPTY);
        
        for (int i = 0; i < needed; ++i)
        {
            
        	NBTTagCompound slottag = slots.getCompoundTagAt(i);
            int j = slottag.getByte("Slot") & 255;
            int x, y;
            if((x = getInteger(slottag, "PosX")) != -1) setSlotX(i, x);
            if((y = getInteger(slottag, "PosY")) != -1) setSlotY(i, y);
            
            if (i == j)
            {
                
                items.set(j, new ItemStack(slottag));

            }
            // if not then the slot is out of order, and every slot is a requirement
        }
        
        xSize = getInteger(tag, "SizeX");
        ySize = getInteger(tag, "SizeY");
        
        int xBody = getInteger(tag, "PlayerInvX");
        int yBody = getInteger(tag, "PlayerInvY");
        
        if(xBody != -1 && yBody != -1) {
        	ptracker = new PlayerSlotTracker(xBody, yBody);
			ptracker.hotbarX = getInteger(tag, "HotbarX");
			ptracker.hotbarY = getInteger(tag, "HotbarY");
        } else {
        	ptracker = null;
        }
        markDirty();
	}
	

	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
        int needed = slotCount(); // floor
    	
        if(items.size() > needed) {
        	// WAT
        	throw new RuntimeException("Not enough slot positions to describe the items");
        }
        
//        tag.setInteger("NeededSlots", needed);
        
    	NBTTagList slots = new NBTTagList();
        
    	int i=0;
        for (; i < needed; ++i)
        {
            NBTTagCompound slottag = new NBTTagCompound(); 

            slottag.setByte("Slot", (byte) i);
            slottag.setInteger("PosX", getSlotX(i));
            slottag.setInteger("PosY", getSlotY(i));
            
            if(i < itemCount()) {
            	ItemStack itemstack = items.get(i); // if there's an itemstack, it is saved
            	if (!itemstack.isEmpty()) {
            		itemstack.writeToNBT(slottag);
            	}
            }
            
            slots.appendTag(slottag);
            
        }

        
        tag.setTag("SlotItems", slots);
        
		tag.setInteger("SizeX", xSize);
		tag.setInteger("SizeY", ySize);
		if(ptracker != null) {
			tag.setInteger("PlayerInvX", ptracker.bodyX);
			tag.setInteger("PlayerInvY", ptracker.bodyY);
			if (ptracker.hotbarX != -1) tag.setInteger("HotbarX", ptracker.hotbarX);
			if (ptracker.hotbarY != -1) tag.setInteger("HotbarY", ptracker.hotbarY);
		}
		return tag;
	}
	
	@Override
	public void clear() {
		items.clear();
		markDirty();
	}

	/**
	 * Same as NBTTagCompound.getInteger(), but returns -1 if it fails
	 * @param tag
	 * @param key
	 * @return
	 */
	public static int getInteger(NBTTagCompound tag, String key) {

	    
        try
        {
            if (tag.hasKey(key, 99))
            {
                return ((NBTPrimitive)tag.getTag(key)).getInt();
            }
        }
        catch (ClassCastException var3)
        {
            ;
        }

        return -1;
    
	}

	public NonNullList<ItemStack> getItems() {
		return items;
	}
	
	
	// TODO not a todo but a marker
	// SlotItems
	NonNullList<ItemStack> items;
	private int[] internal;
	public void initSlotItems(int... iarray) {
		this.internal = iarray.clone();
		items = NonNullList.withSize(slotCount(), ItemStack.EMPTY);
		
	}
	
	/**
	 * Not cloned; this is a direct "backed" reference
	 * @return
	 */
	public int[] toIntArray() {
		return internal;
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