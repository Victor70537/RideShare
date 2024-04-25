package edu.uga.cs.rideshare;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class OptionsFragment extends ListFragment {

    private static final String TAG = "RideShare";

    private final String[] options = {
            "Rides",
            "Create",
            "Your Profile",
            "Logout"
    };

    boolean twoFragmentsActivity = false;

    int index = 0;
    public OptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "OptionsFragment.onViewCreated(): savedInstanceState: " + savedInstanceState);

        setListAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, options));

        View detailsFrame = getActivity().findViewById(R.id.info);
        Log.d(TAG, "RideShare.onViewCreated(): detailsFrame: " + detailsFrame);

        twoFragmentsActivity = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null) {
            index = savedInstanceState.getInt("optionSelection", 0);
            Log.d(TAG, "OptionsFragment.onActivityCreated(): restored index: " + index);
        }

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        getListView().setItemChecked(index, true);

        if (twoFragmentsActivity) {
            showAppInfo(index);

            getListView().smoothScrollToPosition(index);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showAppInfo(position);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("optionSelection", index);
        Log.d(TAG, "RideShare.onSaveInstanceState(): saved index: " + index);
    }

    void showAppInfo(int index) {
        this.index = index;

        if (twoFragmentsActivity) {
            getListView().setItemChecked(index, true);

            InfoFragment details = (InfoFragment) getParentFragmentManager().findFragmentById(R.id.info);

            Log.d(TAG, "RideShare.showAppInfo(): details: " + details);

            if ((details == null) || (details.getShownIndex() != index)) {
                details = InfoFragment.newInstance(index);

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.info, details);

                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

                fragmentTransaction.commit();
            }
        } else {
            Intent intent = new Intent();
            intent.setClass(getActivity(), InfoActivity.class);
            intent.putExtra("index", index);

            startActivity(intent);
        }
    }
//    public static OptionsFragment newInstance(String param1, String param2) {
//        OptionsFragment fragment = new OptionsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_options, container, false);
//    }


}