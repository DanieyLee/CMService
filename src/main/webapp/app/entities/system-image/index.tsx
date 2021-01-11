import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SystemImage from './system-image';
import SystemImageDetail from './system-image-detail';
import SystemImageUpdate from './system-image-update';
import SystemImageDeleteDialog from './system-image-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SystemImageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SystemImageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SystemImageDetail} />
      <ErrorBoundaryRoute path={match.url} component={SystemImage} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SystemImageDeleteDialog} />
  </>
);

export default Routes;
