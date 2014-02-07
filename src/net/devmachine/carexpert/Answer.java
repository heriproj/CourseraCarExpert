package net.devmachine.carexpert;

/**
 * Answer is presented with selected image and relevant string.
 */
class Answer
{
    private CarImage image;
    private String   answer;

    public Answer(CarImage image, String answer)
    {
        this.image = image;
        this.answer = answer;
    }

    public boolean isCorrect()
    {
        return originalAnswer().equals(answer);
    }

    public String originalAnswer()
    {
        return image.getCar().getModel();
    }

    public int getAttempt()
    {
        return image.getIndex() + 1;
    }
}
