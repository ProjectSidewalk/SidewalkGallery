export class Constants {
    static readonly curbRampId: number = 1;
    static readonly missingCurbRampId: number = 2;
    static readonly noSidewalkId: number = 7;
    static readonly obstacleId: number = 3;
    static readonly surfaceProblemId: number = 4;

    /** The maximum cards to display in a gallery overview. */
    static readonly maxCards: number = 6;

    /** Routing URLs */
    static readonly curbRampAPI: string = '/api/curbramp/';
    static readonly missingCurbRampAPI: string = '/api/mcr/';
    static readonly obstacleAPI: string = '/api/obstacle/';
    static readonly surfaceProblemAPI: string = '/api/sfcp/';
    static readonly noSidewalkAPI: string = '/api/nosidewalk/';
    static readonly tagsAPI: string = '/api/tags/';
}
