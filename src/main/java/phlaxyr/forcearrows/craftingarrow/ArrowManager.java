package phlaxyr.forcearrows.craftingarrow;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phlaxyr.forcearrows.crafting.CraftXbyXManager;
// import phlaxyr.forcearrows.event.ArrowShearRenderer;
import phlaxyr.forcearrows.items.ItemRegistrar;
// import phlaxyr.forcearrows.util.NullableDelegate;

public class ArrowManager {
	// public static final Delegate renderer = new Delegate();
	// public static class Delegate extends NullableDelegate<ArrowShearRenderer>{}
	public static IRecipe arrow_recipe;
	public static final CraftXbyXManager<ShapedRecipes> CUSTOM_MANAGER = new CraftXbyXManager<ShapedRecipes>(){

		@Override
		public ShapedRecipes getNewRecipe(int width, int height, NonNullList<Ingredient> ingredients,
				ItemStack result) {
			return new ShapedRecipes("",width, height, ingredients, result);
		}};
	public static void init() {
		
		arrow_recipe = 
		CUSTOM_MANAGER.oldAddRecipe(new ItemStack(ItemRegistrar.item_triggerer_no_get),"trigger_shears", new Object[]{
		        "EBF",
		        "BES",
		        "EB.",
				  'F', Items.FLINT_AND_STEEL,
                  'E', ItemRegistrar.item_energyIngot,
                  'B', Items.BLAZE_POWDER,
                  'S', ItemRegistrar.item_mass_shears// note carefully - 'E' not "E" !
		});
	}
	
	public static final int ANIMATION_TICK_LENGTH = 80; 
	public static final int TICK_TO_PUT_ARROW = 55;
	public static class ArrowTicker {
		
		int frameno = 1;
		BlockPos pos;
		World w;
		ContainerWorkbench ct;
		EntityPlayerMP player;
		public ArrowTicker(World worldin, BlockPos bp, ContainerWorkbench ctin, EntityPlayerMP playerin) {
			pos = bp;
			w = worldin;
			ct = ctin;
			player = playerin;
		}
		/**
		 * true means remove me
		 * @return
		 */
		boolean onTick() {
			
			PacketHandler.INSTANCE.sendTo(new PacketDisplayFrame(frameno), player);
			
			if(frameno == ANIMATION_TICK_LENGTH) {
				
				// spawn the item with the container item, so if the item has already been taken, it doesn't spawn
				if(w.getBlockState(pos).getBlock().getClass().equals(BlockWorkbench.class)) {
					ItemStack istack = ct.craftResult.getStackInSlot(0);
					ct.craftResult.setInventorySlotContents(0, ItemStack.EMPTY);
					player.connection.sendPacket(new SPacketSetSlot(ct.windowId, 0, ItemStack.EMPTY));
					if(istack != ItemStack.EMPTY) {
						EntityItem item = new EntityItem(w, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 
			        		istack);
						w.spawnEntity(item);
					}
				}
				else {
					// something punishing-ful
				}
				
				return true;
			
			} else {
			// tick
				if(frameno == TICK_TO_PUT_ARROW) { 
					ItemStack istack = new ItemStack(ItemRegistrar.item_craftingArrow);
					ct.craftResult.setInventorySlotContents(0, istack);
					player.connection.sendPacket(new SPacketSetSlot(ct.windowId, 0, istack));
				}
				frameno++;
			}
			return false;
		}

	}
	
	static List<ArrowTicker> tickers = new ArrayList<>();
	static boolean commonnone = true;
	public static class EventHandlerCommon {
		@SubscribeEvent
		public void onWorldTick(TickEvent.WorldTickEvent e) {
			// System.out.println("wtick");
			if(commonnone) return;
			List<ArrowTicker> toRemove = new ArrayList<>();
			for(ArrowTicker ticker : tickers) {
				if(ticker.onTick()) toRemove.add(ticker); // to avoid ConcurrentModificationException
			}
			tickers.removeAll(toRemove);
		}
	}

	static final int DAMAGE_BY = 200;
	
