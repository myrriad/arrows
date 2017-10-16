package phlaxyr.forcearrows.machines.transformer;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.forcearrows.inventory.ContainerCommon;

public class ContainerTransformer extends ContainerCommon<TileTransformer>{

	
	public IInventory items;
	/***
	 * Required in clump: constructor args, tile
	 * @param playerInventory
	 * @param worldIn
	 * @param posIn
	 * @param tile
	 */
	public ContainerTransformer(InventoryPlayer playerInv, TileTransformer tile, World world, BlockPos pos)
	{
		super(playerInv, tile, world, pos);
		

			
		
		this.addSlots();
		super.addInventories();
		this.onCraftMatrixChanged(items);
	}

	@Override
	public int bodyX()
	{
		// TODO Make inventory corner
		return 0;
	}

	@Override
	public int bodyY()
	{
		// TODO Make inventory corner
		return 0;
	}

	
	protected void addSlots()
	{
		//TODO MAKE THIS THING
				// result
		this.addSlotToContainer(new Slot(this.items, 0,
				10, 10));
		this.addSlotToContainer(new Slot(this.items, 1,
				10,10));
		this.addSlotToContainer(new Slot(this.items, 2,
				10,10));
		this.addSlotToContainer(new Slot(this.items, 3,
				10,10));
		this.addSlotToContainer(new Slot(this.items, 4,
				10,10));
		this.addSlotToContainer(new Slot(this.items, 5,
				10,10));
	}

}
