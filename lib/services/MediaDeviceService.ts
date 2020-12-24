import { NativeModules } from 'react-native';

export const { RNMediaDeviceService } = NativeModules;

export default class MediaDeviceService {
  
  switchCamera = async (): Promise<boolean> => RNMediaDeviceService.switchCamera();

}