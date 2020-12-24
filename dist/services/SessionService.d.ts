import ConferenceUser from "../types/ConferenceUser";
export declare const RNSessionService: any;
export default class SessionService {
    participant: () => Promise<ConferenceUser>;
    open: (participant: ConferenceUser) => Promise<boolean>;
    close: () => Promise<boolean>;
}
