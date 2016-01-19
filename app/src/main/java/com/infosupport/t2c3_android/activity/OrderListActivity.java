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
import com.infosupport.t2c3_android.pojo.Order;
import com.infosupport.t2c3_android.service.OrderService;
import com.infosupport.t2c3_android.service.RetrofitConn;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * An activity representing a list of Orders. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link OrderDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class OrderListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private static List<Order> ordersList = new ArrayList<>();
    private View recyclerView;

    //Change this to your local IP-Networking address to use the Spring REST implementation on your mobile phone
    private static final String BASE_URL = "http://10.32.42.76:6789";
//    private static final String BASE_URL = "http://192.168.178.12:6789";

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        //TODO: REST singleton implementation
        //Singleton for connecting to REST API
        retrofit = RetrofitConn.INSTANCE.init(BASE_URL);


//        ArrayList<Order> orders = new ArrayList<Order>();
//        orderAdapter = new OrderAdapter(this, orders);

        //Add orders to adapter

        recyclerView = findViewById(R.id.order_list);
        assert recyclerView != null;

        //retrieve from REST OrderService call

        retrieveOrders();

        if (findViewById(R.id.order_detail_container) != null) {
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
        retrieveOrders();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new OrderRecyclerViewAdapter(ordersList));
    }

    public class OrderRecyclerViewAdapter extends RecyclerView.Adapter<OrderRecyclerViewAdapter.ViewHolder> {

        private final List<Order> mValues;

        public OrderRecyclerViewAdapter(List<Order> orders) {
            mValues = orders;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.order_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mOrderIdView.setText(String.valueOf(mValues.get(position).id));
            holder.mNumberOfItemsView.setText(String.valueOf(mValues.get(position).items.size()));
            holder.mOrderTotalPriceView.setText(String.valueOf(mValues.get(position).totalPrice));

            //TODO: WORKAROUND change when default status is changed
            holder.mStatusView.setText(String.valueOf(mValues.get(position).status.toString()));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();

                        //TODO: Check this line
                        arguments.putString(OrderDetailFragment.ARG_ITEM_ID, holder.mItem.id.toString());
                        OrderDetailFragment fragment = new OrderDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.order_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, OrderDetailActivity.class);
                        intent.putExtra(OrderDetailFragment.ARG_ITEM_ID, holder.mItem.id.toString());

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
            public final TextView mOrderIdView;
            public final TextView mNumberOfItemsView;
            public final TextView mOrderTotalPriceView;
            public final TextView mStatusView;
            public Order mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mOrderIdView = (TextView) view.findViewById(R.id.tvOrderId);
                mNumberOfItemsView = (TextView) view.findViewById(R.id.tvNumberOfItems);
                mOrderTotalPriceView = (TextView) view.findViewById(R.id.tvOderTotalPrice);
                mStatusView = (TextView) view.findViewById(R.id.tvStatus);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mNumberOfItemsView.getText() + "'";
            }
        }
    }

    private void retrieveOrders() {
        OrderService orderService = retrofit.create(OrderService.class);
        Call<List<Order>> callOrdersGetRequest = orderService.listOrders();
        callOrdersGetRequest.enqueue(new Callback<List<Order>>() {

            @Override
            public void onResponse(Response<List<Order>> response) {
                int HTTPStatusCode = response.code();
                if (HTTPStatusCode == 200) {
                    ordersList = response.body();
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
