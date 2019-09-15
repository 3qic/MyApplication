package com.example.myapplication;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.ListItem;

import org.w3c.dom.Text;

import java.util.ArrayList;

//Coder zu Adaptererstellung: https://www.youtube.com/watch?v=E6vE8fqQPTE

public class ListItemAdapter extends ArrayAdapter <ListItem> {

private Context mContext;

private int mResource;
    public ListItemAdapter(Context context, int resource, ArrayList<ListItem>objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).getName();
        String amount = getItem(position).getAmount();
        ListItem listItem = new ListItem(name,amount);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView showItemName = convertView.findViewById(R.id.Shopping_List_Item_Name);
        TextView showItemAmount = convertView.findViewById(R.id.Shopping_List_Item_Amount);

        showItemName.setText(name);
        showItemAmount.setText(amount);

        return convertView;

    }
}
