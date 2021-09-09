import { NativeModules } from 'react-native';
import CreateOptions from "../types/CreateConference";
import JoinOptions from "../types/JoinConference";

const { RNConferenceService } = NativeModules;

export default class ConferenceService {

  create = async (options: CreateOptions): Promise<boolean> => RNConferenceService.create(options);

  join = async (conferenceId: string, options: JoinOptions): Promise<boolean> => RNConferenceService.join(conferenceId, options);

  leave = async (): Promise<boolean> => RNConferenceService.leave();

  startVideo = async (): Promise<boolean> => RNConferenceService.startVideo();

  stopVideo = async (): Promise<boolean> => RNConferenceService.stopVideo();
  
}