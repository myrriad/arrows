package phlaxyr.forcearrows.util.clump;

import javax.annotation.Nullable;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.forcearrows.client.gui.GuiCommon;
import phlaxyr.forcearrows.inventory.ContainerCommon;
import phlaxyr.forcearrows.tile.TileCommon;

@SuppressWarnings("rawtypes")
@Deprecated
public class ClumpContainerd<C extends ContainerCommon, G extends GuiCommon> {
	private ResourceLocation guiLoc;
	protected ResourceLocation getGuiLoc()
	{
		return guiLoc;
	}
	protected void setGuiLoc(ResourceLocation guiLoc)
	{
		this.guiLoc = guiLoc;
	}
	private InventoryPlayer playerInventory;
	private final World worldIn;
	private final BlockPos posIn;
	private TileCommon tile;
	@Nullable
	private G gui;
	@Nullable
	private C container;

	public ClumpContainerd(InventoryPlayer playerInventory, World worldIn, BlockPos posIn, TileCommon tile)
	{
		this.playerInventory = playerInventory;
		this.worldIn = worldIn;
		this.posIn = posIn;
		this.tile = tile;
	}
	public InventoryPlayer getPlayerInventory()
	{
		return playerInventory;
	}
	public void setPlayerInventory(InventoryPlayer playerInventory)
	{
		this.playerInventory = playerInventory;
	}
	public World getWorldIn()
	{
		return worldIn;
	}
	public BlockPos getPosIn()
	{
		return posIn;
	}
	public TileCommon getTile()
	{
		return tile;
	}
	public void setTile(TileCommon tile)
	{
		this.tile = tile;
		
	}
	public G getGui()
	{
		return gui;
	}
	public void setGui(G gui)
	{
		this.gui = gui;
	}
	public C getContainer()
	{
		return container;
	}
	public void setContainer(C container)
	{
		this.container = container;
	}
	
	
}
