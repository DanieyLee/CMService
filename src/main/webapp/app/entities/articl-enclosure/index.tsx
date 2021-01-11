import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ArticlEnclosure from './articl-enclosure';
import ArticlEnclosureDetail from './articl-enclosure-detail';
import ArticlEnclosureUpdate from './articl-enclosure-update';
import ArticlEnclosureDeleteDialog from './articl-enclosure-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ArticlEnclosureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ArticlEnclosureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ArticlEnclosureDetail} />
      <ErrorBoundaryRoute path={match.url} component={ArticlEnclosure} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ArticlEnclosureDeleteDialog} />
  </>
);

export default Routes;
