package phlaxyr.forcearrows.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import phlaxyr.forcearrows.ForceArrows;
import phlaxyr.forcearrows.crafting.ManagerCrafting;
import phlaxyr.forcearrows.inventory.ContainerCrafter;
import phlaxyr.forcearrows.tile.TileCrafter;

public abstract class GuiHandlerCrafter
	<T extends TileCrafter, C extends ContainerCrafter<T, M>, 
	G extends GuiCommon<C, T>, M extends ManagerCrafting>
		extends GuiHandlerCommon<T, C, G> implements IGuiHandler{

	private final ManagerCrafting manager;
	
	/*
	public abstract ContainerCrafter passTheContainerConstructor(
			InventoryPlayer playerInv, World world, BlockPos xyz, TileCommon tile, ManagerCrafting manager);*/

	/*
	public abstract GuiCommon passTheGuiConstructor(InventoryPlayer ip, World worldIn, BlockPos pos, 
			TileCommon tilee5by5, ManagerCrafting manager);*/
	// public abstract GuiCommon constrGui(ClumpContainerCrafter clump);
	
	
	public GuiHandlerCrafter(int guiID, ManagerCrafting managerInstance) {
		super(guiID);
		this.manager = managerInstance;
	}
	
	// Gets the server side element for the given gui id- this should return a container
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		ForceArrows.lumberjack.debug("get server gui element");
		if (ID != GUI_ID) {
			System.err.println("Invalid ID: expected " + GUI_ID + ", received " + ID);
		}

		if(manager == null) {
			System.err.println("MANAGER IS NULL!");
		}
		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(xyz);
		T casttile = upCastTile(te);
			
		//	return new ContainerCrafter5by5(playerInv.inventory, tileEntityInventoryBasic);
			//return passTheContainerConstructor(playerInv.inventory, world, xyz, 
				//	 tile, manager);
			
		if( casttile == null )  {
			ForceArrows.lumberjack.error("Oh no! the tile entity is not of the right type! Returning null client element!");
			return null;
		}
		return constrContainer(player.inventory, casttile, world, xyz);
		
	}
	
	
	
	// Gets the client side element for the given gui id- this should return a gui
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		ForceArrows.lumberjack.debug("get client gui element");
		if (ID != GUI_ID) {
			System.err.println("Invalid ID: expected " + GUI_ID + ", received " + ID);
		}
		if(manager == null) {
			System.err.println("MANAGER IS NULL!");
		}
		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(xyz);
		T casttile = upCastTile( te);
		//return passTheGuiConstructor(
			//	playerInv.inventory, world, xyz, tilee5by5, Manager5by5.getInstance());
		
		if( casttile == null ) {
			ForceArrows.lumberjack.error("Oh no! the tile entity is not of the right type! Returning null client element!");
			return null; 
		
		}
		return constrGui(constrContainer(player.inventory, casttile, world, xyz));

		
	}
}
