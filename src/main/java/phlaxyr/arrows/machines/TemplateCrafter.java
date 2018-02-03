package phlaxyr.arrows.machines;

import javax.annotation.Nonnull;


import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phlaxyr.arrows.Arrows;
import phlaxyr.arrows.blocks.BlockCrafter;
import phlaxyr.arrows.crafting.CraftXbyXManager;
import phlaxyr.arrows.crafting.ShapedRecipe5by5;
import phlaxyr.arrows.inventory.ContainerCrafterXbyX;
import phlaxyr.arrows.tile.TileCrafter;

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

	public BlockCrafter getNewBlock() {

		return new BlockCrafter(this);

		// mmmm so nice
	}

	// getTile()


	// getContainer()

	
	public abstract CraftXbyXManager<R> getManager();
	
	
	// 2 apis
	
	// safe: only works on the client-side
	@SideOnly(Side.CLIENT)
	@Override
	public phlaxyr.arrows.client.gui.GuiCrafterXbyX<T, R, C> getNewGui(C cont) {
		return new phlaxyr.arrows.client.gui.GuiCrafterXbyX<T, R, C>(cont, this);
	}
	
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		Arrows.lumberjack.debug("get server gui element");
		if (ID != this.guiID) {
			Arrows.lumberjack.error("Invalid ID: expected " + guiID + ", received " + ID);
		}

		if(this.getManager() == null) {
			Arrows.lumberjack.error("MANAGER IS NULL!");
		}
		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(xyz);
		T casttile = castTile(te);
			
		//	return new DeprecatedContainerCrafter5by5(playerInv.inventory, tileEntityInventoryBasic);
			//return passTheContainerConstructor(playerInv.inventory, world, xyz, 
				//	 tile, manager);
		return getNewContainer(player.inventory, casttile, world, xyz);
		
	}
	
	
	// it has the client-only `getNewGui`
	// Gets the client side element for the given gui id- this should return a gui
	@SideOnly(Side.CLIENT)
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		Arrows.lumberjack.debug("get client gui element");
		if (ID != guiID) {
			Arrows.lumberjack.error("Invalid ID: expected " + guiID + ", received " + ID);
		}
		if(this.getManager() == null) {
			Arrows.lumberjack.error("MANAGER IS NULL!");
		}
		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(xyz);
		T casttile = castTile(te);
		//return passTheGuiConstructor(
			//	playerInv.inventory, world, xyz, tilee5by5, Craft5by5Manager.getInstance());
		
		return getNewGui(getNewContainer(player.inventory, casttile, world, xyz));

		
	}

	public int getGridSlotLengthX() {
		return gridSlotLengthX;
	}

	public int getGridSlotLengthY() {
		return gridSlotLengthY;
	}


}
