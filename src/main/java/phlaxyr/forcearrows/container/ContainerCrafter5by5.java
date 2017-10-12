package phlaxyr.forcearrows.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.forcearrows.crafting.manager.ManagerCraftCommon;
import phlaxyr.forcearrows.tile.TileCommon;
import phlaxyr.forcearrows.util.Argbus;

public class ContainerCrafter5by5 extends ContainerCrafterExt{
	
	public ContainerCrafter5by5(InventoryPlayer playerInventory, World worldIn,
			BlockPos posIn, TileCommon tile, ManagerCraftCommon manager) {
		super(playerInventory, worldIn, posIn, tile, manager, 5, 5);
	}
	@Override
	public int bodyX() {
		
		return 12;
	}
	@Override
	public int bodyY() {
		
		return 105;
	}
	@Override
	public int resultX() {
		
		return 143;
	}
	@Override
	public int resultY() {
		
		return 43;
	}
	@Override
	public int gridX() {
		
		return 21;
	}
	@Override
	public int gridY() {
		
		return 11;
	}
}
