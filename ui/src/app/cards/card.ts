export class Card {
    canvasHeight: number|undefined;
    canvasWidth: number|undefined;
    canvasX: number|undefined;
    canvasY: number|undefined;
    description: string|undefined;
    heading: number|undefined;
    imageUrl: string|undefined;     // Should use a SafeURL
    gsvPanoramaId: string|undefined;
    labelId: number|undefined;
    labelTypeId: number|undefined;
    pitch: number|undefined;
    severity: number|undefined;
    zoom: number|undefined;

    name: string|undefined;
    tags: string[]|undefined;

    constructor () {}
}
