import { NativeModules } from 'react-native';

export const { RNRecordingService } = NativeModules;

export default class RecordingService {
  start = async (): Promise<void> => RNRecordingService.start();

  stop = async (): Promise<void> => RNRecordingService.stop();
}