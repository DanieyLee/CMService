import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Software from './software';
import SoftwareDetail from './software-detail';
import SoftwareUpdate from './software-update';
import SoftwareDeleteDialog from './software-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SoftwareUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SoftwareUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SoftwareDetail} />
      <ErrorBoundaryRoute path={match.url} component={Software} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SoftwareDeleteDialog} />
  </>
);

export default Routes;
