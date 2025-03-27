package com.example.test1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editWeight, editHeight;
    private Button btnCalculate;
    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editWeight = findViewById(R.id.editWeight);
        editHeight = findViewById(R.id.editHeight);
        btnCalculate = findViewById(R.id.btnCalculate);
        textResult = findViewById(R.id.textResult);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weightStr = editWeight.getText().toString();
                String heightStr = editHeight.getText().toString();

                if (weightStr.isEmpty() || heightStr.isEmpty()) {
                    textResult.setText("请填写体重和身高！");
                    return;
                }

                double weight = Double.parseDouble(weightStr);
                double height = Double.parseDouble(heightStr) / 100;
                double bmi = weight / (height * height);

                String suggestion;
                if (bmi < 18.5) {
                    suggestion = "体重过轻，请注意饮食，多摄入营养";
                } else if (bmi >= 18.5 && bmi < 24) {
                    suggestion = "体重正常，请继续保持";
                } else if (bmi >= 24 && bmi < 28) {
                    suggestion = "体重超重，请注意运动和饮食";
                } else {
                    suggestion = "肥胖，请及时控制饮食，多运动";
                }

                textResult.setText("BMI值: " + String.format("%.2f", bmi) + "\n建议: " + suggestion);
            }
        });
    }
}
