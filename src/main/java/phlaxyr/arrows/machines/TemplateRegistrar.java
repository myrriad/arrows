package phlaxyr.arrows.machines;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.registry.GameRegistry;
import phlaxyr.arrows.Arrows;
import phlaxyr.arrows.machines.TemplateCrafter5by5.BlockCrafter5by5;
import phlaxyr.arrows.registrars.Registrar;


public class TemplateRegistrar extends Registrar{
    
    public static final TemplateCrafter5by5 machine_mWorkbench = new TemplateCrafter5by5();
    
    static List<Template<?,?>> templates = new ArrayList<>(); // frankly, the generics don't matter when registering
    static {
    	templates.add(machine_mWorkbench);
    }
    
    public static void preInit() {
    	

    	// SYSTEMATIC REGISTER-ING
    	for(Template<?,?> m : templates) {
    		registerTile(m.getNewTile().getClass(), m.getRegistryName());
    		
    	}
    	/*
    	
		registerTile(machine_mWorkbench.getNewTile().getClass(), machine_mWorkbench.getRegistryName());
		registerGui(machine_mWorkbench);*/
    }

    


    
    @GameRegistry.ObjectHolder(Arrows.MODID + ":" + TemplateCrafter5by5.REGISTRY_NAME) // annotations can't allow expressions :(
    public static BlockCrafter5by5 block_mWorkbench;
    
    // static List<Template<? extends TileCommon, ? extends ContainerCommon<? extends TileCommon>>> commonMachines = new ArrayList<>(); // hurray diamond operator :)

    public static void registerBlocks(Register<Block> event) {
		
		registerBlock(event, machine_mWorkbench.getNewBlock()); 
    	/*
		for(Template<?,?> m : templates) {
			registerBlock(event, m.getNewBlock()); // machine already adds the unlocalized/registry names
		}*/ // the block needs its type
		Arrows.lumberjack.info("done registering blocks");
    }
    
    public static void registerItems(Register<Item> event) {
    	// No systematicness because mandatory object-holders
		registerItem(event, block_mWorkbench); // problem line
		
    }


	protected static void registerTile(Class<? extends TileEntity> tileclass, String registryname){
		GameRegistry.registerTileEntity(tileclass, registryname);

	}

	

}
