import { NativeModules } from 'react-native';
import ConferenceUser from "../types/ConferenceUser";

const { RNVoxeetSdkSession } = NativeModules;

export default class SessionService {
  participant = async (): Promise<ConferenceUser> => RNVoxeetSdkSession.participant();

  open = async (participant: ConferenceUser): Promise<boolean> => {
      return RNVoxeetSdkSession.open(participant);
  }

  close = async (): Promise<boolean> => RNVoxeetSdkSession.close();
}