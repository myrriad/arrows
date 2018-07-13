package phlaxyr.largetents.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import phlaxyr.largetents.blocks.BlockRegistrar;

public class ItemTent extends ItemCommon{
    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack stack)
    {
        switch(stack.getMetadata()) {
        case 0:
        	return 1;
        }
    	return 0;
    }
    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.EAT;
    }
    /**
    * Called when the equipped item is right clicked.
    */
   public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
   {
       playerIn.setActiveHand(handIn);
       return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
   }
    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        System.out.println("ON item use finish" + stack.getMetadata());
        int md = stack.getMetadata();

        if (entityLiving instanceof EntityPlayer && !((EntityPlayer)entityLiving).capabilities.isCreativeMode)
        {
            stack.shrink(1);
        }
        
    	switch(md) {
        case 0:
//        	Vec3d origin = entityLiving.getPositionVector(); // one below
//        	System.out.println(origin);
////        	tryPlace(worldIn, filledCircle(origin.getX(), origin.getY(), origin.getZ(), 3));
////        	tryPlace(worldIn, emptyCircle(origin.getX(), origin.getY() + 1, origin.getZ(), 3));
////        	tryPlace(worldIn, emptyCircle(origin.getX(), origin.getY() + 2, origin.getZ(), 2));
////        	tryPlace(worldIn, emptyCircle(origin.getX(), origin.getY() + 3, origin.getZ(), 1));
//        	tryPlace(worldIn, emptyCircle(
//        			(int)origin.x, 
//        			(int)origin.y + 1, 
//        			(int)origin.z, 3)); // subtract 0.5 to correct rounding
        	BlockPos origin = entityLiving.getPosition().down();
        	List<BlockPos> poss = new ArrayList<>();
        	poss.add(origin);
        	poss.addAll(sliceToPos(origin, 
        			"_###_",
        			"#####",
        			"##0##",
        			"#####",
        			"_###_"));
        	for(int i=0;i<2;i++) {
	        	origin = origin.up();
	        	poss.addAll(sliceToPos(origin, 
	        			"_###_",
	        			"#___#",
	        			"#_0_#",
	        			"#___#",
	        			"_###_"));
        	}
        	origin = origin.up();
        	poss.add(origin);
        	poss.addAll(sliceToPos(origin, 
        			"###",
        			"#O#",
        			"###"));
        	tryPlace(worldIn, poss);
        	break;
        }
        return stack;
    }
    public float sizes = 1;
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            for (int i = 0; i < sizes; ++i)
            {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }
    
    public void tryPlace(World worldIn, BlockPos at) {
    	if(worldIn.isRemote) return;
    	if(BlockRegistrar.block_replaceBlock.canPlaceBlockAt(worldIn, at)) {
    		worldIn.setBlockState(at, BlockRegistrar.block_replaceBlock.getDefaultState(), 3);
    	}
    }
    public void tryPlace(World worldIn, Collection<BlockPos> poss) {
    	for(BlockPos pos : poss) tryPlace(worldIn, pos);
    }
    /**
     * does not include origin; you can add that to this list before parsing so 
     * @param origin
     * @param slices
     * @return
     */
    public List<BlockPos> sliceToPos(BlockPos origin, String...slices) {
    	int originx, originy;
    	originx = originy = 0;
    	List<Integer> xs = new ArrayList<>();
    	List<Integer> zs = new ArrayList<>();
    	List<BlockPos> ret = new ArrayList<>();
    	for(int i=0;i<slices.length; i++) {
    		String s = slices[i];
    		for(int j=0;j<s.length();j++) {
    			char c = s.charAt(j);
    			if(c == '0' || c == 'O') {
    				originx = j;
    				originy = i;
    			} else if(c == '#') {
    				xs.add(j);
    				zs.add(i);
    				// todo clarify x/z difference
    			} else {} // air
    		}
    	}
    	for(int i=0;i<xs.size();i++) {
    		int x = xs.get(i);
    		int z = zs.get(i);
    		ret.add(origin.add(x-originx, 0, z-originy));
    	}
    	return ret;
    }

    
    Set<BlockPos> emptyCircle(int x0, int y, int z0, int radius)
    {
        int x = radius-1;
        int z = 0;
        int dx = 1;
        int dz = 1;
        int err = dx - (radius << 1);
        Set<BlockPos> bps = new HashSet<BlockPos>();
        while (x >= z)
        {
            bps.add(new BlockPos(x0 + x, y, z0 + z));
            bps.add(new BlockPos(x0 + z, y, z0 + x));
            bps.add(new BlockPos(x0 - z, y, z0 + x));
            bps.add(new BlockPos(x0 - x, y, z0 + z));
            bps.add(new BlockPos(x0 - x, y, z0 - z));
            bps.add(new BlockPos(x0 - z, y, z0 - x));
            bps.add(new BlockPos(x0 + z, y, z0 - x));
            bps.add(new BlockPos(x0 + x, y, z0 - z));

            if (err <= 0)
            {
                z++;
                err += dz;
                dz += 2;
            }
            
            if (err > 0)
            {
                x--;
                dx += 2;
                err += dx - (radius << 1);
            }
        }
        return bps;
    }
    Set<BlockPos> filledCircle(int x0, int y, int z0, int radius)
    {
        int x = radius-1;
        int z = 0;
        int dx = 1;
        int dz = 1;
        int err = dx - (radius << 1);
        Set<BlockPos> bps = new HashSet<BlockPos>();
        while (x >= z)
        {
            add(BlockPos.getAllInBox(new BlockPos(x0 + x, y, z0 + z),new BlockPos(x0 - x, y, z0 + z)), bps);
            add(BlockPos.getAllInBox(new BlockPos(x0 - x, y, z0 - z),new BlockPos(x0 + x, y, z0 - z)), bps);
            add(BlockPos.getAllInBox(new BlockPos(x0 + z, y, z0 + x),new BlockPos(x0 - z, y, z0 + x)), bps);
            add(BlockPos.getAllInBox(new BlockPos(x0 - z, y, z0 - x),new BlockPos(x0 + z, y, z0 - x)), bps);


            if (err <= 0)
            {
                z++;
                err += dz;
                dz += 2;
            }
            
            if (err > 0)
            {
                x--;
                dx += 2;
                err += dx - (radius << 1);
            }
        }
        return bps;
    }
    public static <E> Collection<E> add(Iterable<E> iter, Collection<E> to) {
        for (E item : iter) {
            to.add(item);
        }
        return to;
    }
}
