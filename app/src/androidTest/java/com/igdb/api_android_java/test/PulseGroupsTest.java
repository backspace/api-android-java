package com.igdb.api_android_java.test;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.android.volley.VolleyError;
import com.igdb.api_android_java.callback.onSuccessCallback;
import com.igdb.api_android_java.model.APIWrapper;
import com.igdb.api_android_java.model.Parameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * Created by captainawesome on 2017-09-15.
 */

public class PulseGroupsTest {

    private Context context;
    private APIWrapper wrapper;


    public void setUp() throws InterruptedException {
        context = InstrumentationRegistry.getContext();
        String key = System.getProperty("API_KEY");
        wrapper = new APIWrapper(context, key);

    }

    @Test
    public void testSinglePulseGroups() throws InterruptedException {
        setUp();
        Parameters parameters = new Parameters()
                .addIds("5768");

        final CountDownLatch lock = new CountDownLatch(1);
        wrapper.pulseGroups(parameters, new onSuccessCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    lock.countDown();
                    JSONObject object = result.getJSONObject(0);
                    int testId = object.getInt("id");
                    assertEquals(5768, testId);
                } catch (JSONException e) {
                    e.printStackTrace();
                    fail("JSONException!!");
                }
            }

            @Override
            public void onError(VolleyError error) {
                fail("Volley Error!!");
            }
        });
        lock.await(20000, TimeUnit.MILLISECONDS);

    }

    @Test
    public void testMultiplePulseGroups() throws InterruptedException {
        setUp();
        Parameters parameters = new Parameters()
                .addIds("5772,5775,5777");

        final CountDownLatch lock = new CountDownLatch(1);
        wrapper.pulseGroups(parameters, new onSuccessCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    lock.countDown();
                    JSONObject object1 = result.getJSONObject(0);
                    JSONObject object2 = result.getJSONObject(1);
                    JSONObject object3 = result.getJSONObject(2);

                    assertEquals(5772, object1.getInt("id"));
                    assertEquals(5775, object2.getInt("id"));
                    assertEquals(5777, object3.getInt("id"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    fail("JSONException!!");
                }
            }

            @Override
            public void onError(VolleyError error) {
                fail("Volley Error!!");
            }
        });
        lock.await(20000, TimeUnit.MILLISECONDS);

    }
}
