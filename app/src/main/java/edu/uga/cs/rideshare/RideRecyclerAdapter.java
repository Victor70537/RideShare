package edu.uga.cs.rideshare;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RideRecyclerAdapter extends RecyclerView.Adapter<RideRecyclerAdapter.RideHolder> {

    public static final String DEBUG_TAG = "RideRecyclerAdapter";

    private int userStatus;
    private List<Ride> rideList;
    private Context context;

    public RideRecyclerAdapter(int userStatus, List<Ride> rideList, Context context) {
        this.rideList = rideList;
        this.context = context;
        this.userStatus = userStatus;
    }

    class RideHolder extends RecyclerView.ViewHolder {

        TextView rider;
        TextView driver;
        TextView phone;
        TextView destination;
        TextView comments;

        public RideHolder(View itemView) {
            super(itemView);

            rider = itemView.findViewById( R.id.rider );
            driver = itemView.findViewById( R.id.driver );
            phone = itemView.findViewById( R.id.phone );
            destination = itemView.findViewById( R.id.destination );
            comments = itemView.findViewById( R.id.comments );
        }
    }

    @NonNull
    @Override
    public RideHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ride, parent, false);
        return new RideHolder( view );
    }

    @Override
    public void onBindViewHolder(RideHolder holder, int position) {
        Ride ride = rideList.get(position);

        Log.d( DEBUG_TAG, "onBindViewHolder: " + ride );

        String key = ride.getKey();
        String rider = ride.getRider();
        String driver = ride.getDriver();
        String phone = ride.getPhone();
        String destination = ride.getDestination();
        String comments = ride.getComments();

        holder.rider.setText( ride.getRider());
        holder.driver.setText(ride.getDriver());
        holder.phone.setText( ride.getPhone() );
        holder.destination.setText( ride.getDestination() );
        holder.comments.setText( ride.getComments() );

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(DEBUG_TAG, String.valueOf(((View) v.getParent().getParent()).getId()));

                // profile = 2131231123
                // list = -1

                if (((View) v.getParent().getParent()).getId() == -1) {
                    AcceptRideDialogFragment acceptRideFragment =
                            AcceptRideDialogFragment.newInstance(holder.getAdapterPosition(), key, rider, driver, phone, destination, comments);
                    acceptRideFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), null);
                } else {
                    EditRideDialogFragment editRideFragment =
                            EditRideDialogFragment.newInstance(holder.getAdapterPosition(), userStatus, key, rider, driver, phone, destination, comments);
                    editRideFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), null);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rideList.size();
    }
}

