package phlaxyr.forcearrows.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phlaxyr.forcearrows.ForceArrows;
import phlaxyr.forcearrows.tile.TileTransformer;

public class BlockTransformer extends BlockContainerCommon {
	public int guiID;

	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
				new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	public BlockTransformer(Material m, int guiID)
	{
		super(m, CreativeTabs.MISC, guiID);
	}

	/**
	 * Create the Tile Entity for this block. If your block doesn't extend
	 * BlockContainer, use createTileEntity(World worldIn, IBlockState state)
	 * instead
	 * 
	 * @param worldIn
	 * @param meta
	 * @return
	 */
	// Called when the block is right clicked
	// In this block it is used to open the blocks gui when right clicked by a
	// player
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, /* ItemStack heldItem, */ EnumFacing side, float hitX, float hitY, float hitZ)
	{
		// Uses the gui handler registered to your mod to open the gui for the
		// given gui id
		// open on the server side only (not sure why you shouldn't open client
		// side too... vanilla doesn't, so we better not either)
		if (worldIn.isRemote)
			return true;

		playerIn.openGui(ForceArrows.instance, guiID, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks
	 * for render
	 */
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileTransformer("container.transformer.name");
	}
}
