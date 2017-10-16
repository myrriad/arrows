package phlaxyr.forcearrows.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.forcearrows.inventory.ContainerCommon;
import phlaxyr.forcearrows.tile.TileCommon;

public abstract class GuiHandlerCommon<T extends TileCommon, C extends ContainerCommon<T>, G extends GuiCommon<C, T>> {
	public final int GUI_ID;
	public GuiHandlerCommon(int guiID) {
		this.GUI_ID = guiID;
	}
	public abstract C constrContainer(InventoryPlayer inv, T tile, World world, BlockPos pos);
	/*InventoryPlayer inv, T tile, World world, BlockPos pos, M manager, int gridX, int gridY
	public abstract GuiCommon passTheGuiConstructor(InventoryPlayer ip, World worldIn, BlockPos pos, 
			TileCommon tilee5by5, ManagerCrafting manager);*/
	public abstract G constrGui(C cont);
	/***
	 * 
	 * @param t
	 * @return if it can't be casted, return null
	 */
	public abstract T upCastTile(TileEntity t);
}
