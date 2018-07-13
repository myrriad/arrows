package phlaxyr.largetents.blocks;

import net.minecraft.block.BlockContainer;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phlaxyr.largetents.LargeTents;

public abstract class BlockContainerCommon extends BlockContainer {
	public int guiID;
	
	
	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
				new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	
	public BlockContainerCommon(Material m, CreativeTabs tab, int guiID)
	{
		super(m);
		this.guiID = guiID;
		this.setCreativeTab(tab); // the block will appear on the Blocks tab.
	}
	


	


	// Called when the block is right clicked
	// In this block it is used to open the blocks gui when right clicked by a
	// player
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, /* ItemStack heldItem, */ EnumFacing side, float hitX, float hitY, float hitZ)
	{
		// Uses the gui handler registered to your mod to open the gui for the
		// given gui id
		// open on the server side only (not sure why you shouldn't open client
		// side too... vanilla doesn't, so we better not either)
		if (worldIn.isRemote)
			return true;

		playerIn.openGui(LargeTents.instance, guiID, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	// This is where you can do something when the block is broken. In this case
	// drop the inventory's contents
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{

		IInventory inventory = worldIn.getTileEntity(pos) instanceof IInventory
				? (IInventory) worldIn.getTileEntity(pos) : null;

		if (inventory != null) {
			// For each slot in the inventory
			for (int i = 0; i < inventory.getSizeInventory(); i++) {
				// If the slot is not empty
				if (inventory.getStackInSlot(i) != null) // isEmpty
				{
					// Create a new entity item with the item stack in the slot
					EntityItem item = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
							inventory.getStackInSlot(i));

					// Apply some random motion to the item
					float multiplier = 0.1f;
					float motionX = worldIn.rand.nextFloat() - 0.5f;
					float motionY = worldIn.rand.nextFloat() - 0.5f;
					float motionZ = worldIn.rand.nextFloat() - 0.5f;

					item.motionX = motionX * multiplier;
					item.motionY = motionY * multiplier;
					item.motionZ = motionZ * multiplier;

					// Spawn the item in the world
					worldIn.spawnEntity(item);
				}
			}

			// Clear the inventory so nothing else (such as another mod) can do
			// anything with the items
			inventory.clear();
		}

		// Super MUST be called last because it removes the tile entity
		super.breakBlock(worldIn, pos, state);
	}

	// ---------------------------------------------------------

	// the block will render in the SOLID layer.
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.SOLID;
	}

	// required because the default (super method) is INVISIBLE for
	// BlockContainers.
	@Override
	public EnumBlockRenderType getRenderType(IBlockState iBlockState)
	{
		return EnumBlockRenderType.MODEL;
	}
}
