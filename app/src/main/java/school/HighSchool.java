package school;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.app.zimfiz.zimfiz.R;
import java.util.ArrayList;
import java.util.List;
import classes.SimpleList;
import utils.SimpleDividerItemDecoration;


/**
 * Created by neokree on 12/12/14.
 */
public class HighSchool extends ActionBarActivity {
    List<SimpleList> simpleListList;
    InstitutionListAdapter institutionListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_instition);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        simpleListList = new ArrayList<>();
        simpleListList.add(new SimpleList("Form 1"));
        simpleListList.add(new SimpleList("Form 2"));
        simpleListList.add(new SimpleList("Form 3"));
        simpleListList.add(new SimpleList("Form 4"));
        simpleListList.add(new SimpleList("Form 5"));
        simpleListList.add(new SimpleList("Form 6"));

        institutionListAdapter = new InstitutionListAdapter(this, simpleListList);
        recyclerView.setAdapter(institutionListAdapter);

    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_school, menu);
        return true;
    }

    public  boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        if(id == R.id.notifications){
           startActivity(new Intent(HighSchool.this, AddNotification.class));
        }
        if(id == R.id.details){
            startActivity(new Intent(HighSchool.this, EditContact.class));
        }

        return super.onOptionsItemSelected(item);

    }

}
