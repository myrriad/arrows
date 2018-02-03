package phlaxyr.forcearrows.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import phlaxyr.forcearrows.machines.ITemplate;

public class BlockMachine extends BlockContainerCommon{
	
	private final ITemplate<?,?> template;
	
	/**
	 * Automatically sets the registry names too :)
	 * @param m
	 */
	public BlockMachine(ITemplate<?,?> m) {
		super(m.getMaterial(),m.getCreativeTab(),m.getGuiID());
		this.template = m;
		this.setRegistryName(m.getRegistryName());
		this.setUnlocalizedName(m.getUnlocalizedName());
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return template.getNewTile();
	}
	
	
}
