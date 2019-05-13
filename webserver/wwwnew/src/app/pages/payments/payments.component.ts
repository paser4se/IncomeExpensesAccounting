import { Component, OnInit } from '@angular/core';
import { PaymentService } from 'src/app/shared/services/payment.service';
import { Payment } from 'src/app/shared/models/payment.model';

@Component({
  selector: 'app-payments',
  templateUrl: './payments.component.html',
  styleUrls: ['./payments.component.scss']
})
export class PaymentsComponent implements OnInit {
  disableDropzone: boolean;
  disableButtons: boolean;
  files: File[];
  payments: Payment[];
  displayedColumns: string[] = ['bookingdate', 'amount', 'currency', 'bookingtext', 'category'];

  constructor(private paymentService: PaymentService) {
    this.disableDropzone = false;
    this.disableButtons = false;
  }

  commitPayments() {

  }

  onFilesAdded(files: File[]) {
    this.files = files;
    if (this.getFilesCount() >= 1) {
      this.disableDropzone = true;
    }
  }

  cancleUpload() {
    this.files = new Array();
    this.disableDropzone = false;
    this.disableButtons = false;
  }

  getFilesCount() {
    if (this.files) {
      return this.files.length;
    }

    return 0;
  }

  uploadFiles() {
    this.disableButtons = true;
    this.files.forEach(file => {
      const reader = new FileReader();

      reader.onload = (e: ProgressEvent) => {
        const content = (e.target as FileReader).result;

        this.paymentService.uploadCsvContent(content.toString()).subscribe((data) => {
          this.cancleUpload();
        }, error => {
          this.cancleUpload();
          alert(error.message);
        });
      };

      reader.readAsText(file);
    });
  }

  ngOnInit() {
    this.paymentService.getAllPayments().subscribe((data) => {
      this.payments = data;
    }, error => {
      alert(error.message);
    });
  }
}
