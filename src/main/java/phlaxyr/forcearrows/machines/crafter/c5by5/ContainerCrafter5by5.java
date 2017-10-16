package phlaxyr.forcearrows.machines.crafter.c5by5;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.forcearrows.inventory.ContainerCrafter;

public class ContainerCrafter5by5 extends ContainerCrafter<TileCrafter5by5, Manager5by5>{
	
	/*
	public ContainerCrafter5by5(InventoryPlayer playerInventory, World worldIn,
			BlockPos posIn, TileCommon tile, ManagerCrafting manager) {
		super(playerInventory, worldIn, posIn, tile, manager, 5, 5);
	}*/
	/***
	 * Required in clump: constructor args, tile, manager
	 * @param clump
	 */
	public ContainerCrafter5by5(InventoryPlayer inv, TileCrafter5by5 tile, World world,
			BlockPos pos, Manager5by5 manager) {
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
