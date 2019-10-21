export class Card {
    name: string;
    imageUrl: string;   // Should use a SafeURL

    constructor (name: string, imageUrl: string) {
        this.name = name;
        this.imageUrl = imageUrl;
    }
}