package nil.error.korsa.Acitivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import nil.error.korsa.R;

import static nil.error.korsa.Acitivity.OfferRIde.dest;
import static nil.error.korsa.Acitivity.OfferRIde.source;

/**
 * Created by nilerror on 18/4/17.
 */

public class ListViewAdapter extends BaseAdapter {

    Activity activity;

    List<Offerride> offerrideList;
    LayoutInflater layoutInflater;

    public ListViewAdapter(Activity activity, List<Offerride> offerrideList){
        this.activity = activity;
        this.offerrideList = offerrideList;
    }

    @Override
    public int getCount() {
        return offerrideList.size();
    }

    @Override
    public Object getItem(int position) {
        return offerrideList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater) activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = layoutInflater.inflate(R.layout.listview_items, null);

        final TextView textSource = (TextView) itemView.findViewById(R.id.listtvsource);
        final TextView textDestination = (TextView) itemView.findViewById(R.id.listtvdestination);
        TextView textStartDate = (TextView) itemView.findViewById(R.id.listtvstartdate);
        TextView textStartTime = (TextView) itemView.findViewById(R.id.listtvstarttime);
        Button viewRoute = (Button) itemView.findViewById(R.id.viewRoute);


        textSource.setText(offerrideList.get(position).getSource());
        textDestination.setText(offerrideList.get(position).getDestination());
        textStartDate.setText(offerrideList.get(position).getStartDate());
        textStartTime.setText(offerrideList.get(position).getStartTime());


        viewRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent route = new Intent(itemView.getContext(), MapsActivity.class);
                route.putExtra(source, textSource.getText().toString());
                route.putExtra(dest, textDestination.getText().toString());
                itemView.getContext().startActivity(route);

            }
        });
        return itemView;
    }
}
