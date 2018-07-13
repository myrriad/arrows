package phlaxyr.largetents.machines;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import phlaxyr.largetents.LargeTents;
import phlaxyr.largetents.blocks.BlockMachine;
import phlaxyr.largetents.inventory.ContainerCommon;
import phlaxyr.largetents.tile.TileCommon;

public interface ITemplateMachine<T extends TileCommon, C extends ContainerCommon<T>> extends 
IGuiHandler, 
ITemplateGui<T, C>,
ITemplateContainer<T, C>,
ITemplateTile<T>{
	
	public default BlockMachine<T> getNewBlock() {
		return new BlockMachine<T>(this);
	}

	public Material getMaterial();

	public CreativeTabs getCreativeTab();

	public String getUnlocalizedName();

	public String getRegistryName();

	@Override
	public default Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		LargeTents.lumberjack.debug("get server gui element");
		if (ID != getGuiID()) {
			LargeTents.lumberjack.error("Invalid ID: expected " + getGuiID() + ", received " + ID);
		}

		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(xyz);
		T casttile = castTile(te);
			
		//	return new DeprecatedContainerCrafter5by5(playerInv.inventory, tileEntityInventoryBasic);
			//return passTheContainerConstructor(playerInv.inventory, world, xyz, 
				//	 tile, manager);
		return getNewContainer(player.inventory, casttile, world, xyz);
		
	}
	
	// it has the client-only `getNewGui`
	// Gets the client side element for the given gui id- this should return a gui
	
	@Override
	public default Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		LargeTents.lumberjack.debug("get client gui element");
		if (ID != getGuiID()) {
			LargeTents.lumberjack.error("Invalid ID: expected " + getGuiID() + ", received " + ID);
		}

		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(xyz);
		T casttile = castTile(te);
		//return passTheGuiConstructor(
			//	playerInv.inventory, world, xyz, tilee5by5, Craft5by5Manager.getInstance());
		
		return getNewGui(getNewContainer(player.inventory, casttile, world, xyz));

		
	}
	
}
