import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import KeyBox from './key-box';
import KeyBoxDetail from './key-box-detail';
import KeyBoxUpdate from './key-box-update';
import KeyBoxDeleteDialog from './key-box-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={KeyBoxUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={KeyBoxUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={KeyBoxDetail} />
      <ErrorBoundaryRoute path={match.url} component={KeyBox} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={KeyBoxDeleteDialog} />
  </>
);

export default Routes;
