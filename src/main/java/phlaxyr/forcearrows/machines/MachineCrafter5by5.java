package phlaxyr.forcearrows.machines;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.forcearrows.ForceArrows;
import phlaxyr.forcearrows.blocks.BlockCrafter;
import phlaxyr.forcearrows.client.gui.GuiID;
import phlaxyr.forcearrows.crafting.Craft5by5Manager;
import phlaxyr.forcearrows.inventory.ContainerCrafterXbyX;
import phlaxyr.forcearrows.machines.MachineCrafter5by5.ContainerCrafter5by5;
import phlaxyr.forcearrows.tile.TileCrafter5by5;

public class MachineCrafter5by5 extends MachineCrafter<
		TileCrafter5by5,
		ContainerCrafter5by5>{

	
	private static final ResourceLocation tex = 
			new ResourceLocation(ForceArrows.MODID+":textures/gui/m_workbench_gui.png");
    public static final String UNLOCALIZED_NAME = "m_workbench", REGISTRY_NAME = "m_workbench";
	
	
	public MachineCrafter5by5()
	{
		super(UNLOCALIZED_NAME, REGISTRY_NAME, "container.m_workbench.name", Material.WOOD, CreativeTabs.MISC, GuiID.CRAFTER_GUI,
				tex, 183, 197, 5, 5);
		
	}
	
	public BlockCrafter5by5 getNewBlock() {

		// return new DeprecatedBlockCrafter5by5(this);

		// mmmm so nice
		return new BlockCrafter5by5(this);
			
	}
	
	static class BlockCrafter5by5 extends BlockCrafter{
		protected BlockCrafter5by5(MachineCrafter5by5 m) {
			super(m);
			
		}
		//TODO: public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos);
		@Override
		public boolean isOpaqueCube(IBlockState state) {return false;}
		
		@Override
		public boolean isFullCube(IBlockState state) {return false;}
	}
	
	@Override
	public TileCrafter5by5 getNewTile()
	{
		// CANNOT BE REFACTORED
		// BECAUSE MINECRAFT RELIES ON THE CLASS
		// TO IDENTIFY TILES
		return new TileCrafter5by5();
	}

	
	
	@Override
	public Craft5by5Manager getManager()
	{
		return Craft5by5Manager.getInstance();
	}

	@Override
	public ContainerCrafter5by5 getNewContainer(InventoryPlayer inv, TileCrafter5by5 tile, World world, BlockPos pos)
	{
		// TODO Refactor codes into anonymous class or a static inner class
		return new ContainerCrafter5by5(inv, tile, world, pos, this);
	}
	
	static class ContainerCrafter5by5 extends ContainerCrafterXbyX<TileCrafter5by5> {

		protected ContainerCrafter5by5(InventoryPlayer inv, TileCrafter5by5 tile, World world,
				BlockPos pos, MachineCrafter5by5 m) {
			super(inv, tile, world, pos, 
					m.getManager(),
					m.getGridSlotLengthX(),
					m.getGridSlotLengthY());
			
		}
		@Override
		public int bodyX() {
			
			return 12;
		}
		@Override
		public int bodyY() {
			
			return 109;
		}
		@Override
		public int resultX() {
			
			return 143;
		}
		@Override
		public int resultY() {
			
			return 47;
		}
		@Override
		public int gridX() {
			
			return 21;
		}
		@Override
		public int gridY() {
			
			return 15;
		}
	}
	
	/* No need to override this
	 * 
	@Override
	public GuiCrafterXbyX<TileCrafter5by5, DeprecatedContainerCrafter5by5> getGui(int textureSizeX, int textureSizeY,
			DeprecatedContainerCrafter5by5 cont)
	{
		// Refactor finished
		return new GuiCrafterXbyX<TileCrafter5by5, DeprecatedContainerCrafter5by5>(textureSizeX, textureSizeY, cont, tex);
	}*/

	@Override
	public TileCrafter5by5 castTile(TileEntity t) {
		if(t instanceof TileCrafter5by5) {
			return (TileCrafter5by5) t;
		}
		return null;
	}




	

	

}
