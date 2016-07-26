package com.smartlocation;

import android.location.Location;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.utils.LocationState;

/**
 * Created by mavenave on 7/24/16.
 */
public class SmartLocationModule extends ReactContextBaseJavaModule {
    private String TAG = "SmartLocationModule";
    private ReactApplicationContext mReactContext;

    public SmartLocationModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return "SmartLocation";
    }

    private class Status {
        private boolean value = false;
        public void setToTrue() {
            value = true;
        }
        public boolean getVal() {
            return value;
        }
    }

    @ReactMethod
    public void getCurrentLocation(final Promise promise) {
        final Status resolved = new Status();
        SmartLocation.with(mReactContext).location()
                .oneFix()
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        // somehow a oneFix() runs twice O.o
                        if (!resolved.getVal()) {
                            WritableMap locationMap = new Position(location).serializeToMap();
                            resolved.setToTrue();
                            promise.resolve(locationMap);
                        }
                    }
                });
    }

    @ReactMethod
    public void onLocationUpdate() {
        SmartLocation.with(mReactContext).location()
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        WritableMap locationMap = new Position(location).serializeToMap();
                        mReactContext
                                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                                .emit("smartLocationUpdated", locationMap);
                    }
                });
    }

    @ReactMethod
    public void stopListeningForLocationUpdates() {
        SmartLocation.with(mReactContext).location().stop();
    }

    @ReactMethod
    public void getLocationState(Promise promise) {
        LocationState locationState = SmartLocation.with(mReactContext).location().state();
        WritableMap map = Arguments.createMap();
        map.putBoolean("locationServicesEnabled", locationState.locationServicesEnabled());
        map.putBoolean("isAnyProviderAvailable", locationState.isAnyProviderAvailable());
        map.putBoolean("isGpsAvailable", locationState.isGpsAvailable());
        map.putBoolean("isNetworkAvailable", locationState.isNetworkAvailable());
        promise.resolve(map);
    }
}
