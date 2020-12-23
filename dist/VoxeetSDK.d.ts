import { NativeEventEmitter } from 'react-native';
import SessionService from "./services/SessionService";
import ConferenceService from "./services/ConferenceService";
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
    events: NativeEventEmitter;
    initialize(consumerKey: string, consumerSecret: string): Promise<any>;
    initializeToken(accessToken: string | undefined, refreshToken: TokenRefreshCallback): any;
}
declare const _default: _VoxeetSDK;
export default _default;
