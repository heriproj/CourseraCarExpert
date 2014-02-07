package net.devmachine.carexpert;

import java.util.Arrays;

/**
 * Represents an image of the car of certain size.
 */
class CarImage
{
    public enum Size
    {
        SMALL, MEDIUM, LARGE
    };

    private Size size;
    private int  resourceId;
    private Car  car;
    
    private static Size[] imageMap = { Size.SMALL, Size.MEDIUM, Size.LARGE };

    public CarImage(Size size, int resourceId)
    {
        this.size = size;
        this.resourceId = resourceId;
    }

    public Size getSize()
    {
        return size;
    }

    public int getResourceId()
    {
        return resourceId;
    }

    public void setCar(Car car)
    {
        this.car = car;
    }

    public Car getCar()
    {
        return car;
    }

    /**
     * Find index number by name.
     * 
     * @return Index number.
     */
    public int getIndex()
    {
        return Arrays.binarySearch(imageMap, size);
    }
    
    public static Size[] getImageMap()
    {
        return imageMap;
    }
}
