package com.example.test1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class moneyconverted extends AppCompatActivity {

    EditText editTextRMB;
    TextView textViewResult;
    Button btnDollar, btnEuro, btnWon, btnConfig, btnUpdateRate;

    float dollarRate = 0.14f;
    float euroRate = 0.13f;
    float wonRate = 160f;

    ActivityResultLauncher<Intent> launcher;
    private final String TAG = "MoneyApp";

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
        btnUpdateRate = findViewById(R.id.btnUpdateRate);

        SharedPreferences prefs = getSharedPreferences("rate_storage", MODE_PRIVATE);
        dollarRate = prefs.getFloat("usd", 0.14f);
        euroRate = prefs.getFloat("eur", 0.13f);
        wonRate = prefs.getFloat("krw", 160f);

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == 5) {
                        Intent data = result.getData();
                        if (data != null && data.hasExtra("key_dollar")) {
                            dollarRate = data.getFloatExtra("key_dollar", dollarRate);
                            euroRate = data.getFloatExtra("key_euro", euroRate);
                            wonRate = data.getFloatExtra("key_won", wonRate);
                            saveRatesToLocal();
                            Toast.makeText(this, "汇率已更新", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        btnDollar.setOnClickListener(v -> convertCurrency(dollarRate, "USD"));
        btnEuro.setOnClickListener(v -> convertCurrency(euroRate, "EUR"));
        btnWon.setOnClickListener(v -> convertCurrency(wonRate, "KRW"));

        btnConfig.setOnClickListener(v -> {
            Intent configIntent = new Intent(moneyconverted.this, config.class);
            configIntent.putExtra("dollar_rate_key", dollarRate);
            configIntent.putExtra("euro_rate_key", euroRate);
            configIntent.putExtra("won_rate_key", wonRate);
            launcher.launch(configIntent);
        });

        btnUpdateRate.setOnClickListener(v -> updateRatesFromWeb());
    }

    private void convertCurrency(float rate, String currencyName) {
        String input = editTextRMB.getText().toString();
        if (!input.isEmpty()) {
            try {
                float rmb = Float.parseFloat(input);
                float result = rmb * rate;
                textViewResult.setText(String.format("%.2f %s", result, currencyName));
            } catch (NumberFormatException e) {
                Toast.makeText(this, "请输入有效的数字", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "请输入人民币金额", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateRatesFromWeb() {
        new Thread(() -> {
            try {
                String url = "http://www.boc.cn/sourcedb/whpj/";
                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36")
                        .timeout(10000)
                        .get();
                Element table = doc.select("table").first();
                Elements rows = table.select("tr");

                for (Element row : rows) {
                    Elements tds = row.select("td");
                    if (tds.size() >= 6) {
                        String currencyName = tds.get(0).text().trim();
                        String sellRateStr = tds.get(2).text().trim();
                        float sellRate = Float.parseFloat(sellRateStr);
                        float rmbPerUnit = sellRate / 100f;

                        if (currencyName.contains("美元") || currencyName.contains("USD")) {
                            dollarRate = rmbPerUnit;
                        } else if (currencyName.contains("欧元") || currencyName.contains("EUR")) {
                            euroRate = rmbPerUnit;
                        } else if (currencyName.contains("韩元") || currencyName.contains("KRW")) {
                            wonRate = rmbPerUnit;
                        }

                        Log.i(TAG, currencyName + " 汇率 = " + rmbPerUnit);
                    }
                }

                saveRatesToLocal();

                new Handler(Looper.getMainLooper()).post(() ->
                        Toast.makeText(this, "已成功从中国银行更新汇率", Toast.LENGTH_SHORT).show()
                );

            } catch (Exception e) {
                e.printStackTrace();
                new Handler(Looper.getMainLooper()).post(() ->
                        Toast.makeText(this, "更新失败: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
            }
        }).start();
    }

    private void saveRatesToLocal() {
        getSharedPreferences("rate_storage", MODE_PRIVATE).edit()
                .putFloat("usd", dollarRate)
                .putFloat("eur", euroRate)
                .putFloat("krw", wonRate)
                .apply();
    }
}
