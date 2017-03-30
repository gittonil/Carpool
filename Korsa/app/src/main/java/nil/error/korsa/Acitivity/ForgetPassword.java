package nil.error.korsa.Acitivity;

import android.app.ProgressDialog;
import android.content.Context;
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

    private EditText tvMobileno;
    private Button btnClick;
    private Context context;
    ProgressDialog PD;

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
        init(view);
        return view;
    }

    void init(View view){
        context = view.getContext();
        tvMobileno = (EditText) view.findViewById(R.id.edtOTP);
        btnClick = (Button)view.findViewById(R.id.btnClick);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile[]=new String[1];
                mobile[0]=tvMobileno.getText().toString();
                new ForgetAsync().execute(mobile);
            }
        });
    }


    class ForgetAsync extends AsyncTask<String, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PD = new ProgressDialog(getContext());
            PD.setTitle("Please Wait..");
            PD.setMessage("Loading...");
            PD.setCancelable(false);
            PD.show();
        }

        @Override
        protected Void doInBackground(String... params) {
//            String uname = params[0];
//            String pass = params[1];

            InputStream is = null;



//                nameValuePairs.add(new BasicNameValuePair("", pass));
            String result = null;
            Handler handler = new Handler(context.getMainLooper());
//            Looper.prepare();

            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(
                        "http://apps.zealeducation.com/forgotpassword.php?mobileno=" + params[0]);
//                httpPost.setEntity(new UrlEncodedFormEntity(""));

                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                String rbody = EntityUtils.toString(entity);
                System.out.print("Entity" + rbody + params[0]);
                try {
                    final JSONObject resp = new JSONObject(rbody);
//                    handler = new Handler(context.getMainLooper());
                    //   Looper.prepare();
                    if(resp.get("mobileno").equals(params[0])){ // {"id":"3","fullname":"yadav","mobileno":"8237471787","password":"1234","email":"yadav@s","activate":"1","otp":"63519"}
//                        Fragment f=new ForgetOTP();
//                        Bundle b =new Bundle();
//                        b.putString("json",resp.toString());
//                        f.setArguments(b);
//                        FragmentManager fm=getActivity().getSupportFragmentManager();
//                        FragmentTransaction ft=fm.beginTransaction();
//                        ft.replace(R.id.baseframelayout,f);
//                        ft.addToBackStack(null);
//                        ft.commit();
                    }else{

                    }

                } catch (JSONException e) {
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context, "Check your mobile number ", Toast.LENGTH_SHORT).show();

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


}
