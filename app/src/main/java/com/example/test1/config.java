package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        btnSave.setOnClickListener(v -> {
            String dollar = inputDollar.getText().toString();
            String euro = inputEuro.getText().toString();
            String won = inputWon.getText().toString();

            SharedPreferences prefs = getSharedPreferences("Rates", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("rateDollar", dollar);
            editor.putString("rateEuro", euro);
            editor.putString("rateWon", won);
            editor.apply();

            Toast.makeText(this, "汇率已保存", Toast.LENGTH_SHORT).show();

            setResult(RESULT_OK);
            finish();
        });

    }
}
