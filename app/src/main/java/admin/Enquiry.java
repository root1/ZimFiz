package admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.zimfiz.zimfiz.AppController;
import com.app.zimfiz.zimfiz.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import utils.Constants;

/**
 * Created by neokree on 24/11/14.
 */
public class Enquiry extends ActionBarActivity {
    private Toolbar toolbar;
    private ImageView schoolImage;
    private TextView dateSent;
    private TextView subject;
    private TextView detail;
    private TextView schoolName;
    private TextView phone;
    private TextView email;
    private Button send;
    private String sPhone, school_id, row_id;
    private String sEmail;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enquiry);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mProgress = new ProgressDialog(Enquiry.this);
        mProgress.setMessage("Please wait...");
        schoolImage = (ImageView)findViewById(R.id.schoolImage);
        dateSent = (TextView)findViewById(R.id.date);
        subject = (TextView)findViewById(R.id.subject);
        detail = (TextView)findViewById(R.id.detail);
        schoolName = (TextView)findViewById(R.id.schoolName);

        phone = (TextView)findViewById(R.id.phone);
        email = (TextView)findViewById(R.id.email);
        send = (Button)findViewById(R.id.mark);

        dateSent.setText("Sent "+getIntent().getExtras().get("dateSent"));
        subject.setText(getIntent().getExtras().getString("subject"));
        detail.setText(getIntent().getExtras().getString("detail"));
        schoolName.setText(getIntent().getExtras().getString("schoolName"));

        school_id = getIntent().getExtras().getString("school_id");

                row_id = getIntent().getExtras().getString("row_id");

        sPhone = getIntent().getExtras().getString("sPhone");
        sEmail = getIntent().getExtras().getString("sEmail");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProgress.show();
                StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, Constants.UPDATEENQUIRY,
                        registerAccountrSuccessListener(),
                        registerAccountErrorListener()) {
                    protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("row_id", row_id);
                        params.put("school_id", school_id);

                        return params;

                    }
                };
                AppController.getInstance().addToRequestQueue(jsonObjRequest);
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + Integer.parseInt(sPhone.replace(" ", ""))));
                startActivity(intent);
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("plain/text");
                i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{sEmail});
                i.putExtra(android.content.Intent.EXTRA_SUBJECT, "In response to your request for assistance");
                i.putExtra(android.content.Intent.EXTRA_TEXT, "Hello ...");
                startActivity(Intent.createChooser(i, "Send email"));
            }
        });
        Typeface custom_font = Typeface.createFromAsset(this.getAssets(), "fonts/RobotoSlab-Light.ttf");
        ColorGenerator generator = ColorGenerator.DEFAULT;
        int color = generator.getRandomColor();
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.WHITE)
                .toUpperCase()
                .fontSize(30)
                .useFont(custom_font)
                .endConfig()
                .buildRect(String.valueOf(getIntent().getExtras().getString("schoolName").charAt(0)), color);
        Picasso.with(this)
                .load(getIntent().getExtras().getString("schoolImage"))
                .placeholder(drawable)
                .error(drawable)
                .into(schoolImage);


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


                    if(status.equals("success")){
                        send.setText("Attended");
                        send.setClickable(false);
                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
