import { Component } from '@angular/core';

import { GalleryService } from './gallery-service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title: string | undefined;
  postRequestResponse: string | undefined;

  constructor(private galleryService: GalleryService) {
    this.galleryService.getWelcomeMessage().subscribe((data: any) => {
      this.title = data.content;
    });
  }

  // /**
  //  * This method is used to test the post request
  //  */
  // public postData(): void {
  //   console.log("Posting data!!");
  //   this.galleryService.sendData().subscribe((data: any) => {
  //     this.postRequestResponse = data.content;
  //   });
  // }
}
