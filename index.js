// @flow
import { NativeModules, DeviceEventEmitter } from 'react-native';

const { SmartLocation } = NativeModules;
const {
  getCurrentLocation,
  getLocationState,
  stopListeningForLocationUpdates,
} = SmartLocation;
export {
  getCurrentLocation,
  getLocationState,
  stopListeningForLocationUpdates,
};

export function onLocationUpdate(cb: (e: Event) => void) {
  DeviceEventEmitter.addListener('smartLocationUpdated', (e: Event) => {
    cb(e);
  });
  SmartLocation.onLocationUpdate();
}
