package phlaxyr.largetents.machines.guieditor;

import javax.annotation.Nonnull;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phlaxyr.largetents.LargeTents;
import phlaxyr.largetents.blocks.BlockMachine;
import phlaxyr.largetents.client.gui.GuiID;
import phlaxyr.largetents.inventory.ContainerGuiEditPenMode;
import phlaxyr.largetents.inventory.ContainerGuiEditResult;
import phlaxyr.largetents.items.ItemRegistrar;
import phlaxyr.largetents.machines.ITemplateMachine;
import phlaxyr.largetents.tile.TileGuiEditor;

public class TemplateGuiEditor implements ITemplateMachine<TileGuiEditor, ContainerGuiEditResult>{

    public static final String UNLOCALIZED_NAME = "gui_editor", 
    		REGISTRY_NAME = "gui_editor";
//	public static final ResourceLocation corners = 
//			new ResourceLocation(LargeTents.MODID+":textures/gui/gui_corners.png");
//    public static final ResourceLocation borders = 
//    		new ResourceLocation(LargeTents.MODID+":textures/gui/gui_borders.png");
    public static final ResourceLocation both = 
    		new ResourceLocation(LargeTents.MODID+":textures/gui/gui_both.png");
    public static final ResourceLocation slot = 
    		new ResourceLocation(LargeTents.MODID+":textures/gui/gui_slot.png");
    
    
    
	public TemplateGuiEditor() {
		this(UNLOCALIZED_NAME, REGISTRY_NAME, "guiEditor", Material.IRON, LargeTents.TAB, GuiID.GUI_EDITOR_GUI);
		// TODO Auto-generated constructor stub
	}
	@Override
	public TileGuiEditor getNewTile() {
		return new TileGuiEditor(); // the default
	}
	@Override
	public boolean isInstanceOf(TileEntity t) {
		return t instanceof TileGuiEditor;
	}
	@Override
	public ContainerGuiEditResult getNewContainer(InventoryPlayer inv, TileGuiEditor tile, World world, BlockPos pos) {
		ContainerGuiEditResult c;
		ItemStack held = inv.getCurrentItem();
		if(held.getItem().equals(ItemRegistrar.item_edit_pen)) {// TODO
			
			c = new ContainerGuiEditPenMode(inv, tile, world, pos, tile, tile.xSize, tile.ySize, tile.ptracker, held);
			
		
		} else {
			c = new ContainerGuiEditResult(inv, tile, world, pos, tile, tile.xSize, tile.ySize, tile.ptracker);
		}

		return c;
	}
    @SideOnly(Side.CLIENT)
	public phlaxyr.largetents.client.gui.GuiGuiEditable getNewGui(ContainerGuiEditResult cont) {
    	if(cont.isEditing()) return new phlaxyr.largetents.client.gui.GuiGuiEditing(cont, both, slot);
		return new phlaxyr.largetents.client.gui.GuiGuiEditable(cont, both, slot);
	}
	@Nonnull
    protected final String unlocalizedName, registryName, tileName;
	@Nonnull
    protected final Material material;
	@Nonnull
    protected final CreativeTabs creativeTab;
	@Nonnull
    protected final int guiID;

	public TemplateGuiEditor(String unlocalizedName, String registryName, String tileName, Material material,
			CreativeTabs creativeTab, int guiID) {
		
		this.unlocalizedName = unlocalizedName;
		this.registryName = registryName;
		this.tileName = tileName;
		this.material = material;
		this.creativeTab = creativeTab;
		this.guiID = guiID;
	}


	public BlockMachine<TileGuiEditor> getNewBlock() {

		return new BlockMachine<TileGuiEditor>(this);

	}
	
	public Material getMaterial() {
		return material;
	}

	public CreativeTabs getCreativeTab() {
		return creativeTab;
	}

	public int getGuiID() {
		return guiID;
	}

	public String getTileName() {
		return tileName;
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	public String getRegistryName() {
		return registryName;
	}


	public int getTextureSizeX() {
		return 0;
	}


	public int getTextureSizeY() {
		return 0;
	}
	
	@Override
	public ResourceLocation getGuiTex() {
		return both;
	}
	
}
