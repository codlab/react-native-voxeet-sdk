import CreateOptions from "../types/CreateConference";
import JoinOptions from "../types/JoinConference";
export default class ConferenceService {
    create: (options: CreateOptions) => Promise<boolean>;
    join: (conferenceId: string, options: JoinOptions) => Promise<boolean>;
    leave: () => Promise<boolean>;
    startVideo: () => Promise<boolean>;
    stopVideo: () => Promise<boolean>;
}
