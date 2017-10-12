package phlaxyr.forcearrows.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phlaxyr.forcearrows.Registrar;
import phlaxyr.forcearrows.tile.TileCrafter5by5;


public class BlockRegistrar extends Registrar {
	
	@GameRegistry.ObjectHolder("forcearrows:mass_block")
	public static BlockCommon block_massBlock;
	
	@GameRegistry.ObjectHolder("forcearrows:m_workbench")
	public static BlockCrafter block_mWorkbench;
		
	public static void registerBlocks(Register<Block> event) {
		registerBlock(event, new BlockCommon(Material.IRON,CreativeTabs.MATERIALS)
				.setUnlocalizedName("mass_block")
				.setRegistryName("mass_block"));
		registerBlock(event, new BlockCrafter(Material.WOOD, 1943) {
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
				.setUnlocalizedName("m_workbench")
				.setRegistryName("m_workbench"));
	}
	
    @SideOnly(Side.CLIENT)
    public static void initModels() {
        block_massBlock.initModel();
        block_mWorkbench.initModel();
    }
	
	public static void registerItems(Register<Item> event) {
		registerItem(event, BlockRegistrar.block_massBlock);
		registerItem(event, BlockRegistrar.block_mWorkbench);
	}
	

	
}
