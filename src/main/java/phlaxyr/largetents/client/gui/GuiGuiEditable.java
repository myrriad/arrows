package phlaxyr.largetents.client.gui;

import java.util.List;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import phlaxyr.largetents.inventory.ContainerGuiEditResult;
import phlaxyr.largetents.tile.TileGuiEditor;

public class GuiGuiEditable extends GuiCommon<TileGuiEditor, ContainerGuiEditResult>{

//	ResourceLocation corners;
//	ResourceLocation borders;
	public static final int BSIZE = 4;
	ResourceLocation both;
	ResourceLocation slot;
	
	int color = 0xFFc6c6c6;
	public GuiGuiEditable(ContainerGuiEditResult cont, ResourceLocation both, ResourceLocation slot) {
		super(cont.xSize, cont.ySize, cont, both);
		this.both = both;
		this.slot = slot;
	}

    int i;
    int j;
    int k;
    int l;
    int bi;
    int bj;
    int bk;
    int bl;
    protected void helperValues() {
    	i = (this.width - this.xSize) / 2; // left border
        j = (this.height - this.ySize) / 2; // top border
        k = i + xSize; // right border excl
        l = j + ySize; // bottom border excl

        
        // innerbox: the largest box which does not include the corners
        bi = i + 4; // left border of inner box incl
        bj = j + 4; // top border of inner box incl
        bk = k - 4; // right border of inner box excl
        bl = l - 4; // bottom border of inner box excl
    }
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
    	helperValues();
    	
    	this.mc.renderEngine.bindTexture(both);
    	
        drawBorders();
    	drawCorners(); // afaik working
    	
    	drawRect(bi, bj, bk, bl, color);    	
        
    	this.mc.renderEngine.bindTexture(slot);
    	drawSlots();

    }
    protected void drawSlots() {
    	List<Slot> slots = this.inventorySlots.inventorySlots;
    	
    	
    	for(Slot s : slots) {
    		// System.out.println("slot " + s.xPos + " " + s.yPos);
    		this.drawTexturedModalRect(i + s.xPos - 1, j + s.yPos - 1 , 0, 0, 18, 18);
    	}
    	
    }
    protected void drawCorners() {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        
        // test
        // this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        // top left u,v = 0,0
        drawCSizeTexture(
        		i, j, 
        		0, 0, 
        		4, 4);

        // top right u,v = 4 + 256,0
        drawCSizeTexture(
        		k - 4, j, 
        		4 + 256, 0, 
        		4, 4);
        
        // bottom left u,v = 0,4 + 256
        drawCSizeTexture(
        		i, l - 4, 
        		0, 4 + 256, 
        		4, 4);
        
        // bottom right u,v = 4 + 256,4 + 256
        drawCSizeTexture(
        		k - 4, l - 4, 
        		4 + 256, 4 + 256, 
        		4, 4);
        
    }
    protected void drawBorders() {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        // i = left border
        // j = top border
        // k = right border excl
        // l = bottom border excl

        
        // innerbox: the largest box which does not include the corners
        // bi = left border of inner box incl
        // bj = top border of inner box incl
        // bk = right border of inner box excl
        // bl = bottom border of inner box excl
        
        // inner box dimensions: width, height
        int width = this.xSize - 4 - 4; // 4 is the size of the corners
        int height = this.ySize - 4 - 4;
        
        // last 2 parameters are u,v
        // top good
        drawHorizontalBorder(bi, j, width, 4, 0);
        
        // left good
        drawVerticalBorder(i, bj, height, 0, 4);
        
        // bottom TODO : u, v appear to be 4, 4
        drawHorizontalBorder(bi, bl, width, 4, 4 + 256);
        
        // right TODO : u, v also appear to be 4, 4
        drawVerticalBorder(bk, bj, height, 4 + 256, 4);
        
    }

    /**
     * Texture: uses the tile lcorner (u, v)
     */
    protected void drawHorizontalBorder(int x, int y, int width, int u, int v) {
    	
    	int reps = width / 256;
    	int remaining = width - reps * 256;
    	// System.out.println(u + " " + v + " " + remaining);
    	for(int i=0; i < reps; i++) {
    		drawCSizeTexture(i * 256 + x, y, u, v, 256, BSIZE);
    		
    	}
    	// this.drawTexturedModalRect(reps * 256 + x, y, u, v, remaining, BSIZE);
    	drawCSizeTexture(reps * 256 + x, y, u, v, remaining, BSIZE);
    }
    /**
     * Texture: uses the tile lcorner (u, v)
     */
    protected void drawVerticalBorder(int x, int y, int height, int u, int v) {
    	

    	int reps = height / 256;
    	int remaining = height - reps * 256;
    	// System.out.println(u + " " + v + " " + remaining);
    	for(int i=0; i < reps; i++) {
    		drawCSizeTexture(x, i * 256 + y, u, v, BSIZE, 256);
    		
    	}
    	
//    	this.drawTexturedModalRect(x, reps * 256 + y, u, v, BSIZE, remaining);
    	drawCSizeTexture(x, reps * 256 + y, u, v, BSIZE, remaining);
    }
    
    

    /**
     * Helper with custom size texture
     * Hopefully java caches f and f1
     * Taken from drawModalRectWithCustomSizedTexture
     * @param x
     * @param y
     * @param u
     * @param v
     * @param width
     * @param height
     */
    public static void drawCSizeTexture(int x, int y, float u, float v, int width, int height)
    {
        float f = 1.0F / (float)(256 + 4 + 4);
        float f1 = 1.0F / (float)(256 + 4 + 4);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)x, (double)(y + height), 0.0D).tex((double)(u * f), (double)((v + (float)height) * f1)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + height), 0.0D).tex((double)((u + (float)width) * f), (double)((v + (float)height) * f1)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)y, 0.0D).tex((double)((u + (float)width) * f), (double)(v * f1)).endVertex();
        bufferbuilder.pos((double)x, (double)y, 0.0D).tex((double)(u * f), (double)(v * f1)).endVertex();
        tessellator.draw();
    }
    




}
