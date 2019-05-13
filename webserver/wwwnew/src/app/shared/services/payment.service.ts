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
  colors: string[] = [
    "#61BC6D",
    "#F57835",
    "#1BBC9B",
    "#54ACD2",
    "#75706C",
    "#EC6B58",
    "#2B82C9",
    "#9265B8",
    "#28314E",
    "#475677",
    "#41A85F",
    "#00A885",
    "#3D8EB9",
    "#2969B0",
    "#553983",
    "#F7DA64",
    "#FAA026",
    "#E14A39",
    "#A38F84",
    "#EFEFEF",
    "#FBC51D",
    "#D14840",
    "#B8312E",
    "#D1D5D8"
  ];
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
