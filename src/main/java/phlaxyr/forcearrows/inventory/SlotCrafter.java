package phlaxyr.forcearrows.inventory;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import phlaxyr.forcearrows.crafting.CraftXbyXManager;

public class SlotCrafter extends Slot
{
    /** The craft matrix inventory linked to this result slot. */
    private final InventoryCrafting craftMatrix;
    /** The playerInv that is using the GUI where this slot resides. */
    private final EntityPlayer thePlayer;
    /** The number of items that have been crafted so far. Gets passed to ItemStack.onCrafting before being reset. */
    private int amountCrafted;

    private final CraftXbyXManager<?> MANAGER;
    
    public SlotCrafter(CraftXbyXManager<?> manager, EntityPlayer player, InventoryCrafting craftingInventory, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition)
    {
        super(inventoryIn, slotIndex, xPosition, yPosition);
        MANAGER = manager;
        this.thePlayer = player;
        this.craftMatrix = craftingInventory;
    }

    /**
     * Check if the stack is a valid item for this slot. Never true: it's the slot
     */
    public boolean isItemValid(@Nullable ItemStack stack)
    {
        return false;
    }

    /**
     * Crafting time! yay!
     */
    public ItemStack decrStackSize(int amount)
    {
        if (this.getHasStack())
        	// if we've got the goods (crafted stuffz)
        {
            this.amountCrafted += Math.min(amount, this.getStack().getCount());
        }

        return super.decrStackSize(amount);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
     * internal count then calls onCrafting(item).
     */
    protected void onCrafting(ItemStack stack, int amount)
    {
        this.amountCrafted += amount;
        this.onCrafting(stack);
    }

    protected void onSwapCraft(int p_190900_1_)
    {
        this.amountCrafted += p_190900_1_;
        //             net.minecraftforge.fml.common.FMLCommonHandler.instance().firePlayerCraftingEvent(this.player, stack, craftMatrix);
    }
    
    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     */
    protected void onCrafting(ItemStack stack)
    {
        if (this.amountCrafted > 0)
        {
            stack.onCrafting(this.thePlayer.world, this.thePlayer, this.amountCrafted);
        }

        this.amountCrafted = 0;
    }

    /***
     * Take only one ?
     */
    public ItemStack onTake(EntityPlayer playerIn, ItemStack stack)
    {
//        net.minecraftforge.fml.common.FMLCommonHandler.instance().firePlayerCraftingEvent(playerIn, stack, craftMatrix);
        this.onCrafting(stack);
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(playerIn);
        NonNullList<ItemStack> leftOverBuckets = MANAGER.getRemainingItems(this.craftMatrix, playerIn.world);
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(null);

        //           the remaining
        //                    |
        //                    v
        // milk buckets -> buckets
        //
        // oak planks   -> nothing
        // it's what's left over in the slot
        // for example, when crafting cake, the milk buckets turn into normal buckets.
        for (int i = 0; i < leftOverBuckets.size(); ++i)
        {
            ItemStack item = this.craftMatrix.getStackInSlot(i);
            ItemStack leftOverBucket = leftOverBuckets.get(i);

            // if it's not empty
            if (!item.isEmpty())
            {
                // decrement by one
            	this.craftMatrix.decrStackSize(i, 1);
                item = this.craftMatrix.getStackInSlot(i);
            }
            
            // if there should be a bucket left over
            if (!leftOverBucket.isEmpty())
            {
                // the grid is empty
            	if (item.isEmpty())
                {
                    // put a bucket in the grid
            		this.craftMatrix.setInventorySlotContents(i, leftOverBucket);
                }
            	// if they're both the same item
                else if (ItemStack.areItemsEqual(item, leftOverBucket) && ItemStack.areItemStackTagsEqual(item, leftOverBucket))
                {
                    // merge!
                	leftOverBucket.grow(item.getCount());
                    this.craftMatrix.setInventorySlotContents(i, leftOverBucket);
                }
            	// the heck
            	// since we can't put the bucket in the slot
            	// we stick it in the playerInv's inventory
                else if (!this.thePlayer.inventory.addItemStackToInventory(leftOverBucket))
                {
                    // if we can't stick it in the playerInv's inventory!
                	// drop the item
                	this.thePlayer.dropItem(leftOverBucket, false);
                }
            }
        }
        return stack;
    }
}