package admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.zimfiz.zimfiz.Login;
import com.app.zimfiz.zimfiz.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import utils.Constants;


/**
 * Created by neokree on 12/12/14.
 */
public class CreateSchoolAccount extends ActionBarActivity {
    Toolbar toolbar;
    RequestQueue mVolleyQueue;
    ProgressDialog mProgress;
    JsonObjectRequest jsonObjRequest;
    EditText mSchoolName, mType, mPhone, mPassword, vPassword, memail;
    String smSchoolName = null, smType = null, smPhone = null, smPassword = null, svPassword = null, smemail = null;
    Button mSubmit;
    String sSchoolId, email, phone, name;
    String schoolType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_school_account);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mVolleyQueue = Volley.newRequestQueue(this);


        mProgress = new ProgressDialog(CreateSchoolAccount.this);
        mProgress.setMessage("Please wait...");
        mSchoolName = (EditText) findViewById(R.id.school_name);
        mType = (EditText) findViewById(R.id.type);
        mPhone = (EditText) findViewById(R.id.phone);
        mPassword = (EditText) findViewById(R.id.password);
        vPassword = (EditText) findViewById(R.id.retype_password);
        mSubmit = (Button) findViewById(R.id.btncontinue);
        memail = (EditText) findViewById(R.id.email);



        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        sSchoolId = extras.getString("sSchoolId");
        schoolType = extras.getString("schoolType");

        email = extras.getString("email");
        phone = extras.getString("phone");
        name = extras.getString("name");

        mSchoolName.setText(name);
        mType.setText(schoolType);
        mPhone.setText(phone);
        memail.setText(email);




        mSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                smSchoolName = mSchoolName.getText().toString();
                smType = mType.getText().toString();
                smPhone = mPhone.getText().toString();
                smPassword = mPassword.getText().toString();
                smemail = memail.getText().toString();
                svPassword = vPassword.getText().toString();

                if(mSchoolName.getText().toString().equals("") || mType.getText().toString().equals("") || mPhone.getText().toString().equals("")  ||
                        mPassword.getText().toString().equals("")  || mPassword.getText().toString().equals("")  || vPassword.getText().toString().equals("") ){
                    Toast.makeText(CreateSchoolAccount.this, "Please fill in the missing fields", Toast.LENGTH_LONG).show();

                }else{
                    if(smPassword.equals(svPassword)){
                        mProgress.show();
                        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, Constants.CREATESCHOOLACCOUNT,
                                registerAccountrSuccessListener(),
                                registerAccountErrorListener()) {
                            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("smSchoolName", smSchoolName);
                                params.put("smType", smType);
                                params.put("smPhone", smPhone);
                                params.put("smPassword", smPassword);
                                params.put("smemail", smemail);
                                params.put("school_id", sSchoolId);
                                params.put("user_type", "school");
                                return params;

                        };
                    };
                        mVolleyQueue.add(jsonObjRequest);

                    }else{
                        Toast.makeText(CreateSchoolAccount.this, "Passwords do not match, verify and try again", Toast.LENGTH_LONG).show();
                    }

                }


            }
        });


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
                    new AlertDialog.Builder(CreateSchoolAccount.this)
                            .setTitle(status)
                            .setCancelable(false)
                            .setMessage(message)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which ) {

                                    startActivity(new Intent(getApplicationContext(), Login.class));

                                }

                            })
                            .show();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }


}
