package admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
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
import com.app.zimfiz.zimfiz.AppController;
import com.app.zimfiz.zimfiz.Login;
import com.app.zimfiz.zimfiz.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fragments.SelectSchool;
import utils.Constants;


/**
 * Created by neokree on 12/12/14.
 */
public class AddSchool extends ActionBarActivity {
    final int getBankData = 1;
    EditText mInstitutionName, address, bankName, accountName, accountNumber, mPhoneNumber, mEmail;
    Spinner mType;
    RequestQueue mVolleyQueue;
    ProgressDialog mProgress;
    Button mSubmit;
    String bankId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_school);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mProgress = new ProgressDialog(AddSchool.this);
        mProgress.setMessage("Please wait...");

        mInstitutionName = (EditText) findViewById(R.id.institution_name);
        address = (EditText) findViewById(R.id.address);
        bankName = (EditText) findViewById(R.id.bankName);
        accountName = (EditText) findViewById(R.id.accountName);
        accountNumber = (EditText) findViewById(R.id.accountNumber);
        mPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        mEmail = (EditText) findViewById(R.id.email);
        mType = (Spinner) findViewById(R.id.type);

        bankName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(AddSchool.this, SelectBank.class);
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
                    Toast.makeText(AddSchool.this, "Please fill in the missing fields", Toast.LENGTH_LONG).show();

                }else{

                        mProgress.show();
                        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, Constants.ADDSCHOOL,
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
                                return params;

                            }
                        };
                       // mVolleyQueue.add(jsonObjRequest);
                    AppController.getInstance().addToRequestQueue(jsonObjRequest);


                }


            }
        });


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
                    new AlertDialog.Builder(AddSchool.this)
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
