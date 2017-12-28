package phlaxyr.forcearrows.machines;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import phlaxyr.forcearrows.blocks.BlockMachine;
import phlaxyr.forcearrows.client.gui.GuiCommon;
import phlaxyr.forcearrows.inventory.ContainerCommon;
import phlaxyr.forcearrows.tile.TileCommon;

public interface IMachine<T extends TileCommon, C extends ContainerCommon<T>> extends IGuiHandler{
	public default BlockMachine getNewBlock() {

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
    
    
    
    @SideOnly(Side.CLIENT)
	public default GuiCommon<T, C> getNewGui(C cont) {
		return new GuiCommon<T, C>(cont, this){};
	}
    
    public abstract C getNewContainer(InventoryPlayer inv, T tile, World world, BlockPos pos);


	public Material getMaterial();

	public CreativeTabs getCreativeTab();

	public int getGuiID();

	public String getTileName();

	public ResourceLocation getGuiTex();

	public String getUnlocalizedName();

	public String getRegistryName();


	public int getTextureSizeX();


	public int getTextureSizeY();
}
