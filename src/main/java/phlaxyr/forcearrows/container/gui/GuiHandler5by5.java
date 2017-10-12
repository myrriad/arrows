package phlaxyr.forcearrows.container.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.forcearrows.container.ContainerCrafter5by5;
import phlaxyr.forcearrows.container.ContainerCrafterExt;
import phlaxyr.forcearrows.crafting.manager.Manager5by5;
import phlaxyr.forcearrows.crafting.manager.ManagerCraftCommon;
import phlaxyr.forcearrows.tile.TileCommon;

public class GuiHandler5by5 extends GuiHandlerCommon{
	public static int GUIID=1943;
	public GuiHandler5by5() {
		super(GUIID, Manager5by5.getInstance());

	}

	@Override
	public ContainerCrafterExt passTheContainerConstructor(InventoryPlayer playerInv, World world, BlockPos xyz,
			TileCommon tile, ManagerCraftCommon manager) {
		return new ContainerCrafter5by5(playerInv, world, xyz, tile, manager);
	}

	@Override
	public GuiCommon passTheGuiConstructor(InventoryPlayer ip, World worldIn, BlockPos pos, TileCommon tile,
			ManagerCraftCommon manager)
	{
		return new GuiCrafter5by5(183, 197, ip, worldIn, pos, tile, manager);
	}
}
