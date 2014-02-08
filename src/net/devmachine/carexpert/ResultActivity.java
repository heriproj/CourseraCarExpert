package net.devmachine.carexpert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Simple activity to show game result.
 */
public class ResultActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        
        // Score passed from Game activity.
        int score = getIntent().getIntExtra(GameActivity.SCORE, 0);
        
        TextView scoreView = (TextView) findViewById(R.id.score);
        scoreView.setText(String.valueOf(score));
        
        TextView messageView = (TextView) findViewById(R.id.score_message);
        messageView.setText(Game.getResultMessage(score));

        // Button to restart the game.
        Button button = (Button) findViewById(R.id.restart);
        button.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ResultActivity.this, GameActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
