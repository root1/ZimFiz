package com.app.zimfiz.zimfiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.InstitutionListAdapter;
import classes.SimpleList;
import utils.SimpleDividerItemDecoration;


/**
 * Created by neokree on 24/11/14.
 */
public class SelectInstition extends ActionBarActivity {
     List<SimpleList> simpleListList;
    InstitutionListAdapter institutionListAdapter;
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


        simpleListList.add(new SimpleList("Primary School"));
        simpleListList.add(new SimpleList("High School"));
        simpleListList.add(new SimpleList("College"));
        simpleListList.add(new SimpleList("University"));
        institutionListAdapter = new InstitutionListAdapter(this, simpleListList);
        recyclerView.setAdapter(institutionListAdapter);

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

//    @Override
//    public void onItemClicked(View view, int position) {
//        Toast.makeText(this, simpleListList.get(position).listItem, Toast.LENGTH_SHORT).show();
//        switch (position){
//            case 0:
//                startActivity(new Intent(SelectInstition.this, UniversityPayment.class));
//                break;
//            case 1:
//                startActivity(new Intent(SelectInstition.this, CollegePayment.class));
//                break;
//            case 2:
//                startActivity(new Intent(SelectInstition.this, HighSchoolPayment.class));
//                break;
//            case 3:
//                startActivity(new Intent(SelectInstition.this, PrimarySchoolPayment.class));
//                break;
//        }
//    }
}
