package phlaxyr.forcearrows.util.clump;

import javax.annotation.Nullable;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.forcearrows.crafting.ManagerCrafting;
import phlaxyr.forcearrows.gui.GuiCommon;
import phlaxyr.forcearrows.inventory.ContainerCrafter;
import phlaxyr.forcearrows.tile.TileCrafter;
@Deprecated
public class ClumpContainerCrafterd<C extends ContainerCrafter, G extends GuiCommon> extends ClumpContainerd<C, G>{

	public ClumpContainerCrafterd(InventoryPlayer playerInventory, World worldIn, BlockPos posIn, TileCrafter tile, ManagerCrafting manager)
	{
		super(playerInventory, worldIn, posIn, tile);
		this.setManager(manager);
	}
	
	@Nullable
	private ManagerCrafting manager;
	public ManagerCrafting getManager()
	{
		return manager;
	}
	public void setManager(ManagerCrafting manager)
	{
		this.manager = manager;
	}
	private int gridX, gridY;
	public int getGridX()
	{
		return gridX;
	}
	public void setGridX(int gridX)
	{
		this.gridX = gridX;
	}
	public int getGridY()
	{
		return gridY;
	}
	public void setGridY(int gridY)
	{
		this.gridY = gridY;
	}
	
	// order of setting
	// playerinv, worldin, posin
	// tile, manager
	
	
	
	// container

}
