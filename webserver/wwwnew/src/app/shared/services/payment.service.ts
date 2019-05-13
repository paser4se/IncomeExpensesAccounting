import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable()
export class PaymentService {
  fileUploadUrl = "http://localhost:8085/iea/api/files/uploadtext";

  constructor(private http: HttpClient) { }

  uploadCsvContent(content: string) {
    return this.http.post(this.fileUploadUrl, content, { responseType: 'text' });
  }
}
