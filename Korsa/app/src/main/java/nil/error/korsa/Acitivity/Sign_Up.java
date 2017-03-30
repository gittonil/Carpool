package nil.error.korsa.Acitivity;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.app.ProgressDialog;
import android.os.Looper;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import nil.error.korsa.R;
import nil.error.korsa.models.request.SignUpRequestData;

public class Sign_Up extends Fragment {

    private TextInputLayout namelayout, unamelayout, passlayout, compasslayout, phonelayout;
    private TextView tvsgnpg;
    private View view;
    private Button submite;
    private EditText reg_name, reg_uname, reg_pass, reg_conpass, reg_phone, reg_address;
    private LinearLayout signup_layout;
    private SignUpRequestData signUpRequestData;
    Context context;
    ProgressDialog PD;
    ProgressBar progressBar;
    private FirebaseAuth auth;
    private Activity mActivity;


    //Check the textlayout field
    private TextWatcher mTextWatcher = new TextWatcher() {
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

    //    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void checkFieldsForEmptyValues() {
        Button b = submite;

        String s1 = reg_name.getText().toString();
        String s2 = reg_uname.getText().toString();
//        String s3 = reg_phone.getText().toString();
        String s4 = reg_pass.getText().toString();
        String s5 = reg_conpass.getText().toString();
//        String s6 = reg_address.getText().toString();

        if (s1.equals("")) {
            b.setEnabled(false);
            b.setBackground(getResources().getDrawable(R.drawable.disableshape));
        } else if (s2.equals("")) {
            b.setEnabled(false);
            b.setBackground(getResources().getDrawable(R.drawable.disableshape));
//        } else if (s3.length() < 10) {
//            b.setEnabled(false);
//            b.setBackground(getResources().getDrawable(R.drawable.disableshape));
        } else if (s4.equals("")) {
            b.setEnabled(false);
            b.setBackground(getResources().getDrawable(R.drawable.disableshape));
        } else if (!(s5.equals(s4))) {
            b.setEnabled(false);
            b.setBackground(getResources().getDrawable(R.drawable.disableshape));
//        } else if(s6.equals("")){
//            b.setEnabled(false);
//            b.setBackground(getResources().getDrawable(R.drawable.disableshape));
        } {
            b.setEnabled(true);
            b.setBackground(getResources().getDrawable(R.drawable.shape));
        }
    }

    public Sign_Up() {
        // Required empty public constructor
    }


    void init(View view) {
        context = view.getContext();
        reg_name = (EditText) view.findViewById(R.id.reg_name);
        reg_uname = (EditText) view.findViewById(R.id.reg_email);
        reg_pass = (EditText) view.findViewById(R.id.reg_pass);
        reg_conpass = (EditText) view.findViewById(R.id.reg_confirmpass);
//        reg_phone = (EditText) view.findViewById(R.id.reg_phone);
//        reg_address = (EditText) view.findViewById(R.id.reg_address);
        namelayout = (TextInputLayout) view.findViewById(R.id.register_layout);
        unamelayout = (TextInputLayout) view.findViewById(R.id.uname_layout);
        passlayout = (TextInputLayout) view.findViewById(R.id.pass_layout);
        compasslayout = (TextInputLayout) view.findViewById(R.id.compass_layout);
        submite = (Button) view.findViewById(R.id.btnsignup);

        view.setFocusableInTouchMode(true);
        view.requestFocus();

        reg_name.addTextChangedListener(mTextWatcher);
        reg_uname.addTextChangedListener(mTextWatcher);
//        reg_phone.addTextChangedListener(mTextWatcher);
        reg_pass.addTextChangedListener(mTextWatcher);
        reg_conpass.addTextChangedListener(mTextWatcher);
//        reg_address.addTextChangedListener(mTextWatcher);


        checkFieldsForEmptyValues();



        signUpRequestData = new SignUpRequestData();


    }

    public void setFragment(Fragment f) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.baseframelayout, f);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_sign__up, container, false);
        init(view);

        mActivity = getActivity();

        setupUI(view.findViewById(R.id.signup_activity));

        submite.getBackground().setAlpha(175);

        reg_name.addTextChangedListener(mTextWatcher);
        reg_uname.addTextChangedListener(mTextWatcher);
        reg_pass.addTextChangedListener(mTextWatcher);
        reg_conpass.addTextChangedListener(mTextWatcher);
