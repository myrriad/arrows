package phlaxyr.forcearrows.event;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import phlaxyr.forcearrows.ForceArrows;

public class ArrowShearAnimation {
	private Minecraft mc;
	private World world;
	// private Entity entity;
	
	
	
	public ArrowShearAnimation(Minecraft mc, World worldIn/*, Entity entityIn*/) {
		this(mc, 0);
		this.world = worldIn;
		// this.entity = entityIn;
	}
	public ArrowShearAnimation(Minecraft mc, int stage) {
		this.mc = mc;
		this.stage = stage;
	}
	private final static ResourceLocation animAt = new ResourceLocation(ForceArrows.MODID,
			"textures/gui/animation_shears_trigger.png");
	private final static int BAR_WIDTH = 24;
	private final static int BAR_HEIGHT = 16;

	private int stage;
	private static final int MAX = 30;
	  // private Object nul = null;
	  /* This helper method will render the bar */
	/***
	 * Renders the next frame of the shearing animation
	 * Returns if this should be destroyed or not
	 * 
	 * @param screenWidth
	 * @param screenHeight
	 * 
	 */
	public void render(int screenWidth, int screenHeight) {
		stage++;
		System.out.println(isTable());
		// System.out.println(stage);
		// only if you're in a crafting table
		// if you're not in a crafting table, skip all of this, but still increment stage
		if(!(mc.currentScreen instanceof GuiCrafting)) return;
		// TODO
		// if(nul == null) return;
		// EntityPlayer player = mc.player;
		// FontRenderer fr = mc.fontRenderer;
		/* This object inserts commas into number strings */
		// DecimalFormat decFormat = new DecimalFormat("#,###");
		/*
		 * Saving the stage state of OpenGL so that I can restore it when
		 * I'm done
		 */
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glPushMatrix();
			/* Set the rendering color to white */
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			/* This method tells OpenGL to draw with the custom texture */
			mc.renderEngine.bindTexture(animAt);
			
			final int cornerLeftX = screenWidth / 2 - 22; 
			final int cornerLeftY = screenHeight / 2 - 48; 
			GL11.glTranslatef(cornerLeftX, cornerLeftY /*- BAR_SPACING_ABOVE_EXP_BAR - BAR_HEIGHT*/, 0);
			
			mc.currentScreen.drawTexturedModalRect(0, 0, 0, 0, BAR_WIDTH, BAR_HEIGHT);
			
	
		GL11.glPopMatrix();
	    GL11.glPopAttrib();
	}
	public boolean ended(){
		if(stage > MAX) return true;
		return false;
		
	}
	public boolean isTable(){

		RayTraceResult res = mc.objectMouseOver;
		if (res.typeOfHit != RayTraceResult.Type.BLOCK)
			return false;

		// should get the block hit
		BlockPos pos = res.getBlockPos();
		Block block = world.getBlockState(pos).getBlock();
		if(block.getRegistryName().equals(Blocks.CRAFTING_TABLE.getRegistryName())) {
			
			return true;
		}
		
		return false;
		
	}
}
