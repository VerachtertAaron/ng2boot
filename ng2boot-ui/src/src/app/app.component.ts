import {Component, OnInit} from '@angular/core';
import {SmurfenService} from "./smurfen.service";
import {Smurf} from "./smurf";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'Smurfs';
  smurfs: Smurf[];

  constructor(private smurfenService: SmurfenService){

  }

  ngOnInit(): void {
    this.smurfenService.getSmurfen().subscribe(smurfs => this.smurfs = smurfs);
  }

}
