package edu.uga.cs.rideshare;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * This class delivers the dialog format for a ride when attempting to
 * accept the ride either as a driver or rider.
 */
public class AcceptRideDialogFragment extends DialogFragment {
    public static final int ACCEPT = 1;

    private TextView riderView;
    private TextView driverView;
    private TextView phoneView;
    private TextView destinationView;
    private TextView commentsView;

    private EditText usernameInput;

    int position;
    int userStatus;

    String key;
    String rider;
    String driver;
    String phone;
    String destination;
    String comments;
    String riderUserId;
    String driverUserId;

    public interface AcceptRideDialogListener {
        void acceptRide(int position, Ride ride, int action);
    }

    public static AcceptRideDialogFragment newInstance(int position, int userStatus, String key, String rider,
                                                       String driver, String phone, String destination,
                                                       String comments, String riderUserId, String driverUserId) {
        AcceptRideDialogFragment dialog = new AcceptRideDialogFragment();

        Bundle args = new Bundle();
        args.putInt("user status", userStatus);
        args.putString("key", key );
        args.putString("rider", rider );
        args.putString("driver", driver);
        args.putString("phone", phone);
        args.putString("destination", destination);
        args.putString("comments", comments);
        args.putString("riderUserId", riderUserId);
        args.putString("driverUserId", driverUserId);
        dialog.setArguments(args);

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ) {

        userStatus = getArguments().getInt("user status");
        key = getArguments().getString( "key" );
        position = getArguments().getInt( "position" );
        rider = getArguments().getString( "rider" );
        driver = getArguments().getString("driver");
        phone = getArguments().getString( "phone" );
        destination = getArguments().getString( "destination" );
        comments = getArguments().getString( "comments" );
        riderUserId = getArguments().getString( "riderUserId" );
        driverUserId = getArguments().getString( "driverUserId" );

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate( R.layout.accept_ride_dialog, getActivity().findViewById( R.id.root ) );

        riderView = layout.findViewById( R.id.textView1 );
        driverView = layout.findViewById( R.id.textView2 );
        phoneView = layout.findViewById( R.id.textView3 );
        destinationView = layout.findViewById( R.id.textView4 );
        commentsView = layout.findViewById( R.id.textView5 );

        usernameInput = layout.findViewById( R.id.editTextText2 );

        riderView.setText( rider );
        driverView.setText( driver );
        phoneView.setText( phone );
        destinationView.setText( destination );
        commentsView.setText( comments );

        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity(), R.style.AlertDialogStyle );
        builder.setView(layout);

        builder.setTitle( "Accept Ride Dialog" );

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
            String driver = driverView.getText().toString();
            String phone = phoneView.getText().toString();
            String destination = destinationView.getText().toString();
            String comments = commentsView.getText().toString();
            Ride ride = new Ride( rider, driver, phone, destination, comments );
            ride.setKey( key );

            if (userStatus == 1) {
                if (riderUserId == null && !driverUserId.equals("")) {
                    riderView.setText(usernameInput.getText().toString());

                    ride.setRider(riderView.getText().toString());
                    ride.setDriverId(driverUserId);

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String userId = user.getUid();

                    ride.setRiderId(userId);
                } else {
                    Toast.makeText(getContext(), "Cannot accept ride offer",
                            Toast.LENGTH_SHORT).show();
                }
            } else if (userStatus == 2) {
                if (driverUserId == null && !riderUserId.equals("")) {
                    Log.d("AcceptRideDialogFragment", "Accepting a ride as a driver");

                    driverView.setText(usernameInput.getText().toString());

                    ride.setDriver(driverView.getText().toString());
                    ride.setRiderId(riderUserId);

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String userId = user.getUid();

                    ride.setDriverId(userId);

                    Log.d("AcceptRideDialogFragment", ride.toString());

                } else {
                    Toast.makeText(getContext(), "Cannot accept ride request",
                            Toast.LENGTH_SHORT).show();
                }
            }

            AcceptRideDialogListener listener = (AcceptRideDialogListener) getActivity();
            listener.acceptRide( position, ride, ACCEPT );

            dismiss();
        }
    }
}
