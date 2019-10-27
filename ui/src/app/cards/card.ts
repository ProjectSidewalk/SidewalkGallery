export class Card {
    name: string|undefined;
    imageUrl: string|undefined;   // Should use a SafeURL
    severity: number|undefined;
    description: string|undefined;
    tags: string[]|undefined;

    constructor () {}
}