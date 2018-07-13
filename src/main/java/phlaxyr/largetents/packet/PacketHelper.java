package phlaxyr.largetents.packet;

public class PacketHelper {
// see vanilla's PacketBuffer
    /**
     * Stack Overflow warns that getEnumConstants is expensive
     * @param enumClass
     * @param ordinal
     * @return
     */
	public static <T extends Enum<T>> T intToEnum(Class<T> enumClass, int ordinal)
    {
        
		
		return enumClass.getEnumConstants()[ordinal];
    }
}
