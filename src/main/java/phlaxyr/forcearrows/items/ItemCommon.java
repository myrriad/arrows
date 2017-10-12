package phlaxyr.forcearrows.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCommon extends Item{
	public ItemCommon(CreativeTabs creativeTab) {
		this.setCreativeTab(creativeTab);
	}
	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(
				this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}

