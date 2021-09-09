import { NativeModules } from 'react-native';

export const { RNCommandService } = NativeModules;

export default class CommandService {

  send = async (conferenceId: string, message: string): Promise<boolean> => RNCommandService.send(conferenceId, message);
  
}