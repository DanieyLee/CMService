import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ArticleTypeManagement from './article-type-management';
import ArticleTypeManagementUpdate from './article-type-management-update';
import ArticleTypeManagementDeleteDialog from './article-type-management-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ArticleTypeManagementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ArticleTypeManagementUpdate} />
      <ErrorBoundaryRoute path={match.url} component={ArticleTypeManagement} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ArticleTypeManagementDeleteDialog} />
  </>
);

export default Routes;
