package phlaxyr.largetents.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phlaxyr.largetents.LargeTents;
import phlaxyr.largetents.Lumber;

public class BlockAuLieu extends BlockCommon {

	public BlockAuLieu() {
		super(Material.CARPET, LargeTents.TAB);
	}
	private boolean debug = false;
	// i think i found the bug
	// SERVERSIDE: the packet is first, it changes the block to a melon.
	// then, forge sees the melon and tries to rclick on it.
	// PlayerControllerMP:
	// 1. onBlockActivated called
	// 1a. custom packet is sent
	// 2. rclick packet is sent
	// Server:
	// 1. custom packet receive; rclick; change block
	// 2. rclick packet receive; rclick on changed block; duplicate rclick
	/**
	 * Called when the block is right clicked by a player.
	 */
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		boolean sendPacket = true;
		System.out.println("onBlockActivated");
		// why fence gates don't work: this is executed only client-side
		ItemStack istack = playerIn.getHeldItem(hand);
		if (istack.isEmpty()) {
			return false;
		}

		if(worldIn.isRemote) {
			// System.out.println("Sending to Server the Packet");
			if(!debug || sendPacket) { // if packet is not sent, the rclick is swallowed, 
				//so the rclick is properly swallowed by the `return true`s, suggesting that the block is not placed that way
				// PacketHandler.INSTANCE.sendToServer(new PacketCClickReplacer(pos, facing, hand, hitX, hitY, hitZ));
			}
            if (istack.getItem() instanceof ItemBlock)
            {
                ItemBlock itemblock = (ItemBlock)istack.getItem();

                if (!itemblock.canPlaceBlockOnSide(worldIn, pos, facing, playerIn, istack))
                {
                   // if this is true then the rclick will fail
                	return true;
                }
            } 
			worldIn.setBlockToAir(pos); // client-side only
			return false; // this makes the client send a packet + have it try to place a block.
		} else {
//			System.out.println("Server is activating block!");
//			// serverside 
			serverOnlyActivateBlock(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ, istack);
			return true;
		}

	}
	

	public void serverOnlyActivateBlock(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, ItemStack istack) {
		boolean skipAll = false;
		boolean skipRClick = false;
		boolean placeMelonInstead = true;
		if(worldIn.isRemote) throw new IllegalArgumentException("Server only!");
		
		if(debug && skipAll) return;
		System.out.println("serverOnlyActivateBlock");
//		System.out.println(pos);
//		System.out.println(state);
		System.out.println(worldIn.getBlockState(pos));
		if(!Block.isEqualTo(worldIn.getBlockState(pos).getBlock(), BlockRegistrar.block_replaceBlock)) {
			System.out.println("Returning due to the block "+pos+" already being nonreplacer!");
			return;
		}
		
		// boolean doorfix = worldIn.getBlockState(pos.up()).getBlock() == BlockRegistrar.block_replaceBlock;
		// if the block above should be counted
		worldIn.setBlockToAir(pos);
		// if(doorfix) worldIn.setBlockToAir(pos.up()); // door fix
		
//		System.out.println(worldIn.getBlockState(pos));
		
		EntityPlayerMP player = (EntityPlayerMP) playerIn;
		Block b = Block.getBlockFromItem(istack.getItem());
		
		if (b != null && b != Blocks.AIR) {
			
			if (!b.canPlaceBlockAt(worldIn, pos)) { // preemptive failing
				
				checkRevert1(worldIn, pos, player);
				player.connection.sendPacket(new SPacketBlockChange(worldIn, pos));
				return;
			}
			
			
		}
		
		Lumber.jack.debug("Block before is "+worldIn.getBlockState(pos).getBlock());
		EnumActionResult e;
		if(debug && placeMelonInstead) { // if placeMelonInstead=true then it still right clicks 
			
			System.out.println("PLACING A MELON");
			worldIn.setBlockState(pos, Blocks.MELON_BLOCK.getDefaultState());
			// e = EnumActionResult.SUCCESS; 	
			e = EnumActionResult.SUCCESS;
			
		} else if(!debug || !skipRClick) { 
			
//			System.out.println("RCLICKING!");
			e = player.interactionManager.processRightClickBlock(player, worldIn, istack, hand, pos,
				facing, hitX, hitY, hitZ);
			
		} else {
			e = EnumActionResult.SUCCESS; 	
		}
		// packet sent
		Lumber.jack.debug("Block is now "+worldIn.getBlockState(pos).getBlock());
		
		checkRevert1(worldIn, pos, player);
		player.connection.sendPacket(new SPacketBlockChange(worldIn, pos));
		
		
	}
	
//	
//	protected void checkRevertAll(World worldIn, BlockPos pos, EntityPlayerMP player, boolean doorfix) {
//		checkRevert1(worldIn, pos);
//		if(doorfix) checkRevert1(worldIn, pos.up());
//	}
	/**
	 * Return: whether anything was modified
	 * @param worldIn
	 * @param pos
	 * @return
	 */
	private boolean checkRevert1(World worldIn, BlockPos pos, EntityPlayer player) {
		if(worldIn.getBlockState(pos).getBlock() == Blocks.AIR) {
			worldIn.setBlockState(pos, BlockRegistrar.block_replaceBlock.getDefaultState(), 3); // abort! abort!
			return true;
		}
		return false;
	}
