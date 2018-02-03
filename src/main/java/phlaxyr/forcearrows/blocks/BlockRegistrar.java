package phlaxyr.forcearrows.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phlaxyr.forcearrows.ForceArrows;
import phlaxyr.forcearrows.registrars.Registrar;


public class BlockRegistrar extends Registrar {
	
    @GameRegistry.ObjectHolder(ForceArrows.MODID + ":mass_block")
    public static BlockCommon block_massBlock;
    /*
    @GameRegistry.ObjectHolder(ForceArrows.MODID + ":m_workbench")
    public static BlockCrafter block_mWorkbench;*/ 
    // TemplateRegistrar

    public static void registerBlocks(Register<Block> event) {

    	registerBlock(event, new BlockCommon(Material.IRON, CreativeTabs.MATERIALS).setUnlocalizedName("mass_block")
    			.setRegistryName("mass_block"));
	// registerBlock(event, new DeprecatedBlockCrafter5by5().setUnlocalizedName("m_workbench").setRegistryName("m_workbench"));
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
    	block_massBlock.initModel();
	// block_mWorkbench.initModel();
    }
    // some registering is in machineregistrar

    public static void registerItems(Register<Item> event) {
    	registerItem(event, BlockRegistrar.block_massBlock);
	// registerItem(event, BlockRegistrar.block_mWorkbench);
	// MOVED TO TemplateRegistrar
    }

}
