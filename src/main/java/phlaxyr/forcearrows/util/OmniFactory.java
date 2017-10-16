package phlaxyr.forcearrows.util;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.forcearrows.inventory.ContainerCommon;
import phlaxyr.forcearrows.tile.TileCommon;

public abstract class OmniFactory<C extends ContainerCommon<T>, T extends TileCommon> {
	public abstract C constrContainer(InventoryPlayer inv, T tile, World world, BlockPos pos, int gridX, int gridY);
}
