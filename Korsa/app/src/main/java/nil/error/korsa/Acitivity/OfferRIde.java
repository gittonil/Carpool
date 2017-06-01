package nil.error.korsa.Acitivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.client.Config;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import nil.error.korsa.R;

import static android.app.Activity.RESULT_OK;
import static nil.error.korsa.Acitivity.Offerride.mapdest;
import static nil.error.korsa.Acitivity.Offerride.mapsource;


public class OfferRIde extends Fragment {

    View view;
    private AutoCompleteTextView atvsource, atvdestination, atvstartDate, atvstartTime, atvseats;
    private TextView tvsource, tvdest, tvdate, tvtime, tvseats;
    private ListView listView;
    private CheckBox checkgendermale, checkgenderfemale, checkgenderother;
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;
    private DatabaseReference mFirebaseDatabase;
    int PLACE_PICKER_REQUEST_SOURCE = 1;
    int PLACE_PICKER_REQUEST_DESTINATION = 10;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Button btnoffer_ride, mEn_route;
    private FirebaseDatabase mFirebaseInstance;
    private String userID;
    private String offeremail;
    public static final String FIREBASE_URL = "https://korsa-e03ae.firebaseio.com/";

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
        atvseats = (AutoCompleteTextView) view.findViewById(R.id.seatsVacant);
        checkgendermale = (CheckBox) view.findViewById(R.id.gendermale);
        checkgenderother = (CheckBox) view.findViewById(R.id.genderfemale);
        checkgenderfemale = (CheckBox) view.findViewById(R.id.genderother);
        mEn_route = (Button) view.findViewById(R.id.en_route);
        //listView = (ListView) view.findViewById(R.id.listView);

        //listView.setVisibility(View.INVISIBLE);
        //Firebase Client for android set up
        Firebase.setAndroidContext(getContext());


        //get user email address
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null){
            offeremail = user.getEmail();
        }

        //firebase database initialize
        FirebaseApp.initializeApp(getContext());
        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference();
//        databaseRef.setValue("Hello value!");

/*
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
*/


        tvsource = (TextView) view.findViewById(R.id.sourceText);
        tvdest = (TextView) view.findViewById(R.id.DestinationText);
        tvdate = (TextView) view.findViewById(R.id.DateText);
        tvtime = (TextView) view.findViewById(R.id.TimeText);
        tvseats = (TextView) view.findViewById(R.id.SeatText);
        btnoffer_ride = (Button) view.findViewById(R.id.submit_ride);

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

                if (isNetworkAvailable() == true) {

                    Offerride offerride = new Offerride(UUID.randomUUID().toString(),
                            atvsource.getText().toString(),
                            atvdestination.getText().toString(),
                            atvstartDate.getText().toString(),
                            atvstartTime.getText().toString(),
                            atvseats.getText().toString(),
                            checkgendermale.isChecked(),
                            checkgenderfemale.isChecked(),
                            checkgenderother.isChecked());

                    databaseRef.child("Ride").child(offerride.getUid()).setValue(offerride);


                    final Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showMessage("Published Ride","Successfully Published Your Ride.");
                        }
                    },2000);

                }else {
                    Toast.makeText(getContext(),"Please connect to internet",Toast.LENGTH_LONG).show();
                }
            }
        });
        
        mEn_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent route = new Intent(getActivity(), MapsActivity.class);
                route.putExtra(mapsource, atvsource.getText().toString());
                route.putExtra(mapdest, atvdestination.getText().toString());
                startActivity(route);

            }
        });


//        toggleButton();

        this.view = view;

        return view;
    }


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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Fragment f=new MainFragment();
                                FragmentManager fm=getActivity().getSupportFragmentManager();
                                FragmentTransaction ft=fm.beginTransaction();
                                ft.replace(R.id.frghomeholder,f);
                                ft.addToBackStack(null);
                                ft.commit();
                            }
                        },1500);

                    }
                });
        builder.setMessage(message);
        builder.show();
    }

}
