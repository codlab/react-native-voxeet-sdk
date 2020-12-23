import { NativeModules } from 'react-native';
import ConferenceUser from "../types/ConferenceUser";

export const { RNSessionService } = NativeModules;

export default class SessionService {
  participant = async (): Promise<ConferenceUser> => RNSessionService.participant();

  open = async (participant: ConferenceUser): Promise<boolean> => RNSessionService.open(participant);

  close = async (): Promise<boolean> => RNSessionService.close();
}