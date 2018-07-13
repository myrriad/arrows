package phlaxyr.largetents.inventory;

import net.minecraft.block.BlockContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.largetents.LargeTents;
import phlaxyr.largetents.tile.TileCommon;

public abstract class ContainerCommon<T extends TileCommon> extends Container{
//    /**Player's Inventory; measured at the upper left corner (inside the black border)*/
//    public abstract int bodyX();
//    /**Player's Inventory; measured at the upper left corner (inside the black border)*/
//    public abstract int bodyY();
//    /**measured at the upper left corner (inside the black border)*/
//    public int hotbarY() {
//    	return bodyY() + 58;
//    }
//    /**measured at the upper left corner (inside the black border)*/
//    public int hotbarX() {
//    	return bodyX();
//    }
//    
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
    	if(playerInv.player == null) LargeTents.lumberjack.error("NULL Player");
    	this.player = playerInv.player;
    	this.tile = tile;
    	this.world = world;
    	this.pos = pos;
    	// addAllSlots(playerInv, tile, world, pos);
    }
    
    // protected abstract void addAllSlots(InventoryPlayer playerInv, T tile, World world, BlockPos pos);
    // allows my package and thus IPlayerTracker to see this and thus be able to call this
    @Override
    protected Slot addSlotToContainer(Slot s) {
    	return super.addSlotToContainer(s);
    }
    
    public boolean withinVicinity(EntityPlayer playerIn) {
    	 return playerIn.getDistanceSq(
    			 (double)this.pos.getX() + 0.5D, 
    			 (double)this.pos.getY() + 0.5D, 
    			 (double)this.pos.getZ() + 0.5D
    			 ) <= 64.0D;
    }
}
