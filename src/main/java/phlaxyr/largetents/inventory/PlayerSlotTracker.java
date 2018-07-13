package phlaxyr.largetents.inventory;

public class PlayerSlotTracker implements IPlayerSlotTracker{
	
	public int bodyX, bodyY;
	public int hotbarX, hotbarY;
	public PlayerSlotTracker(int bodyX, int bodyY) {
		this.bodyX = bodyX;
		this.bodyY = bodyY;
	}
	{
		hotbarX = -1;
		hotbarY = -1;
	}
	@Override
	public int bodyX() {
		return bodyX;
	}

	@Override
	public int bodyY() {
		return bodyY;
	}
	@Override
	public int hotbarX() {
		return hotbarX == -1 ? IPlayerSlotTracker.super.hotbarX() : hotbarX;
	}
	@Override
	public int hotbarY() {
		return hotbarY == -1 ? IPlayerSlotTracker.super.hotbarY() : hotbarX;
	}

}