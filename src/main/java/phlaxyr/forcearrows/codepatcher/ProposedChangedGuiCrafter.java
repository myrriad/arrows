package phlaxyr.forcearrows.codepatcher;

import java.util.ArrayList;

import java.util.List;

import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;
import phlaxyr.forcearrows.event.ArrowManager;
import phlaxyr.forcearrows.event.ArrowShearAnimation;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.item.crafting.CraftingManager;

@SuppressWarnings("unused")
public class ProposedChangedGuiCrafter extends GuiCrafting{
	
	public ProposedChangedGuiCrafter(InventoryPlayer playerInv, World worldIn)
	{
		super(playerInv, worldIn);
	}
	CraftingManager m;
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
		// ArrowManager.newAnim(this);
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    }
}
