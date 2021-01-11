import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ArticleType from './article-type';
import ArticleTypeDetail from './article-type-detail';
import ArticleTypeUpdate from './article-type-update';
import ArticleTypeDeleteDialog from './article-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ArticleTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ArticleTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ArticleTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={ArticleType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ArticleTypeDeleteDialog} />
  </>
);

export default Routes;
