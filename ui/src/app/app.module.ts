import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HTTP_INTERCEPTORS, HttpClientModule, HttpClientXsrfModule } from '@angular/common/http';

import { AppHttpInterceptorService } from './http-interceptor.service';
import { AppComponent } from './app.component';

import { AppService } from './app.service';
import { GalleryModule } from './gallery/gallery-module';

import {GalleryRoot} from './gallery/gallery-root';

import { RouteExampleComponent } from './route-example/route-example.component';

const routes: Routes = [
  {
    path: 'scala',
    component: RouteExampleComponent,
    data: { technology: 'Scala' }
  },
  {
    path: 'play',
    component: RouteExampleComponent,
    data: { technology: 'Play' }
  },
  {
    path: 'angular',
    component: RouteExampleComponent,
    data: { technology: 'Angular' }
  },
  {
    path: 'gallery',
    component: GalleryRoot,
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
    RouteExampleComponent
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
    BrowserAnimationsModule
  ],
  providers: [
    AppService,
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
