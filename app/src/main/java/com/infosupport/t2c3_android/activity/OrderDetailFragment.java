package com.infosupport.t2c3_android.activity;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infosupport.t2c3_android.R;
import com.infosupport.t2c3_android.pojo.Order;
import com.infosupport.t2c3_android.service.OrderService;
import com.infosupport.t2c3_android.service.RetrofitConn;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A fragment representing a single Order detail screen.
 * This fragment is either contained in a {@link OrderListActivity}
 * in two-pane mode (on tablets) or a {@link OrderDetailActivity}
 * on handsets.
 */
public class OrderDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */

    public static final String ARG_ITEM_ID = "id";

    private Retrofit retrofit;
    /**
     * The dummy content this fragment is presenting.
     */
    //TODO: Change with specific order POJO
    private Order mItem;
    private View rootView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OrderDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //REST Endpoint
        retrofit = RetrofitConn.INSTANCE.RESTWebService;

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            OrderService orderService = retrofit.create(OrderService.class);
            Call<Order> callOrdersGetRequest = orderService.getOrder(getArguments().getString(ARG_ITEM_ID));
            callOrdersGetRequest.enqueue(new Callback<Order>() {

                @Override
                public void onResponse(Response<Order> response) {
                    int HTTPStatusCode = response.code();
                    if (HTTPStatusCode == 200) {
                        mItem = response.body();
                        ((TextView) rootView.findViewById(R.id.order_detail)).setText(mItem.customerData.emailAddress);

                        showDetailFragment();

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

    private void showDetailFragment() {
        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(String.valueOf(mItem.id));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.order_detail, container, false);

        ((TextView) rootView.findViewById(R.id.order_detail)).setText("BlaBlaOnCreateViewShit");
        if(mItem != null) {
            //TODO: Fill in other TextViews
            ((TextView) rootView.findViewById(R.id.order_detail)).setText(mItem.customerData.emailAddress);
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
