import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Wallpaper from './wallpaper';
import WallpaperDetail from './wallpaper-detail';
import WallpaperUpdate from './wallpaper-update';
import WallpaperDeleteDialog from './wallpaper-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WallpaperUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WallpaperUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WallpaperDetail} />
      <ErrorBoundaryRoute path={match.url} component={Wallpaper} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={WallpaperDeleteDialog} />
  </>
);

export default Routes;
