package phlaxyr.largetents.inspectable;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.largetents.LargeTents;
import phlaxyr.largetents.client.gui.GuiID;
import phlaxyr.largetents.items.ItemCommon;

public class ItemInspectable extends ItemCommon{

	
	/**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        player.openGui(LargeTents.instance, GuiID.BEAKER_GUI, worldIn, pos.getX(), pos.getY(), pos.getZ());
    	return EnumActionResult.PASS;
    }
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
		// player.openGui(LargeTents.instance, GuiID.BEAKER_GUI, worldIn, pos.getX(), pos.getY(), pos.getZ());
    	
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

}
