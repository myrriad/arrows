package phlaxyr.largetents.machines;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.registry.GameRegistry;
import phlaxyr.largetents.LargeTents;
import phlaxyr.largetents.blocks.BlockMachine;
import phlaxyr.largetents.machines.TemplateCrafter5by5.BlockCrafter5by5;
import phlaxyr.largetents.machines.guieditor.TemplateGuiEditor;
import phlaxyr.largetents.registrars.Registrar;
import phlaxyr.largetents.tile.TileGuiEditor;


public class TemplateRegistrar extends Registrar{
    
    public static final TemplateCrafter5by5 machine_mWorkbench = new TemplateCrafter5by5();
    public static final TemplateGuiEditor machine_guieditor = new TemplateGuiEditor();
    
    static List<ITemplateMachine<?,?>> templates = new ArrayList<>(); // frankly, the generics don't matter when registering
    static {
    	templates.add(machine_mWorkbench);
    	templates.add(machine_guieditor);
    }
    
    public static void preInit() {
    	

    	// SYSTEMATIC REGISTER-ING
    	for(ITemplateMachine<?,?> m : templates) {
    		registerTile(m.getNewTile().getClass(), m.getRegistryName());
    		
    	}
    	/*
    	
		registerTile(machine_mWorkbench.getNewTile().getClass(), machine_mWorkbench.getRegistryName());
		registerGui(machine_mWorkbench);*/
    }

    


    
    @GameRegistry.ObjectHolder(LargeTents.MODID + ":" + TemplateCrafter5by5.REGISTRY_NAME) // annotations can't allow expressions :(
    public static BlockCrafter5by5 block_mWorkbench;
    @GameRegistry.ObjectHolder(LargeTents.MODID + ":" + TemplateGuiEditor.REGISTRY_NAME) // annotations can't allow expressions :(
    public static BlockMachine<TileGuiEditor> block_guiEditor;
    
    // static List<Template<? extends TileCommon, ? extends ContainerCommon<? extends TileCommon>>> commonMachines = new ArrayList<>(); // hurray diamond operator :)

    public static void registerBlocks(Register<Block> event) {
		
//		registerBlock(event, machine_mWorkbench.getNewBlock()); 
		
    	
		for(ITemplateMachine<?,?> m : templates) {
			registerBlock(event, m.getNewBlock()); // machine already adds the unlocalized/registry names
		} // the block needs its type
		LargeTents.lumberjack.info("done registering blocks");
    }
    
    public static void registerItems(Register<Item> event) {
    	// No systematicness because mandatory object-holders
		registerItem(event, block_mWorkbench);
		registerItem(event, block_guiEditor);
    }


	protected static void registerTile(Class<? extends TileEntity> tileclass, String registryname){
		GameRegistry.registerTileEntity(tileclass, registryname);

	}

	

}
