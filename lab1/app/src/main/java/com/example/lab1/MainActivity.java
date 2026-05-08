package com.example.lab1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Spinner dishSpinner;

    private CheckBox checkLuminarc;
    private CheckBox checkTefal;
    private CheckBox checkIkea;

    private CheckBox checkLowPrice;
    private CheckBox checkMediumPrice;
    private CheckBox checkHighPrice;

    private TextView resultTextView;
    private Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dishSpinner = findViewById(R.id.dishSpinner);

        checkLuminarc = findViewById(R.id.checkLuminarc);
        checkTefal = findViewById(R.id.checkTefal);
        checkIkea = findViewById(R.id.checkIkea);

        checkLowPrice = findViewById(R.id.checkLowPrice);
        checkMediumPrice = findViewById(R.id.checkMediumPrice);
        checkHighPrice = findViewById(R.id.checkHighPrice);

        resultTextView = findViewById(R.id.resultTextView);
        okButton = findViewById(R.id.okButton);

        String[] dishes = {
                "Тарілка",
                "Чашка",
                "Каструля",
                "Сковорідка",
                "Набір столових приборів"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                dishes
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dishSpinner.setAdapter(adapter);

        okButton.setOnClickListener(v -> showResult());
    }

    private void showResult() {
        String selectedDish = dishSpinner.getSelectedItem().toString();

        ArrayList<String> producers = new ArrayList<>();
        ArrayList<String> prices = new ArrayList<>();

        if (checkLuminarc.isChecked()) {
            producers.add("Luminarc");
        }

        if (checkTefal.isChecked()) {
            producers.add("Tefal");
        }

        if (checkIkea.isChecked()) {
            producers.add("IKEA");
        }

        if (checkLowPrice.isChecked()) {
            prices.add("до 500 грн");
        }

        if (checkMediumPrice.isChecked()) {
            prices.add("500–1500 грн");
        }

        if (checkHighPrice.isChecked()) {
            prices.add("понад 1500 грн");
        }

        if (producers.isEmpty() || prices.isEmpty()) {
            Toast.makeText(
                    this,
                    "Оберіть виробника та діапазон цін",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        String result = "Ваш вибір:\n\n"
                + "Тип посуду: " + selectedDish + "\n"
                + "Виробник: " + String.join(", ", producers) + "\n"
                + "Діапазон цін: " + String.join(", ", prices);

        resultTextView.setText(result);
    }
}