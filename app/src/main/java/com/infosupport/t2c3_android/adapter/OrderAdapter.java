package com.infosupport.t2c3_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.infosupport.t2c3_android.R;
import com.infosupport.t2c3_android.pojo.Order;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OrderAdapter extends ArrayAdapter<Order> {
    public OrderAdapter(Context context, ArrayList<Order> orders) {
        super(context, 0, orders);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Order order = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_order, parent, false);
        }
        // Lookup view for data population
        TextView tvOrderID = (TextView) convertView.findViewById(R.id.tvOrderId);
//        TextView tvFirstName = (TextView) convertView.findViewById(R.id.tvFirstName);
//        TextView tvLastName = (TextView) convertView.findViewById(R.id.tvLastName);
        TextView tvNumberOfItems = (TextView) convertView.findViewById(R.id.tvNumberOfItems);
        TextView tvOrderTotalPrice = (TextView) convertView.findViewById(R.id.tvOderTotalPrice);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
        // Populate the data into the template view using the data object

        //TODO: Set values
        tvOrderID.setText(String.valueOf(order.id));
//        tvFirstName.setText(String.valueOf(order.customerData.firstName));
//        tvLastName.setText(String.valueOf(order.customerData.lastName));
        tvNumberOfItems.setText(String.valueOf(order.items.size()));
        tvOrderTotalPrice.setText(String.valueOf(order.totalPrice));
        tvStatus.setText(String.valueOf(order.status));
        // Return the completed view to render on screen

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String orderID = ((TextView) v.findViewById(R.id.tvOrderId)).getText().toString();
                //TODO: Go to SpecificOrderActivtiy and show order based on orderID
            }
        });

        return convertView;
    }
}