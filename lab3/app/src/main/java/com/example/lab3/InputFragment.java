package com.example.lab3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class InputFragment extends Fragment {

    private static final String FILE_NAME = "orders.txt";

    private Spinner dishSpinner;

    private CheckBox checkLuminarc;
    private CheckBox checkTefal;
    private CheckBox checkIkea;

    private CheckBox checkLowPrice;
    private CheckBox checkMediumPrice;
    private CheckBox checkHighPrice;

    private TextView currentResultTextView;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_input, container, false);

        dishSpinner = view.findViewById(R.id.dishSpinner);

        checkLuminarc = view.findViewById(R.id.checkLuminarc);
        checkTefal = view.findViewById(R.id.checkTefal);
        checkIkea = view.findViewById(R.id.checkIkea);

        checkLowPrice = view.findViewById(R.id.checkLowPrice);
        checkMediumPrice = view.findViewById(R.id.checkMediumPrice);
        checkHighPrice = view.findViewById(R.id.checkHighPrice);

        Button okButton = view.findViewById(R.id.okButton);
        Button openButton = view.findViewById(R.id.openButton);
        currentResultTextView = view.findViewById(R.id.currentResultTextView);

        String[] dishes = {
                "Тарілка",
                "Чашка",
                "Каструля",
                "Сковорідка",
                "Набір столових приборів"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                dishes
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dishSpinner.setAdapter(adapter);

        okButton.setOnClickListener(v -> processAndSaveData());

        openButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), SavedDataActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void processAndSaveData() {
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
                    requireContext(),
                    "Оберіть виробника та діапазон цін",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        String result = "Тип посуду: " + selectedDish + "\n"
                + "Виробник: " + String.join(", ", producers) + "\n"
                + "Діапазон цін: " + String.join(", ", prices);

        currentResultTextView.setText("Поточний вибір:\n\n" + result);

        saveToFile(result);

        Toast.makeText(
                requireContext(),
                "Дані успішно збережено",
                Toast.LENGTH_SHORT
        ).show();
    }

    private void saveToFile(String result) {
        String data = result + "\n-------------------------\n";

        try {
            FileOutputStream fos = requireContext().openFileOutput(
                    FILE_NAME,
                    Context.MODE_APPEND
            );

            OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            writer.write(data);
            writer.close();

        } catch (IOException e) {
            Toast.makeText(
                    requireContext(),
                    "Помилка запису у файл",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
