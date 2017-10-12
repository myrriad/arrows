package phlaxyr.forcearrows.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import phlaxyr.forcearrows.blocks.BlockRegistrar;
import phlaxyr.forcearrows.items.ItemRegistrar;

@Mod.EventBusSubscriber
public class ClientProxy extends CommonProxy{
	public void preInit() {
		super.preInit();
	}
	public void init() {
		super.init();
	}
	public void postInit() {
		super.postInit();
	}
	
	@Override
	public boolean playerIsInCreativeMode(EntityPlayer player) {
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP entityPlayerMP = (EntityPlayerMP)player;
			return entityPlayerMP.interactionManager.isCreative();
		} else if (player instanceof EntityPlayerSP) {
			return Minecraft.getMinecraft().playerController.isInCreativeMode();
		}
		return false;
	}
	
	@Override
	public boolean isDedicatedServer() {return false;}
	
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        BlockRegistrar.initModels();
        ItemRegistrar.initModels();
    }
}
