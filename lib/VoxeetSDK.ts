import { DeviceEventEmitter, NativeEventEmitter, NativeModules, Platform } from 'react-native';
import {
  CommandService,
  ConferenceService,
  MediaDeviceService,
  RecordingService,
  SessionService
} from "./services/index";

const { RNVoxeetSdk } = NativeModules;

export interface RefreshCallback {
  (): void;
};

export interface TokenRefreshCallback {
  (): Promise<string>
};

class _VoxeetSDK {
  refreshAccessTokenCallback: RefreshCallback | null = null;

  public session = new SessionService();
  public conference = new ConferenceService();
  public recording = new RecordingService();
  public command = new CommandService();
  public mediaDevice = new MediaDeviceService();
  
  public events = new NativeEventEmitter(RNVoxeetSdk);

  initialize(consumerKey: string, consumerSecret: string): Promise<any> {
    return RNVoxeetSdk.initialize(consumerKey, consumerSecret);
  }

  initializeToken(accessToken: string | undefined, refreshToken: TokenRefreshCallback) {
    if (!this.refreshAccessTokenCallback) {
      this.refreshAccessTokenCallback = () => {
        refreshToken()
        .then(token => RNVoxeetSdk.onAccessTokenOk(token))
        .catch(err => RNVoxeetSdk.onAccessTokenKo("Token retrieval error"));
      }

      const eventEmitter = Platform.OS == "android" ? DeviceEventEmitter : new NativeEventEmitter(RNVoxeetSdk);
      eventEmitter.addListener("refreshToken", (e: Event) => {
        this.refreshAccessTokenCallback && this.refreshAccessTokenCallback();
      });
    }

    return RNVoxeetSdk.initializeToken(accessToken);
  }

}

export default new _VoxeetSDK();