//        reg_address.addTextChangedListener(mTextWatcher);

        auth = FirebaseAuth.getInstance();

        ViewGroup.MarginLayoutParams marginLayoutParams;
        marginLayoutParams = (ViewGroup.MarginLayoutParams) container.getLayoutParams();
        marginLayoutParams.setMargins(0, 50, 0, 0);

        checkFieldsForEmptyValues();

        submite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = reg_name.getText().toString();
                String uname = reg_uname.getText().toString();
                String pass = reg_pass.getText().toString();
//                String phone = reg_phone.getText().toString();
//                String address = reg_address.getText().toString();


                auth.createUserWithEmailAndPassword(uname, pass)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(getContext(), "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                if (!task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(getActivity(), HomePage.class));
                                    mActivity.finish();
                                }
                            }
                        });

//                signUpRequestData.setUserFullName(reg_name.getText().toString());
//                signUpRequestData.setUserEmail(reg_uname.getText().toString());
//                signUpRequestData.setPassword(reg_pass.getText().toString());
//                signUpRequestData.setMobileno(reg_phone.getText().toString());
//                signUpRequestData.setAddress(reg_address.getText().toString());
//                new LoginAsync().execute();
            }
        });

        tvsgnpg = (TextView) view.findViewById(R.id.login_link);
        tvsgnpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f = new Login();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.baseframelayout, f);
                ft.addToBackStack(null);
                ft.commit();
            }
        });


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

/*
    class LoginAsync extends AsyncTask<String, Void, Void> {

//        private Dialog loadingDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PD = new ProgressDialog(getContext());
            PD.setTitle("Please Wait..");
            PD.setMessage("Loading...");
            PD.setCancelable(false);
            PD.show();
        }



//        @Override
//        protected void onPreExecute() {
////            super.onPreExecute();
//            loadingDialog = ProgressDialog.show(this,"Please wait", "Loading...");
//        }

        @Override
        protected Void doInBackground(String... params) {
//            String uname = params[0];
//            String pass = params[1];

            InputStream is = null;


            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("fullname", signUpRequestData.getUserFullName()));
            nameValuePairs.add(new BasicNameValuePair("email", signUpRequestData.getUserEmail()));
            nameValuePairs.add(new BasicNameValuePair("password", signUpRequestData.getPassword()));
            nameValuePairs.add(new BasicNameValuePair("mobileno", signUpRequestData.getMobileno()));
            nameValuePairs.add(new BasicNameValuePair("address",signUpRequestData.getAddress()));
//                nameValuePairs.add(new BasicNameValuePair("", pass));
            String result = null;
            Handler handler = new Handler(context.getMainLooper());
//            Looper.prepare();

            try {

                HttpClient httpClient = new DefaultHttpClient();
                String name = URLEncoder.encode(signUpRequestData.getUserFullName(),"utf-8");
               System.out.print(name);
                HttpPost httpPost = new HttpPost(
                        "http://apps.zealeducation.com/signup.php?email=" + signUpRequestData.getUserEmail() + "&mobileno=" + signUpRequestData.getMobileno() + "&fullname=" + name + "&password=" + signUpRequestData.getPassword());
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                String rbody = EntityUtils.toString(entity);
                System.out.print("Entity" + rbody);
                try {
                    final JSONObject resp = new JSONObject(rbody);
//                    handler = new Handler(context.getMainLooper());
                 //   Looper.prepare();
                    handler.post(new Runnable() {
                        public void run() {
                            try {
//                                Toast.makeText(context, resp.get("result").toString(), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                System.out.print("Jsonnexp" + e.toString());
                            }
                        }
                    });
                    if(resp.get("result").equals("Success")){
//                        Fragment f=new OTP();
//                        Bundle b =new Bundle();
//                        b.putString("mobileno",signUpRequestData.getMobileno());
//                        f.setArguments(b);
//                        FragmentManager fm=getActivity().getSupportFragmentManager();
//                        FragmentTransaction ft=fm.beginTransaction();
//                        ft.replace(R.id.baseframelayout,f);
//                        ft.addToBackStack(null);
//                        ft.commit();
                    }
                } catch (JSONException e) {
                        e.printStackTrace();
                        handler.post(new Runnable() {
                            public void run() {
                                Toast.makeText(context, "Number is already register ", Toast.LENGTH_SHORT).show();
                                showMessage("Number already Register","Try Forget Password link");

                            }
                        });
                    System.out.print("Jsonnexp" + e.toString());
                }

                System.out.println("\n\n\nDatttaaa" + rbody);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            Toast.makeText(getContext(), "Check connection", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            System.out.print("Jsonnexp" + e.toString());
                        }
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            PD.dismiss();
        }

    }
*/
    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}
