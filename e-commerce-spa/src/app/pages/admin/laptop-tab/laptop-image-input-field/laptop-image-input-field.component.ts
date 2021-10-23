import {Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";

@Component({
  selector: 'app-laptop-image-input-field',
  templateUrl: './laptop-image-input-field.component.html',
  styleUrls: ['./laptop-image-input-field.component.scss']
})
export class LaptopImageInputFieldComponent implements OnInit {

  // IMAGE UPLOAD
  @Input()
  public image: string | null = null;

  // @ts-ignore
  @ViewChild('fileInput') fileInput: ElementRef;
  fileAttr = 'Choose File';

  @Output()
  imageEmitter = new EventEmitter<string>()

  onFileSelected($event: Event) {
    // @ts-ignore
    if ($event.target.files && $event.target.files[0]) {
      // @ts-ignore
      const file = $event.target.files[0]
      this.handleImage(file);
    }
  }
  acceptedImageTypes = ['image/png', 'image/jpeg', 'image/jpg'];

  dropzoneActive: boolean = false;

  dropzoneState($event: boolean) {
    this.dropzoneActive = $event;
  }


  handleDrop(file: File) {
    this.handleImage(file);
  }

  handleImage(file: File): void {
    if (file && this.acceptedImageTypes.includes(file['type'])){
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        // @ts-ignore
        this.image = reader.result;
        if (this.image != null) {
          this.imageEmitter.emit(this.image)
        }
      }
    }
  }

  constructor(private sanitization: DomSanitizer) { }

  ngOnInit(): void {
  }



  parseImage(image: string|null): SafeUrl | string {
    if (image?.includes("data:image")) {
      return image;
    }
    return this.sanitization.bypassSecurityTrustUrl("data:image/png;base64, " + image);
  }
}
