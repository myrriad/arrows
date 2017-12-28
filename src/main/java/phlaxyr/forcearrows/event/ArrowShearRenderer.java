package phlaxyr.forcearrows.event;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ArrowShearRenderer extends Gui{
	
	// public static ArrowShearRenderer singleton = new ArrowShearRenderer(Minecraft.getMinecraft());
	private boolean active = false;
	private int stage = 0;
	private int MAX = 24;
	/*
	 * Sometimes you want to include extra information from the game. This
	 * instance of Minecraft will let you access the World and EntityPlayer
	 * objects which is more than enough for most purposes. It also contains
	 * some helper objects for OpenGL which can be used for drawing things.
	 *
	 * To actually get the instance of Minecraft, you should pass it in through
	 * the constructor. It is possible to import Minecraft and use
	 * Minecraft.getMinecraft() here, but this won't always be possible if you
	 * want to include information that's part of another class.
	 */
	private Minecraft mc;
	
	public ArrowShearRenderer(Minecraft mc)
	{
		this.mc = mc;
	}

	List<ArrowShearAnimation> anims = new ArrayList<>();
	// Called when a new frame is displayed (See fps)
	@SubscribeEvent
	public void onUpdate(TickEvent.RenderTickEvent event)
	{
		// only if active
		if (!active)
		{
			return;
		}
		ScaledResolution res = new ScaledResolution(mc);
		
		if (mc.player == null)
			return; // just in case
		
		
		for(int i=0;i<anims.size();i++) {
			ArrowShearAnimation anim = anims.get(i);
			// if
			anim.render(res.getScaledWidth(),res.getScaledHeight());
			if(anim.ended()) {
				anims.remove(i);
				nextShear();
			}
		}
		stage++;
		if(stage >= MAX) stage = 0;
	}

	
	public void addShear(World worldIn, Entity entityIn, BlockPos pos, ContainerWorkbench cont)
	{
		
		anims.add(new ArrowShearAnimation(mc, worldIn/*, entityIn*/));
		active = true;
	}
	private static int SHEAR_ADD_FREQ = 24;
	public void addShear(int ct){
		for(int i=0;i<ct;i++)
			anims.add(new ArrowShearAnimation(mc, i * -SHEAR_ADD_FREQ));
		active = true;
	}
	
	private void nextShear()
	{
		stage = 0;
		active = !anims.isEmpty();
		
	}
}
