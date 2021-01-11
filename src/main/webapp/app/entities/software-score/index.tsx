import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SoftwareScore from './software-score';
import SoftwareScoreDetail from './software-score-detail';
import SoftwareScoreUpdate from './software-score-update';
import SoftwareScoreDeleteDialog from './software-score-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SoftwareScoreUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SoftwareScoreUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SoftwareScoreDetail} />
      <ErrorBoundaryRoute path={match.url} component={SoftwareScore} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SoftwareScoreDeleteDialog} />
  </>
);

export default Routes;
