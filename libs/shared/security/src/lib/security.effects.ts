import { inject, Injectable } from '@angular/core';
import { Actions, concatLatestFrom, createEffect, ofType } from '@ngrx/effects';
import {
  concatMap,
  delay,
  distinctUntilChanged,
  filter,
  map,
  tap,
} from 'rxjs/operators';
import { securityActions } from './security.actions';
import { ANONYMOUS_USER, User } from './security.reducer';
import { AuthService } from '@auth0/auth0-angular';
import { KeycloakService } from 'keycloak-angular';
import { from, of } from 'rxjs';

@Injectable()
export class SecurityEffects {
  #actions$ = inject(Actions);
  #authService = inject(AuthService);
  #keycloak = inject(KeycloakService);

  user$ = createEffect(() =>
    this.#keycloak.keycloakEvents$.pipe(
      concatMap(() => this.#keycloak.isLoggedIn()),
      distinctUntilChanged(),
      map((isLoggedIn) =>
        securityActions.loaded({
          user: isLoggedIn
            ? {
                id: '1',
                email: 'hi',
                name: '',
                anonymous: false,
              }
            : ANONYMOUS_USER,
        })
      )
    )
  );

  signInUser$ = createEffect(
    () =>
      this.#actions$.pipe(
        ofType(securityActions.signIn),
        tap(() => this.#keycloak.login())
      ),
    { dispatch: false }
  );

  signOutUser$ = createEffect(
    () =>
      this.#actions$.pipe(
        ofType(securityActions.signOut),
        tap(() => this.#keycloak.logout())
      ),
    { dispatch: false }
  );
}
