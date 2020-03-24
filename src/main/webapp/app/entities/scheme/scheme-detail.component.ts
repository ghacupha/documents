import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IScheme } from 'app/shared/model/scheme.model';

@Component({
  selector: 'gha-scheme-detail',
  templateUrl: './scheme-detail.component.html'
})
export class SchemeDetailComponent implements OnInit {
  scheme: IScheme | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ scheme }) => (this.scheme = scheme));
  }

  previousState(): void {
    window.history.back();
  }
}
