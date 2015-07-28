package adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.app.zimfiz.zimfiz.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import classes.School;

public class SchoolsListAdapter extends RecyclerView.Adapter<SchoolsListAdapter.SimpleViewHolder> {
    List<School> schools;
    Context context;
     private ClickListener clickListener;

    public SchoolsListAdapter(Context context , List<School> schools) {
		this.schools = schools;
        this.context = context;
	}



	@Override
	public int getItemCount() {
		return schools.size();
	}

	@Override
	public void onBindViewHolder(SimpleViewHolder schoolsViewHolder, int i) {
        schoolsViewHolder.schoolName.setText(schools.get(i).SchoolName);
        schoolsViewHolder.schoolAddress.setText(schools.get(i).SchoolAddress);
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
                .buildRound(String.valueOf(schools.get(i).SchoolName.charAt(0)), color);
        Picasso.with(context)
                .load(schools.get(i).SchoolImage)
                .placeholder(drawable)
                .error(drawable)
                .into(schoolsViewHolder.schoolImage);
		
	}

	@Override
	public SimpleViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.school_list_item, viewGroup, false);
        SimpleViewHolder pvh = new SimpleViewHolder(v);
        return pvh;

	}



    public  class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView schoolName;
    TextView schoolAddress;
    ImageView schoolImage;
	public SimpleViewHolder(View itemView) {
		super(itemView);
        itemView.setOnClickListener(this);
        schoolName = (TextView)itemView.findViewById(R.id.schoolName);
        schoolAddress = (TextView)itemView.findViewById(R.id.schoolAddress);
        schoolImage = (ImageView)itemView.findViewById(R.id.schoolImage);


	}

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemClicked(v, getPosition());
            }
        }

    }
    public interface ClickListener {
        public void onItemClicked(View view , int position);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
