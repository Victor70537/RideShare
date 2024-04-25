package edu.uga.cs.rideshare;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class InfoFragment extends Fragment {

    private static final String TAG = "RideShare";

    public InfoFragment() {
        // Required empty public constructor
    }

    public static InfoFragment newInstance(int index) {
        Log.d(TAG, "RideShare.newInstance(): index: " + index);

        // Creates new fragment with information.
        InfoFragment fragment = new InfoFragment();
        Log.d(TAG, "RideShare.newInstance(): fragment: " + fragment);

        // Save the selected country in the new fragment's Bundle data.
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "RideShare.onCreateView()");

        ScrollView scroller = new ScrollView(getActivity());

        LinearLayout layout = new LinearLayout(getActivity());
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(getActivity());

        layout.addView(textView);

        // Adds the linear layout with the children to scroller element.
        scroller.addView(layout);

        // Sets the padding and size for the text.
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getActivity().getResources().getDisplayMetrics());
        textView.setPadding(padding, padding, padding, padding);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);

        // Gets the index of which country was selected.
        int num = getShownIndex();

        if (num == 0) {
            textView.setText("Rides");
        } else if (num == 1) {
            textView.setText("Create");
        } else if (num == 2) {
            textView.setText("Your Profile");
        } else {
            textView.setText("Logout");
        }

        return scroller;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }
}