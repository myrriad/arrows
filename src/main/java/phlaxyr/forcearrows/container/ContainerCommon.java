package phlaxyr.forcearrows.container;

import net.minecraft.block.BlockContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.forcearrows.tile.TileCommon;

public abstract class ContainerCommon extends Container{
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
	protected TileCommon tile;
    public EntityPlayer player;
    final World world;
    /** Position of the workbench */
    final BlockPos pos;
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.world.getBlockState(this.pos).getBlock() 
        		instanceof BlockContainer ? withinVicinity(playerIn) : false;
    }
    public ContainerCommon(InventoryPlayer playerInventory, World worldIn, 
    		BlockPos posIn, TileCommon tile) {
    	player = playerInventory.player;
    	this.tile=tile;
        this.world = worldIn;
        this.pos = posIn;
    }
    public boolean withinVicinity(EntityPlayer playerIn) {
    	 return playerIn.getDistanceSq(
    			 (double)this.pos.getX() + 0.5D, 
    			 (double)this.pos.getY() + 0.5D, 
    			 (double)this.pos.getZ() + 0.5D
    			 ) <= 64.0D;
    }
}
