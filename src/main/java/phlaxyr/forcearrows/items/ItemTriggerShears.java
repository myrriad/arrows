package phlaxyr.forcearrows.items;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import phlaxyr.forcearrows.ForceArrows;
import phlaxyr.forcearrows.event.ArrowManager;

public class ItemTriggerShears extends ItemCommon{	
	
	private final ArrowManager.Delegate renderer;
	public ItemTriggerShears(ArrowManager.Delegate renderer2)
	{
		super(CreativeTabs.MISC);
		this.renderer = renderer2;
	}
	
	@Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
		super.onCreated(stack, worldIn, playerIn);
		Minecraft mc = Minecraft.getMinecraft();
		// EnumFacing c = mc.objectMouseOver.sideHit;
		
		
		// This is super hacky
		// TODO a more elegant way of dealing with this
		StackTraceElement[] all = Thread.currentThread().getStackTrace();
		String first = all[2].getMethodName();
		String second = all[4].getMethodName();
		ForceArrows.lumberjack.info("first: "+first);
		ForceArrows.lumberjack.info(second);
		
		if(first.equals("transferStackInSlot")) {
			return;
			// Do nothing because we'll worry about what happens when the item
			// Gets in the inventory
			
		} else if(second.equals("onTake")/*first.equals("onCrafting")*/){
			// yay it's single take
			// Destroy the item, since it shouldn't be here
			stack.setCount(0);
		
			// make the ticking start
			renderer.obj.addShear(worldIn, playerIn);
		}
		
		
    }
	
	
	
	//@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		
		// destroy: we shouldn't be able to get this
		stack.setCount(0);
		
		renderer.obj.addShear(worldIn, entityIn);
	}
	//             net.minecraftforge.fml.common.FMLCommonHandler.instance().firePlayerCraftingEvent(this.player, stack, craftMatrix);
	
	

	/*
	 //Called when a new frame is displayed (See fps) 
	 @SubscribeEvent
	 public void onUpdate(TickEvent.RenderTickEvent event) {
	
		 // only if active
		 if(!active) return;
		 ScaledResolution res = new ScaledResolution(mc);
		
		 EntityPlayerSP entityPlayerSP = mc.player;
		 if (entityPlayerSP == null)
			return; // just in case
		 renderer.renderStatusBar(res.getScaledWidth(), res.getScaledHeight()); /*
											 * Call a helper method so that this
											 * method stays organized
											 *//*
	 }
	 
	 private void startAnim(){
		 active = true;
		 current = 0;
	 }
	 private void endAnim(){
		 active = false;
	 }
	 */
}
