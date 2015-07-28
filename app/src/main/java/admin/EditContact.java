package admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.zimfiz.zimfiz.AppController;
import com.app.zimfiz.zimfiz.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import utils.Constants;


/**
 * Created by neokree on 12/12/14.
 */
public class EditContact extends ActionBarActivity {
    final int getBankData = 1;
    EditText mInstitutionName, address, bankName, accountName, accountNumber, mPhoneNumber, mEmail;
    Spinner mType;
    RequestQueue mVolleyQueue;
    ProgressDialog mProgress;
    Button mSubmit;
    String bankId;
    String sSchoolId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mProgress = new ProgressDialog(EditContact.this);
        mProgress.setMessage("Please wait...");
        mVolleyQueue = Volley.newRequestQueue(this);
        mInstitutionName = (EditText) findViewById(R.id.institution_name);
        address = (EditText) findViewById(R.id.address);
        bankName = (EditText) findViewById(R.id.bankName);
        accountName = (EditText) findViewById(R.id.accountName);
        accountNumber = (EditText) findViewById(R.id.accountNumber);
        mPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        mEmail = (EditText) findViewById(R.id.email);
        mType = (Spinner) findViewById(R.id.type);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

         sSchoolId = extras.getString("sSchoolId");
        getShoolData(sSchoolId);
        bankName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(EditContact.this, SelectBank.class);
                startActivityForResult(myIntent, getBankData);
            }
        });

        mSubmit = (Button) findViewById(R.id.btncontinue);
        mSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                final String sName = mInstitutionName.getText().toString();
                final String saddress = address.getText().toString();
                final String sbankName = bankName.getText().toString();
                final String saccountName = accountName.getText().toString();
                final String saccountNumber = accountNumber.getText().toString();
                final String smPhoneNumber = mPhoneNumber.getText().toString();
                final  String smEmail = mEmail.getText().toString();
                final String smType = mType.getSelectedItem().toString();

                if(mInstitutionName.getText().toString().equals("") || address.getText().toString().equals("") || bankName.getText().toString().equals("")  ||
                        accountNumber.getText().toString().equals("")  ){
                    Toast.makeText(EditContact.this, "Please fill in the missing fields", Toast.LENGTH_LONG).show();

                }else{

                    mProgress.show();
                    StringRequest jsonObjRequest1 = new StringRequest(Request.Method.POST, Constants.EDITSCHOOLDATA,
                            registerAccountrSuccessListener(),
                            registerAccountErrorListener()) {
                        protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("sName", sName);
                            params.put("saddress", saddress);
                            params.put("sbankid", bankId);
                            params.put("saccountName", saccountName);
                            params.put("saccountNumber", saccountNumber);
                            params.put("smPhoneNumber", smPhoneNumber);
                            params.put("smEmail", smEmail);
                            params.put("smType", smType);
                            params.put("school_id", sSchoolId);
                            return params;

                        }
                    };
                    // mVolleyQueue.add(jsonObjRequest);
                    AppController.getInstance().addToRequestQueue(jsonObjRequest1);


                }


            }
        });


    }

    private void getShoolData(final String school_id) {
        mProgress.show();

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, Constants.GETSCHOOLDATA,
                loginSuccessListener(),
                loginErrorListener()) {
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("school_id", school_id);

                return params;
            };
        };
        mVolleyQueue.add(jsonObjRequest);
    }
    private Response.ErrorListener loginErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if (mProgress.isShowing())
                    mProgress.dismiss();
                new android.support.v7.app.AlertDialog.Builder(EditContact.this)
                        .setCancelable(false)
                        .setMessage("Network unavailable. Please try again later")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which ) {

                            }

                        })
                        .show();
            }
        };
    }

    private Response.Listener<String> loginSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject responses =new JSONObject(response) ;


                        String school_name = responses.getString("school_name");
                        String school_address = responses.getString("school_address");
                        String school_bank_id = responses.getString("school_bank_id");
                        String school_account_name = responses.getString("school_account_name");
                        String school_account_number = responses.getString("school_account_number");
                        String school_email_address = responses.getString("school_email_address");
                        String school_phone_number = responses.getString("school_phone_number");
                        String school_current_fees = responses.getString("school_current_fees");
                        String bank_name = responses.getString("bank_name");

                    if (mProgress.isShowing())
                        mProgress.dismiss();

                    mInstitutionName.setText(school_name);
                    address.setText(school_address);
                    bankName.setText(bank_name);
                    accountNumber.setText(school_account_number);
                    mPhoneNumber.setText(school_phone_number);
                    mEmail.setText(school_email_address);
                    accountName.setText(school_account_name);



                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private Response.ErrorListener registerAccountErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if (mProgress.isShowing())
                    mProgress.dismiss();
            }
        };
    }
    private Response.Listener<String> registerAccountrSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (mProgress.isShowing())
                    mProgress.dismiss();

                try {
                    JSONObject responses =new JSONObject(response) ;
                    String status = responses.getString("success");
                    String message = responses.getString("message");
                    Log.e("response", response);
                    new AlertDialog.Builder(EditContact.this)
                            .setTitle(status)
                            .setCancelable(false)
                            .setMessage(message)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which ) {

                                    //startActivity(new Intent(getApplicationContext(), Login.class));

                                }

                            })
                            .show();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null){
            bankName.setText("");
            bankId = "";
        }else{
            bankName.setText(data.getStringExtra("bank_name"));
            bankId = data.getStringExtra("bank_id");
        }


    }

}
