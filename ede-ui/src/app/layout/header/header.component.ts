import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { HeaderService } from 'src/app/Services/header/header.service';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: [
    './header.component.css',
	  "../../../assets/css/header/header1.css",
  ]
})
export class HeaderComponent implements OnInit {

  constructor(private cookieService:CookieService,private headerService:HeaderService ) {
    this.u={} as User;
  }

  public txtKeysearch: string = ''

  ngOnInit(): void {
    this.getUserLogin();
  }
  public login:boolean=false;
  public u:User;

  public async getUserLogin(){
   await this.headerService.getUserByToken(this.cookieService.get("auth")).toPromise(
    ).then(data=>{
      console.log(data)
      this.login=true;
      this.u=data;
      
    }).catch(err=>{
      console.log(err)
      this.login=false;
    })
  console.log("545665")
  }

  public logout(){
    this.cookieService.delete("auth")
    document.location.href='';
  }

}
