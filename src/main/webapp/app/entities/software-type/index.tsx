import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SoftwareType from './software-type';
import SoftwareTypeDetail from './software-type-detail';
import SoftwareTypeUpdate from './software-type-update';
import SoftwareTypeDeleteDialog from './software-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SoftwareTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SoftwareTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SoftwareTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={SoftwareType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SoftwareTypeDeleteDialog} />
  </>
);

export default Routes;
