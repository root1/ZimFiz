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

import datamodel.NotificationModel;

public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationsListAdapter.SimpleViewHolder> {
    List<NotificationModel> notifications;
    Context context;
     private ClickListener clickListener;

    public NotificationsListAdapter(Context context, List<NotificationModel> notifications) {
		this.notifications = notifications;
        this.context = context;
	}



	@Override
	public int getItemCount() {
		return notifications.size();
	}

	@Override
	public void onBindViewHolder(SimpleViewHolder schoolsViewHolder, int i) {
        schoolsViewHolder.schoolName.setText(notifications.get(i).SchoolName);
        schoolsViewHolder.notificationSubject.setText(notifications.get(i).NotificationSubject);
        schoolsViewHolder.date.setText(notifications.get(i).NotificationDate);

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
                .buildRound(String.valueOf(notifications.get(i).SchoolName.charAt(0)), color);
        Picasso.with(context)
                .load(notifications.get(i).SchoolImage)
                .placeholder(drawable)
                .error(drawable)
                .into(schoolsViewHolder.schoolImage);
		
	}

	@Override
	public SimpleViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notifications_list_item, viewGroup, false);
        SimpleViewHolder pvh = new SimpleViewHolder(v);
        return pvh;

	}



    public  class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView schoolName;
        TextView date;
    TextView notificationSubject;
    ImageView schoolImage;
	public SimpleViewHolder(View itemView) {
		super(itemView);
        itemView.setOnClickListener(this);
        schoolName = (TextView)itemView.findViewById(R.id.schoolName);
        date = (TextView)itemView.findViewById(R.id.date);
        notificationSubject = (TextView)itemView.findViewById(R.id.notification_subject);
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
        public void onItemClicked(View view, int position);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
