package phlaxyr.arrows.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ServerProxy extends CommonProxy{
	public void preInit() {
		super.preInit();
	}
	public void init() {
		super.init();

		
	}
	public void postInit() {
		super.postInit();
	}
	public boolean playerIsInCreativeMode(EntityPlayer player) {
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
			return entityPlayerMP.interactionManager.isCreative();
		}
		return false;
	}
	
	@Override
	public boolean isDedicatedServer() {return true;}
	
	
}
