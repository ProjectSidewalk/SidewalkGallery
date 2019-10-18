import { Component } from '@angular/core';

import { AppService } from '../app.service';

@Component({
  selector: 'gallery-root',
  templateUrl: './gallery-component.html',
  styleUrls: ['./gallery-component.css']
})
export class GalleryComponent {
  title: string | undefined
  postRequestResponse: string | undefined;

  // TODO: Pass in a different service (GalleryService).
  constructor(private appService: AppService) {
    console.log("This is the Gallery Component");
  }

  /**
   * This method is used to test the post request
   */
  public postData(): void {
    this.appService.sendData().subscribe((data: any) => {
      this.postRequestResponse = data.content;
    });
  }
}
