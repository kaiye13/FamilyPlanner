package the.family.planner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import the.family.planner.R;
import the.family.planner.models.Task;

public class TaskListAdapter extends ArrayAdapter<Task> {

    private Context mContext;
    int resource;

    public TaskListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Task> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       //get the info
        Task task = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(resource, parent, false);

        //get views set
        TextView startTime = convertView.findViewById(R.id.startTimeLabel);
        TextView endTime = convertView.findViewById(R.id.endTimeLabel);
        TextView title = convertView.findViewById(R.id.titleLabel);

        //set views
        startTime.setText(task.getStart_time());
        endTime.setText(task.getEnd_time());
        title.setText(task.getTitle());

        return convertView;


    }
}
