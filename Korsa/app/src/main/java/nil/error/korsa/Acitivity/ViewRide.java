package nil.error.korsa.Acitivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import nil.error.korsa.R;

import static android.app.Activity.RESULT_OK;


public class ViewRide extends Fragment {


    private ListView listView;
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;

    private List<Offerride> listOfferride = new ArrayList<>();


    View view;

    AutoCompleteTextView atvsource, atvdestination, atvstartDate, atvstartTime;
    TextView tvsource, tvdest, tvdate, tvtime, tvseats;
    int PLACE_PICKER_REQUEST_SOURCE = 1;
    int PLACE_PICKER_REQUEST_DESTINATION = 10;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Button button;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewride, container, false);
        /*atvsource = (AutoCompleteTextView) view.findViewById(R.id.source);
        atvdestination = (AutoCompleteTextView) view.findViewById(R.id.destination);
        atvstartDate = (AutoCompleteTextView) view.findViewById(R.id.startDate);
        atvstartTime = (AutoCompleteTextView) view.findViewById(R.id.startTime);
        tvsource = (TextView) view.findViewById(R.id.sourceText);
        tvdest = (TextView) view.findViewById(R.id.DestinationText);
        tvdate = (TextView) view.findViewById(R.id.DateText);
        tvtime = (TextView) view.findViewById(R.id.TimeText);*/
        listView = (ListView) view.findViewById(R.id.listViewView);
        button = (Button) view.findViewById(R.id.btnViewRide);

        //      tvseats = (TextView) view.findViewById(R.id.SeatText);

//        tvseats.setText("Vacant Seats : ");

//        mFirebaseInstance = FirebaseDatabase.getInstance();
//
//        mFirebaseDatabase = mFirebaseInstance.getReference("users");
//
//        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");
//
//        mFirebaseInstance.getReference("app_title").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
//            @Override
//            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
//
//                String apptitle = dataSnapshot.getValue(String.class);
//
//                tvsource.setText(apptitle);
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//                System.out.println("Failed to set app title");
//            }
//        });
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




        button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference reference = database.getReference("users");

        Firebase.setAndroidContext(getContext());

        Firebase ref = new Firebase("https://korsa-e03ae.firebaseio.com/Ride");

//        String userID = mFirebaseDatabase.push().getKey();


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                String value = dataSnapshot.getValue(String.class);

                Map<String, String> map = dataSnapshot.getValue(Map.class);

                String source = map.get("source");
                String destination = map.get("destination");

                Log.v("E_Value", "Source : " + source);
                Log.v("E_Value", "Destination : " + destination);

//                atvsource.setText(value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getContext(), "Error in view ride", Toast.LENGTH_LONG).show();
            }
        });

//        reference.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
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
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(getContext(), "Failefddd", Toast.LENGTH_LONG).show();
//            }
//        });


    }
});
//        atvsource.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//
//                Intent intent;
//                try {
//                    intent = builder.build(getActivity());
//                    startActivityForResult(intent,PLACE_PICKER_REQUEST_SOURCE);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        atvdestination.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//
//                Intent intent;
//                try {
//                    intent = builder.build(getActivity());
//                    startActivityForResult(intent,PLACE_PICKER_REQUEST_DESTINATION);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        atvstartDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Calendar c = Calendar.getInstance();
//                mYear = c.get(Calendar.YEAR);
//                mMonth = c.get(Calendar.MONTH);
//                mDay = c.get(Calendar.DAY_OF_MONTH);
//
//                // Launch Date Picker Dialog
//                DatePickerDialog dpd = new DatePickerDialog(getContext(),
//                        new DatePickerDialog.OnDateSetListener() {
//
//                            @Override
//                            public void onDateSet(DatePicker view, int year,
//                                                  int monthOfYear, int dayOfMonth) {
//
//                                // Display Selected date in textbox
//                                atvstartDate.setText(dayOfMonth + "-"
//                                        + (monthOfYear + 1) + "-" + year);
//                                tvdate.setText("Start Date : ");
//                            }
//                        }, mYear, mMonth, mDay);
//                dpd.show();
//            }
//        });
//
//        atvstartTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Process to get Current Time
//                final Calendar c = Calendar.getInstance();
//                mHour = c.get(Calendar.HOUR_OF_DAY);
//                mMinute = c.get(Calendar.MINUTE);
//
//                // Launch Time Picker Dialog
//                TimePickerDialog tpd = new TimePickerDialog(getContext(),
//                        new TimePickerDialog.OnTimeSetListener() {
//
//                            @Override
//                            public void onTimeSet(TimePicker view, int hourOfDay,
//                                                  int minute) {
//                                // Display Selected time in textbox
//                                atvstartTime.setText(hourOfDay + ":" + minute);
//                                tvtime.setText("Start Time : ");
//                            }
//                        }, mHour, mMinute, false);
//                tpd.show();
//            }
//        });


        this.view = view;

        return view;
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        if (requestCode == PLACE_PICKER_REQUEST_SOURCE){
//            if (resultCode == RESULT_OK){
//                Place place = PlacePicker.getPlace(data, getContext());
//                String address = String.format("%s",place.getAddress());
//                atvsource.setText(address);
//                tvsource.setText("Source Point : ");
//            }
//        }
//
//        if (requestCode == PLACE_PICKER_REQUEST_DESTINATION){
//            if (resultCode == RESULT_OK){
//                Place place = PlacePicker.getPlace(data, getContext());
//                String address = String.format("%s",place.getAddress());
//                atvdestination.setText(address);
//                tvdest.setText("Destination Point : ");
//            }
//        }
//
//
//    }

}
