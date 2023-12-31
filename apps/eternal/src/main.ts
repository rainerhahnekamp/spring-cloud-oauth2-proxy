import {
  APP_INITIALIZER,
  enableProdMode,
  importProvidersFrom,
  LOCALE_ID,
} from '@angular/core';

import { environment } from './environments/environment';
import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { Configuration } from '@eternal/shared/config';
import { MAT_DATE_LOCALE } from '@angular/material/core';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BaseUrlInterceptor } from '@eternal/shared/http';
import {
  LoadingInterceptor,
  sharedUiMessagingProvider,
} from '@eternal/shared/ui-messaging';
import { MAT_FORM_FIELD_DEFAULT_OPTIONS } from '@angular/material/form-field';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideStore } from '@ngrx/store';
import { provideEffects } from '@ngrx/effects';
import { provideStoreDevtools } from '@ngrx/store-devtools';
import { FormlyModule } from '@ngx-formly/core';
import { FormlyMaterialModule } from '@ngx-formly/material';
import { sharedMasterDataProvider } from '@eternal/shared/master-data';
import { appRoutes } from './app/app.routes';
import { provideRouter } from '@angular/router';
import { registerLocaleData } from '@angular/common';

import localeDeAt from '@angular/common/locales/de-AT';
import {
  AuthHttpInterceptor,
  AuthModule,
  provideAuth0,
} from '@auth0/auth0-angular';
import { securityProviders } from '@eternal/shared/security';
import { FileField } from '@eternal/shared/ui';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';

if (environment.production) {
  enableProdMode();
}

function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8082',
        realm: 'eternal',
        clientId: 'account',
      },
      initOptions: {
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri:
          window.location.origin + '/assets/silent-check-sso.html',
      },
    });
}

registerLocaleData(localeDeAt, 'de-AT');

bootstrapApplication(AppComponent, {
  providers: [
    provideAnimations(),
    provideRouter(appRoutes),
    provideAuth0({
      domain: 'dev-xbu2-fid.eu.auth0.com',
      clientId: 'YgUoOMh2jc4CQuo8Ky9PS7npW3Q4ckX9',
    }),

    provideStore(),
    provideEffects([]),
    provideStoreDevtools(),

    securityProviders,
    sharedMasterDataProvider,

    importProvidersFrom(
      KeycloakAngularModule,
      HttpClientModule,
      FormlyModule.forRoot({
        extras: { lazyRender: true },
        types: [
          { name: 'file', component: FileField, wrappers: ['form-field'] },
        ],
        validationMessages: [
          {
            name: 'required',
            message: 'This field is mandatory',
          },
        ],
      }),
      FormlyMaterialModule
    ),
    sharedUiMessagingProvider,
    {
      provide: Configuration,
      useFactory: () => new Configuration(environment.baseUrl),
    },
    {
      provide: MAT_DATE_LOCALE,
      useValue: 'de-AT',
    },
    { provide: HTTP_INTERCEPTORS, multi: true, useClass: BaseUrlInterceptor },
    { provide: HTTP_INTERCEPTORS, multi: true, useClass: LoadingInterceptor },
    { provide: LOCALE_ID, useValue: 'de-AT' },
    {
      provide: MAT_FORM_FIELD_DEFAULT_OPTIONS,
      useValue: { appearance: 'outline' },
    },
    {
      provide: APP_INITIALIZER,
      multi: true,
      useFactory: initializeKeycloak,
      deps: [KeycloakService],
    },
  ],
});
