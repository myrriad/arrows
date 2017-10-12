package phlaxyr.forcearrows.util;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.forcearrows.container.ContainerCrafterExt;
import phlaxyr.forcearrows.crafting.manager.ManagerCraftCommon;
import phlaxyr.forcearrows.tile.TileCommon;

public abstract class PackagerCrafter extends Packager{
	
	public abstract ContainerCrafterExt getNewContainerCrafter(InventoryPlayer playerInventory, World worldIn, 
    		BlockPos posIn, TileCommon tile, ManagerCraftCommon manager, 
    		int gridX, int gridY);
}
