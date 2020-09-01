import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { LoginService } from 'app/core/login/login.service';
import { AccountService } from 'app/core/auth/account.service';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { Router } from '@angular/router';

@Component({
  selector: 'gha-share-menu-navigation',
  templateUrl: './share-menu-navigation.component.html',
  styleUrls: ['./share-menu-navigation.component.scss']
})
export class ShareMenuNavigationComponent implements OnInit {
  isNavbarCollapsed: boolean;
  modalRef?: NgbModalRef;

  constructor(
    private loginService: LoginService,
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private router: Router
  ) {
    this.isNavbarCollapsed = false;
  }

  ngOnInit(): void {}

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }
}
