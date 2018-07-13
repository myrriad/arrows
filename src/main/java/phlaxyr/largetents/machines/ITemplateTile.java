package phlaxyr.largetents.machines;

import net.minecraft.tileentity.TileEntity;
import phlaxyr.largetents.tile.TileCommon;

public interface ITemplateTile<T extends TileCommon> {
	
    public abstract T getNewTile();
	
    /**
     * test if object is instanceof your Tile
     */
    public boolean isInstanceOf(TileEntity t);
    // public T castTile(TileEntity t);
    
    @SuppressWarnings("unchecked")
	public default T castTile(TileEntity t) {
    	if(isInstanceOf(t)) return (T) t;
    	return null;
    }
    
	public String getTileName();
}
