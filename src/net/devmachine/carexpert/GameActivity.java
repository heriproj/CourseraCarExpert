package net.devmachine.carexpert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class GameActivity extends Activity
{
    public static final String  SCORE     = "SCORE";
    private static final String TAG       = "CarExpert.Game";

    private Game                game      = gameFactory();
    private Button[]            buttonMap = new Button[4];
    private Question            currentQuestion;
    private CarImage            currentImage;
    private ImageView           imageView;

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

        buttonMap[0] = (Button) findViewById(R.id.variantA);
        buttonMap[1] = (Button) findViewById(R.id.variantB);
        buttonMap[2] = (Button) findViewById(R.id.variantC);
        buttonMap[3] = (Button) findViewById(R.id.variantD);

        buttonMap[0].setOnClickListener(listener);
        buttonMap[1].setOnClickListener(listener);
        buttonMap[2].setOnClickListener(listener);
        buttonMap[3].setOnClickListener(listener);

        imageView = (ImageView) findViewById(R.id.car);

        // Clicking on image just loads the next if one is available.
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
            imageView.setImageResource(currentImage.getResourceId());
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
        car.addImage(new CarImage(CarImage.Size.SMALL, R.drawable.bmw_x3_small));
        car.addImage(new CarImage(CarImage.Size.MEDIUM, R.drawable.bmw_x3_medium));
        car.addImage(new CarImage(CarImage.Size.LARGE, R.drawable.bmw_x3_large));

        game.addQuestion(new Question(car, new String[] { "Audi Q5", "BMW X5", "Lexus LX" }));
        Log.d(TAG, "Game is full " + (game.isFull() ? "yes" : "no"));

        // Car 2
        car = new Car("BMW X5");
        car.addImage(new CarImage(CarImage.Size.SMALL, R.drawable.bmw_x5_small));
        car.addImage(new CarImage(CarImage.Size.MEDIUM, R.drawable.bmw_x5_medium));
        car.addImage(new CarImage(CarImage.Size.LARGE, R.drawable.bmw_x5_large));

        game.addQuestion(new Question(car, new String[] { "BMW X3", "Volvo XC60", "Volvo XC90" }));
        Log.d(TAG, "Game is full " + (game.isFull() ? "yes" : "no"));

        // Car 3
        car = new Car("BMW X6");
        car.addImage(new CarImage(CarImage.Size.SMALL, R.drawable.bmw_x6_small));
        car.addImage(new CarImage(CarImage.Size.MEDIUM, R.drawable.bmw_x6_medium));
        car.addImage(new CarImage(CarImage.Size.LARGE, R.drawable.bmw_x6_large));

        game.addQuestion(new Question(car, new String[] { "Audi Q7", "Audi Q3", "Lexus RX" }));
        Log.d(TAG, "Game is full " + (game.isFull() ? "yes" : "no"));

        return game;
    }
}
