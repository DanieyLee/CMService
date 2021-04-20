import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import WallpaperManagement from './wallpaper-management';
import WallpaperManagementUpdate from './wallpaper-management-update';
import WallpaperManagementDeleteDialog from './wallpaper-management-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WallpaperManagementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WallpaperManagementUpdate} />
      <ErrorBoundaryRoute path={match.url} component={WallpaperManagement} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={WallpaperManagementDeleteDialog} />
  </>
);

export default Routes;
