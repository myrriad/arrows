package phlaxyr.forcearrows.util;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.forcearrows.container.ContainerCrafter5by5;
import phlaxyr.forcearrows.container.ContainerCrafterExt;
import phlaxyr.forcearrows.crafting.manager.ManagerCraftCommon;
import phlaxyr.forcearrows.tile.TileCommon;

public class PackagerCrafter5by5 extends PackagerCrafter {

	@Override
	public ContainerCrafterExt getNewContainerCrafter(InventoryPlayer playerInventory, World worldIn, BlockPos posIn,
			TileCommon tile, ManagerCraftCommon manager, int gridX, int gridY)
	{	
		return new ContainerCrafter5by5(playerInventory, worldIn, posIn, tile, manager);
	}

}
