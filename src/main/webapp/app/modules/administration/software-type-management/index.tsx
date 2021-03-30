import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SoftwareTypeManagement from './software-type-management';
import SoftwareTypeManagementUpdate from './software-type-management-update';
import SoftwareTypeManagementDeleteDialog from './software-type-management-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SoftwareTypeManagementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SoftwareTypeManagementUpdate} />
      <ErrorBoundaryRoute path={match.url} component={SoftwareTypeManagement} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SoftwareTypeManagementDeleteDialog} />
  </>
);

export default Routes;
