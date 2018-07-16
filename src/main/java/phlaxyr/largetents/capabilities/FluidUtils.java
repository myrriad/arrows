package phlaxyr.largetents.capabilities;

import javax.annotation.Nullable;

import net.minecraftforge.fluids.FluidStack;

public class FluidUtils {
	/**
	 * returns how much was "shaved" from `a`
	 * @param a
	 * @param ceil
	 * @return
	 */
	public static int ceil(FluidStack a, int ceil) {
		
		int overflow = a.amount - ceil;
		if(overflow > 0) {
			a.amount = ceil;
			return overflow;
		}
		return 0;
	}
	@Nullable
	public static FluidStack add(@Nullable FluidStack a, @Nullable FluidStack b) {
		if(a == null) return b;
		if(b == null) return a;
		if(a.isFluidEqual(b)) {
			int amount = a.amount + b.amount;
			return setSoft(a, amount);
		}
		return null;
	}
	/**
	 * return a - b
	 * if b > a returns null
	 * @param a
	 * @param b
	 * @return
	 * null means it can't subtract
	 */
	@Nullable
	public static FluidStack subtract(@Nullable FluidStack a, @Nullable FluidStack b) {
		
		if(b == null) return a;
		if(a == null) return null;
		if(a.containsFluid(b)) {
			int amount = a.amount - b.amount;
			return setSoft(a, amount);
		}
		return null;
	}
	private static FluidStack set(FluidStack a, int amount, boolean mutate) {
		if(mutate) {
			a.amount = amount;
			return a;
		} else { 
			return setSoft(a, amount);
		}
	}
	private static FluidStack setSoft(FluidStack a, int amount) {
		FluidStack c = a.copy();
		c.amount = amount;
		return c;
	}
	
}
