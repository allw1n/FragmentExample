package com.example.android.fragmentexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.android.fragmentexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements SimpleFragment.onFragmentInteractionListener{

    private final static String STATE_FRAGMENT = "state_of_fragment";

    private AppCompatButton openFragmentButton;
    private boolean isFragmentDisplayed = false;

    private int checkedRadio = 2;
    private static final String CHECKED = "checked";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        openFragmentButton = binding.openFragmentButton;

        if (savedInstanceState != null) {
            isFragmentDisplayed = savedInstanceState.getBoolean(STATE_FRAGMENT);
            if (isFragmentDisplayed) {
                openFragmentButton.setText(R.string.close);
            }
            checkedRadio = savedInstanceState.getInt(CHECKED);
        }

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
        SimpleFragment simpleFragment = SimpleFragment.newInstance(checkedRadio);

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
                .findFragmentById(R.id.fragment_container);

        if (simpleFragment != null) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(simpleFragment).commit();
        }
        openFragmentButton.setText(R.string.open);
        isFragmentDisplayed = false;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed);
        outState.putInt(CHECKED, checkedRadio);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRadioChecked(int checked) {
        checkedRadio = checked;
    }
}