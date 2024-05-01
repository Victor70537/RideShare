package edu.uga.cs.rideshare;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditRideDialogFragment extends DialogFragment {

    public static final int SAVE = 1;
    public static final int DELETE = 2;

    private EditText riderView;
    private EditText driverView;
    private EditText phoneView;
    private EditText destinationView;
    private EditText commentsView;

    int position;
    int userStatus;
    String key;
    String rider;
    String driver;
    String phone;
    String destination;
    String comments;

    public interface EditRideDialogListener {
        void updateRide(int position, Ride ride, int action);
    }

    public static EditRideDialogFragment newInstance(int position, int userStatus, String key, String rider, String driver, String phone, String destination, String comments) {
        EditRideDialogFragment dialog = new EditRideDialogFragment();

        Bundle args = new Bundle();
        args.putInt("user status", userStatus);
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

        userStatus = getArguments().getInt("user status");
        key = getArguments().getString( "key" );
        position = getArguments().getInt( "position" );
        rider = getArguments().getString("rider");
        driver = getArguments().getString( "driver" );
        phone = getArguments().getString( "phone" );
        destination = getArguments().getString( "destination" );
        comments = getArguments().getString( "comments" );

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate( R.layout.edit_ride_dialog, getActivity().findViewById( R.id.root ) );

        riderView = layout.findViewById( R.id.editText1 );
        driverView = layout.findViewById( R.id.editText2 );
        phoneView = layout.findViewById( R.id.editText3 );
        destinationView = layout.findViewById( R.id.editText4 );
        commentsView = layout.findViewById( R.id.editText5 );

        riderView.setText( rider );
        driverView.setText( driver );
        phoneView.setText( phone );
        destinationView.setText( destination );
        commentsView.setText( comments );

        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity(), R.style.AlertDialogStyle );
        builder.setView(layout);

        builder.setTitle( "Edit Ride" );

        builder.setNegativeButton( android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                // close the dialog
                dialog.dismiss();
            }
        });

        builder.setPositiveButton( "SAVE", new SaveButtonClickListener() );
        builder.setNeutralButton( "DELETE", new DeleteButtonClickListener() );

        return builder.create();
    }

    private class SaveButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String rider = riderView.getText().toString();
            String driver = driverView.getText().toString();
            String phone = phoneView.getText().toString();
            String destination = destinationView.getText().toString();
            String comments = commentsView.getText().toString();
            Ride ride = new Ride( rider, driver, phone, destination, comments );
            ride.setKey( key );

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String userId = user.getUid();

            ride.setRiderId(userId);

            if (userStatus == 1) {
//                ride.setRider(username);
                ride.setRiderId(userId);
            } else if (userStatus == 2) {
//                ride.setDriver(username);
                ride.setDriverId(userId);
            }

            EditRideDialogListener listener = (EditRideDialogListener) getActivity();
            listener.updateRide( position, ride, SAVE );

            dismiss();
        }


    }

    private class DeleteButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick( DialogInterface dialog, int which ) {

            Ride ride = new Ride( rider, driver, phone, destination, comments );
            ride.setKey( key );

            EditRideDialogListener listener = (EditRideDialogListener) getActivity();            // add the new job lead
            listener.updateRide( position, ride, DELETE );

            dismiss();
        }
    }
}