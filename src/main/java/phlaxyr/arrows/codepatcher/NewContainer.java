package phlaxyr.arrows.codepatcher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.world.World;
import phlaxyr.arrows.craftingarrow.ArrowManager;

public abstract class NewContainer extends Container{
    protected void slotChangedCraftingGrid(World world, EntityPlayer player, InventoryCrafting invcraft, InventoryCraftResult invcraftresult)
    {
        if (!world.isRemote)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)player;
            ItemStack itemstack = ItemStack.EMPTY;
            IRecipe irecipe = CraftingManager.findMatchingRecipe(invcraft, world);

            if (irecipe != null && (irecipe.isHidden() || !world.getGameRules().getBoolean("doLimitedCrafting") || entityplayermp.getRecipeBook().containsRecipe(irecipe)))
            {
                invcraftresult.setRecipeUsed(irecipe);
                itemstack = irecipe.getCraftingResult(invcraft);
            }
            // start patch
            itemstack = ArrowManager.injectArrowResult(this, invcraft, itemstack, world);
            // end patch
            invcraftresult.setInventorySlotContents(0, itemstack);
            entityplayermp.connection.sendPacket(new SPacketSetSlot(this.windowId, 0, itemstack));
        }
    }

}
