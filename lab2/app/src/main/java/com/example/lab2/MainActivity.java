package com.example.lab2;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements InputFragment.OnDataSelectedListener {

    private InputFragment inputFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputFragment = new InputFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.inputFragmentContainer, inputFragment)
                .commit();

        findViewById(R.id.resultFragmentContainer).setVisibility(View.GONE);
    }

    @Override
    public void onDataSelected(String result) {
        ResultFragment resultFragment = ResultFragment.newInstance(result);

        findViewById(R.id.resultFragmentContainer).setVisibility(View.VISIBLE);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.resultFragmentContainer, resultFragment);
        transaction.commit();
    }

    public void clearResult() {
        findViewById(R.id.resultFragmentContainer).setVisibility(View.GONE);

        ResultFragment fragment = (ResultFragment) getSupportFragmentManager()
                .findFragmentById(R.id.resultFragmentContainer);

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(fragment)
                    .commit();
        }

        if (inputFragment != null) {
            inputFragment.clearForm();
        }
    }
}
