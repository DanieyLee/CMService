import React from 'react';
import { Switch } from 'react-router-dom';
import Loadable from 'react-loadable';
import Login from 'app/modules/login/login';
import Register from 'app/modules/account/register/register';
import PasswordReset from 'app/modules/account/password-reset/password-reset';
import Logout from 'app/modules/login/logout';
import Home from 'app/modules/home/home';
import Articles from 'app/modules/articles/articles';
import ArticlesDetail from 'app/modules/articles/article-detail';
import Softwares from 'app/modules/softwares/softwares';
import SoftwaresDetail from 'app/modules/softwares/software-detail';
import Wallpapers from 'app/modules/wallpapers/wallpapers';
import WallpapersDetail from 'app/modules/wallpapers/wallpaper-detail';
import KeyBoxs from 'app/modules/key-boxs'
import About from 'app/modules/explain/explain-about';
import Link from 'app/modules/explain/explain-link';
import Hide from 'app/modules/explain/explain-hide';
import Copy from 'app/modules/explain/explain-copy';
import Entities from 'app/entities';
import PrivateRoute from 'app/shared/auth/private-route';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import PageNotFound from 'app/shared/error/page-not-found';
import { AUTHORITIES } from 'app/config/constants';

const Account = Loadable({
  loader: () => import(/* webpackChunkName: "account" */ 'app/modules/account'),
  loading: () => <div>loading ...</div>,
});

const Admin = Loadable({
  loader: () => import(/* webpackChunkName: "administration" */ 'app/modules/administration'),
  loading: () => <div>loading ...</div>,
});

const Routes = () => (
  <div className="view-routes">
    <Switch>
      <ErrorBoundaryRoute path="/login" component={Login} />
      <ErrorBoundaryRoute path="/logout" component={Logout} />
      <ErrorBoundaryRoute path="/account/register" component={Register} />
      <ErrorBoundaryRoute path="/account/reset/request" component={PasswordReset} />
      <PrivateRoute path="/admin" component={Admin} hasAnyAuthorities={[AUTHORITIES.ADMIN]} />
      <PrivateRoute path="/account" component={Account} hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.USER]} />
      <ErrorBoundaryRoute exact path="/" component={Home} />
      <ErrorBoundaryRoute exact path={`/articles/:type`} component={Articles} />
      <ErrorBoundaryRoute exact path={`/articles/detail/:id`} component={ArticlesDetail} />
      <ErrorBoundaryRoute exact path={`/softwares/:type`} component={Softwares} />
      <ErrorBoundaryRoute exact path={`/softwares/detail/:id`} component={SoftwaresDetail} />
      <ErrorBoundaryRoute exact path={`/wallpapers/:type`} component={Wallpapers} />
      <ErrorBoundaryRoute exact path={`/wallpapers/detail/:id`} component={WallpapersDetail} />
      <ErrorBoundaryRoute exact path="/about" component={About} />
      <ErrorBoundaryRoute exact path="/link" component={Link} />
      <ErrorBoundaryRoute exact path="/hide" component={Hide} />
      <ErrorBoundaryRoute exact path="/copy" component={Copy} />
      <PrivateRoute path="/key-boxs" component={KeyBoxs} hasAnyAuthorities={[AUTHORITIES.USER]} />
      <PrivateRoute path="/" component={Entities} hasAnyAuthorities={[AUTHORITIES.USER]} />
      <ErrorBoundaryRoute component={PageNotFound} />
    </Switch>
  </div>
);

export default Routes;
