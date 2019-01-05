import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { GamerDetailsComponent } from './gamer-details/gamer-details.component';
import { BoardComponent } from './board/board.component';
import { FieldComponent } from './field/field.component';
import {BoardService} from './board/board.service';
import {HttpClientModule} from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    GamerDetailsComponent,
    BoardComponent,
    FieldComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [BoardService],
  bootstrap: [AppComponent]
})
export class AppModule { }
