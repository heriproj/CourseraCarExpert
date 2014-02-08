package net.devmachine.carexpert;

import java.util.Arrays;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Represents an image of the car of certain size.
 */
class CarImage
{
    public enum Size
    {
        SMALL, MEDIUM, LARGE
    };

    private Size          size;
    private int           resourceId;
    private Car           car;
    private Resources     resources;

    private static Size[] imageMap = { Size.SMALL, Size.MEDIUM, Size.LARGE };

    public CarImage(Size size, int resourceId, Resources resources)
    {
        this.size = size;
        this.resourceId = resourceId;
        this.resources = resources;
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
     * Retrieve image dimensions.
     * 
     * @return Image dimensions.
     */
    public Dimensions getImageDimensions()
    {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resourceId, options);
        
        return new Dimensions() {
            
            @Override
            public int getWidth()
            {
                return options.outWidth;
            }
            
            @Override
            public int getHeight()
            {
                return options.outHeight;
            }
        };
    }
    
    /**
     * Sample image as per lectures.
     * 
     * @param max
     * 
     * @return Bitmap of re-scaled image.
     */
    public Bitmap getBitmapInDimensions(Dimensions max)
    {
        Dimensions image = getImageDimensions();
        int sample = 1;
        
        while (image.getWidth() > max.getWidth() * sample || image.getHeight() > max.getHeight() * sample) {
            sample = sample * 2;
        }
        
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sample;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, resourceId, options);
        
        // Convert to mutable bitmap. 
        Bitmap resultBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(bitmap, 0, 0, null);
        
        bitmap.recycle();
        
        return resultBitmap;
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
