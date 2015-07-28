package com.app.zimfiz.zimfiz;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import fragments.SelectSchool;
import utils.AutoCompleteAdapter;
import utils.Constants;

/**
 * Created by neokree on 24/11/14.
 */
public class UniversityPayment extends ActionBarActivity {
	final int getSchoolData = 1;
    AutoCompleteTextView mBankName;
	EditText mAccountNumber;
	EditText mAccountName;
    EditText mSelectSchool;
    EditText mStudentName;
    Spinner mForm;
  Spinner mTerm;
    EditText mAmount;
    Spinner mPaymentMethod;
    EditText mPaidBy;

    String sBankName;
    String sAccountNumber;
    String sAccountName;
    String sSelectSchool;
    String sStudentName;
    String sForm;
    String sTerm;
    String sAmount;
    String sPaymentMethod;
    String sPaidBy;
    String id;
    ProgressDialog mProgress;
    RequestQueue mVolleyQueue;
    private static final String TAG = "TAG";
    ArrayList<String> searchArrayList;
    Button mContinue;
    private Toolbar toolbar;
    String[] payments = {"Ecocash","Telecash",
            "ZimSwitch", "Visa Card", "Master Card"};
    int arr_images[] = { R.drawable.ecocash,
            R.drawable.telecash, R.drawable.zimswitch,
            R.drawable.visa, R.drawable.ic_check_box_push};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.university_payment);
        mContinue = (Button)findViewById(R.id.btncontinue);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mVolleyQueue = Volley.newRequestQueue(this);
        mProgress = new ProgressDialog(UniversityPayment.this);
        mProgress.setMessage("Please wait...");
        searchArrayList = new ArrayList<>();

        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(Constants.GETALLBANKS);
        if(entry != null){
            //Cache data available.
            try {
                String data = new String(entry.data, "UTF-8");
                Log.d("CACHE DATA", data);
                JSONArray jsonArray=new JSONArray(data);
                setData(jsonArray,true);

                //Toast.makeText(getActivity(), "Loading from cache.", Toast.LENGTH_SHORT).show();
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            makeSampleHttpRequest();
        }

        AutoCompleteAdapter adapter = new AutoCompleteAdapter(this,
                R.layout.layout, R.id.textView1, searchArrayList);

        mBankName = (AutoCompleteTextView) findViewById(R.id.bankName);
        mBankName.setAdapter(adapter);

        SharedPreferences sharedPreferences = getSharedPreferences("user_login", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("user_name", Constants.NOTAVAILABLE);
        String surname = sharedPreferences.getString("user_surname", Constants.NOTAVAILABLE);
         id = sharedPreferences.getString("id", Constants.NOTAVAILABLE);

        mSelectSchool = (EditText)findViewById(R.id.schoolName);

        mAccountNumber = (EditText)findViewById(R.id.accountNumber);
        mAccountName = (EditText)findViewById(R.id.accountName);
        mStudentName = (EditText) findViewById(R.id.studentName);
        mForm = (Spinner)findViewById(R.id.form);
        mTerm = (Spinner)findViewById(R.id.term);
        mPaymentMethod = (Spinner)findViewById(R.id.paymentMethod);
        mAmount = (EditText)findViewById(R.id.amount);
        mPaidBy = (EditText)findViewById(R.id.paidBy);

        mPaidBy.setText(name + " "+ surname);

        mPaymentMethod.setAdapter(new MyAdapter(UniversityPayment.this, R.layout.spinner_list_item, payments));
        mSelectSchool.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(UniversityPayment.this, SelectSchool.class);
                startActivityForResult(myIntent, getSchoolData);

            }
        });

        mContinue.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                sBankName = mBankName.getText().toString();
                sAccountNumber = mAccountNumber.getText().toString();
                sAccountName = mAccountName.getText().toString();
                sSelectSchool = mSelectSchool.getText().toString();
                sStudentName = mStudentName.getText().toString();
                sForm = mForm.getSelectedItem().toString();
                sTerm = mTerm.getSelectedItem().toString();
                sAmount = mAmount.getText().toString();
                sPaymentMethod = mPaymentMethod.getSelectedItem().toString();
                sPaidBy = mPaidBy.getText().toString();
                if(sBankName.equals("") || sAccountNumber.equals("") || sAccountName.equals("") || sSelectSchool.equals("") || sStudentName.equals("") || sAmount.equals("") || sPaymentMethod.equals("") || sPaidBy.equals("")){
                    Toast.makeText(UniversityPayment.this, "Please fill in the missing fields", Toast.LENGTH_LONG).show();
                }else{
                    Intent myIntent = new Intent(UniversityPayment.this, ConfirmPayment.class);
                    Bundle extras = new Bundle();


                    extras.putString("sBankName", sBankName);
                    extras.putString("sAccountNumber", sAccountNumber);
                    extras.putString("sAccountName", sAccountName);
                    extras.putString("sSelectSchool", sSelectSchool);
                    extras.putString("sStudentName", sStudentName);
                    extras.putString("sForm", sForm);
                    extras.putString("sTerm", sTerm);
                    extras.putString("sAmount", sAmount);
                    extras.putString("sPaymentMethod", sPaymentMethod);
                    extras.putString("sPaidBy", sPaidBy);
                    extras.putString("id", id);
                    myIntent.putExtras(extras);

                    startActivity(myIntent);
                }


            }
        });
    }
    public void onDestroy() {
        super.onDestroy();
    }

    public void onStop() {
        super.onStop();
        if (mProgress.isShowing())
            mProgress.dismiss();
    }
    private void makeSampleHttpRequest() {
        try{
            mProgress.show();
            JsonArrayRequest movieReq = new JsonArrayRequest(Constants.GETALLBANKS,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            setData(response,false);
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if( error instanceof NetworkError) {

                    } else if( error instanceof ServerError) {
                    } else if( error instanceof AuthFailureError) {
                    } else if( error instanceof ParseError) {
                    } else if( error instanceof NoConnectionError) {
                    } else if( error instanceof TimeoutError) {
                    }

                    if (mProgress.isShowing())
                        mProgress.dismiss();
                }
            });
            AppController.getInstance().addToRequestQueue(movieReq);
            //mVolleyQueue.add(movieReq);
        }

        catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void setData(JSONArray response,Boolean isCache){
        Log.d(TAG, response.toString());
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject jsonObj = response.getJSONObject(i);


                searchArrayList.add(jsonObj.getString("bank_name"));


            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        if(!isCache){
            // Toast.makeText(getActivity(), "Cache not available..Loading from service", Toast.LENGTH_SHORT).show();
            if (mProgress.isShowing())
                mProgress.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
	     super.onActivityResult(requestCode, resultCode, data);
        if(data == null){
            mAccountName.setText("");
            mAccountNumber.setText("");
            mBankName.setText("");
            mSelectSchool.setText("");
        }else{
            mAccountName.setText(data.getStringExtra("accountName"));
            // mAccountNumber.setText(data.getStringExtra("accountNumber"));
            // mBankName.setText(data.getStringExtra("bankName"));
            mSelectSchool.setText(data.getStringExtra("schoolName"));
        }

	     
	    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public  boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);

    }
    public class MyAdapter extends ArrayAdapter<String> {

        public MyAdapter(Context context, int textViewResourceId,	String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater=getLayoutInflater();
            View row=inflater.inflate(R.layout.spinner_list_item, parent, false);
            TextView label=(TextView)row.findViewById(R.id.schoolName);
            label.setText(payments[position]);


            ImageView icon=(ImageView)row.findViewById(R.id.icon);
            icon.setImageResource(arr_images[position]);

            return row;
        }
    }
}
