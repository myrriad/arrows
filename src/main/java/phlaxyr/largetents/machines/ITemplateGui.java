package phlaxyr.largetents.machines;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phlaxyr.largetents.client.gui.GuiCommon;
import phlaxyr.largetents.inventory.ContainerCommon;
import phlaxyr.largetents.tile.TileCommon;

public interface ITemplateGui<T extends TileCommon, C extends ContainerCommon<T>> extends IGuiHandler{
	public int getGuiID();

	public ResourceLocation getGuiTex();

	public int getTextureSizeX();


	public int getTextureSizeY();
	
    @SideOnly(Side.CLIENT)
	public default GuiCommon<T, C> getNewGui(C cont) {
		return new GuiCommon<T, C>(cont, this){};
	}
}
