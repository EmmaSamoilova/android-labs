package com.example.lab6;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private EditText cityEditText;
    private TextView resultTextView;
    private Button getWeatherButton;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private static final String API_URL = "https://wttr.in/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityEditText = findViewById(R.id.cityEditText);
        resultTextView = findViewById(R.id.resultTextView);
        getWeatherButton = findViewById(R.id.getWeatherButton);

        getWeatherButton.setOnClickListener(v -> {
            String city = cityEditText.getText().toString().trim();

            if (city.isEmpty()) {
                Toast.makeText(this, "Введіть назву міста", Toast.LENGTH_SHORT).show();
                return;
            }

            getWeather(city);
        });
    }

    private void getWeather(String city) {
        resultTextView.setText("Завантаження даних...");

        executorService.execute(() -> {
            try {
                String urlString = API_URL + city + "?format=j1";
                URL url = new URL(urlString);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );

                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                connection.disconnect();

                String result = parseWeather(response.toString(), city);

                mainHandler.post(() -> resultTextView.setText(result));

            } catch (Exception e) {
                mainHandler.post(() -> resultTextView.setText(
                        "Помилка отримання даних.\nПеревірте підключення до Інтернету або назву міста."
                ));
            }
        });
    }

    private String parseWeather(String json, String city) {
        try {
            JSONObject root = new JSONObject(json);

            JSONObject currentCondition = root
                    .getJSONArray("current_condition")
                    .getJSONObject(0);

            String temperature = currentCondition.getString("temp_C");
            String feelsLike = currentCondition.getString("FeelsLikeC");
            String windSpeed = currentCondition.getString("windspeedKmph");
            String humidity = currentCondition.getString("humidity");

            String description = currentCondition
                    .getJSONArray("weatherDesc")
                    .getJSONObject(0)
                    .getString("value");

            return "Місто: " + city + "\n\n"
                    + "Температура: " + temperature + " °C\n"
                    + "Відчувається як: " + feelsLike + " °C\n"
                    + "Опис: " + description + "\n"
                    + "Вологість: " + humidity + " %\n"
                    + "Швидкість вітру: " + windSpeed + " км/год";

        } catch (Exception e) {
            return "Помилка обробки даних погоди.";
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}