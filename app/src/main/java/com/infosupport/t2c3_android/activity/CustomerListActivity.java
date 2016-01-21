package com.infosupport.t2c3_android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infosupport.t2c3_android.R;
import com.infosupport.t2c3_android.pojo.Customer;
import com.infosupport.t2c3_android.service.CustomerService;
import com.infosupport.t2c3_android.service.RetrofitConn;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * An activity representing a list of Customers. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link CustomerDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class CustomerListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private static List<Customer> customersList = new ArrayList<>();
    private View recyclerView;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        //TODO: REST singleton implementation
        //Singleton for connecting to REST API
        retrofit = RetrofitConn.INSTANCE.init();

        //Add customers to adapter
        recyclerView = findViewById(R.id.customer_list);
        assert recyclerView != null;

        //retrieve from REST CustomerService call

        retrieveCustomers();

        if (findViewById(R.id.customer_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        retrieveCustomers();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new CustomerRecyclerViewAdapter(customersList));
    }

    public class CustomerRecyclerViewAdapter extends RecyclerView.Adapter<CustomerRecyclerViewAdapter.ViewHolder> {

        private final List<Customer> mValues;

        public CustomerRecyclerViewAdapter(List<Customer> customer) {
            mValues = customer;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.customer_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mCustomerIdView.setText(String.valueOf(mValues.get(position).id));
            holder.mCustomerFirstAndLastNameView.setText(String.valueOf(mValues.get(position).firstName) + " " + String.valueOf(mValues.get(position).lastName));
            if(mValues.get(position).address != null) {
                holder.mCustomerZipCodeView.setText(String.valueOf(mValues.get(position).address.zipcode));
                holder.mCustomerHouseNumberView.setText(String.valueOf(mValues.get(position).address.streetNumber));
            }

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();

                        //TODO: Check this line
                        arguments.putString(CustomerDetailFragment.ARG_CUSTOMER_ID, holder.mItem.id.toString());
                        CustomerDetailFragment fragment = new CustomerDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.customer_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, CustomerDetailActivity.class);
                        intent.putExtra(CustomerDetailFragment.ARG_CUSTOMER_ID, holder.mItem.id.toString());

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mCustomerIdView;
            public final TextView mCustomerFirstAndLastNameView;
            public final TextView mCustomerZipCodeView;
            public final TextView mCustomerHouseNumberView;

            public Customer mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mCustomerIdView = (TextView) view.findViewById(R.id.tvCustomerID);
                mCustomerFirstAndLastNameView = (TextView) view.findViewById(R.id.tvCustomerFirstAndLastName);
                mCustomerZipCodeView = (TextView) view.findViewById(R.id.tvCustomerZipCode);
                mCustomerHouseNumberView = (TextView) view.findViewById(R.id.tvCustomerHouseNumber);

            }

//            @Override
//            public String toString() {
//                return super.toString() + " '" + mNumberOfItemsView.getText() + "'";
//            }
        }
    }

    private void retrieveCustomers() {
        CustomerService customerService = retrofit.create(CustomerService.class);
        Call<List<Customer>> callCustomerGetRequest = customerService.listCustomers();
        callCustomerGetRequest.enqueue(new Callback<List<Customer>>() {

            @Override
            public void onResponse(Response<List<Customer>> response) {
                int HTTPStatusCode = response.code();
                if (HTTPStatusCode == 200) {
                    customersList = response.body();
                    setupRecyclerView((RecyclerView) recyclerView);
                } else {
                    Log.d("Failed, HTTP code: ", String.valueOf(HTTPStatusCode));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
