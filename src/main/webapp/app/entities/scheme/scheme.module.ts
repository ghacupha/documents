import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DocumentsSharedModule } from 'app/shared/shared.module';
import { SchemeComponent } from './scheme.component';
import { SchemeDetailComponent } from './scheme-detail.component';
import { SchemeUpdateComponent } from './scheme-update.component';
import { SchemeDeleteDialogComponent } from './scheme-delete-dialog.component';
import { schemeRoute } from './scheme.route';

@NgModule({
  imports: [DocumentsSharedModule, RouterModule.forChild(schemeRoute)],
  declarations: [SchemeComponent, SchemeDetailComponent, SchemeUpdateComponent, SchemeDeleteDialogComponent],
  entryComponents: [SchemeDeleteDialogComponent]
})
export class DocumentsSchemeModule {}
