package com.infosupport.t2c3_android;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;

import com.infosupport.t2c3_android.activity.OrderListActivity;

/**
 * Created by koen on 1/14/16.
 */
public class OrderActivityListTest extends ActivityUnitTestCase<OrderListActivity> {

    private Intent mLaunchIntent;

    public OrderActivityListTest(Class<OrderListActivity> activityClass) {
        super(activityClass);

    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), OrderListActivity.class);
        startActivity(mLaunchIntent, null, null);
        final Button launchNextButton =
                (Button) getActivity()
                        .findViewById(R.id.btnChangeOrder);
    }

    @MediumTest
    public void testNextActivityWasLaunchedWithIntent() {
        startActivity(mLaunchIntent, null, null);
        final Button launchNextButton =
                (Button) getActivity()
                        .findViewById(R.id.btnChangeOrder);
        launchNextButton.performClick();

        final Intent launchIntent = getStartedActivityIntent();
        assertNotNull("Intent was null", launchIntent);
        assertTrue(isFinishCalled());


        //TODO: Add called field to assertion.
//        final String payload =
//                launchIntent.getStringExtra(OrderDetailActivity.);
//        assertEquals("Payload is empty", OrderDetailActivity., payload);
    }
}
