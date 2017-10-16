package phlaxyr.forcearrows.inventory;

import net.minecraft.block.BlockContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.forcearrows.ForceArrows;
import phlaxyr.forcearrows.tile.TileCommon;

public abstract class ContainerCommon<T extends TileCommon> extends Container{
    /**measured at the upper left corner (inside the black border)*/
    public abstract int bodyX();
    /**measured at the upper left corner (inside the black border)*/
    public abstract int bodyY();
    /**measured at the upper left corner (inside the black border)*/
    public int hotbarY() {
    	return bodyY() + 58;
    }
    /**measured at the upper left corner (inside the black border)*/
    public int hotbarX() {
    	return bodyX();
    }
    
	private T tile;
    protected EntityPlayer player;
    protected final World world;
    /** Position of the workbench */
    protected final BlockPos pos;
    
    // public final ClumpContainer clump;
    public T getTile(){
    	return tile;
    }
    
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.world.getBlockState(this.pos).getBlock() 
        		instanceof BlockContainer ? withinVicinity(playerIn) : false;
    }
    /*
    public ContainerCommon(InventoryPlayer playerInventory, World worldIn, 
    		BlockPos posIn, TileCommon tile) {
    	playerInv = playerInventory.player;
    	this.tile=tile;
        this.world = worldIn;
        this.pos = posIn;
        
    }*/
    /***
     * Required in clump: constructor args, tile
     * 
     * @param clump
     */
    public ContainerCommon(InventoryPlayer playerInv, T tile, World world, BlockPos pos) {
    	// this.clump = clump;
    	if(playerInv.player == null) ForceArrows.lumberjack.error("NULL Player");
    	this.player = playerInv.player;
    	this.tile = tile;
    	this.world = world;
    	this.pos = pos;
    }
    
    
    protected void addInventories(){
    	int index = 0;
    	
    	//hotbar
        for (int l = 0; l < 9; ++l)
        {
            this.addSlotToContainer(new Slot(player.inventory, /*l*/index++, hotbarX() + l * 18, hotbarY()));
        }
        //main inventory
        for (int k = 0; k < 3; ++k) //rows
        {
            for (int i1 = 0; i1 < 9; ++i1) //slots in each row
            {
                this.addSlotToContainer(new Slot(player.inventory, 
                		// index
                		 //9 + /*slot in row*/i1 + /*row*/k * 9, 
                		index++,
                		bodyX() + i1 * 18, bodyY() + k * 18));
            }
        }


    	
    }
    public boolean withinVicinity(EntityPlayer playerIn) {
    	 return playerIn.getDistanceSq(
    			 (double)this.pos.getX() + 0.5D, 
    			 (double)this.pos.getY() + 0.5D, 
    			 (double)this.pos.getZ() + 0.5D
    			 ) <= 64.0D;
    }
}
