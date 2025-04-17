package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

public class config extends AppCompatActivity {

    EditText inputDollar, inputEuro, inputWon;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        inputDollar = findViewById(R.id.inputDollar);
        inputEuro = findViewById(R.id.inputEuro);
        inputWon = findViewById(R.id.inputWon);
        btnSave = findViewById(R.id.btnSave);
        Intent intent = getIntent();
        float oldDollar = intent.getFloatExtra("dollar_rate_key", 0.14f);
        float oldEuro = intent.getFloatExtra("euro_rate_key", 0.13f);
        float oldWon = intent.getFloatExtra("won_rate_key", 160f);

        inputDollar.setText(String.valueOf(oldDollar));
        inputEuro.setText(String.valueOf(oldEuro));
        inputWon.setText(String.valueOf(oldWon));

        btnSave.setOnClickListener(v -> {
            float newDollar = Float.parseFloat(inputDollar.getText().toString());
            float newEuro = Float.parseFloat(inputEuro.getText().toString());
            float newWon = Float.parseFloat(inputWon.getText().toString());

            Intent result = new Intent();
            result.putExtra("key_dollar", newDollar);
            result.putExtra("key_euro", newEuro);
            result.putExtra("key_won", newWon);

            setResult(5, result);
            finish();
        });
    }
}