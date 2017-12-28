package phlaxyr.forcearrows.machines;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.registry.GameRegistry;
import phlaxyr.forcearrows.ForceArrows;
import phlaxyr.forcearrows.machines.MachineCrafter5by5.BlockCrafter5by5;
import phlaxyr.forcearrows.registrars.Registrar;


public class MachineRegistrar extends Registrar{
    
    public static final MachineCrafter5by5 machine_mWorkbench = new MachineCrafter5by5();
    
    static List<Machine<?,?>> machines = new ArrayList<>(); // frankly, the generics don't matter when registering
    static {
    	machines.add(machine_mWorkbench);
    }
    
    public static void preInit() {
    	

    	// SYSTEMATIC REGISTER-ING
    	for(Machine<?,?> m : machines) {
    		registerTile(m.getNewTile().getClass(), m.getRegistryName());
    		
    	}
    	/*
    	
		registerTile(machine_mWorkbench.getNewTile().getClass(), machine_mWorkbench.getRegistryName());
		registerGui(machine_mWorkbench);*/
    }

    


    
    @GameRegistry.ObjectHolder(ForceArrows.MODID + ":" + MachineCrafter5by5.REGISTRY_NAME) // annotations can't allow expressions :(
    public static BlockCrafter5by5 block_mWorkbench;
    
    // static List<Machine<? extends TileCommon, ? extends ContainerCommon<? extends TileCommon>>> commonMachines = new ArrayList<>(); // hurray diamond operator :)

    public static void registerBlocks(Register<Block> event) {
		
		registerBlock(event, machine_mWorkbench.getNewBlock()); 
    	/*
		for(Machine<?,?> m : machines) {
			registerBlock(event, m.getNewBlock()); // machine already adds the unlocalized/registry names
		}*/ // the block needs its type
		ForceArrows.lumberjack.info("done registering blocks");
    }
    
    public static void registerItems(Register<Item> event) {
    	// No systematicness because mandatory object-holders
		registerItem(event, block_mWorkbench); // problem line
		
    }


	protected static void registerTile(Class<? extends TileEntity> tileclass, String registryname){
		GameRegistry.registerTileEntity(tileclass, registryname);

	}

	

}
