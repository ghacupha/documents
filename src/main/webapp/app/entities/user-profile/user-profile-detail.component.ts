import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserProfile } from 'app/shared/model/user-profile.model';

@Component({
  selector: 'gha-user-profile-detail',
  templateUrl: './user-profile-detail.component.html'
})
export class UserProfileDetailComponent implements OnInit {
  userProfile: IUserProfile | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userProfile }) => (this.userProfile = userProfile));
  }

  previousState(): void {
    window.history.back();
  }
}
