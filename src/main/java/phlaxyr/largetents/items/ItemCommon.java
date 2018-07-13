package phlaxyr.largetents.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phlaxyr.largetents.LargeTents;

public class ItemCommon extends Item{
	public ItemCommon() {
		this.setCreativeTab(LargeTents.TAB);
	}
	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(
				this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}

