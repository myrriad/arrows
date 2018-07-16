package phlaxyr.largetents.packet;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import phlaxyr.largetents.packet.PacketDisplayFrame.Handler;

public class PacketHandler {
    private static int packetId = 7460978; // 

    public static SimpleNetworkWrapper INSTANCE = null;

    public PacketHandler() {
    }

    public static int nextID() {
        return packetId++;
    }

    public static void preinit(String channelName) {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
        registerMessages();
    }

    public static void registerMessages() {
        // Register messages which are sent from the client to the server here:
        // INSTANCE.registerMessage(PacketSummonItemArrow.Handler.class, PacketSummonItemArrow.class, nextID(), Side.SERVER);
        INSTANCE.registerMessage(PacketDisplayFrame.Handler.class, PacketDisplayFrame.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(PacketCSetReplacer.Handler.class, PacketCSetReplacer.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(PacketCClickReplacer.Handler.class, PacketCClickReplacer.class, nextID(), Side.SERVER);
    }
}