package nil.error.korsa.Acitivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import nil.error.korsa.R;

import static android.app.Activity.RESULT_OK;


public class OfferRIde extends Fragment {

    View view;
    AutoCompleteTextView atvsource, atvdestination, atvstartDate, atvstartTime;
    TextView tvsource, tvdest, tvdate, tvtime, tvseats;
    int PLACE_PICKER_REQUEST_SOURCE = 1;
    int PLACE_PICKER_REQUEST_DESTINATION = 10;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Button btnoffer_ride;
    FirebaseDatabase database;
    DatabaseReference databaseRef;

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

        //firebase database initialize
        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference("message");
        databaseRef.setValue("Hello value!");

        tvsource = (TextView) view.findViewById(R.id.sourceText);
        tvdest = (TextView) view.findViewById(R.id.DestinationText);
        tvdate = (TextView) view.findViewById(R.id.DateText);
        tvtime = (TextView) view.findViewById(R.id.TimeText);
        tvseats = (TextView) view.findViewById(R.id.SeatText);
        btnoffer_ride = (Button) view.findViewById(R.id.submit_ride);

        tvseats.setText("Vacant Seats : ");

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
                databaseRef = database.getReference("Source_Point");
                databaseRef.setValue(atvsource.getText().toString());
                atvsource.setText("");
                databaseRef = database.getReference("Dest_Point");
                databaseRef.setValue(atvdestination.getText().toString());
                atvdestination.setText("");
                databaseRef = database.getReference("Start_Date");
                databaseRef.setValue(atvstartDate.getText().toString());
                atvstartDate.setText("");
                databaseRef = database.getReference("Start_Time");
                databaseRef.setValue(atvstartTime.getText().toString());
                atvstartTime.setText("");

                Fragment main = new MainFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frghomeholder, main);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                getActivity().setTitle("Home Page");

                Toast.makeText(getContext(),"Your ride is created.",Toast.LENGTH_LONG).show();

            }
        });

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

}
