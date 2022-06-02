package com.example.android.fragmentexample;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SimpleFragment extends Fragment {

    private static final int YES = 0;
    private static final int NO = 1;
    private static final int NONE = 2;

    public int checkedRadio = NONE;

    private static final String CHECKED = "checked";

    onFragmentInteractionListener listener;

    interface onFragmentInteractionListener {
        void onRadioChecked(int checked);
    }

    public SimpleFragment() {
        // Required empty public constructor
    }

    public static SimpleFragment newInstance(int checkedRadio) {
        SimpleFragment fragment = new SimpleFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(CHECKED, checkedRadio);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView =  inflater.inflate(R.layout.fragment_simple, container, false);

        final RadioGroup radioGroup = rootView.findViewById(R.id.radio_group);

        final AppCompatTextView textView = rootView.findViewById(R.id.fragment_header);

        if (getArguments() != null && getArguments().containsKey(CHECKED)) {
            checkedRadio = getArguments().getInt(CHECKED);
            if (checkedRadio != NONE) {
                radioGroup.check(radioGroup.getChildAt(checkedRadio).getId());
                if (checkedRadio == 0) textView.setText(R.string.yes_message);
                else textView.setText(R.string.no_message);
            }
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);

                switch (index) {
                    case YES:
                        textView.setText(R.string.yes_message);
                        checkedRadio = YES;
                        listener.onRadioChecked(YES);
                        break;
                    case NO:
                        textView.setText(R.string.no_message);
                        checkedRadio = NO;
                        listener.onRadioChecked(NO);
                        break;
                    default:
                        checkedRadio = NONE;
                        listener.onRadioChecked(NONE);
                        break;
                }
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof onFragmentInteractionListener) {
            listener = (onFragmentInteractionListener) context;
        } else {
            throw new ClassCastException(context +
                    getResources().getString(R.string.exception_message));
        }
    }
}