//		player.connection.sendPacket(new SPacketBlockChange(worldIn, pos));
//		return true;
//	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}
//
//	// PlayerController
//	private EnumActionResult clientProcessRightClickBlock(PlayerControllerMP cont, EntityPlayerSP player, WorldClient worldIn, BlockPos pos, EnumFacing direction, Vec3d vec, EnumHand hand) {
//		// cont.syncCurrentPlayItem();
//		Minecraft mc = Minecraft.getMinecraft();
//        ItemStack itemstack = player.getHeldItem(hand);
//        float f = (float)(vec.x - (double)pos.getX());
//        float f1 = (float)(vec.y - (double)pos.getY());
//        float f2 = (float)(vec.z - (double)pos.getZ());
//        boolean flag = false;
//
//        if (!mc.world.getWorldBorder().contains(pos))
//        {
//            return EnumActionResult.FAIL;
//        }
//        else
//        {
//            net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock event = net.minecraftforge.common.ForgeHooks
//                    .onRightClickBlock(player, hand, pos, direction, net.minecraftforge.common.ForgeHooks.rayTraceEyeHitVec(player, cont.getBlockReachDistance() + 1));
//            /**^forge*/
//            if (event.isCanceled())
//            {
//                // Give the server a chance to fire event as well. That way server event is not dependant on client event.
//                //this.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f1, f2));
//                return event.getCancellationResult();
//            }
//            EnumActionResult result = EnumActionResult.PASS;
//
//            if (cont.getCurrentGameType() != GameType.SPECTATOR)
//            {
//                EnumActionResult ret = itemstack.onItemUseFirst(player, worldIn, pos, hand, direction, f, f1, f2);
//                if (ret != EnumActionResult.PASS)
//                {
//                    // The server needs to process the item use as well. Otherwise onItemUseFirst won't ever be called on the server without causing weird bugs
//                    // this.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f1, f2));
//                    return ret;
//                }
//
//                IBlockState iblockstate = worldIn.getBlockState(pos);
//                boolean bypass = player.getHeldItemMainhand().doesSneakBypassUse(worldIn, pos, player) && player.getHeldItemOffhand().doesSneakBypassUse(worldIn, pos, player);
//
//                if ((!player.isSneaking() || bypass || event.getUseBlock() == net.minecraftforge.fml.common.eventhandler.Event.Result.ALLOW))
//                {
//                    if (event.getUseBlock() != net.minecraftforge.fml.common.eventhandler.Event.Result.DENY)
//                    //{}
//                    flag = iblockstate.getBlock().onBlockActivated(worldIn, pos, iblockstate, player, hand, direction, f, f1, f2);
//                    if (flag) result = EnumActionResult.SUCCESS;
//                }
//
//                if (!flag && itemstack.getItem() instanceof ItemBlock)
//                {
//                    ItemBlock itemblock = (ItemBlock)itemstack.getItem();
//
//                    if (!itemblock.canPlaceBlockOnSide(worldIn, pos, direction, player, itemstack))
//                    {
//                        return EnumActionResult.FAIL;
//                    }
//                }
//            }
//
//            // this.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f1, f2));
//
//            if (!flag && cont.getCurrentGameType() != GameType.SPECTATOR || event.getUseItem() == net.minecraftforge.fml.common.eventhandler.Event.Result.ALLOW)
//            {
//                if (itemstack.isEmpty())
//                {
//                    return EnumActionResult.PASS;
//                }
//                else if (player.getCooldownTracker().hasCooldown(itemstack.getItem()))
//                {
//                    return EnumActionResult.PASS;
//                }
//                else
//                {
//                    if (itemstack.getItem() instanceof ItemBlock && !player.canUseCommandBlock())
//                    {
//                        Block block = ((ItemBlock)itemstack.getItem()).getBlock();
//
//                        if (block instanceof BlockCommandBlock || block instanceof BlockStructure)
//                        {
//                            return EnumActionResult.FAIL;
//                        }
//                    }
//
//                    if (cont.getCurrentGameType().isCreative())
//                    {
//                        int i = itemstack.getMetadata();
//                        int j = itemstack.getCount();
//                        if (event.getUseItem() != net.minecraftforge.fml.common.eventhandler.Event.Result.DENY) {
//	                        EnumActionResult enumactionresult = itemstack.onItemUse(player, worldIn, pos, hand, direction, f, f1, f2);
//	                        itemstack.setItemDamage(i);
//	                        itemstack.setCount(j);
//	                        return enumactionresult;
//                        } else return result;
//                    }
//                    else
//                    {
//                        ItemStack copyForUse = itemstack.copy();
//                        if (event.getUseItem() != net.minecraftforge.fml.common.eventhandler.Event.Result.DENY)
//                        result = itemstack.onItemUse(player, worldIn, pos, hand, direction, f, f1, f2);
//                        if (itemstack.isEmpty()) net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(player, copyForUse, hand);
//                        /**^forge*/
//                        return result;
//                    }
//                }
//            }
//            else
//            {
//                return EnumActionResult.SUCCESS;
//            }
//        }
//	}

}
