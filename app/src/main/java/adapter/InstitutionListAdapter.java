package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.app.zimfiz.zimfiz.CollegePayment;
import com.app.zimfiz.zimfiz.HighSchoolPayment;
import com.app.zimfiz.zimfiz.PrimarySchoolPayment;
import com.app.zimfiz.zimfiz.R;
import com.app.zimfiz.zimfiz.UniversityPayment;
import com.squareup.picasso.Picasso;

import java.util.List;

import classes.SimpleList;

public class InstitutionListAdapter extends RecyclerView.Adapter<InstitutionListAdapter.SimpleViewHolder> {
    List<SimpleList> simplelist;
    Context context;

    public InstitutionListAdapter(Context context, List<SimpleList> simplelist) {
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



    public  class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView instutionType;
    ImageView institutionImage;
	public SimpleViewHolder(View itemView) {
		super(itemView);
        itemView.setOnClickListener(this);
        instutionType = (TextView)itemView.findViewById(R.id.schoolName);
        institutionImage = (ImageView)itemView.findViewById(R.id.schoolImage);


	}


        @Override
        public void onClick(View v) {
            Toast.makeText(context, simplelist.get(getPosition()).listItem, Toast.LENGTH_SHORT).show();

            switch (getPosition()){
            case 0:

                context.startActivity(new Intent(context, PrimarySchoolPayment.class));
                break;
            case 1:

                context.startActivity(new Intent(context, HighSchoolPayment.class));
                break;
            case 2:
                context.startActivity(new Intent(context, CollegePayment.class));
                break;
            case 3:
                Intent i = new Intent(context, UniversityPayment.class);
                context.startActivity(i);

                break;
        }
        }
    }

}
