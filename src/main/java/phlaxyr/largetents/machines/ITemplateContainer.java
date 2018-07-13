package phlaxyr.largetents.machines;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.largetents.inventory.ContainerCommon;
import phlaxyr.largetents.tile.TileCommon;

public interface ITemplateContainer<T extends TileCommon, C extends ContainerCommon<T>> {
    public abstract C getNewContainer(InventoryPlayer inv, T tile, World world, BlockPos pos);
}
