package phlaxyr.forcearrows.container.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import phlaxyr.forcearrows.container.ContainerCrafterExt;
import phlaxyr.forcearrows.crafting.manager.Manager5by5;
import phlaxyr.forcearrows.crafting.manager.ManagerCraftCommon;
import phlaxyr.forcearrows.tile.TileCommon;
import phlaxyr.forcearrows.tile.TileCrafter;

public abstract class GuiHandlerCommon implements IGuiHandler{
	public final int GUI_ID;
	private final ManagerCraftCommon manager;
	
	public abstract ContainerCrafterExt passTheContainerConstructor(
			InventoryPlayer playerInv, World world, BlockPos xyz, TileCommon tile, ManagerCraftCommon manager);
	public abstract GuiCommon passTheGuiConstructor(InventoryPlayer ip, World worldIn, BlockPos pos, 
			TileCommon tilee5by5, ManagerCraftCommon manager);
	
	
	public GuiHandlerCommon(int guiID, ManagerCraftCommon managerInstance) {
		this.GUI_ID = guiID;
		this.manager = managerInstance;
	}
	
	// Gets the server side element for the given gui id- this should return a container
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID != GUI_ID) {
			System.err.println("Invalid ID: expected " + GUI_ID + ", received " + ID);
		}

		if(manager == null) {
			System.err.println("MANAGER IS NULL!");
		}
		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(xyz);
		if (te instanceof TileCrafter) {
			TileCrafter tile = (TileCrafter) te;
			
		//	return new ContainerCrafter5by5(player.inventory, tileEntityInventoryBasic);
			return passTheContainerConstructor(player.inventory, world, xyz, 
					 tile, manager);
		}
		return null;
	}
	
	
	
	// Gets the client side element for the given gui id- this should return a gui
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID != GUI_ID) {
			System.err.println("Invalid ID: expected " + GUI_ID + ", received " + ID);
		}

		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(xyz);
		if (te instanceof TileCommon) {
			TileCommon tilee5by5 = (TileCommon) te;
			
			return passTheGuiConstructor(
					player.inventory, world, xyz, tilee5by5, Manager5by5.getInstance());

		}
		return null;
	}
}
