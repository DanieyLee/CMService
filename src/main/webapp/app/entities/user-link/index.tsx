import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserLink from './user-link';
import UserLinkDetail from './user-link-detail';
import UserLinkUpdate from './user-link-update';
import UserLinkDeleteDialog from './user-link-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserLinkUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserLinkUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserLinkDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserLink} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={UserLinkDeleteDialog} />
  </>
);

export default Routes;
