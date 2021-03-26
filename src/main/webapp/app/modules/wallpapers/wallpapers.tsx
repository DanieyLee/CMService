import React from 'react';
import { connect } from 'react-redux';

import Wallpaper from './wallpaper';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

export type IWallpapersProp = StateProps;

export const Wallpapers = (props: IWallpapersProp) => {

  return (
    <div>
      <ErrorBoundaryRoute component={Wallpaper} />
    </div>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Wallpapers);
