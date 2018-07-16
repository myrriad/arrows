package phlaxyr.largetents.capabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

/***
 * 
 * Silly me didn't see the forge implementation
 *
 */
@Deprecated
public class FluidItemImpl implements IFluidHandlerItem, ICapabilityProvider{

	public int cap;
	// public FluidStack fluid;
	@Nonnull
	public ItemStack stack;

	// IFluidHandler i;
	
    @Nullable
    public FluidStack getFluid()
    {
        return FluidStack.loadFluidStackFromNBT(stack.getTagCompound());

    }
    
    protected void setFluid(@Nullable FluidStack fluidStack)
    {
        // FluidStack fluidContents = new FluidStack(fluidStack, bucket.getCapacity());
    	// FluidUtils.ceil(fluidStack, cap);
        NBTTagCompound tag = new NBTTagCompound();
        fluidStack.writeToNBT(tag);
        stack.setTagCompound(tag);

        
    }
	
    @Override
    public IFluidTankProperties[] getTankProperties()
    {
        FluidStack f = getFluid();
    	return new FluidTankProperties[] { new FluidTankProperties(f, f.amount) };
    }

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		FluidStack f = getFluid();
		if(!f.isFluidEqual(resource)) {
			return 0;
		}
		FluidStack s = new FluidStack(f, f.amount + resource.amount);
		
		int added = resource.amount - FluidUtils.ceil(s, cap);
		
		if(doFill) {
			setFluid(s);
		}
		return added;
		

	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		FluidStack f = getFluid();
		if(resource == null) {
			return null;
		}
		if(!resource.isFluidEqual(f)) {
			return null;
		}
		FluidStack drained;
		
		if(f.containsFluid(resource)) {
			drained = resource.copy();
		} else {
			drained = f.copy();
		}
		if(doDrain) {
			f.amount -= drained.amount;
			setFluid(f);
		}
		return drained;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		FluidStack f = getFluid();
		FluidStack drained = f.copy();
		
		if(f.amount > maxDrain) {
			drained.amount = maxDrain;
		}
		if(doDrain) {
			f.amount -= drained.amount;
			setFluid(f);
		}
		return drained;
	}

	@Override
	public ItemStack getContainer() {
		return stack;
	}

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY;
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY)
        {
            return CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY.cast(this);
        }
        return null;
    }

	public FluidItemImpl(int cap, @Nonnull ItemStack stack) {
		this.cap = cap;
		this.stack = stack;
		
	}
}
