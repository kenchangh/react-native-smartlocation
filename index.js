// @flow
import { NativeModules, DeviceEventEmitter } from 'react-native';

const { SmartLocation } = NativeModules;

type Coordinates = {
  speed: number,
  heading: number,
  accuracy: number,
  longitude: number,
  altitude: number,
  latitude: number,
};

type Position = {
  timestamp: number,
  timeTaken?: number, // self-added for measuring
  coords: Coordinates,
};


function onLocationUpdate(cb: (pos: Position) => void) {
  DeviceEventEmitter.addListener('smartLocationUpdated', (pos: Position) => {
    cb(pos);
  });
  SmartLocation.onLocationUpdate();
}

const {
  getCurrentLocation,
  getLocationState,
  stopListeningForLocationUpdates,
} = SmartLocation;

module.exports = {
  getCurrentLocation,
  getLocationState,
  stopListeningForLocationUpdates,
  onLocationUpdate,
};
