package phlaxyr.largetents.util;

public interface ISlotTracker {
	public void initSlotItems(int... iarray);
	

	public int[] toIntArray();
	public int slotCount();
	public int itemCount();
	public int getSlotX(int i);
	public int getSlotY(int i);
	public void setSlotX(int i, int x);
	public void setSlotY(int i, int y);
	public void resizeTo(int newsize);
	
	/**
	 * if inconsistent, a runtimeexception is thrown
	 * @return
	 */
	public default boolean isConsistent() {
		if(slotCount() != itemCount()) throw new RuntimeException("SlotTracker's slotcount/itemcount doesn't match. SlotCount: "+ slotCount() + ", ItemCount:" + itemCount());
		if(toIntArray().length % 2 != 0) throw new RuntimeException("SlotTracker's internal array is of odd len");
		return true;
	}
	
		// System.out.println("Slotpos is " + Arrays.toString(st.internal));
	
}
