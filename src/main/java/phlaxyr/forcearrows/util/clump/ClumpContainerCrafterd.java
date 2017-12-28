package phlaxyr.forcearrows.util.clump;

import javax.annotation.Nullable;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.forcearrows.client.gui.GuiCommon;
import phlaxyr.forcearrows.crafting.CraftXbyXManager;
import phlaxyr.forcearrows.inventory.ContainerCrafterXbyX;
import phlaxyr.forcearrows.tile.TileCrafter;
@SuppressWarnings("rawtypes")
@Deprecated
public class ClumpContainerCrafterd<C extends ContainerCrafterXbyX, G extends GuiCommon> extends ClumpContainerd<C, G>{

	public ClumpContainerCrafterd(InventoryPlayer playerInventory, World worldIn, BlockPos posIn, TileCrafter tile, CraftXbyXManager manager)
	{
		super(playerInventory, worldIn, posIn, tile);
		this.setManager(manager);
	}
	
	@Nullable
	private CraftXbyXManager manager;
	public CraftXbyXManager getManager()
	{
		return manager;
	}
	public void setManager(CraftXbyXManager manager)
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