	static ResourceLocation tex = new ResourceLocation("textures/gui/guiarrowexplosion.png");
	
	@SideOnly(Side.CLIENT)
	public static void displayFrame(int frameno) {
		System.out.println("displayFrame " + frameno);
		Minecraft mc = Minecraft.getMinecraft();
		GuiScreen screen = mc.currentScreen;
		if(screen.getClass().equals(GuiCrafting.class)) {
			// instanceof might work
			GuiCrafting gui = (GuiCrafting) screen;
			
			// refresh the draw-ing
            final ScaledResolution scaledresolution = new ScaledResolution(mc);
            int xw = scaledresolution.getScaledWidth();
            int yw = scaledresolution.getScaledHeight();
            final int x = Mouse.getX() * xw / mc.displayWidth;
            final int y = yw - Mouse.getY() * yw / mc.displayHeight - 1;
			gui.drawScreen(x, y, mc.getTickLength());
			// TODO block-specific guis
			
			
	        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	        mc.getTextureManager().bindTexture(tex);
	        int i = gui.getGuiLeft();
	        int j = (gui.height - gui.getYSize()) / 2;
	        gui.drawTexturedModalRect(i, j, 0, 0, gui.getXSize(), gui.getYSize());
			
		}
	}
	
	
	/**
	 * Returns whether or not the stack will craft an arrow
	 * Undergoes the process of creating the arrow
	 * Return is for transforstackinslot
	 * @param stack
	 * @return
	 */
	
	public static boolean onCraftArrow(ContainerWorkbench cw, BlockPos bp, World w, EntityPlayer player) {
		// System.out.println("oncraftarrow");
		
		System.out.println("onCraftArrow");
		if(ItemStack.areItemsEqual(cw.craftResult.getStackInSlot(0), arrow_recipe.getRecipeOutput())) {
			if(cw.getClass().equals(ContainerWorkbench.class)) { // make sure it's not a subclass
				if(Block.isEqualTo(w.getBlockState(bp).getBlock(), Blocks.CRAFTING_TABLE)) {

					ItemStack shear = cw.craftMatrix.getStackInSlot(5);
					
					
					int dmgby = shear.getItemDamage() + DAMAGE_BY;
					if(dmgby > shear.getMaxDamage()) return false; // if will damage it too much, return
					shear.setItemDamage(dmgby);
					
					InventoryCrafting mx = cw.craftMatrix;
					// success, all the conditions pass
					//012
					//345
					//678
					mx.decrStackSize(0, 1);			mx.decrStackSize(1, 1);			mx.decrStackSize(2, 1);
					mx.decrStackSize(3, 1);			mx.decrStackSize(4, 1);			// shear has special handling
					mx.decrStackSize(6, 1);			mx.decrStackSize(7, 1);			// no item here
					
					// maybe display some particles?
					
					cw.detectAndSendChanges();
					
					// AFAIK this is strictly serverside
					if(!w.isRemote) {
						tickers.add(new ArrowTicker(w, bp, cw, (EntityPlayerMP)player));
						System.out.println("constructed a world ticker!");
						commonnone = false;
					}
					
					// renderer.obj.addShear(1);
					return true;
				}
			}
			
		}
		return false;
	}
	/**
	 * Adds the hidden recipe to the crafting table, if applicable
	 * 
	 */
	public static ItemStack injectArrowResult(Container c, InventoryCrafting matr, ItemStack existing, World w) {
		if(existing != ItemStack.EMPTY) return existing; // if there's already something there
		
		System.out.println("injectArrowResult");
		
		if(arrow_recipe.matches(matr, w)) { // just one additional recipe
			ItemStack istack = matr.getStackInSlot(5);
			System.out.println(istack.getItemDamage());
			System.out.println(istack.getMaxDamage());
			if(istack.getItemDamage() + DAMAGE_BY > istack.getMaxDamage()) return ItemStack.EMPTY;
			if(c.getClass().equals(ContainerWorkbench.class)) { // same craftingtable
				return arrow_recipe.getCraftingResult(matr);
			}
		}
		
		return ItemStack.EMPTY;
	}
}
