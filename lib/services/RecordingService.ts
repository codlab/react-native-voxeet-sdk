import { NativeModules } from 'react-native';

export const { RNRecordingService } = NativeModules;

export default class RecordingService {
  
  start = async (): Promise<boolean> => RNRecordingService.start();

  stop = async (): Promise<boolean> => RNRecordingService.stop();

}