import { NativeEventEmitter } from 'react-native';
import ConferenceService from "./services/ConferenceService";
import RecordingService from "./services/RecordingService";
import SessionService from "./services/SessionService";
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
    events: NativeEventEmitter;
    initialize(consumerKey: string, consumerSecret: string): Promise<any>;
    initializeToken(accessToken: string | undefined, refreshToken: TokenRefreshCallback): any;
}
declare const _default: _VoxeetSDK;
export default _default;
