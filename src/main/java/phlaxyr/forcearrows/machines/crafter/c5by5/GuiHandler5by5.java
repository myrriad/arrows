package phlaxyr.forcearrows.machines.crafter.c5by5;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.forcearrows.ForceArrows;
import phlaxyr.forcearrows.gui.GuiCommon;
import phlaxyr.forcearrows.gui.GuiHandlerCrafter;
import phlaxyr.forcearrows.gui.GuiID;

public class GuiHandler5by5 extends GuiHandlerCrafter
	<TileCrafter5by5, 
	ContainerCrafter5by5, 
	GuiCommon<ContainerCrafter5by5,TileCrafter5by5>, Manager5by5>{
	public GuiHandler5by5() {
		super(GuiID.CRAFTER_GUI, Manager5by5.getInstance());

	}
/*
	@Override
	public ContainerCrafter passTheContainerConstructor(InventoryPlayer playerInv, World world, BlockPos xyz,
			TileCommon tile, ManagerCrafting manager) {
		return new ContainerCrafter5by5(playerInv, world, xyz, tile, manager);
	}*/
	/***
	 * Required in clump: contructor args, tile, manager
	 */

/*
	@Override
	public GuiCommon passTheGuiConstructor(InventoryPlayer ip, World worldIn, BlockPos pos, TileCommon tile,
			ManagerCrafting manager)
	{
		return new GuiCrafter5by5(new ContainerCrafter5by5(ip, worldIn, pos, tile, manager), 183, 197);
	}*/


	@Override
	public GuiCommon<ContainerCrafter5by5,TileCrafter5by5> constrGui(ContainerCrafter5by5 cont)
	{
		return new GuiCrafter5by5(183, 197, cont, cont.getTile());
	}
	@Override
	public ContainerCrafter5by5 constrContainer(InventoryPlayer inv, TileCrafter5by5 tile, World world, BlockPos pos)
	{
		return new ContainerCrafter5by5(inv, tile, world, pos, Manager5by5.getInstance());
	}

	@Override
	public TileCrafter5by5 upCastTile(TileEntity t)
	{
		if (t instanceof TileCrafter5by5) return (TileCrafter5by5) t;
		ForceArrows.lumberjack.error("Tile Entity is not a TileCrafter5by5! Gui not getted!");
		return null;
	}


}
