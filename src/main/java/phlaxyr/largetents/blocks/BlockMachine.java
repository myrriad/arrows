package phlaxyr.largetents.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.largetents.machines.ITemplateMachine;
import phlaxyr.largetents.tile.TileCommon;

public class BlockMachine<T extends TileCommon> extends BlockContainerCommon{
	
	private final ITemplateMachine<T,?> templateMachine;
	
	/**
	 * Automatically sets the registry names too :)
	 * @param m
	 */
	public BlockMachine(ITemplateMachine<T,?> m) {
		super(m.getMaterial(),m.getCreativeTab(),m.getGuiID());
		this.templateMachine = m;
		this.setRegistryName(m.getRegistryName());
		this.setUnlocalizedName(m.getUnlocalizedName());
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return templateMachine.getNewTile();
	}
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{

		T inventory = templateMachine.castTile(worldIn.getTileEntity(pos));
		
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
		superBreakBlock(worldIn, pos, state);
	}
	/**
	 * removes the tile entity
	 * @param worldIn
	 * @param pos
	 * @param state
	 */
	protected final void superBreakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
	}
	
}
