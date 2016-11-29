package com.androidbelieve.materialnavigationdrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    EditText editsearch;
    Context mContext;
    String stop_code;
    LayoutInflater inflater;
    private List<Stop> display_list = null;
    private ArrayList<Stop> arraylist;


    public ListViewAdapter(Context context, List<Stop> display_list) {
        mContext = context;
        this.display_list = display_list;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Stop>();
        this.arraylist.addAll(display_list);
    }


    public ListViewAdapter() {

    }

    public class ViewHolder {
        TextView stop_code;
        TextView stop_name;
        TextView stop_desc;
    }

    @Override
    public int getCount() {
        return display_list.size();
    }

    @Override
    public Stop getItem(int position) {
        return display_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.activity_list_view_adapter, null);

            // Locate the TextViews in listview_item.xml
            holder.stop_code = (TextView) view.findViewById(R.id.stop_id);
            holder.stop_name = (TextView) view.findViewById(R.id.stop_name);
            holder.stop_desc = (TextView) view.findViewById(R.id.stop_desc);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.stop_code.setText(display_list.get(position).getStop_id());
        holder.stop_name.setText(display_list.get(position).getStop_name());
        holder.stop_desc.setText(display_list.get(position).getStop_desc());


        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {


            public void onClick(View arg0) {  //we have in One screen  2 listviews
                // one for Source and 1 for Dest


                if (display_list.get(position).getDirection().equals("1")) {
                    // To split the user input from different adapters.


                    Post_Activity.editsearch.setText(display_list.get(position).getStop_name());


                } else {
                    Post_Activity.editsearch2.setText(display_list.get(position).getStop_name());

                }
            }


        });

        return view;

    }

    // Filter Class
    public void filter(String charText) {
        display_list.clear();
        if (charText.length() == 0) {
            display_list.addAll(arraylist);
        }
        else
        {
            for (Stop wp : arraylist)
            {          //we can searchmor by stop number,stop description or stop name
                if (wp.getStop_name().contains(charText) || wp.getStop_desc().contains(charText) ||
                        wp.getStop_id().contains(charText))
                {
                    display_list.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}

