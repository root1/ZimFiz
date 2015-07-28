package fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.app.zimfiz.zimfiz.R;


/**
 * Created by neokree on 12/12/14.
 */
public class Contact extends ActionBarActivity {
    Button mCall, mEmail, mFeedBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar  toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCall = (Button) findViewById(R.id.call_us);
        mEmail = (Button) findViewById(R.id.email_us);
        mFeedBack = (Button)findViewById(R.id.feedback);
        mFeedBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), Feedback.class);
//                startActivity(i);

            }
        });

        mCall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + 0774106236));
                startActivity(intent);

            }
        });
        mEmail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("plain/text");
                i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"help@greencrunch.co.zw"});
                i.putExtra(android.content.Intent.EXTRA_SUBJECT, "May you please assist me");
                i.putExtra(android.content.Intent.EXTRA_TEXT, "Hello ...");
                startActivity(Intent.createChooser(i, "Send email"));

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
}
