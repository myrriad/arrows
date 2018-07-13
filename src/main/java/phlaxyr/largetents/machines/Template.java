package phlaxyr.largetents.machines;

import javax.annotation.Nonnull;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phlaxyr.largetents.blocks.BlockMachine;
import phlaxyr.largetents.inventory.ContainerCommon;
import phlaxyr.largetents.tile.TileCommon;

public abstract class Template<T extends TileCommon, C extends ContainerCommon<T>> implements ITemplateMachine<T, C>{
	

	
	@Nonnull
    protected final String unlocalizedName, registryName, tileName;
	@Nonnull
    protected final Material material;
	@Nonnull
    protected final CreativeTabs creativeTab;
	@Nonnull
    protected final int guiID, textureSizeX, textureSizeY;
	
	@Nonnull
    protected final ResourceLocation guiTex;

	public Template(String unlocalizedName, String registryName, String tileName, Material material,
			CreativeTabs creativeTab, int guiID, ResourceLocation guiTex, int textureSizeX, int textureSizeY) {
		
		this.unlocalizedName = unlocalizedName;
		this.registryName = registryName;
		this.tileName = tileName;
		this.material = material;
		this.creativeTab = creativeTab;
		this.guiID = guiID;
		this.guiTex = guiTex;
		this.textureSizeX = textureSizeX;
		this.textureSizeY = textureSizeY;
	}


	public BlockMachine<T> getNewBlock() {

		return new BlockMachine<T>(this);

	}

    public abstract T getNewTile();

    
    
    
    public abstract C getNewContainer(InventoryPlayer inv, T tile, World world, BlockPos pos);

    @SideOnly(Side.CLIENT)
	public phlaxyr.largetents.client.gui.GuiCommon<T, C> getNewGui(C cont) {
		return new phlaxyr.largetents.client.gui.GuiCommon<T, C>(cont, this){};
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

	public ResourceLocation getGuiTex() {
		return guiTex;
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	public String getRegistryName() {
		return registryName;
	}


	public int getTextureSizeX() {
		return textureSizeX;
	}


	public int getTextureSizeY() {
		return textureSizeY;
	}
	
	
}
