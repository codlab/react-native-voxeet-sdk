import { DeviceEventEmitter, NativeEventEmitter, NativeModules, Platform } from 'react-native';
import { CommandService, ConferenceService, MediaDeviceService, RecordingService, SessionService } from "./services/index";
var RNVoxeetSdk = NativeModules.RNVoxeetSdk;
;
;
var _VoxeetSDK = /** @class */ (function () {
    function _VoxeetSDK() {
        this.refreshAccessTokenCallback = null;
        this.session = new SessionService();
        this.conference = new ConferenceService();
        this.recording = new RecordingService();
        this.command = new CommandService();
        this.mediaDevice = new MediaDeviceService();
        this.events = new NativeEventEmitter(RNVoxeetSdk);
    }
    _VoxeetSDK.prototype.initialize = function (consumerKey, consumerSecret) {
        return RNVoxeetSdk.initialize(consumerKey, consumerSecret);
    };
    _VoxeetSDK.prototype.initializeToken = function (accessToken, refreshToken) {
        var _this = this;
        if (!this.refreshAccessTokenCallback) {
            this.refreshAccessTokenCallback = function () {
                refreshToken()
                    .then(function (token) { return RNVoxeetSdk.onAccessTokenOk(token); })
                    .catch(function (err) { return RNVoxeetSdk.onAccessTokenKo("Token retrieval error"); });
            };
            var eventEmitter = Platform.OS == "android" ? DeviceEventEmitter : new NativeEventEmitter(RNVoxeetSdk);
            eventEmitter.addListener("refreshToken", function (e) {
                _this.refreshAccessTokenCallback && _this.refreshAccessTokenCallback();
            });
        }
        return RNVoxeetSdk.initializeToken(accessToken);
    };
    return _VoxeetSDK;
}());
export default new _VoxeetSDK();
//# sourceMappingURL=VoxeetSDK.js.map