package phlaxyr.largetents.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import phlaxyr.largetents.blocks.BlockRegistrar;

public class PacketCSetReplacer implements IMessage{

	public BlockPos pos;
	public PacketCSetReplacer(BlockPos pos) {
			this.pos = pos;

	}
	public PacketCSetReplacer(){
	}



	/**
	 * Called by the network code once it has received the message bytes over
	 * the network.
	 * Used to read the ByteBuf contents into your member variables
	 * 
	 * @param buf
	 */
	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			
			pos = BlockPos.fromLong(buf.readLong());
			// these methods may also be of use for your code:
			// for Itemstacks - ByteBufUtils.readItemStack()
			// for NBT tags ByteBufUtils.readTag();
			// for Strings: ByteBufUtils.readUTF8String();

		} catch (IndexOutOfBoundsException ioe) {
			System.err.println("Exception while reading PacketDisplayFrame: " + ioe);
			return;
		}
	}

	/**
	 * Called by the network code.
	 * Used to write the contents of your message member variables into the
	 * ByteBuf, ready for transmission over the network.
	 * 
	 * -1 means I am uninstantiated
	 * @param buf
	 */
	@Override
	public void toBytes(ByteBuf buf) {
		
		// buf.writeInt(frameno);
		buf.writeLong(pos.toLong());
		// these methods may also be of use for your code:
		// for Itemstacks - ByteBufUtils.writeItemStack()
		// for NBT tags ByteBufUtils.writeTag();
		// for Strings: ByteBufUtils.writeUTF8String();
	}

	

	public static class Handler extends PacketClientHandler<PacketCSetReplacer, IMessage> {
		
		public void handle(PacketCSetReplacer message, MessageContext ctx) {
			WorldClient world = Minecraft.getMinecraft().world;
			BlockPos p = message.pos;
			world.setBlockState(p, BlockRegistrar.block_replaceBlock.getDefaultState());
			System.out.println("HANDLED!");
		}
	}
}
