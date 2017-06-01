package nil.error.korsa.Acitivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import nil.error.korsa.R;

public class ForgetPassword extends Fragment {

    private EditText tvemailid;
    private Button btnClick;
    private Context context;
    private FirebaseAuth mAuth;

    public ForgetPassword() {
        // Required empty public constructor
    }

    public static ForgetPassword newInstance(String param1, String param2) {
        ForgetPassword fragment = new ForgetPassword();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);


        mAuth = FirebaseAuth.getInstance();

        init(view);
        return view;
    }

    void init(View view){
        context = view.getContext();
        tvemailid = (EditText) view.findViewById(R.id.edtOTP);
        btnClick = (Button)view.findViewById(R.id.btnClick);

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNetworkAvailable() == true) {
                    String email = tvemailid.getText().toString().trim();
                    mAuth.sendPasswordResetEmail(email);

                    final Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showMessage("Password Reset","Password reset mail sent to your email id.");
                        }
                    },2000);

                }else {
                    Toast.makeText(getContext(),"Please connect to internet",Toast.LENGTH_LONG).show();
                }


            }
        });
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
                                Fragment f=new Login();
                                FragmentManager fm=getActivity().getSupportFragmentManager();
                                FragmentTransaction ft=fm.beginTransaction();
                                ft.replace(R.id.baseframelayout,f);
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
