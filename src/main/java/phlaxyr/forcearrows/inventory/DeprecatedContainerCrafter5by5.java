package phlaxyr.forcearrows.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.forcearrows.crafting.Craft5by5Manager;
import phlaxyr.forcearrows.tile.TileCrafter5by5;

@Deprecated
public class DeprecatedContainerCrafter5by5 extends ContainerCrafterXbyX<TileCrafter5by5>{
	
	/*
	public DeprecatedContainerCrafter5by5(InventoryPlayer playerInventory, World worldIn,
			BlockPos posIn, TileCommon tile, CraftXbyXManager manager) {
		super(playerInventory, worldIn, posIn, tile, manager, 5, 5);
	}*/
	/***
	 * Required in clump: constructor args, tile, manager
	 * @param clump
	 */
	public DeprecatedContainerCrafter5by5(InventoryPlayer inv, TileCrafter5by5 tile, World world,
			BlockPos pos, Craft5by5Manager manager) {
		super(inv, tile, world, pos, manager, 5, 5);
		
	}
	@Override
	public int bodyX() {
		
		return 12;
	}
	@Override
	public int bodyY() {
		
		return 109;
	}
	@Override
	public int resultX() {
		
		return 143;
	}
	@Override
	public int resultY() {
		
		return 47;
	}
	@Override
	public int gridX() {
		
		return 21;
	}
	@Override
	public int gridY() {
		
		return 15;
	}
}
