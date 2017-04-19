package nil.error.korsa.Acitivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;

import nil.error.korsa.R;

public class VehicleDetail extends AppCompatActivity {

    private AutoCompleteTextView atvvehibrandname, atvvehimodelname, atvvehinumber;
    private RadioButton rblicence, rbpuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_detail);

        atvvehibrandname = (AutoCompleteTextView) findViewById(R.id.vehi_brandname);
        atvvehimodelname = (AutoCompleteTextView) findViewById(R.id.vehi_modelname);
        atvvehinumber = (AutoCompleteTextView) findViewById(R.id.vehi_number);
        rblicence = (RadioButton) findViewById(R.id.vehi_licence);
        rbpuc = (RadioButton) findViewById(R.id.vehi_puc);


    }
}
