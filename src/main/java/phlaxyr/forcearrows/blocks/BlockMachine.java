package phlaxyr.forcearrows.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import phlaxyr.forcearrows.machines.IMachine;

public class BlockMachine extends BlockContainerCommon{
	
	private final IMachine<?,?> machine;
	
	/**
	 * Automatically sets the registry names too :)
	 * @param m
	 */
	public BlockMachine(IMachine<?,?> m) {
		super(m.getMaterial(),m.getCreativeTab(),m.getGuiID());
		this.machine = m;
		this.setRegistryName(m.getRegistryName());
		this.setUnlocalizedName(m.getUnlocalizedName());
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return machine.getNewTile();
	}
	
	
}
