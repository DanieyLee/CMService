import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ArticleEnclosure from './article-enclosure';
import ArticleEnclosureDetail from './article-enclosure-detail';
import ArticleEnclosureUpdate from './article-enclosure-update';
import ArticleEnclosureDeleteDialog from './article-enclosure-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ArticleEnclosureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ArticleEnclosureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ArticleEnclosureDetail} />
      <ErrorBoundaryRoute path={match.url} component={ArticleEnclosure} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ArticleEnclosureDeleteDialog} />
  </>
);

export default Routes;
