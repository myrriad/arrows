package phlaxyr.forcearrows.craftingarrow;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

// Written with mcjty's modding tutorial at https://wiki.mcjty.eu/modding/index.php?title=Networking-1.12
public class PacketDisplayFrame implements IMessage {

	private int frameno;
	public PacketDisplayFrame(int framenoin) {
			frameno = framenoin;
	}
	public PacketDisplayFrame(){
		frameno = -1;
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

			frameno = buf.readInt();

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
		

		buf.writeInt(frameno);

		// these methods may also be of use for your code:
		// for Itemstacks - ByteBufUtils.writeItemStack()
		// for NBT tags ByteBufUtils.writeTag();
		// for Strings: ByteBufUtils.writeUTF8String();
	}

	public int getFrameno() {
		return frameno;
	}

	public static class Handler implements IMessageHandler<PacketDisplayFrame, IMessage> {
		@Override
		public IMessage onMessage(PacketDisplayFrame message, MessageContext ctx) {
			// Always use a construct like this to actually handle your message. This ensures that
			// your 'handle' code is run on the main Minecraft thread. 'onMessage' itself
			// is called on the networking thread so it is not safe to do a lot of things
			// here.
			Minecraft.getMinecraft().addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(PacketDisplayFrame message, MessageContext ctx) {
			// This code is run on the server side. So you can do server-side calculations here
			// EntityPlayerMP playerEntity = ctx.getServerHandler().player;
			// World world = playerEntity.getEntityWorld();
			// Check if the block (chunk) is loaded to prevent abuse from a client
			// trying to overload a server by randomly loading chunks
			int frame = message.getFrameno();
			
			// boom 
			//if(Minecraft.getMinecraft().currentScreen
			ArrowManager.displayFrame(frame);
			
		}
	}

	

}
