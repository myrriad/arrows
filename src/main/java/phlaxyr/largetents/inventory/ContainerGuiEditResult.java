package phlaxyr.largetents.inventory;

import javax.annotation.Nullable;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.largetents.tile.TileGuiEditor;
import phlaxyr.largetents.util.ISlotTracker;

public class ContainerGuiEditResult extends ContainerCommon<TileGuiEditor>{

	public IPlayerSlotTracker ptracker;
	public int xSize, ySize;
	/**
	 * NOTE: DOES NOT GIVE A COMPLETE CONTAINER!
	 * See the chained methods and addInventories()
	 * @param playerInv
	 * @param tile
	 * @param world
	 * @param pos
	 * @param poss
	 * @param xSize
	 * @param ySize
	 */
	public ContainerGuiEditResult(InventoryPlayer playerInv, TileGuiEditor tile, World world, BlockPos pos, ISlotTracker st,
			int xSize, int ySize, @Nullable IPlayerSlotTracker ptracker) {
		super(playerInv, tile, world, pos);
		this.xSize = xSize;
		this.ySize = ySize;
		this.ptracker = ptracker;
		this.addAllSlots(tile, st);
	}
	
	public void addAllSlots(TileGuiEditor tile, ISlotTracker st) {
		if (st != null && st.isConsistent()) {
		int index = 0;
			for (; index < st.slotCount(); index ++) { // index is the item number
				int x = st.getSlotX(index);  
				int y = st.getSlotY(index);

				if(0 <= x && x < xSize && 0 <= y && y < ySize) {

					this.addSlotToContainer(new Slot(tile, index, x, y));
					System.out.println("Adding slot "+ index + " at" + x + ", " + y);
				}
			}
		}
		
		System.out.println("Size is "+ this.inventorySlots.size());
		
		if(ptracker != null) {
			ptracker.addInventories(this);
		}
		
	}
	

	public ContainerGuiEditResult setPlayerSlotTracker(IPlayerSlotTracker ptracker) {
		this.ptracker = ptracker;
		return this;
	}
	/**
	 * Sets the size
	 * @return
	 */
	public ContainerGuiEditResult setSize(int x, int y) {
		this.xSize = x;
		this.ySize = y;
		return this;
	}
	
	public boolean isEditing() {
		return false;
	}



}