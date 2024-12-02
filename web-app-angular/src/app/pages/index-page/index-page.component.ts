import { Component } from '@angular/core';

@Component({
  selector: 'app-index-page',
  templateUrl: './index-page.component.html',
  styleUrls: ['./index-page.component.scss']
})
export class IndexPageComponent {
  githubImage:any = "assets/github.svg";  
  linkedInImage:any = "assets/linkedin.svg";
  bankImage:any = "assets/bank-svgrepo-com.svg";
}
