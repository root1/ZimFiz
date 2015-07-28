package school;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.app.zimfiz.zimfiz.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import classes.SimpleList;
import utils.SimpleDividerItemDecoration;


/**
 * Created by neokree on 12/12/14.
 */
public class College extends ActionBarActivity {
    List<SimpleList> simpleListList;
    UniversityListAdapter universityListAdapter;
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
        simpleListList.add(new SimpleList("Year 1"));
        simpleListList.add(new SimpleList("Year 2"));
        simpleListList.add(new SimpleList("Year 3"));
        simpleListList.add(new SimpleList("Year 4"));
        simpleListList.add(new SimpleList("Year 5"));
        simpleListList.add(new SimpleList("Year 6"));



        universityListAdapter = new UniversityListAdapter(this, simpleListList);
        recyclerView.setAdapter(universityListAdapter);

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
           startActivity(new Intent(College.this, AddNotification.class));
        }
        if(id == R.id.details){
            startActivity(new Intent(College.this, EditContact.class));
        }

        return super.onOptionsItemSelected(item);

    }
    class UniversityListAdapter extends RecyclerView.Adapter<UniversityListAdapter.SimpleViewHolder> {
        List<SimpleList> simplelist;
        Context context;

        public UniversityListAdapter(Context context, List<SimpleList> simplelist) {
            this.simplelist = simplelist;
            this.context = context;
        }


        @Override
        public int getItemCount() {
            return simplelist.size();
        }

        @Override
        public void onBindViewHolder(SimpleViewHolder schoolsViewHolder, int i) {
            schoolsViewHolder.instutionType.setText(simplelist.get(i).listItem);

            Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoSlab-Light.ttf");
            ColorGenerator generator = ColorGenerator.DEFAULT;
            int color = generator.getRandomColor();
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .textColor(Color.WHITE)
                    .toUpperCase()
                    .fontSize(30)
                    .useFont(custom_font)
                    .endConfig()
                    .buildRound(String.valueOf(simplelist.get(i).listItem.charAt(0)), color);
            Picasso.with(context)
                    .load(simplelist.get(i).listItem)
                    .placeholder(drawable)
                    .error(drawable)
                    .into(schoolsViewHolder.institutionImage);


        }

        @Override
        public SimpleViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.institution_list_item, viewGroup, false);
            SimpleViewHolder pvh = new SimpleViewHolder(v);

            return pvh;

        }


        public class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView instutionType;
            ImageView institutionImage;

            public SimpleViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                instutionType = (TextView) itemView.findViewById(R.id.schoolName);
                institutionImage = (ImageView) itemView.findViewById(R.id.schoolImage);


            }


            @Override
            public void onClick(View v) {
                Toast.makeText(context, simplelist.get(getPosition()).listItem, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, Classes.class);
                Bundle extras = new Bundle();
                switch (getPosition()) {
                    case 0:
                        extras.putString("form", "Year 1");
                        break;
                    case 1:
                        extras.putString("form", "Year 2");
                        break;
                    case 2:
                        extras.putString("form", "Year 3");
                        break;
                    case 3:
                        extras.putString("form", "Year 4");
                        break;
                    case 4:
                        extras.putString("form", "Year 5");
                        break;
                    case 5:
                        extras.putString("form", "Year 6");
                        break;


                }
                i.putExtras(extras);

                context.startActivity(i);
            }
        }

    }

}
