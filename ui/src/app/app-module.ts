import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HTTP_INTERCEPTORS, HttpClientModule, HttpClientXsrfModule } from '@angular/common/http';

import { AppHttpInterceptorService } from './http-interceptor.service';
import { AppComponent } from './app-component';

import { GalleryService } from './gallery-service';
import { GalleryModule } from './gallery/gallery-module';

import {GalleryRoot} from './gallery/gallery-root';
import {SingleGalleryRoot} from "./gallery/single-gallery-root";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatMenuModule} from "@angular/material/menu";
import {MatButtonModule} from "@angular/material/button";

const routes: Routes = [
  {
    path: 'gallery',
    component: GalleryRoot,
  },
  {
    path: 'label/:labelType',
    component: SingleGalleryRoot,
  },
  {
    path: 'test',
    component: AppComponent,
  },
  {
    path: '**',
    redirectTo: '/gallery',
    pathMatch: 'full'
  }
];

/**
 * Highest level Angular module for SidewalkGallery.
 * TODO: Break up into a GalleryModule?
 */
@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    GalleryModule,
    HttpClientModule,
    HttpClientXsrfModule.withOptions({
      cookieName: 'Csrf-Token',
      headerName: 'Csrf-Token',
    }),
    RouterModule.forRoot(routes),
    BrowserAnimationsModule,
    MatToolbarModule,
    MatToolbarModule,
    MatMenuModule,
    MatButtonModule
  ],
  providers: [
    GalleryService,
    {
      multi: true,
      provide: HTTP_INTERCEPTORS,
      useClass: AppHttpInterceptorService
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
