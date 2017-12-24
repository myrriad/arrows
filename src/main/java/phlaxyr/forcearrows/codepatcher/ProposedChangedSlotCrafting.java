package phlaxyr.forcearrows.codepatcher;

import com.google.common.collect.Lists;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import phlaxyr.forcearrows.event.ArrowManager;

public class ProposedChangedSlotCrafting extends SlotCrafting{


    public ProposedChangedSlotCrafting(EntityPlayer player, InventoryCrafting craftingInventory, IInventory inventoryIn,
			int slotIndex, int xPosition, int yPosition)
	{
		super(player, craftingInventory, inventoryIn, slotIndex, xPosition, yPosition);
		// TODO Auto-generated constructor stub
		
	}
	/** The craft matrix inventory linked to this result slot. */
    private final InventoryCrafting craftMatrix = null;
    /** The player that is using the GUI where this slot resides. */
    private final EntityPlayer player = null;
    /** The number of items that have been crafted so far. Gets passed to ItemStack.onCrafting before being reset. */
    private int amountCrafted = 0;
    
    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     */
    protected void onCrafting(ItemStack stack)
    {
        if (this.amountCrafted > 0)
        {
            stack.onCrafting(this.player.world, this.player, this.amountCrafted);
            // start
           //  ArrowManager.onCraftArrow(this.craftMatrix.eventHandler, slot, bp, w)
            
            // end
            
            net.minecraftforge.fml.common.FMLCommonHandler.instance().firePlayerCraftingEvent(this.player, stack, craftMatrix);
        }

        this.amountCrafted = 0;
        InventoryCraftResult inventorycraftresult = (InventoryCraftResult)this.inventory;
        IRecipe irecipe = inventorycraftresult.getRecipeUsed();

        if (irecipe != null && !irecipe.isHidden())
        {
            this.player.unlockRecipes(Lists.newArrayList(irecipe));
            inventorycraftresult.setRecipeUsed((IRecipe)null);
        }
    }
}
