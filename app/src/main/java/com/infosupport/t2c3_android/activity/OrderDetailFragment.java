package com.infosupport.t2c3_android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.infosupport.t2c3_android.R;
import com.infosupport.t2c3_android.pojo.Order;
import com.infosupport.t2c3_android.pojo.OrderStatus;
import com.infosupport.t2c3_android.service.OrderService;
import com.infosupport.t2c3_android.service.RetrofitConn;

import java.util.ArrayList;
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
    public static OrderService orderService;
    public Spinner statusSpinner;

    private static Retrofit retrofit;
    /**
     * The dummy content this fragment is presenting.
     */
    //TODO: Change with specific order POJO
    private Order order;
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

            orderService = retrofit.create(OrderService.class);
            Call<Order> callOrdersGetRequest = orderService.getOrder(getArguments().getString(ARG_ITEM_ID));
            callOrdersGetRequest.enqueue(new Callback<Order>() {

                @Override
                public void onResponse(Response<Order> response) {
                    int HTTPStatusCode = response.code();
                    if (HTTPStatusCode == 200) {
                        order = response.body();
                        ((TextView) rootView.findViewById(R.id.orderID)).setText(String.valueOf(order.id));
                        ((TextView) rootView.findViewById(R.id.orderCustomerFirstAndLastName)).setText(order.customerData.firstName + " " + order.customerData.lastName);
                        ((TextView) rootView.findViewById(R.id.orderCustomerStreetAndNumber)).setText(order.customerData.address.street + " " + order.customerData.address.streetNumber);
                        ((TextView) rootView.findViewById(R.id.orderCustomerZipcode)).setText(order.customerData.address.zipcode);
                        ((TextView) rootView.findViewById(R.id.orderCustomerCity)).setText(order.customerData.address.city);
                        ((TextView) rootView.findViewById(R.id.orderCustomerEmail)).setText(order.customerData.emailAddress);
                        setStatusSpinner(statusSpinner);
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
            appBarLayout.setTitle("Order-number: " + String.valueOf(order.id));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.order_detail, container, false);

        //Add listeners to controls
        statusSpinner = (Spinner) rootView.findViewById(R.id.statusSpinner);
        Button changeOrderBtn = (Button) rootView.findViewById(R.id.btnChangeOrder);
        changeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatus();
            }
        });

        return rootView;
    }

    private void updateStatus() {
        order.status = statusSpinner.getSelectedItem();
        Call<Order> callPostOrderStatusRequest = orderService.postOrderStatus(order);
        callPostOrderStatusRequest.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Response<Order> response) {
                int HTTPStatusCode = response.code();
                if (HTTPStatusCode == 200) {

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


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setStatusSpinner(Spinner statusSpinner) {
        List<OrderStatus> statusList = new ArrayList<>();
        for (OrderStatus orderStatus : OrderStatus.values()) {
            statusList.add(orderStatus);
        }

//        statusList.add(OrderStatus.PLACED);
//        statusList.add(OrderStatus.REJECTED);
//        statusList.add(OrderStatus.SENT);

        ArrayAdapter<OrderStatus> statusDataAdapter = new ArrayAdapter<>(super.getActivity(), android.R.layout.simple_spinner_item, OrderStatus.values());
        statusDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusDataAdapter);

        OrderStatus orderStatusValue = OrderStatus.valueOf(String.valueOf(order.status));
        if (!orderStatusValue.equals(null)) {
            int spinnerPosition = statusDataAdapter.getPosition(orderStatusValue);
            statusSpinner.setSelection(spinnerPosition);
        }
    }

}
