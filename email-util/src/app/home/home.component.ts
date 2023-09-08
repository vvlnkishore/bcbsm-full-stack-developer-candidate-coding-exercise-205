import { Component, OnInit } from '@angular/core';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { RestapiService } from '../restapi.service';
import { Observable } from 'rxjs';

export interface Files {
  fileId: string;
  senderEmail: string;
  recipientEmail: string;
  fileName: string;
  file: File;
  uploadUser: string;
  uploadDate: Date;
}

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  currentFile?: File;
  progress = 0;
  message = '';

  fileName = 'Add Attachment';
  senderEmail = '';
  recipientEmail = '';
  fileInfos?: Observable<Array<Files>>;
  files?: Array<Files>;
  order = true;

  constructor(private uploadService: RestapiService) { }

  ngOnInit(): void {
    this.uploadService.getFiles().subscribe(
      fileInfos => this.files = fileInfos
   );
  }

  selectFile(event: any): void {
    if (event.target.files && event.target.files[0]) {
      const file: File = event.target.files[0];
      this.currentFile = file;
      this.fileName = this.currentFile.name;
    } else {
      this.fileName = 'Add Attachment';
    }
  }

  upload(): void {
    this.progress = 0;
    this.message = "";

    if (this.currentFile) {
      this.uploadService.upload(this.currentFile, this.senderEmail, this.recipientEmail).subscribe(
        (event: any) => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress = Math.round(100 * event.loaded / event.total);
            this.message = "Email Sent";
          } else if (event instanceof HttpResponse) {
            this.message = "Email Sent";
            //this.fileInfos = this.uploadService.getFiles();
            this.uploadService.getFiles().subscribe(
              fileInfos => this.files = fileInfos
           );
           this.reloadPage();
          }
        },
        (err: any) => {
          console.log(err);
          this.progress = 0;

          if (err.error && err.error.message) {
            this.message = err.error.message;
          } else {
            this.message = 'Could not upload the file!';
          }

          this.currentFile = undefined;
        });
    }

  }

  reloadPage(){
    window.location.reload()
  }

  sortSenderEmail(){
    if(this.order){
      let newArr = this.files?.sort((a, b) => a.senderEmail.localeCompare(b.senderEmail));
      this.files = newArr;
    } else {
      let newArr = this.files?.sort((a, b) => b.senderEmail.localeCompare(a.senderEmail));
      this.files = newArr;
    }
    this.order = !this.order;
  }

  sortRecipientEmail(){
    if(this.order){
      let newArr = this.files?.sort((a, b) => a.recipientEmail.localeCompare(b.recipientEmail));
      this.files = newArr;
    } else {
      let newArr = this.files?.sort((a, b) => b.recipientEmail.localeCompare(a.recipientEmail));
      this.files = newArr;
    }
    this.order = !this.order;
  }

  sortFileName(){
    if(this.order){
      let newArr = this.files?.sort((a, b) => a.fileName.localeCompare(b.fileName));
      this.files = newArr;
    } else {
      let newArr = this.files?.sort((a, b) => b.fileName.localeCompare(a.fileName));
      this.files = newArr;
    }
    this.order = !this.order;
  }

  sortDate(){
    if(this.order){
      let newArr = this.files?.sort((a, b) => a.uploadDate.getTime()-b.uploadDate.getTime());
      this.files = newArr;
    } else {
      let newArr = this.files?.sort((a, b) => b.uploadDate.getTime()-a.uploadDate.getTime());
      this.files = newArr;
    }
    this.order = !this.order;
  }

}