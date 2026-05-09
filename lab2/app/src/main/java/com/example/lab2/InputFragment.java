package com.example.lab2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class InputFragment extends Fragment {

    private Spinner dishSpinner;

    private CheckBox checkLuminarc;
    private CheckBox checkTefal;
    private CheckBox checkIkea;

    private CheckBox checkLowPrice;
    private CheckBox checkMediumPrice;
    private CheckBox checkHighPrice;

    private Button okButton;

    private OnDataSelectedListener listener;

    public interface OnDataSelectedListener {
        void onDataSelected(String result);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnDataSelectedListener) {
            listener = (OnDataSelectedListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnDataSelectedListener");
        }
    }

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

        okButton = view.findViewById(R.id.okButton);

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

        okButton.setOnClickListener(v -> processData());

        return view;
    }

    private void processData() {
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

        String result = "Ваш вибір:\n\n"
                + "Тип посуду: " + selectedDish + "\n"
                + "Виробник: " + String.join(", ", producers) + "\n"
                + "Діапазон цін: " + String.join(", ", prices);

        listener.onDataSelected(result);
    }

    public void clearForm() {
        if (dishSpinner != null) {
            dishSpinner.setSelection(0);
        }

        if (checkLuminarc != null) checkLuminarc.setChecked(false);
        if (checkTefal != null) checkTefal.setChecked(false);
        if (checkIkea != null) checkIkea.setChecked(false);

        if (checkLowPrice != null) checkLowPrice.setChecked(false);
        if (checkMediumPrice != null) checkMediumPrice.setChecked(false);
        if (checkHighPrice != null) checkHighPrice.setChecked(false);
    }
}

