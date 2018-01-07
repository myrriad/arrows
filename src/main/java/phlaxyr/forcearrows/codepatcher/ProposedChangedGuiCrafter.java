package phlaxyr.forcearrows.codepatcher;

import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;


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
