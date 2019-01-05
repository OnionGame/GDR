import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {IField} from '../field/field';
import {Observable} from 'rxjs';

@Injectable()
export class BoardService {

  private url = '/assets/board.json';


  constructor(private http: HttpClient) {
  }

  getBoard(): Observable<IField[]> {
    return this.http.get<IField[]>(this.url);
  }

}
