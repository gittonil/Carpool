package nil.error.korsa.Acitivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import nil.error.korsa.R;

public class Authentication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        Fragment fragment = new Login();


        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        if (savedInstanceState == null){

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Login logIn = new Login();
            fragmentTransaction.add(R.id.baseframelayout, logIn);
            fragmentTransaction.commit();
        }else{
//             Toast.makeText(getApplicationContext(),"Error Occure",Toast.LENGTH_LONG).show();
        }
    }

    public void zeal(){

    }

}
