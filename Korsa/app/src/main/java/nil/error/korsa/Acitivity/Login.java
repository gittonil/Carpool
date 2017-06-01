package nil.error.korsa.Acitivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import nil.error.korsa.R;
import nil.error.korsa.models.request.LoginRequestData;


public class Login extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private LoginRequestData loginRequestData;
    private AutoCompleteTextView tvemail, tvpass;
    Button btnlog_in, btnsign_up;
    private TextView tvForgotPass, tvCreateAcoount;
    private TextInputLayout iplemail, iplpass;
    private View view;
    private Activity mActivity;
    private Inflater inflater;
    private Context context;
    ProgressBar progressBar;
    ProgressDialog PD;
    private FirebaseAuth auth;


        String data;
        String email, pass;

    //  create a textWatcher member for disable and enabel the button
    public TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
            checkFieldsForEmptyValues();
        }
    };

    public void checkFieldsForEmptyValues() {
        Button b = btnlog_in;

        String s1 = tvemail.getText().toString();
        String s2 = tvpass.getText().toString();

        if (s1.equals(" ")  && s2.length() > 4) {
            b.setEnabled(false);
            b.setBackground(getResources().getDrawable(R.drawable.shape));
        } else {
            b.setEnabled(true);
            b.setBackground(getResources().getDrawable(R.drawable.shape));
        }
    }
    public Login() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login, container, false);
        tvemail = (AutoCompleteTextView) view.findViewById(R.id.email);
        tvpass = (AutoCompleteTextView) view.findViewById(R.id.password);
        btnlog_in = (Button) view.findViewById(R.id.btnlogin);
        iplemail = (TextInputLayout) view.findViewById(R.id.textinputlayout);
        iplpass = (TextInputLayout) view.findViewById(R.id.textlayout);
        tvForgotPass = (TextView) view.findViewById(R.id.tvforgotpassword);
        tvCreateAcoount = (TextView) view.findViewById(R.id.signup);
        context = view.getContext();
        loginRequestData = new LoginRequestData();

        mActivity = getActivity();

        //Get Firebase Instance
        auth = FirebaseAuth.getInstance();

        setupUI(view.findViewById(R.id.loginactivity));

        //Check the textfield
        tvemail.addTextChangedListener(mTextWatcher);
        tvpass.addTextChangedListener(mTextWatcher);

        checkFieldsForEmptyValues();

        view.setFocusableInTouchMode(true);
        view.requestFocus();

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK){
                    mActivity.finish();
                    System.exit(0);
                }

                return false;
            }
        });

        btnlog_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = tvemail.getText().toString();
                final String pass = tvpass.getText().toString();

                auth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (pass.length() < 6) {
                                        tvpass.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(getContext(), getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent i =new Intent(getActivity(),MainActivity.class);
                                    i.putExtra("mobileno",loginRequestData.getUserEmail());
                                    startActivity(i);
                                    mActivity.finish();
                                }
                            }
                        });


            }
        });

        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f=new ForgetPassword();
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.baseframelayout,f);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        tvCreateAcoount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f=new Sign_Up();
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.baseframelayout,f);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }




    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
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

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                    }
                });
        builder.setMessage(message);
        builder.show();
    }

}
