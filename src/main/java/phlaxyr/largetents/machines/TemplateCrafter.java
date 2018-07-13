package phlaxyr.largetents.machines;

import javax.annotation.Nonnull;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phlaxyr.largetents.LargeTents;
import phlaxyr.largetents.blocks.BlockCrafter;
import phlaxyr.largetents.crafting.CraftXbyXManager;
import phlaxyr.largetents.crafting.ShapedRecipe5by5;
import phlaxyr.largetents.inventory.ContainerCrafterXbyX;
import phlaxyr.largetents.tile.TileCrafter;

public abstract class TemplateCrafter<T extends TileCrafter, R extends ShapedRecipe5by5, C extends ContainerCrafterXbyX<T, R>> extends Template<T, C>{

	@Nonnull
	protected final int gridSlotLengthX, gridSlotLengthY;
	
	
	public TemplateCrafter(String unlocalizedName, String registryName, String tileName, Material material, CreativeTabs creativeTab, int guiID, 
			ResourceLocation guiTex, int textureSizeX, int textureSizeY, int gridSlotLengthX, int gridSlotLengthY)
	{
		super(unlocalizedName, registryName, tileName, material, creativeTab, guiID, guiTex, textureSizeX, textureSizeY); // 183, 197
		this.gridSlotLengthX = gridSlotLengthX;
		this.gridSlotLengthY = gridSlotLengthY;
	}

	public BlockCrafter<T> getNewBlock() {

		return new BlockCrafter<T>(this);

		// mmmm so nice
	}

	// getTile()


	// getContainer()

	
	public abstract CraftXbyXManager<R> getManager();
	
	
	// 2 apis
	
	// safe: only works on the client-side
	@SideOnly(Side.CLIENT)
	@Override
	public phlaxyr.largetents.client.gui.GuiCrafterXbyX<T, R, C> getNewGui(C cont) {
		return new phlaxyr.largetents.client.gui.GuiCrafterXbyX<T, R, C>(cont, this);
	}
	
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		if(this.getManager() == null) {
			LargeTents.lumberjack.error("MANAGER IS NULL!");
		}
		return super.getServerGuiElement(ID, player, world, x, y, z);
		
	}
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		if(this.getManager() == null) {
			LargeTents.lumberjack.error("MANAGER IS NULL!");
		}
		return super.getClientGuiElement(ID, player, world, x, y, z);
		
	}
	
	

	public int getGridSlotLengthX() {
		return gridSlotLengthX;
	}

	public int getGridSlotLengthY() {
		return gridSlotLengthY;
	}


}
