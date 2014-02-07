package net.devmachine.carexpert;

import java.util.Random;

/**
 * Original car + 3 variants to answer.
 */
class Question
{
    private Car                    car;
    private String[]               variants   = new String[3];
    private int                    imageIndex = 0;
    private static CarImage.Size[] imageMap   = CarImage.getImageMap();

    public Question(Car car, String[] variants)
    {
        this.car = car;
        this.variants = variants;
    }

    public boolean hasNexImage()
    {
        return imageIndex < imageMap.length;
    }

    public CarImage getNextImage()
    {
        if (!hasNexImage()) {
            throw new RuntimeException("Car images exausted.");
        }
        
        return car.getImage(imageMap[imageIndex++]);
    }

    /**
     * Append car model to supplied variants.
     * 
     * @return Variants array.
     */
    public String[] getVariants()
    {
        String[] all = new String[variants.length + 1];

        // Copy
        for (int i = 0; i < variants.length; i++) {
            all[i] = variants[i];
        }

        all[all.length - 1] = car.getModel();

        return shuffleArray(all);
    }

    /**
     * Randomize array in a simple manner.
     * 
     * @param items
     * 
     * @return Array of randomized elements.
     */
    private static String[] shuffleArray(String[] items)
    {
        Random random = new Random();
        for (int i = items.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);

            // Simple swap
            String tmp = items[index];
            items[index] = items[i];
            items[i] = tmp;
        }

        return items;
    }
}
