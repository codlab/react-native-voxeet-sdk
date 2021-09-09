export declare enum RTCPMode {
    WORST = "worst",
    EVERAGE = "average",
    MAX = "max"
}
export declare enum Codec {
    VP8 = "VP8",
    H264 = "H264"
}
export interface CreateParameters {
    dolbyVoice?: boolean;
    ttl?: number;
    rtcpMode?: RTCPMode;
    videoCodec?: Codec;
    liveRecording?: boolean;
}
export default interface CreateOptions {
    alias?: string;
    params?: CreateParameters;
}
