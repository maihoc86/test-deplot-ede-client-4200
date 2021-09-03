import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Observable } from 'rxjs'
import { switchMap, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ImagesService {

  constructor(private httpClient: HttpClient, private cookieService: CookieService) { }

   // upload image
   private httpOptionsFile = {
    headers: new HttpHeaders({
      'Authorization': this.cookieService.get('auth')
    }),
  }
  public createMultiImageProductOption(files: any) {
    return this.httpClient.post<any>('http://localhost:8080/ede-file/create-multi/binary', files, this.httpOptionsFile);
  }
  getData(name: string): Observable<string> {
    return this.httpClient.get('http://localhost:8080/ede-file/get/image/' + name, { responseType: 'blob' })
      .pipe(
        switchMap((response: Blob) => this.readFile(response))
      );
  }
  blobToFile(name: string) {
    return this.httpClient.get('http://localhost:8080/ede-file/get/image/' + name, { responseType: 'blob' });
  }

  private readFile(blob: Blob): Observable<string> {
    return Observable.create((obs: { error: (arg0: ProgressEvent<FileReader>) => any; next: (arg0: string | ArrayBuffer | null) => any; complete: () => any; }) => {
      const reader = new FileReader();

      reader.onerror = err => obs.error(err);
      reader.onabort = err => obs.error(err);
      reader.onload = () => obs.next(reader.result);
      reader.onloadend = () => obs.complete();

      return reader.readAsDataURL(blob);
    });
  }
  public readrFileMultiImageProductOption(name: string) {
    return this.httpClient.get('http://localhost:8080/ede-file/get/image/' + name, {observe: 'response', responseType: 'blob'});
  }
  public updateMultiImageProductOption(files: any) {
    return this.httpClient.post<any>('http://localhost:8080/ede-file/update-multi/binary', files, this.httpOptionsFile);
  }
  public deleteMultiImageProductOption(files: any) {
    return this.httpClient.post<any>('http://localhost:8080/ede-file/delete-multi', files, this.httpOptionsFile);
  }
}
