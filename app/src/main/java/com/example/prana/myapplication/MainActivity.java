package com.example.prana.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton[] buttons = new ImageButton[6];
    int[] buttonState = new int[6];
    int[] dieImages = new int[6];
    int[] dieValue = new int[6];
    final int HOT_DIE = 0;
    final int SCORE_DIE = 1;
    final int LOCKED_DIE = 2;
    Button roll;
    Button stop;
    Button score;
    TextView currentScoreTV;
    TextView totalScoreTV;
    TextView currentRoundTV;
    int currentScore;
    int totalScore;
    int currentRound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttons[0] = this.findViewById(R.id.imageButton1);
        buttons[1] = this.findViewById(R.id.imageButton2);
        buttons[2] = this.findViewById(R.id.imageButton3);
        buttons[3] = this.findViewById(R.id.imageButton4);
        buttons[4] = this.findViewById(R.id.imageButton5);
        buttons[5] = this.findViewById(R.id.imageButton6);
        for (int x = 0; x < buttons.length; x++) {
            buttons[x].setOnClickListener(this);
            buttons[x].setEnabled(false);
            buttons[x].setBackgroundColor(Color.LTGRAY);
        }
        roll = this.findViewById(R.id.button1);
        roll.setOnClickListener(this);
        score = this.findViewById(R.id.button2);
        score.setOnClickListener(this);
        score.setEnabled(false);
        stop = this.findViewById(R.id.button3);
        stop.setOnClickListener(this);
        stop.setEnabled(false);
        currentScoreTV = this.findViewById(R.id.textView1);
        totalScoreTV = this.findViewById(R.id.textView2);
        currentRoundTV = this.findViewById(R.id.textView3);
        dieImages[0] = R.drawable.one;
        dieImages[1] = R.drawable.two;
        dieImages[2] = R.drawable.three;
        dieImages[3] = R.drawable.four;
        dieImages[4] = R.drawable.five;
        dieImages[5] = R.drawable.six;


    }

    @Override
    public void onClick(View v) {
        if (v.equals(roll)) {
            for (int a = 0; a < buttons.length; a++) {
                if (buttonState[a] == HOT_DIE) {
                    int choice = (int) (Math.random() * 6);
                    dieValue[a] = choice;
                    buttons[a].setImageResource(dieImages[choice]);
                    buttons[a].setEnabled(true);
                    roll.setEnabled(false);
                    score.setEnabled(true);
                    stop.setEnabled(false);
                }
            }
        } else if (v.equals(score)) {
            int[] valueCount = new int[7];
            for (int a = 0; a < buttons.length; a++) {
                if (buttonState[a] == SCORE_DIE) {
                    valueCount[dieValue[a] + 1]++;
                }
            }
            if (valueCount[2] > 0 && valueCount[2] < 3 ||
                    valueCount[3] > 0 && valueCount[3] < 3 ||
                    valueCount[4] > 0 && valueCount[4] < 3 ||
                    valueCount[6] > 0 && valueCount[6] < 3) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Invalid die selection!");
                alertDialogBuilder
                        .setMessage("You can only chose scoring dice")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                for( int a = 0; a < buttons.length ; a++){
                                    if(buttonState[a] == SCORE_DIE){
                                        buttonState[a] = HOT_DIE;
                                        buttons[a].setBackgroundColor(Color.LTGRAY);
                                    }
                                }
                                roll.setEnabled(false);
                                stop.setEnabled(false);
                                score.setEnabled(true);
                                dialog.dismiss();
                                return;

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } else if (valueCount[1] == 0 && valueCount[2] == 0 && valueCount[3] == 0 && valueCount[4] == 0 && valueCount[5] == 0
                    && valueCount[6] == 0) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("No score!");
                alertDialogBuilder
                        .setMessage("Forfeit score and go to next round?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                currentScore = 0;
                                currentRound++;
                                currentScoreTV.setText("Current Score: " + currentScore);
                                currentRoundTV.setText("Current Round: " + currentRound);
                                reset();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                roll.setEnabled(false);
                                stop.setEnabled(false);
                                score.setEnabled(true);
                                dialog.cancel();
                                return;
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            } else {
                if (valueCount[1] < 3) {
                    currentScore = currentScore + 100 * valueCount[1];
                }
                if (valueCount[5] < 3) {
                    currentScore = currentScore + 50 * valueCount[5];
                }
                if (valueCount[1] >= 3) {
                    currentScore = currentScore + 1000;
                    currentScore = currentScore + (valueCount[1] - 3) * 1000;
                }
                if (valueCount[2] >= 3) {
                    currentScore = currentScore + (200 * (valueCount[2] - 2));
                }
                if (valueCount[3] >= 3) {
                    currentScore = currentScore + (300 * (valueCount[3] - 2));
                }
                if (valueCount[4] >= 3) {
                    currentScore = currentScore + (400 * (valueCount[4] - 2));
                }
                if (valueCount[5] >= 3) {
                    currentScore = currentScore + (500 * (valueCount[5] - 2));
                }
                if (valueCount[6] >= 3) {
                    currentScore = currentScore + (600 * (valueCount[6] - 2));
                }
                currentScoreTV.setText("Current score: " + currentScore);
                for (int a = 0; a < buttons.length; a++) {
                    if (buttonState[a] == SCORE_DIE) {
                        buttonState[a] = LOCKED_DIE;
                        buttons[a].setBackgroundColor(Color.BLUE);

                    }
                    buttons[a].setEnabled(false);
                }
                int lockedCount = 0;
                for (int a = 0; a < buttons.length; a++) {
                    if(buttonState[a] == LOCKED_DIE){
                        lockedCount++;
                    }
                }
                if(lockedCount == 6){
                    for (int a = 0; a < buttons.length; a++) {
                            buttonState[a] = HOT_DIE;
                            buttons[a].setBackgroundColor(Color.LTGRAY);
                        totalScore = totalScore + currentScore;
                        currentScore = 0;
                        currentScoreTV.setText("Current Score: " + currentScore);
                        totalScoreTV.setText("total Score: " + totalScore);
                        currentRound++;
                        currentRoundTV.setText("Current Round: " + currentRound);

                        }
                }
            }
            roll.setEnabled(true);
            score.setEnabled(false);
            stop.setEnabled(true);
        } else if (v.equals(stop)) {
            totalScore = totalScore + currentScore;
            currentScore = 0;
            currentScoreTV.setText("Current Score: " + currentScore);
            totalScoreTV.setText("total Score: " + totalScore);
            currentRound++;
            currentRoundTV.setText("Current Round: " + currentRound);
            reset();
        } else {
            for (int a = 0; a < buttons.length; a++) {
                if (v.equals(buttons[a])) {
                    if (buttonState[a] == HOT_DIE) {
                        buttons[a].setBackgroundColor(Color.RED);
                        buttonState[a] = SCORE_DIE;
                    } else if (buttonState[a] == SCORE_DIE) {
                        buttons[a].setBackgroundColor(Color.LTGRAY);
                        buttonState[a] = HOT_DIE;
                    }
                }
            }
        }

    }
    public void reset(){
        for (int a = 0; a < buttons.length; a++) {
            buttonState[a] = HOT_DIE;
            buttons[a].setEnabled(false);
            buttons[a].setBackgroundColor(Color.LTGRAY);
        }
        roll.setEnabled(true);
        score.setEnabled(false);
        stop.setEnabled(false);
    }
}

