package phlaxyr.forcearrows.machines;

import javax.annotation.Nonnull;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phlaxyr.forcearrows.blocks.BlockMachine;
import phlaxyr.forcearrows.inventory.ContainerCommon;
import phlaxyr.forcearrows.tile.TileCommon;

public abstract class Template<T extends TileCommon, C extends ContainerCommon<T>> implements ITemplate<T, C>{
	
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


	public BlockMachine getNewBlock() {

		return new BlockMachine(this);

		// mmmm so nice
	}

    public abstract T getNewTile();

    /**
     * @param t
     * @return
     * Null, if you can't cast it
     */
    public abstract T castTile(TileEntity t);
    
    
    
    
    public abstract C getNewContainer(InventoryPlayer inv, T tile, World world, BlockPos pos);

    @SideOnly(Side.CLIENT)
	public phlaxyr.forcearrows.client.gui.GuiCommon<T, C> getNewGui(C cont) {
		return new phlaxyr.forcearrows.client.gui.GuiCommon<T, C>(cont, this){};
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
