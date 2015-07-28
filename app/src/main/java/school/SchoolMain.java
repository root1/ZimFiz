package school;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.app.zimfiz.zimfiz.R;

import utils.Constants;


/**
 * Created by neokree on 12/12/14.
 */
public class SchoolMain extends ActionBarActivity {
    Button mForms, mNotifications, mEditDetails, mHelp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_main);
        Toolbar  toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        mForms = (Button) findViewById(R.id.forms);
        mNotifications = (Button) findViewById(R.id.notifications);
        mEditDetails = (Button) findViewById(R.id.edit);
        mHelp = (Button) findViewById(R.id.help);
        SharedPreferences sharedPreferences = getSharedPreferences("user_login", Context.MODE_PRIVATE);
       final String institution_type = sharedPreferences.getString("institution_type", Constants.NOTAVAILABLE);

        mForms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(institution_type.equals("High School")){
                    startActivity(new Intent(SchoolMain.this, HighSchool.class));
                }
                if(institution_type.equals("College")){
                    startActivity(new Intent(SchoolMain.this, College.class));
                }
                if(institution_type.equals("University")){
                    startActivity(new Intent(SchoolMain.this, University.class));
                }
                if(institution_type.equals("Primary School")){
                    startActivity(new Intent(SchoolMain.this, PrimarySchool.class));
                }

            }
        });


        mNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SchoolMain.this, AddNotification.class));
            }
        });

        mEditDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SchoolMain.this, EditContact.class));
            }
        });
        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SchoolMain.this, Help.class));
            }
        });
    }
}
