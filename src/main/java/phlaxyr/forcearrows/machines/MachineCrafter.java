package phlaxyr.forcearrows.machines;

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
import phlaxyr.forcearrows.ForceArrows;
import phlaxyr.forcearrows.blocks.BlockCrafter;
import phlaxyr.forcearrows.crafting.CraftXbyXManager;
import phlaxyr.forcearrows.inventory.ContainerCrafterXbyX;
import phlaxyr.forcearrows.tile.TileCrafter;

public abstract class MachineCrafter<T extends TileCrafter, C extends ContainerCrafterXbyX<T>> extends Machine<T, C>{

	@Nonnull
	protected final int gridSlotLengthX, gridSlotLengthY;
	
	
	public MachineCrafter(String unlocalizedName, String registryName, String tileName, Material material, CreativeTabs creativeTab, int guiID, 
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

	
	public abstract CraftXbyXManager getManager();
	
	// 2 apis
	
	// safe: only works on the client-side
	@SideOnly(Side.CLIENT)
	@Override
	public phlaxyr.forcearrows.client.gui.GuiCrafterXbyX<T, C> getNewGui(C cont) {
		return new phlaxyr.forcearrows.client.gui.GuiCrafterXbyX<T, C>(cont, this);
	}
	
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		ForceArrows.lumberjack.debug("get server gui element");
		if (ID != this.guiID) {
			System.err.println("Invalid ID: expected " + guiID + ", received " + ID);
		}

		if(this.getManager() == null) {
			System.err.println("MANAGER IS NULL!");
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
		ForceArrows.lumberjack.debug("get client gui element");
		if (ID != guiID) {
			System.err.println("Invalid ID: expected " + guiID + ", received " + ID);
		}
		if(this.getManager() == null) {
			System.err.println("MANAGER IS NULL!");
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
