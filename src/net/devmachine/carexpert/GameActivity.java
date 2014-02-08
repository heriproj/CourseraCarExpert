package net.devmachine.carexpert;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class GameActivity extends Activity
{
    public static final String  SCORE     = "SCORE";
    private static final String TAG       = "CarExpert.Game";

    private Game                game;
    private Button[]            buttonMap = new Button[4];
    private Question            currentQuestion;
    private CarImage            currentImage;
    private ImageView           imageView;
    private Dimensions          screenDimensions;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Internal listener. Same for all buttons.
        final OnClickListener listener = new OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Button button = (Button) v;
                selectAnswer(button.getText().toString());
            }
        };

        final int screenWidth = getResources().getDisplayMetrics().widthPixels;
        final int screenHeight = getResources().getDisplayMetrics().heightPixels;

        // Convenience wrapper around screen dimensions.
        screenDimensions = new Dimensions() {

            @Override
            public int getWidth()
            {
                return screenWidth;
            }

            @Override
            public int getHeight()
            {
                return screenHeight;
            }
        };

        game = gameFactory();

        buttonMap[0] = (Button) findViewById(R.id.variantA);
        buttonMap[1] = (Button) findViewById(R.id.variantB);
        buttonMap[2] = (Button) findViewById(R.id.variantC);
        buttonMap[3] = (Button) findViewById(R.id.variantD);

        buttonMap[0].setOnClickListener(listener);
        buttonMap[1].setOnClickListener(listener);
        buttonMap[2].setOnClickListener(listener);
        buttonMap[3].setOnClickListener(listener);

        imageView = (ImageView) findViewById(R.id.car);

        // Clicking on image loads the next one if available.
        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v)
            {
                loadImage();
            }
        });

        loadQuestion();
    }

    /**
     * Load next question: set buttons with variants + load an image.
     */
    private void loadQuestion()
    {
        currentQuestion = game.nextQuestion();
        String[] variants = currentQuestion.getVariants();

        for (int i = 0; i < variants.length; i++) {
            buttonMap[i].setText(variants[i]);
        }

        loadImage();
    }

    /**
     * Process image and assign into the ImageView.
     */
    private void loadImage()
    {
        if (currentQuestion.hasNexImage()) {
            currentImage = currentQuestion.getNextImage();

            Bitmap bitmap = currentImage.getBitmapInDimensions(screenDimensions);
            drawImageInfo(bitmap, String.valueOf(currentImage.getIndex() + 1));

            imageView.setImageBitmap(bitmap);
        } else {
            Toast.makeText(getApplicationContext(), R.string.no_image_previews, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Create new answer. Assign current image + selected text.
     * 
     * @param answer
     */
    private void selectAnswer(String answer)
    {
        game.addAnswer(new Answer(currentImage, answer));

        if (game.isOver()) {
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra(SCORE, game.getScore());
            startActivity(intent);
            finish(); // Do not return to current activity.
        } else {
            loadQuestion();
        }
    }

    /**
     * Little helper to draw something on bitmap.
     * 
     * @param bitmap
     * @param text
     */
    private void drawImageInfo(Bitmap bitmap, String text)
    {
        Canvas canvas = new Canvas(bitmap);
        TextPaint paint = new TextPaint();
        paint.setTextSize(bitmap.getHeight() / 4);
        paint.setColor(0xFFFF0000);

        // Padding should be relative to width/height.
        canvas.drawText(text, 0 + 30, bitmap.getHeight() - 30, paint);
    }

    /**
     * Game creation.
     * 
     * @return Initialized Game object.
     */
    private Game gameFactory()
    {
        Game game = new Game();
        Car car;

        // Car 1
        car = new Car("BMW X3");
        car.addImage(new CarImage(CarImage.Size.SMALL, R.drawable.bmw_x3_small, getResources()));
        car.addImage(new CarImage(CarImage.Size.MEDIUM, R.drawable.bmw_x3_medium, getResources()));
        car.addImage(new CarImage(CarImage.Size.LARGE, R.drawable.bmw_x3_large, getResources()));
        game.addQuestion(new Question(car, new String[] { "Audi Q5", "BMW X5", "Lexus LX" }));

        // Car 2
        car = new Car("Audi A6");
        car.addImage(new CarImage(CarImage.Size.SMALL, R.drawable.audi_a6_small, getResources()));
        car.addImage(new CarImage(CarImage.Size.MEDIUM, R.drawable.audi_a6_medium, getResources()));
        car.addImage(new CarImage(CarImage.Size.LARGE, R.drawable.audi_a6_large, getResources()));
        game.addQuestion(new Question(car, new String[] { "Honda Accrod", "Volvo S80", "Toyota Camry" }));

        // Car 3
        car = new Car("Jeep Compass");
        car.addImage(new CarImage(CarImage.Size.SMALL, R.drawable.jeep_compass_small, getResources()));
        car.addImage(new CarImage(CarImage.Size.MEDIUM, R.drawable.jeep_compass_medium, getResources()));
        car.addImage(new CarImage(CarImage.Size.LARGE, R.drawable.jeep_compass_large, getResources()));
        game.addQuestion(new Question(car, new String[] { "Infinity QX56", "BMW X5", "Volvo XC60" }));

        // Car 4
        car = new Car("BMW X6");
        car.addImage(new CarImage(CarImage.Size.SMALL, R.drawable.bmw_x6_small, getResources()));
        car.addImage(new CarImage(CarImage.Size.MEDIUM, R.drawable.bmw_x6_medium, getResources()));
        car.addImage(new CarImage(CarImage.Size.LARGE, R.drawable.bmw_x6_large, getResources()));
        game.addQuestion(new Question(car, new String[] { "Audi Q7", "Audi Q3", "Lexus RX" }));

        // Car 5
        car = new Car("Porsche Cayenne");
        car.addImage(new CarImage(CarImage.Size.SMALL, R.drawable.porsche_cayenne_small, getResources()));
        car.addImage(new CarImage(CarImage.Size.MEDIUM, R.drawable.porsche_cayenne_medium, getResources()));
        car.addImage(new CarImage(CarImage.Size.LARGE, R.drawable.porsche_cayenne_large, getResources()));
        game.addQuestion(new Question(car, new String[] { "BMW X6", "Lexus RXH", "Volvo XC90" }));

        // Car 6
        car = new Car("Volvo S80");
        car.addImage(new CarImage(CarImage.Size.SMALL, R.drawable.volvo_s80_small, getResources()));
        car.addImage(new CarImage(CarImage.Size.MEDIUM, R.drawable.volvo_s80_medium, getResources()));
        car.addImage(new CarImage(CarImage.Size.LARGE, R.drawable.volvo_s80_large, getResources()));
        game.addQuestion(new Question(car, new String[] { "Honda Accord", "Audi A6", "Lexus GS" }));

        // Car 7
        car = new Car("Infinity QX56");
        car.addImage(new CarImage(CarImage.Size.SMALL, R.drawable.infiniti_qx56_small, getResources()));
        car.addImage(new CarImage(CarImage.Size.MEDIUM, R.drawable.infiniti_qx56_medium, getResources()));
        car.addImage(new CarImage(CarImage.Size.LARGE, R.drawable.infiniti_qx56_large, getResources()));
        game.addQuestion(new Question(car, new String[] { "Porsche Cayenne", "BMW X5", "Audi Q7" }));

        // Car 8
        car = new Car("BMW X5");
        car.addImage(new CarImage(CarImage.Size.SMALL, R.drawable.bmw_x5_small, getResources()));
        car.addImage(new CarImage(CarImage.Size.MEDIUM, R.drawable.bmw_x5_medium, getResources()));
        car.addImage(new CarImage(CarImage.Size.LARGE, R.drawable.bmw_x5_large, getResources()));
        game.addQuestion(new Question(car, new String[] { "BMW X3", "Volvo XC60", "Volvo XC90" }));

        // Car 9
        car = new Car("Honda Accord");
        car.addImage(new CarImage(CarImage.Size.SMALL, R.drawable.honda_accord7_small, getResources()));
        car.addImage(new CarImage(CarImage.Size.MEDIUM, R.drawable.honda_accord7_medium, getResources()));
        car.addImage(new CarImage(CarImage.Size.LARGE, R.drawable.honda_accord7_large, getResources()));
        game.addQuestion(new Question(car, new String[] { "Audi A4", "Honda Civic", "Toyota Camry" }));

        // Car 10
        car = new Car("Lexus GS");
        car.addImage(new CarImage(CarImage.Size.SMALL, R.drawable.lexus_gs_small, getResources()));
        car.addImage(new CarImage(CarImage.Size.MEDIUM, R.drawable.lexus_gs_medium, getResources()));
        car.addImage(new CarImage(CarImage.Size.LARGE, R.drawable.lexus_gs_large, getResources()));
        game.addQuestion(new Question(car, new String[] { "Audi A8", "Honda Accord", "Volvo S80" }));

        return game;
    }
}
