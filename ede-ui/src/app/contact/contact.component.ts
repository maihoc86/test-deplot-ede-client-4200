import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { RegisterService } from '../Services/register/register-service';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css'],
})
export class ContactComponent implements OnInit {
  constructor(private registerService: RegisterService) {}

  ngOnInit(): void {}

  public contact = new FormGroup({
    fullName: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    content: new FormControl('', [Validators.required]),
    file: new FormControl('', [Validators.required]),
  });
  onselectFile(e: any) {
    if (e.target.files) {
      console.log(e.target.files[0]);
      this.contact.controls['file'].setValue(e.target.files[0]);
    }
  }
  sendContact() { //TODO
    this.registerService
      .sendContact(
        this.contact.controls['fullName'].value,
        this.contact.controls['email'].value,
        this.contact.controls['content'].value,
        this.contact.controls['file'].value
      )
      .subscribe(
        (data: any) => {
          console.log(data);
          Swal.fire({
            icon: 'success',
            title: 'Success',
            text: 'You have successfully send contact',
          });
        },
        (error: any) => {
          Swal.fire({
            icon: 'error',
            title: error.error.errors[0].defaultMessage,
            text: error.error.message,
          });
        }
      );
  }
}
