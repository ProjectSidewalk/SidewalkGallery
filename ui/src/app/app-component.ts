import { Component } from '@angular/core';

import { GalleryService } from './gallery-service';

@Component({
  selector: 'app-root',
  templateUrl: './app-component.html',
  styleUrls: ['./app-component.css']
})
export class AppComponent {
  title: string | undefined;

  constructor(private galleryService: GalleryService) {
    this.galleryService.getWelcomeMessage().subscribe((data: any) => {
      this.title = data.content;
    });
  }
}
