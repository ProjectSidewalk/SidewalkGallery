import { Component, Input } from '@angular/core';
import {MatCardModule} from '@angular/material/card';

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
    // Metadata object containing information about this GalleryCard.
    @Input() card: Card | undefined;


}
