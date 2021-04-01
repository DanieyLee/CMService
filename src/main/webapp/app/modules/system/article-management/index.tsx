import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ArticleManagement from './article-management';
import ArticleManagementUpdate from './article-management-update';
import ArticleManagementDeleteDialog from './article-management-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ArticleManagementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ArticleManagementUpdate} />
      <ErrorBoundaryRoute path={match.url} component={ArticleManagement} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ArticleManagementDeleteDialog} />
  </>
);

export default Routes;
