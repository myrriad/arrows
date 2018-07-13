package phlaxyr.largetents.inventory;

import net.minecraft.inventory.Slot;
import phlaxyr.largetents.tile.TileGuiEditor;

/**
 * Tracks the positions of various slots in a player's inventory
 * @author phlaxyr
 *
 */
public interface IPlayerSlotTracker {
	
	/**Player's Inventory; measured at the upper left corner (inside the black border)*/
    public int bodyX();
    /**Player's Inventory; measured at the upper left corner (inside the black border)*/
    public int bodyY();
    
    /**measured at the upper left corner (inside the black border)*/
    public default int hotbarY() {
    	return bodyY() + 58;
    }
    /**measured at the upper left corner (inside the black border)*/
    public default int hotbarX() {
    	return bodyX();
    }
    
    /**
     * @return 
     * 
     */
    public default void addInventories(ContainerCommon<?> c){
    	
    	int index = 0;
    	//hotbar
        for (int l = 0; l < 9; ++l)
        {
            c.addSlotToContainer(new Slot(c.player.inventory, /*l*/index++, hotbarX() + l * 18, hotbarY()));
        }
        //main inventory
        for (int k = 0; k < 3; ++k) //rows
        {
            for (int i1 = 0; i1 < 9; ++i1) //slots in each row
            {
                c.addSlotToContainer(new Slot(c.player.inventory, 
                		// index
                		 //9 + /*slot in row*/i1 + /*row*/k * 9, 
                		index++,

                		bodyX() + i1 * 18, bodyY() + k * 18));
//                System.out.println(bodyX() + i1 * 18);
//                System.out.println(bodyY() + k * 18);
            }
        }
        

        
    	
    }
}
