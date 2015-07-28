package fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.app.zimfiz.zimfiz.R;
import com.squareup.picasso.Picasso;

/**
 * Created by neokree on 24/11/14.
 */
public class NotificationActivity extends ActionBarActivity {
    private Toolbar toolbar;
    private ImageView schoolImage;
    private TextView dateSent;
    private TextView subject;
    private TextView detail;
    private TextView schoolName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_onclick);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        schoolImage = (ImageView)findViewById(R.id.schoolImage);
        dateSent = (TextView)findViewById(R.id.date);
        subject = (TextView)findViewById(R.id.subject);
        detail = (TextView)findViewById(R.id.detail);
        schoolName = (TextView)findViewById(R.id.schoolName);

        dateSent.setText("Sent "+getIntent().getExtras().get("dateSent"));
        subject.setText(getIntent().getExtras().getString("subject"));
        detail.setText(getIntent().getExtras().getString("detail"));
        schoolName.setText(getIntent().getExtras().getString("schoolName"));




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
}
