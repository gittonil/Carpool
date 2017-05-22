package nil.error.korsa.Acitivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.firebase.client.Firebase;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import nil.error.korsa.R;

import static android.app.Activity.RESULT_OK;


public class OfferRIde extends Fragment {

    public static final String source = "nil.error.korsa.Acitivity.source";
    public static final String dest = "nil.error.korsa.Acitivity.dest";
    public static final String FIREBASE_URL = "https://korsa-e03ae.firebaseio.com/";
    View view;
    int PLACE_PICKER_REQUEST_SOURCE = 1;
    int PLACE_PICKER_REQUEST_DESTINATION = 10;
    private AutoCompleteTextView atvsource, atvdestination, atvstartDate, atvstartTime;
    private TextView tvsource, tvdest, tvdate, tvtime, tvseats;
    private ListView listView;
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;
    private DatabaseReference mFirebaseDatabase;
    private int mYear, mMonth, mDay, mHour, mMinute;
    // public static final String sourceLatitude = "nil.error.korsa.Acitivity.sourcelat";
    //public static final String sourceLongitude = "nil.error.korsa.Acitivity.sourcelong";
    //public static final String destLatitude = "nil.error.korsa.Acitivity.destlat";
    //public static final String destLongitude = "nil.error.korsa.Acitivity.destlong";
    private Button btnoffer_ride, mEn_route;
    private FirebaseDatabase mFirebaseInstance;
    private String userID;
    private List<Offerride> listOfferride = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offerride, container, false);
        atvsource = (AutoCompleteTextView) view.findViewById(R.id.source);
        atvdestination = (AutoCompleteTextView) view.findViewById(R.id.destination);
        atvstartDate = (AutoCompleteTextView) view.findViewById(R.id.startDate);
        atvstartTime = (AutoCompleteTextView) view.findViewById(R.id.startTime);
        listView = (ListView) view.findViewById(R.id.listView);

        listView.setVisibility(View.INVISIBLE);
        //Firebase Client for android set up
        Firebase.setAndroidContext(getContext());



        //firebase database initialize
        FirebaseApp.initializeApp(getContext());
        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference();
//        databaseRef.setValue("Hello value!");

        databaseRef.child("Ride").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                if (listOfferride.size() > 0)
                    listOfferride.clear();
                for (com.google.firebase.database.DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    Offerride offerride = postSnapshot.getValue(Offerride.class);
                    listOfferride.add(offerride);
                }

                ListViewAdapter listViewAdapter = new ListViewAdapter(getActivity(), listOfferride);

                listView.setAdapter(listViewAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        tvsource = (TextView) view.findViewById(R.id.sourceText);
        tvdest = (TextView) view.findViewById(R.id.DestinationText);
        tvdate = (TextView) view.findViewById(R.id.DateText);
        tvtime = (TextView) view.findViewById(R.id.TimeText);
        tvseats = (TextView) view.findViewById(R.id.SeatText);
        btnoffer_ride = (Button) view.findViewById(R.id.submit_ride);
        mEn_route = (Button) view.findViewById(R.id.en_route);

        tvseats.setText("Vacant Seats : ");



        //code with Realtime Firebase Database Android Hive

        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");

        mFirebaseInstance.getReference("app_title").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                String apptitle = dataSnapshot.getValue(String.class);

                tvsource.setText(apptitle);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

                System.out.println("Failed to set app title");
            }
        });



        atvsource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                Intent intent;
                try {
                    intent = builder.build(getActivity());
                    startActivityForResult(intent,PLACE_PICKER_REQUEST_SOURCE);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        atvdestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                Intent intent;
                try {
                    intent = builder.build(getActivity());
                    startActivityForResult(intent,PLACE_PICKER_REQUEST_DESTINATION);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        atvstartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                // Display Selected date in textbox
                                atvstartDate.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);
                                tvdate.setText("Start Date : ");
                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
            }
        });

        atvstartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Process to get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog tpd = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // Display Selected time in textbox
                                atvstartTime.setText(hourOfDay + ":" + minute);
                                tvtime.setText("Start Time : ");
                            }
                        }, mHour, mMinute, false);
                tpd.show();
            }
        });


        btnoffer_ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

         /*   String source = atvsource.getText().toString().trim();
            String destination = atvdestination.getText().toString().trim();
            String startDate = atvstartDate.getText().toString().trim();
            String startTime = atvstartTime.getText().toString().trim();

                if (TextUtils.isEmpty(userID)){
//                    createUser(source, destination, startDate, startTime);
                }else{
//                    updateUser(source, destination, startDate, startTime);
                }*/


         Offerride offerride = new Offerride(UUID.randomUUID().toString(),
                 atvsource.getText().toString(),
                 atvdestination.getText().toString(),
                 atvstartDate.getText().toString(),
                 atvstartTime.getText().toString());

                databaseRef.child("Ride").child(offerride.getUid()).setValue(offerride);

            }
        });

        mEn_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent route = new Intent(getActivity(), MapsActivity.class);
                route.putExtra(source, atvsource.getText().toString());
                route.putExtra(dest, atvdestination.getText().toString());
                startActivity(route);

            }
        });

//        toggleButton();

        this.view = view;

        return view;
    }

/*
    // Changing button text
    private void toggleButton() {
        if (TextUtils.isEmpty(userID)) {
            btnoffer_ride.setText("Save");
        } else {
            btnoffer_ride.setText("Update");
        }
    }

    */
/**
     * Creating new user node under 'users'
     *//*

    private void createUser(String source, String destination, String startDate, String startTime) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userID)) {
            userID = mFirebaseDatabase.push().getKey();
        }

        Offerride user = new Offerride(source, destination, startDate, startTime);

        mFirebaseDatabase.push().child(userID).setValue(user);
*/

//        addUserChangeListener();
    //}
//    private void addUserChangeListener(){
//        mFirebaseDatabase.child(userID).addValueEventListener(new com.google.firebase.database.ValueEventListener() {
//            @Override
//            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
//                Offerride user = dataSnapshot.getValue(Offerride.class);
//
//                if (user == null){
//                    Toast.makeText(getContext(),"User data is null!",Toast.LENGTH_LONG).show();
//                    return;
//                }
//
//                tvsource.setText(user.source + " , " + user.destination + " , " + user.startDate + " , " + user.startTime);
//
//                toggleButton();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(getContext(), "Failefddd", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    private void updateUser(String source, String destination, String startDate, String startTime) {
//        // updating the user via child nodes
//        if (!TextUtils.isEmpty(source))
//            mFirebaseDatabase.child(userID).child("source").setValue(source);
//
//        if (!TextUtils.isEmpty(destination))
//            mFirebaseDatabase.child(userID).child("destination").setValue(destination);
//
//        if (!TextUtils.isEmpty(startDate))
//            mFirebaseDatabase.child(userID).child("startDate").setValue(startDate);
//
//        if (!TextUtils.isEmpty(startTime))
//            mFirebaseDatabase.child(userID).child("startTime").setValue(startTime);
//
//    }
//
//
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == PLACE_PICKER_REQUEST_SOURCE){
            if (resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(data, getContext());
                String address = String.format("%s",place.getAddress());
                atvsource.setText(address);
                tvsource.setText("Source Point : ");
            }
        }

        if (requestCode == PLACE_PICKER_REQUEST_DESTINATION){
            if (resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(data, getContext());
                String address = String.format("%s",place.getAddress());
                atvdestination.setText(address);
                tvdest.setText("Destination Point : ");
            }
        }
    }

}
