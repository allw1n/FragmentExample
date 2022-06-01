package com.example.android.fragmentexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.android.fragmentexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton openFragmentButton;
    private boolean isFragmentDisplayed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        openFragmentButton = binding.openFragmentButton;
        openFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFragmentDisplayed) {
                    displayFragment();
                } else {
                    closeFragment();
                }
            }
        });
    }

    public void displayFragment() {
        SimpleFragment simpleFragment = SimpleFragment.newInstance();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_container, simpleFragment)
                .addToBackStack(null).commit();
        openFragmentButton.setText(R.string.close);
        isFragmentDisplayed = true;
    }

    private void closeFragment() {
        FragmentManager manager = getSupportFragmentManager();

        SimpleFragment simpleFragment = (SimpleFragment) manager
                .findFragmentById(R.id.fragment_header);

        if (simpleFragment != null) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(simpleFragment);
        }
        openFragmentButton.setText(R.string.open);
        isFragmentDisplayed = false;
    }
}
