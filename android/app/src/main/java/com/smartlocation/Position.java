package com.smartlocation;

import android.location.Location;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

/**
 * Created by mavenave on 7/24/16.
 */
public class Position {
    long mTimestamp;
    float mSpeed;
    float mHeading;
    float mAccuracy;
    double mLatitude;
    double mAltitude;
    double mLongitude;

    public Position(Location location) {
        mTimestamp = location.getTime();
        mSpeed = location.hasSpeed() ? location.getSpeed() : 0;
        mHeading = location.hasBearing() ? location.getBearing() : 0;
        mAccuracy = location.hasAccuracy() ? location.getAccuracy(): 0;
        mLatitude = location.getLatitude();
        mAltitude = location.hasAltitude() ? location.getAltitude() : 0;
        mLongitude = location.getLongitude();
    }

    public WritableMap serializeToMap() {
        WritableMap map = Arguments.createMap();
        map.putDouble("timestamp", mTimestamp);
        WritableMap coordsMap = Arguments.createMap();
        coordsMap.putDouble("speed", mSpeed);
        coordsMap.putDouble("heading", mHeading);
        coordsMap.putDouble("accuracy", mAccuracy);
        coordsMap.putDouble("latitude", mLatitude);
        coordsMap.putDouble("altitude", mAltitude);
        coordsMap.putDouble("longitude", mLongitude);
        map.putMap("coords", coordsMap);
        return map;
    }
}
