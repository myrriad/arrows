package phlaxyr.forcearrows.util;


import static phlaxyr.forcearrows.util.ArgbusMappings.BLOCKPOS;
import static phlaxyr.forcearrows.util.ArgbusMappings.GRIDX;
import static phlaxyr.forcearrows.util.ArgbusMappings.GRIDY;
import static phlaxyr.forcearrows.util.ArgbusMappings.MANAGERCRAFTEXT;
import static phlaxyr.forcearrows.util.ArgbusMappings.PLAYERINVENTORY;
import static phlaxyr.forcearrows.util.ArgbusMappings.TEXTURESIZEX;
import static phlaxyr.forcearrows.util.ArgbusMappings.TEXTURESIZEY;
import static phlaxyr.forcearrows.util.ArgbusMappings.TILECOMMON;
import static phlaxyr.forcearrows.util.ArgbusMappings.WORLD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phlaxyr.forcearrows.crafting.CraftXbyXManager;
import phlaxyr.forcearrows.tile.TileCommon;

@Deprecated
public class Argbus {
	
	private Map<Integer,Object> map= new HashMap<>();
	private InventoryPlayer inventoryPlayer;
	private World world; 
	private BlockPos blockPos; 
	private TileCommon tileCommon;
	private CraftXbyXManager managerCraftCommon; 
	private ResourceLocation resourceLocation;
	private int gridX, gridY, textureSizeX, textureSizeY;
	private List<Integer> types=new ArrayList<>();
	
	public Argbus addNeededTypes(int... types) {
		for(int type:types) {
			this.types.add(type);
		}
		return this;
	}
	public Argbus addNeededType(int type) {
		types.add(type);
		return this;
	}
	public void checkPresence() {
		if(types==null) return;
		StringBuilder strapp = new StringBuilder();
		boolean flag=false;
		for (int type:types) {
			if(keyToObj(type)==null) {
				flag=true;
				strapp.append(keyToStr(type));
				strapp.append(", ");
			}
		}
		if(flag) {
			throw new NullPointerException("null arguments: "+strapp.toString()+" note: integers must be nonzero");
		}
		
		
	}
	public Object get(int type) {
		return check(keyToObj(type));
	}
	private Object keyToObj(int type) {
		switch (type) {
			case PLAYERINVENTORY:
				return inventoryPlayer;
			case WORLD:
				return world;
			case BLOCKPOS:
				return blockPos;
			case TILECOMMON:
				return tileCommon;
			case MANAGERCRAFTEXT:
				return managerCraftCommon;
			case GRIDX:
				return gridX==0?null:gridX;
			case GRIDY:
				return gridY==0?null:gridY;
			case TEXTURESIZEX:
				return textureSizeX==0?null:textureSizeX;
			case TEXTURESIZEY:
				return textureSizeY==0?null:textureSizeY;
			default:
				return map.get(type);
		}
	}
	public String keyToStr(int type) {
		switch (type) {
		case PLAYERINVENTORY:
			return "inventoryPlayer";
		case WORLD:
			return "world";
		case BLOCKPOS:
			return "blockPos";
		case TILECOMMON:
			return "tileCommon";
		case MANAGERCRAFTEXT:
			return "managerCraftCommon";
		case GRIDX:
			return "gridX";
		case GRIDY:
			return "gridY";
		case TEXTURESIZEX:
			return "textureSizeX";
		case TEXTURESIZEY:
			return "textureSizeY";
		default:
			return ""+type;
	}
	}
	
	private Object check(Object o) {
		if(o==null) checkPresence();
		return o;
	}
	public InventoryPlayer PlayerInventory()	{return (InventoryPlayer)	check(inventoryPlayer);}
	public World World()						{return (World)				check(world);}
	public BlockPos BlockPos()					{return (BlockPos)			check(blockPos);}
	public TileCommon TileCommon()				{return (TileCommon)		check(tileCommon);}
	public CraftXbyXManager ManagerCraftCommon()	{return (CraftXbyXManager)	check(managerCraftCommon);}
	public ResourceLocation ResourceLocation()	{return (ResourceLocation)	check(resourceLocation);}
	public int gridX()	{return gridX;}
	public int gridY()	{return gridY;}
	public int textureSizeX()	{return textureSizeX;}
	public int textureSizeY()	{return textureSizeY;}
	
	public Argbus a(InventoryPlayer i) {
		inventoryPlayer = i;
		return this;
	}
	public Argbus a(World i) {
		world = i;
		return this;
	}
	public Argbus a(BlockPos i) {
		blockPos = i;
		return this;
	}
	public Argbus a(TileCommon i) {
		tileCommon = i;
		return this;
	}

	public Argbus a(CraftXbyXManager i) {
		managerCraftCommon = i;
		return this;
	}
	public Argbus a(ResourceLocation i) {
		resourceLocation = i;
		return this;
	}
	public Argbus gridXY(int gridx, int gridy) {
		gridX = gridx;
		gridY = gridy;
		return this;
	}
	public Argbus textureSizeXY(int texx, int texy) {
		textureSizeX = texx;
		textureSizeY = texy;
		return this;
	}
	public Argbus a(Object o, int key) {
		map.put(key, o);
		return this;
	}

}
