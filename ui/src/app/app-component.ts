import { Component } from '@angular/core';

import { GalleryService } from './gallery-service';

@Component({
  selector: 'app-root',
  templateUrl: './app-component.html',
  styleUrls: ['./app-component.css']
})

// Component for a bare-bones webpage. (No SidewalkGallery related things).
export class AppComponent {
  title: string | undefined;

  constructor(private galleryService: GalleryService) {
    this.galleryService.getWelcomeMessage().subscribe((data: any) => {
      this.title = data.content;
      console.log(data);
    });

    this.galleryService.testGetRequest().subscribe((data: any) => {
      console.log(data);
    });
  }
}
