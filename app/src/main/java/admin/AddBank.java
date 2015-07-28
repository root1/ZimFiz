package admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
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
public class AddBank extends ActionBarActivity {
    EditText mBankName, mBankAgentCode, telecashAgentCode;
    Button mContinue;
    ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bank);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mProgress = new ProgressDialog(AddBank.this);
        mProgress.setMessage("Please wait...");
        mBankName = (EditText) findViewById(R.id.bank_name);
        mBankAgentCode = (EditText) findViewById(R.id.bank_agent_code);
        telecashAgentCode = (EditText)findViewById(R.id.telecash_agent_code);

        mContinue = (Button) findViewById(R.id.btncontinue);
        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String sBankName = mBankName.getText().toString();
                final String sBankAgentCode = mBankAgentCode.getText().toString();
                final String stelecashAgentCode  = telecashAgentCode.getText().toString();
                if(mBankName.getText().toString().equals("") || mBankAgentCode.getText().toString().equals("")){
                    Toast.makeText(AddBank.this, "Please fill in the missing fields", Toast.LENGTH_LONG).show();

                }else{

                    mProgress.show();
                    StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, Constants.ADDBANK,
                            registerAccountrSuccessListener(),
                            registerAccountErrorListener()) {
                        protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("sBankName", sBankName);
                            params.put("sBankAgentCode", sBankAgentCode);
                            params.put("stelecashAgentCode", stelecashAgentCode);

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
                    new AlertDialog.Builder(AddBank.this)
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
