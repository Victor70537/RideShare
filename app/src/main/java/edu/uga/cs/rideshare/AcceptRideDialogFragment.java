package edu.uga.cs.rideshare;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AcceptRideDialogFragment extends DialogFragment {
    public static final int ACCEPT = 1;

    private TextView riderView;
    private TextView driverView;
    private TextView phoneView;
    private TextView destinationView;
    private TextView commentsView;

    int position;

    String key;
    String rider;
    String driver;
    String phone;
    String destination;
    String comments;

    public interface AcceptRideDialogListener {
        void acceptRide(int position, Ride ride, int action);
    }

    public static AcceptRideDialogFragment newInstance(int position, String key, String rider, String driver, String phone, String destination, String comments) {
        AcceptRideDialogFragment dialog = new AcceptRideDialogFragment();

        Bundle args = new Bundle();
        args.putString("key", key );
        args.putString("rider", rider );
        args.putString("driver", driver);
        args.putString("phone", phone);
        args.putString("destination", destination);
        args.putString("comments", comments);
        dialog.setArguments(args);

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ) {

        key = getArguments().getString( "key" );
        position = getArguments().getInt( "position" );
        rider = getArguments().getString( "rider" );
        driver = getArguments().getString("driver");
        phone = getArguments().getString( "phone" );
        destination = getArguments().getString( "destination" );
        comments = getArguments().getString( "comments" );

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate( R.layout.accept_ride_dialog, getActivity().findViewById( R.id.root ) );

        riderView = layout.findViewById( R.id.textView1 );
        driverView = layout.findViewById( R.id.textView2 );
        phoneView = layout.findViewById( R.id.textView3 );
        destinationView = layout.findViewById( R.id.textView4 );
        commentsView = layout.findViewById( R.id.textView5 );

        riderView.setText( rider );
        driverView.setText( driver );
        phoneView.setText( phone );
        destinationView.setText( destination );
        commentsView.setText( comments );

        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity(), R.style.AlertDialogStyle );
        builder.setView(layout);

        builder.setTitle( "Accept Ride Dialog" );

        // The Cancel button handler
        builder.setNegativeButton( android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                // close the dialog
                dialog.dismiss();
            }
        });

        builder.setPositiveButton( "ACCEPT", new AcceptButtonClickListener() );

        return builder.create();
    }

    private class AcceptButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String rider = riderView.getText().toString();
            String driver = riderView.getText().toString();
            String phone = phoneView.getText().toString();
            String destination = destinationView.getText().toString();
            String comments = commentsView.getText().toString();
            Ride ride = new Ride( rider, driver, phone, destination, comments );
            ride.setKey( key );

            AcceptRideDialogListener listener = (AcceptRideDialogListener) getActivity();
            listener.acceptRide( position, ride, ACCEPT );

            dismiss();
        }
    }
}
