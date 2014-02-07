package net.devmachine.carexpert;

/**
 * Holds the name of the car and reference to 3 image sizes.
 */
class Car
{
    private String     model;
    private CarImage[] images = new CarImage[3];

    public Car(String model)
    {
        this.model = model;
    }

    public void addImage(CarImage image)
    {
        images[image.getIndex()] = image;
        image.setCar(this);
    }

    public String getModel()
    {
        return model;
    }

    public CarImage getImage(CarImage.Size size)
    {
        for (CarImage image : images) {
            if (image.getSize() == size) {
                return image;
            }
        }

        return null;
    }
}
