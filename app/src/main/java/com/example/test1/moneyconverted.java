package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class moneyconverted extends AppCompatActivity {

    EditText editTextRMB;
    TextView textViewResult;
    Button btnDollar, btnEuro, btnWon, btnConfig;

    double rateDollar = 0.14;
    double rateEuro = 0.13;
    double rateWon = 160.0;

    final int REQUEST_CODE_CONFIG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moneyconverted);

        editTextRMB = findViewById(R.id.editTextRMB);
        textViewResult = findViewById(R.id.textViewResult);
        btnDollar = findViewById(R.id.btnDollar);
        btnEuro = findViewById(R.id.btnEuro);
        btnWon = findViewById(R.id.btnWon);
        btnConfig = findViewById(R.id.btnConfig);

        readRatesFromPreferences();

        btnDollar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertCurrency(rateDollar, "USD");
            }
        });

        btnEuro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertCurrency(rateEuro, "EUR");
            }
        });

        btnWon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertCurrency(rateWon, "KRW");
            }
        });

        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(moneyconverted.this, config.class);
                startActivityForResult(intent, REQUEST_CODE_CONFIG);
            }
        });
    }

    private void readRatesFromPreferences() {
        SharedPreferences prefs = getSharedPreferences("Rates", MODE_PRIVATE);
        rateDollar = Double.parseDouble(prefs.getString("rateDollar", "0.14"));
        rateEuro = Double.parseDouble(prefs.getString("rateEuro", "0.13"));
        rateWon = Double.parseDouble(prefs.getString("rateWon", "160"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CONFIG && resultCode == RESULT_OK) {
            readRatesFromPreferences(); // 重新读取更新的汇率
            Toast.makeText(this, "已更新汇率", Toast.LENGTH_SHORT).show();
        }
    }

    private void convertCurrency(double rate, String currencyName) {
        String input = editTextRMB.getText().toString();
        if (!input.isEmpty()) {
            try {
                double rmb = Double.parseDouble(input);
                double result = rmb * rate;
                textViewResult.setText(String.format("%.2f %s", result, currencyName));
            } catch (NumberFormatException e) {
                Toast.makeText(this, "请输入有效的数字", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "请输入人民币金额", Toast.LENGTH_SHORT).show();
        }
    }
}
