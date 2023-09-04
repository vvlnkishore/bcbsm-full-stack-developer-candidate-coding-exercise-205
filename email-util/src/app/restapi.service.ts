import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Files } from './home/home.component';

@Injectable({
  providedIn: 'root'
})
export class RestapiService {

  private baseUrl = 'http://localhost:8080/coding';
  private saveDocUrl = 'http://localhost:8080/coding/save-file';
  private getDocsUrl = 'http://localhost:8080/coding/files';

  constructor(private http: HttpClient) { }

  public login(username: String, password: String) {
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(username + ":" + password) });
    return this.http.get(this.baseUrl, { headers, responseType: 'text' as 'json' });
  }

  upload(file: File, fromEmail: string, toEmail: string): Observable<any> {
    let username = "admin";
    let password = "admin";
    const headers = new HttpHeaders({
      Authorization: 'Basic ' + btoa(username + ":" + password),
      responseType: 'json'
    });
    const formData: FormData = new FormData();

    formData.append('file', file);
    formData.append('fromEmail', fromEmail);
    formData.append('toEmail', toEmail);

    /*{
      reportProgress: true,
      responseType: 'json'
    }*/

    //const req = new HttpRequest('POST', this.saveDocUrl, formData, {headers: headers});

    return this.http.post(this.saveDocUrl, formData, {headers: headers});
  }

  getFiles(): Observable<Array<Files>> {
    let username = "admin";
    let password = "admin";
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(username + ":" + password) });
    return this.http.get<Array<Files>>(this.getDocsUrl, { headers });
  }
}
