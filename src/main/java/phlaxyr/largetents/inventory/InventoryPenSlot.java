package phlaxyr.largetents.inventory;

import net.minecraft.inventory.InventoryBasic;

public class InventoryPenSlot extends InventoryBasic{

	public InventoryPenSlot() {
		super("penslot", false, 1);
		// TODO Auto-generated constructor stub
	}
//	ItemStack thestack;
//	
//
//    /**
//     * Returns the number of slots in the inventory.
//     */
//    public int getSizeInventory()
//    {
//        return 1;
//    }
//
//    public boolean isEmpty()
//    {
//        return thestack.isEmpty();
//    }
//
//    /**
//     * Returns the stack in the given slot.
//     */
//    public ItemStack getStackInSlot(int index)
//    {
//        return thestack;
//    }
//
//    /**
//     * Get the name of this object. For players this returns their username
//     */
//    public String getName()
//    {
//        return "container.penslot";
//    }
//
//    /**
//     * Returns true if this thing is named
//     */
//    public boolean hasCustomName()
//    {
//        return false;
//    }
//
//    /**
//     * Get the formatted ChatComponent that will be used for the sender's username in chat
//     */
//    public ITextComponent getDisplayName()
//    {
//        return (ITextComponent)(this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]));
//    }
//
//    /**
//     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
//     */
//    public ItemStack decrStackSize(int index, int count)
//    {
//        return removeStackFromSlot(0);
//    }
//
//    /**
//     * Removes a stack from the given slot and returns it.
//     */
//    public ItemStack removeStackFromSlot(int index)
//    {
//        ItemStack old = thestack;
//        thestack = ItemStack.EMPTY;
//    	return old;
//    }
//
//    /**
//     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
//     */
//    public void setInventorySlotContents(int index, ItemStack stack)
//    {
//        thestack = stack;
//    }
//
//    /**
//     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
//     */
//    public int getInventoryStackLimit()
//    {
//        return 64;
//    }
//
//    /**
//     * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
//     * hasn't changed and skip it.
//     */
//    public void markDirty()
//    {
//    }
//
//    /**
//     * Don't rename this method to canInteractWith due to conflicts with Container
//     */
//    public boolean isUsableByPlayer(EntityPlayer player)
//    {
//        return true;
//    }
//
//    public void openInventory(EntityPlayer player)
//    {
//    }
//
//    public void closeInventory(EntityPlayer player)
//    {
//    }
//
//    /**
//     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
//     * guis use Slot.isItemValid
//     */
//    public boolean isItemValidForSlot(int index, ItemStack stack)
//    {
//        return stack.getItem().equals(ItemRegistrar.item_edit_pen);
//    }
//
//    public int getField(int id)
//    {
//        return 0;
//    }
//
//    public void setField(int id, int value)
//    {
//    }
//
//    public int getFieldCount()
//    {
//        return 0;
//    }
//
//    public void clear()
//    {
//        thestack = ItemStack.EMPTY;
//    }
}
