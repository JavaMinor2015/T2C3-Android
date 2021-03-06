package com.infosupport.t2c3_android.activity;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.ToggleButton;

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
    private static OrderService orderService;
    private Spinner statusSpinner;
    private ToggleButton toggleBtnOrderPaid;

    private AlertDialog alertDialog;
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

            orderService = retrofit.create(OrderService.class);
            Call<Order> callOrdersGetRequest = orderService.getOrder(getArguments().getString(ARG_ITEM_ID));
            callOrdersGetRequest.enqueue(new Callback<Order>() {

                @Override
                public void onResponse(Response<Order> response) {
                    int HTTPStatusCode = response.code();
                    if (HTTPStatusCode == 200) {
                        mItem = response.body();
                        ((TextView) rootView.findViewById(R.id.orderID)).setText(String.valueOf(mItem.id));
                        ((TextView) rootView.findViewById(R.id.orderCustomerFirstAndLastName)).setText(mItem.customerData.firstName + " " + mItem.customerData.lastName);
                        ((TextView) rootView.findViewById(R.id.orderCustomerStreetAndNumber)).setText(mItem.customerData.address.street + " " + mItem.customerData.address.streetNumber);
                        ((TextView) rootView.findViewById(R.id.orderCustomerZipcode)).setText(mItem.customerData.address.zipcode);
                        ((TextView) rootView.findViewById(R.id.orderCustomerCity)).setText(mItem.customerData.address.city);
                        ((TextView) rootView.findViewById(R.id.orderCustomerEmail)).setText(mItem.customerData.emailAddress);
                        setStatusSpinner(statusSpinner);
                        setToggleButtonPaid(toggleBtnOrderPaid);
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

    private void setToggleButtonPaid(ToggleButton toggleBtnOrderPaid) {
        toggleBtnOrderPaid.setChecked(mItem.paid);
    }

    private void showDetailFragment() {
        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle("Order-number: " + String.valueOf(mItem.id));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.order_detail, container, false);
        statusSpinner = (Spinner) rootView.findViewById(R.id.statusSpinner);
        toggleBtnOrderPaid = (ToggleButton) rootView.findViewById(R.id.toggleBtnOrderPaid);
        Button changeOrderBtn = (Button) rootView.findViewById(R.id.btnChangeOrder);
        changeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOrder();
            }
        });
        return rootView;
    }

    private void updateOrder() {
        mItem.status = statusSpinner.getSelectedItem();
        mItem.paid = toggleBtnOrderPaid.isChecked();
        Call<Order> callPostOrderStatusRequest = orderService.editOrder(mItem);
        callPostOrderStatusRequest.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Response<Order> response) {
                int HTTPStatusCode = response.code();
                if (HTTPStatusCode == 200) {
                    changeToOrderListActivity();
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

    private void changeToOrderListActivity() {
        super.getActivity().finish();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setStatusSpinner(Spinner statusSpinner) {
        List<OrderStatus> statusList = new ArrayList<>();
        statusList.add(OrderStatus.PLACED);
        statusList.add(OrderStatus.REJECTED);
        statusList.add(OrderStatus.SENT);

        ArrayAdapter<OrderStatus> statusDataAdapter = new ArrayAdapter<>(super.getActivity(), android.R.layout.simple_spinner_item, OrderStatus.values());
        statusDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusDataAdapter);

        OrderStatus orderStatusValue = OrderStatus.valueOf(String.valueOf(mItem.status));
        if (!orderStatusValue.equals(null)) {
            int spinnerPosition = statusDataAdapter.getPosition(orderStatusValue);
            statusSpinner.setSelection(spinnerPosition);
        }
    }

}
