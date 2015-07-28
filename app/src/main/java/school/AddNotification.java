package school;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.zimfiz.zimfiz.AppController;
import com.app.zimfiz.zimfiz.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import utils.Constants;


/**
 * Created by neokree on 12/12/14.
 */
public class AddNotification extends ActionBarActivity {
    private Toolbar toolbar;
    EditText mSubject, mDetail;
    Button mSubmit;
    RequestQueue mVolleyQueue;
    ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_notification);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSubject = (EditText) findViewById(R.id.notification_subject);
        mDetail = (EditText) findViewById(R.id.notification_detail);
        mSubmit = (Button) findViewById(R.id.btncontinue);
        mProgress = new ProgressDialog(AddNotification.this);
        mProgress.setMessage("Please wait...");
        SharedPreferences sharedPreferences = getSharedPreferences("user_login", Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("id", Constants.NOTAVAILABLE);
        final String school_id = sharedPreferences.getString("school_id", Constants.NOTAVAILABLE);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String smSubject = mSubject.getText().toString();
                final String smDetail = mDetail.getText().toString();
                if(mSubject.getText().toString().equals("") || mDetail.getText().toString().equals("") ){
                    Toast.makeText(AddNotification.this, "Please fill in the missing fields", Toast.LENGTH_LONG).show();

                }else{

                    mProgress.show();
                    StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, Constants.ADDNOTIFICATION,
                            registerAccountrSuccessListener(),
                            registerAccountErrorListener()) {
                        protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("smSubject", smSubject);
                            params.put("smDetail", smDetail);
                            params.put("school_id", school_id);

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
                    new AlertDialog.Builder(AddNotification.this)
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
}
