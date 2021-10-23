import { Directive, EventEmitter, HostListener, Output } from '@angular/core';
import * as _ from 'lodash';

@Directive({
  selector: '[appFileDrop]'
})
export class FileDropDirective {

  @Output() fileDropped = new EventEmitter<File>();
  @Output() fileHovered = new EventEmitter<boolean>();

  @HostListener('drop', ['$event'])
  onDrop($event: any) {
    $event.preventDefault()

    let transfer = $event.dataTransfer;
    this.fileHovered.emit(false)

    console.log(transfer.files[0])
    this.fileDropped.emit(transfer.files[0])
  }

  @HostListener('dragover', ['$event'])
  onDragOver($event: any) {
    $event.preventDefault()
    this.fileHovered.emit(true)
  }

  @HostListener('dragleave', ['$event'])
  dragLeave($event: any) {
    $event.preventDefault()
    this.fileHovered.emit(false)

  }

  @HostListener('dragenter', ['$event'])
  dragEnter($event: any) {
    $event.preventDefault()
    this.fileHovered.emit(true)

  }

  constructor() { }

}
