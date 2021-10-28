import {Directive, Input, OnInit, TemplateRef, ViewContainerRef} from '@angular/core';
import {RolesValidationService} from "../services/roles-validation.service";

@Directive({
  selector: '[appIfRoles]'
})
export class RenderIfRoleDirective implements OnInit {

  @Input()
  // @ts-ignore
  public appIfRoles: Array<string>;

  constructor(private rolesValidationService: RolesValidationService,
              private viewContainerRef: ViewContainerRef,
              private templateRef: TemplateRef<any>)
  { }

  ngOnInit(): void {
        this.viewContainerRef.clear();
        if (this.rolesValidationService.validateRoles(this.appIfRoles)) {
          this.viewContainerRef.createEmbeddedView(this.templateRef);
        }
  }
}
