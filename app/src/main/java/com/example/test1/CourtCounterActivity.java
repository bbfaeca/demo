package com.example.test1;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CourtCounterActivity extends AppCompatActivity {

    int scoreTeamA = 0;
    int scoreTeamB = 0;
    TextView scoreA;
    TextView scoreB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.court_counter_layout);
        scoreA = findViewById(R.id.scoreTeamA);
        scoreB = findViewById(R.id.scoreTeamB);
    }

    public void add3A(View view) {
        scoreTeamA += 3;
        scoreA.setText(String.valueOf(scoreTeamA));
    }

    public void add2A(View view) {
        scoreTeamA += 2;
        scoreA.setText(String.valueOf(scoreTeamA));
    }

    public void add1A(View view) {
        scoreTeamA += 1;
        scoreA.setText(String.valueOf(scoreTeamA));
    }

    public void add3B(View view) {
        scoreTeamB += 3;
        scoreB.setText(String.valueOf(scoreTeamB));
    }

    public void add2B(View view) {
        scoreTeamB += 2;
        scoreB.setText(String.valueOf(scoreTeamB));
    }

    public void add1B(View view) {
        scoreTeamB += 1;
        scoreB.setText(String.valueOf(scoreTeamB));
    }

    public void reset(View view) {
        scoreTeamA = 0;
        scoreTeamB = 0;
        scoreA.setText("0");
        scoreB.setText("0");
    }
}
