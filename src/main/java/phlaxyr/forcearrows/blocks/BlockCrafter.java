package phlaxyr.forcearrows.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phlaxyr.forcearrows.machines.Machine;
import phlaxyr.forcearrows.tile.TileCrafter;

public class BlockCrafter extends BlockMachine {
/*
	public BlockCrafter(Material m, int guiID)
	{
		super(m,CreativeTabs.BUILDING_BLOCKS,guiID);
	}*/
	public BlockCrafter(Machine<?,?> m) {
		super(m);
	}
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer(){
		return BlockRenderLayer.SOLID;
	}
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{

		TileCrafter inventory = worldIn.getTileEntity(pos) instanceof TileCrafter
				? (TileCrafter) worldIn.getTileEntity(pos) : null;
		
		if (inventory != null) {
			
			// For each slot in the inventory
			for (int i = 1; i < inventory.getSizeInventory(); i++) {
				// If the slot is not empty
				if (!inventory.getStackInSlot(i).isEmpty()) // isEmpty
				{
					// Create a new entity item with the item stack in the slot
					EntityItem item = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
							inventory.getStackInSlot(i));

					// Apply some random motion to the item
					float multiplier = 0.1f;
					float motionX = worldIn.rand.nextFloat() - 0.5f;
					float motionY = worldIn.rand.nextFloat() - 0.5f;
					float motionZ = worldIn.rand.nextFloat() - 0.5f;

					item.motionX = motionX * multiplier;
					item.motionY = motionY * multiplier;
					item.motionZ = motionZ * multiplier;

					// Spawn the item in the world
					worldIn.spawnEntity(item);
				}
			}

			// Clear the inventory so nothing else (such as another mod) can do
			// anything with the items
			inventory.clear();
		}

		// Super MUST be called last because it removes the tile entity
		super.breakBlock(worldIn, pos, state);
	}

}
