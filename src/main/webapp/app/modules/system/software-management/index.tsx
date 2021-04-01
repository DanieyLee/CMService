import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SoftwareManagement from './software-management';
import SoftwareManagementUpdate from './software-management-update';
import SoftwareManagementDeleteDialog from './software-management-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SoftwareManagementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SoftwareManagementUpdate} />
      <ErrorBoundaryRoute path={match.url} component={SoftwareManagement} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SoftwareManagementDeleteDialog} />
  </>
);

export default Routes;
