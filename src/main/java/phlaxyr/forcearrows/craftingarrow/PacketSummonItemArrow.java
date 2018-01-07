package phlaxyr.forcearrows.craftingarrow;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import phlaxyr.forcearrows.items.ItemRegistrar;

@Deprecated
public class PacketSummonItemArrow implements IMessage {
	
	public PacketSummonItemArrow(BlockPos blockposin) {
		blockpos = blockposin;
		messageIsValid = true;
	}

	public BlockPos getBlockPos() {
		return blockpos;
	}
	
	public boolean isMessageValid() {
		return messageIsValid;
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
			
			
			
			blockpos = BlockPos.fromLong(buf.readLong());
			
			
			// these methods may also be of use for your code:
			// for Itemstacks - ByteBufUtils.readItemStack()
			// for NBT tags ByteBufUtils.readTag();
			// for Strings: ByteBufUtils.readUTF8String();

		} catch (IndexOutOfBoundsException ioe) {
			System.err.println("Exception while reading PacketSummonItemArrow: " + ioe);
			return;
		}
		messageIsValid = true;
	}

	/**
	 * Called by the network code.
	 * Used to write the contents of your message member variables into the
	 * ByteBuf, ready for transmission over the network.
	 * 
	 * @param buf
	 */
	@Override
	public void toBytes(ByteBuf buf) {
		if (!messageIsValid)
			return;
		
		buf.writeLong(blockpos.toLong());
		
		
		// these methods may also be of use for your code:
		// for Itemstacks - ByteBufUtils.writeItemStack()
		// for NBT tags ByteBufUtils.writeTag();
		// for Strings: ByteBufUtils.writeUTF8String();
	}

	

	public static class Handler implements IMessageHandler<PacketSummonItemArrow, IMessage> {
        @Override
        public IMessage onMessage(PacketSummonItemArrow message, MessageContext ctx) {
            // Always use a construct like this to actually handle your message. This ensures that
            // your 'handle' code is run on the main Minecraft thread. 'onMessage' itself
            // is called on the networking thread so it is not safe to do a lot of things
            // here.
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketSummonItemArrow message, MessageContext ctx) {
            // This code is run on the server side. So you can do server-side calculations here
            EntityPlayerMP playerEntity = ctx.getServerHandler().player;
            World world = playerEntity.getEntityWorld();
            // Check if the block (chunk) is loaded to prevent abuse from a client
            // trying to overload a server by randomly loading chunks
            BlockPos pos = message.blockpos;
            
            if (world.isBlockLoaded(pos)) {
                // Block block = world.getBlockState(pos).getBlock();
                // Note: if this is a real message you want to show to a player and not a debug message you should
                // use localized messages with TextComponentTranslated.
                // playerEntity.sendStatusMessage(new TextComponentString(TextFormatting.GREEN + "Hit block: " + block.getRegistryName()), false);
                
                 
                EntityItem item = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 
                		new ItemStack(ItemRegistrar.item_craftingArrow));
                world.spawnEntity(item);
            }
        }
    }
	BlockPos blockpos;
	boolean messageIsValid;
}