package nil.error.korsa.Acitivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

import nil.error.korsa.R;

public class UserProfile extends AppCompatActivity {

    private AutoCompleteTextView atvusername, atvuserContactNumber, atvuserDOB, atvuserage, atvuserprofession, atvvehibrandname, atvvehimodelname, atvvehinumber;
    private RadioButton rblicence, rbpuc;
    private Button btnVehicleDetails, btnSubmit;
    private FirebaseDatabase mfirebaseDatabase;
    private DatabaseReference mdatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);

        atvusername = (AutoCompleteTextView) findViewById(R.id.user_name);
        atvuserContactNumber= (AutoCompleteTextView) findViewById(R.id.user_contactno);
        atvuserDOB = (AutoCompleteTextView) findViewById(R.id.user_dob);
        atvuserage = (AutoCompleteTextView) findViewById(R.id.user_age);
        atvuserprofession = (AutoCompleteTextView) findViewById(R.id.user_profession);
        atvvehibrandname = (AutoCompleteTextView) findViewById(R.id.vehi_brandname);
        atvvehimodelname = (AutoCompleteTextView) findViewById(R.id.vehi_modelname);
        atvvehinumber = (AutoCompleteTextView) findViewById(R.id.vehi_number);
        rblicence = (RadioButton) findViewById(R.id.vehi_licence);
        rbpuc = (RadioButton) findViewById(R.id.vehi_puc);

        btnSubmit = (Button) findViewById(R.id.submit_userDetails);
//        btnVehicleDetails = (Button) findViewById(R.id.getvechicledetails);

        setupUI(findViewById(R.id.createUser));

        FirebaseApp.initializeApp(this);

        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mdatabaseReference = mfirebaseDatabase.getReference();

        mdatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User(UUID.randomUUID().toString(),
                        atvusername.getText().toString(),
                        atvuserContactNumber.getText().toString(),
                        atvuserDOB.getText().toString(),
                        atvuserage.getText().toString(),
                        atvuserprofession.getText().toString(),
                        atvvehibrandname.getText().toString(),
                        atvvehimodelname.getText().toString(),
                        atvvehinumber.getText().toString(),
                        rblicence.isChecked(),
                        rbpuc.isChecked());

                mdatabaseReference.child("User").child(user.getUid()).setValue(user);

            }
        });

    }

    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(UserProfile.this);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


}
