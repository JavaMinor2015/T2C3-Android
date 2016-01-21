package com.infosupport.t2c3_android.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.infosupport.t2c3_android.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent intentOrderList = new Intent(this, OrderListActivity.class);
        final Intent intentCustomerList = new Intent(this, CustomerListActivity.class);

        Button btnShowOrders = (Button) this.findViewById(R.id.btnShowOrders);
        btnShowOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentOrderList);
            }
        });

        Button btnShowCustomers = (Button) this.findViewById(R.id.btnShowCustomers);
        btnShowCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentCustomerList);
            }
        });
    }
}
