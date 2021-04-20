import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ArticleManagement from './article-management';
import ArticleManagementUpdate from './article-management-update';
import ArticleManagementDeleteDialog from './article-management-delete-dialog';
import ArticleEnclosureManagement from './article-enclosure-management';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute path={`${match.url}/new`} component={ArticleManagementUpdate} />
      <ErrorBoundaryRoute path={`${match.url}/:id/edit`} component={ArticleManagementUpdate} />
      <ErrorBoundaryRoute path={match.url} component={ArticleManagement} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ArticleManagementDeleteDialog} />
    <ErrorBoundaryRoute exact path={`${match.url}/new/enclosure`} component={ArticleEnclosureManagement} />
    <ErrorBoundaryRoute exact path={`${match.url}/:id/edit/enclosure`} component={ArticleEnclosureManagement} />
  </>
);

export default Routes;
