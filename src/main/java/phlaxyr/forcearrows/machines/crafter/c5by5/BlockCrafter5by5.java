package phlaxyr.forcearrows.machines.crafter.c5by5;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import phlaxyr.forcearrows.blocks.BlockCrafter;
import phlaxyr.forcearrows.gui.GuiID;

public class BlockCrafter5by5 extends BlockCrafter{

	public BlockCrafter5by5() {
		super(Material.WOOD, GuiID.CRAFTER_GUI);
		
	}
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileCrafter5by5();
	}
	//TODO: public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos);
	@Override
	public boolean isOpaqueCube(IBlockState state) {return false;}
	
	@Override
	public boolean isFullCube(IBlockState state) {return false;}
}
