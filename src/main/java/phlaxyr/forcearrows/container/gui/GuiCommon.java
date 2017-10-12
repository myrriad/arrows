package phlaxyr.forcearrows.container.gui;

import java.awt.Color;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.forcearrows.container.ContainerCommon;
import phlaxyr.forcearrows.container.ContainerCrafter5by5;
import phlaxyr.forcearrows.crafting.manager.ManagerCraftCommon;
import phlaxyr.forcearrows.tile.TileCommon;

public abstract class GuiCommon extends GuiContainer{

	private final ResourceLocation tex;
	
    TileCommon tile;
	public GuiCommon(int textureSizeX, int textureSizeY, InventoryPlayer ip, World worldIn, BlockPos pos, 
			TileCommon tile2, ManagerCraftCommon manager, ResourceLocation tex) {
		super(new ContainerCrafter5by5(ip, worldIn, pos, tile2, manager));
		this.tile = tile2;
		this.tex=tex;
    	this.xSize = textureSizeX;
    	this.ySize = textureSizeY;
	}

	public GuiCommon(int textureSizeX, int textureSizeY, ContainerCommon cont, TileCommon tile, ResourceLocation tex) {
		super(cont);
		this.tile = tile;
		this.tex=tex;
    	this.xSize = textureSizeX;
    	this.ySize = textureSizeY;
	}
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        this.mc.renderEngine.bindTexture(tex);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int width = (this.width - this.xSize) / 2;
        int height = (this.height - this.ySize) / 2;
        //change
        this.drawTexturedModalRect(width, height, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j)
    {
		this.fontRenderer.drawString(tile.getDisplayName().getUnformattedText(), 5, 5, Color.darkGray.getRGB());
        //this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
