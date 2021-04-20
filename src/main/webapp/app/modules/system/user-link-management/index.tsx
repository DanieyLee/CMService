import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserLinkManagement from './user-link-management';
import UserLinkManagementUpdate from './user-link-management-update';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserLinkManagementUpdate} />
      <ErrorBoundaryRoute path={match.url} component={UserLinkManagement} />
    </Switch>
  </>
);

export default Routes;
