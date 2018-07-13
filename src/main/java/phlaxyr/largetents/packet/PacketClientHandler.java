package phlaxyr.largetents.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * A message handler based on {@link IMessage}. Implement and override {@link #onMessage(IMessage, MessageContext)} to
 * process your packet. Supply the class to {@link SimpleNetworkWrapper#registerMessage(Class, Class, int, net.minecraftforge.fml.relauncher.Side)}
 * to register both the message type and it's associated handler.
 *
 * @author cpw
 *
 * @param <REQ> This is the request type - it is the message you expect to <em>receive</em> from remote.
 * @param <REPLY> This is the reply type - it is the message you expect to <em>send</em> in reply. You can use IMessage as the type here
 * if you don't anticipate sending a reply.
 */
public abstract class PacketClientHandler<REQ extends IMessage, REPLY extends IMessage> implements IMessageHandler<REQ, REPLY>{

	public REPLY onMessage(final REQ message, MessageContext ctx) {
		if (ctx.side != Side.CLIENT) {
			System.err.println(message.getClass().getSimpleName() + " received on wrong side:" + ctx.side);
			return null;
		}

		Minecraft minecraft = Minecraft.getMinecraft();
		minecraft.addScheduledTask(new Runnable() {
			public void run() {
				handle(message, ctx);
			}
		});

		return null;
	}

	public abstract void handle(REQ msg, MessageContext ctx);
}
