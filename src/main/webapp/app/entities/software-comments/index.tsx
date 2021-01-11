import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SoftwareComments from './software-comments';
import SoftwareCommentsDetail from './software-comments-detail';
import SoftwareCommentsUpdate from './software-comments-update';
import SoftwareCommentsDeleteDialog from './software-comments-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SoftwareCommentsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SoftwareCommentsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SoftwareCommentsDetail} />
      <ErrorBoundaryRoute path={match.url} component={SoftwareComments} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SoftwareCommentsDeleteDialog} />
  </>
);

export default Routes;
