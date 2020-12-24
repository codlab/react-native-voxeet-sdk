export enum RTCPMode {
  WORST = "worst",
  EVERAGE = "average",
  MAX = "max"
}

export enum Codec {
  VP8 = "VP8",
  H264 = "H264"
}

export interface CreateParameters {
  dolbyVoice?: boolean; // default is false
  ttl?: number; // default is 0
  rtcpMode?: RTCPMode; // worst / average (default) / max
  videoCodec?: Codec; //default is H264
  liveRecording?: boolean; //default false
}

export default interface CreateOptions {
  alias?: string;
  params?: CreateParameters;
}