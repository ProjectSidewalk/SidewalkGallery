import { Component } from '@angular/core';
import { Card } from './card';

@Component({
    selector: 'gallery-card',
    templateUrl: './gallery-card.html',
    styleUrls: ['./gallery-card.css']
})
/** 
 * Front-end representation of one card in the gallery.
 */
export class GalleryCard {
    card: Card;

    constructor (card: Card) {
        this.card = card;
    }

}
