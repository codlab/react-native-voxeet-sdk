import { NativeEventEmitter } from 'react-native';
import { CommandService, ConferenceService, MediaDeviceService, RecordingService, SessionService } from "./services/index";
export interface RefreshCallback {
    (): void;
}
export interface TokenRefreshCallback {
    (): Promise<string>;
}
declare class _VoxeetSDK {
    refreshAccessTokenCallback: RefreshCallback | null;
    session: SessionService;
    conference: ConferenceService;
    recording: RecordingService;
    command: CommandService;
    mediaDevice: MediaDeviceService;
    events: NativeEventEmitter;
    initialize(consumerKey: string, consumerSecret: string): Promise<any>;
    initializeToken(accessToken: string | undefined, refreshToken: TokenRefreshCallback): any;
}
declare const _default: _VoxeetSDK;
export default _default;
