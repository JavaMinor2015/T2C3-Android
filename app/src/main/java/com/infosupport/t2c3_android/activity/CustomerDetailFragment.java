package com.infosupport.t2c3_android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.infosupport.t2c3_android.R;
import com.infosupport.t2c3_android.pojo.CustomerData;
import com.infosupport.t2c3_android.service.CustomerService;
import com.infosupport.t2c3_android.service.RetrofitConn;

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
public class CustomerDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */

    public static final String ARG_CUSTOMER_ID = "id";
    public static CustomerService customerService;

    private Retrofit retrofit;
    /**
     * The dummy content this fragment is presenting.
     */
    //TODO: Change with specific customer POJO
    private CustomerData mItem;
    private View rootView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CustomerDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //REST Endpoint
        retrofit = RetrofitConn.INSTANCE.RESTWebService;

        if (getArguments().containsKey(ARG_CUSTOMER_ID)) {
            customerService = retrofit.create(CustomerService.class);
            Call<CustomerData> callCustomersGetRequest = customerService.getCustomer(getArguments().getString(ARG_CUSTOMER_ID));
            callCustomersGetRequest.enqueue(new Callback<CustomerData>() {

                @Override
                public void onResponse(Response<CustomerData> response) {
                    int HTTPStatusCode = response.code();
                    if (HTTPStatusCode == 200) {
                        mItem = response.body();
                        ((TextView) rootView.findViewById(R.id.CustomerID)).setText(String.valueOf(mItem.id));
                        ((TextView) rootView.findViewById(R.id.CustomerFirstAndLastName)).setText(String.valueOf(mItem.firstName) + " " + String.valueOf(mItem.lastName));
                        if (mItem.address != null) {
                            ((TextView) rootView.findViewById(R.id.CustomerStreetAndNumber)).setText(String.valueOf(mItem.address.street) + " " + String.valueOf(mItem.address.streetNumber));
                            ((TextView) rootView.findViewById(R.id.CustomerZipcode)).setText(String.valueOf(mItem.address.zipcode));
                            ((TextView) rootView.findViewById(R.id.CustomerCity)).setText(String.valueOf(mItem.address.city));
                        }

                        if (mItem.emailAddress != null) {
                            ((TextView) rootView.findViewById(R.id.CustomerEmail)).setText(String.valueOf(mItem.emailAddress));
                        }
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
            appBarLayout.setTitle("Customer-number: " + String.valueOf(mItem.id));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.customer_detail, container, false);
        Button changeCustomerBtn = (Button) rootView.findViewById(R.id.btnChangeCustomer);
        changeCustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                updateStatus();
            }
        });
        return rootView;
    }

//    private void updateStatus() {
//        mItem.status = statusSpinner.getSelectedItem();
//        Call<Order> callPostOrderStatusRequest = orderService.postOrderStatus(mItem);
//        callPostOrderStatusRequest.enqueue(new Callback<Order>() {
//            @Override
//            public void onResponse(Response<Order> response) {
//                int HTTPStatusCode = response.code();
//                if (HTTPStatusCode == 200) {
//
//                } else {
//                    Log.d("Failed, HTTP code: ", String.valueOf(HTTPStatusCode));
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                Log.d("Error", t.getMessage());
//            }
//        });
//    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
