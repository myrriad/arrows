package phlaxyr.largetents.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phlaxyr.largetents.blocks.BlockRegistrar;

public class PacketCClickReplacer implements IMessage{

    public BlockPos position;
    public EnumFacing placedBlockDirection;
    public EnumHand hand;
    public float facingX;
    public float facingY;
    public float facingZ;

    public PacketCClickReplacer()
    {
    }

    @SideOnly(Side.CLIENT)
    public PacketCClickReplacer(BlockPos posIn, EnumFacing placedBlockDirectionIn, EnumHand handIn, float facingXIn, float facingYIn, float facingZIn)
    {
        this.position = posIn;
        this.placedBlockDirection = placedBlockDirectionIn;
        this.hand = handIn;
        this.facingX = facingXIn;
        this.facingY = facingYIn;
        this.facingZ = facingZIn;
    }
    private static EnumFacing[] cacheFacing;
    private static EnumHand[] cacheHand;
    private EnumFacing getFacing(int ordinal) {
    	
    	if(cacheFacing == null) {
    		cacheFacing = EnumFacing.values();
    	}
		return cacheFacing[ordinal];
    			
    	
    }
    private EnumHand getHand(int ordinal) {
    	
    	if(cacheHand == null) {
    		cacheHand = EnumHand.values();
    	}
    	return cacheHand[ordinal];
    			
    	
    }
    @Override
    /**
     * Reads the raw packet data from the data stream.
     */
    public void fromBytes(ByteBuf buf)
    {
        this.position = BlockPos.fromLong(buf.readLong());
        this.placedBlockDirection = getFacing(buf.readInt());// PacketHelper.intToEnum(EnumFacing.class,buf.readInt());
        this.hand = getHand(buf.readInt());// PacketHelper.intToEnum(EnumHand.class,buf.readInt());
        this.facingX = buf.readFloat();
        this.facingY = buf.readFloat();
        this.facingZ = buf.readFloat();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void toBytes(ByteBuf buf)
    {
        buf.writeLong(this.position.toLong());
        buf.writeInt(this.placedBlockDirection.ordinal());
        buf.writeInt(this.hand.ordinal());
        buf.writeFloat(this.facingX);
        buf.writeFloat(this.facingY);
        buf.writeFloat(this.facingZ);
    }
	public static class Handler extends PacketServerHandler<PacketCClickReplacer, IMessage> {
		
		public void handle(PacketCClickReplacer msg, MessageContext ctx) {
			
			System.out.println("packet handled");
			EntityPlayerMP player = ctx.getServerHandler().player;
			World w = player.world;
			IBlockState state = w.getBlockState(msg.position);
			ItemStack istack = player.getHeldItem(msg.hand);
			BlockPos pos = msg.position;
			// this code somehow causes blocks to be placed twice (?????)
			if(Block.isEqualTo(state.getBlock(), BlockRegistrar.block_replaceBlock)) {
				BlockRegistrar.block_replaceBlock.serverOnlyActivateBlock(
						w, 
						pos, 
						state, 
						player, 
						msg.hand, 
						msg.placedBlockDirection, 
						msg.facingX, msg.facingY, msg.facingZ, istack);
			} else {
				// do nothing
				System.out.println("Do Nothing");
			}
			System.out.println(w.getBlockState(pos.up()));
			System.out.println(w.getBlockState(pos));
			// The output is good as the packet leaves
		}
	}

}
