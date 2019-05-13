import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Payment } from '../models/payment.model';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable()
export class PaymentService {
  fileUploadUrl = "http://localhost:8085/iea/api/files/uploadtext";
  paymentUrl = "http://localhost:8085/iea/api/payments";

  constructor(private http: HttpClient) { }

  uploadCsvContent(content: string) {
    return this.http.post(this.fileUploadUrl, content, { responseType: 'text' });
  }

  getAllPayments() {
    return this.http.get<Payment[]>(this.paymentUrl);
  }
}
