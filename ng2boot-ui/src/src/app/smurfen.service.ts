import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {Smurf} from "./smurf";
import {Http, Response} from "@angular/http";
import {environment} from "../environments/environment";

@Injectable()
export class SmurfenService {

  private endPoint: string;

  constructor(private http: Http) {
    this.endPoint = environment.baseUrl+'/smurf';
  }

  getSmurfen(): Observable<Smurf[]> {
    return this.http.get(this.endPoint)
      .map((response: Response) => response.json() as Smurf[])
      .catch((error: any) => Observable.throw(error));
  }
}
