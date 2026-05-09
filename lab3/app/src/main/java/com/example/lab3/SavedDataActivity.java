package com.example.lab3;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;

public class SavedDataActivity extends AppCompatActivity {

    private static final String FILE_NAME = "orders.txt";

    private TextView savedDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_data);

        savedDataTextView = findViewById(R.id.savedDataTextView);

        String savedData = readFromFile();

        if (savedData.isEmpty()) {
            savedDataTextView.setText("Сховище порожнє. Дані ще не були збережені.");
        } else {
            savedDataTextView.setText(savedData);
        }
    }

    private String readFromFile() {
        StringBuilder builder = new StringBuilder();

        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line).append("\n");
            }

            bufferedReader.close();

        } catch (IOException e) {
            return "";
        }

        return builder.toString();
    }
}

