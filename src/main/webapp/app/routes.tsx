import React from 'react';
import { Switch } from 'react-router-dom';
import Loadable from 'react-loadable';

import Login from 'app/modules/login/login';
import Register from 'app/modules/account/register/register';
import PasswordReset from 'app/modules/account/password-reset/password-reset';
import Logout from 'app/modules/login/logout';
import Home from 'app/modules/home/home';
import Articles from 'app/modules/articles/articles';
import Softwares from 'app/modules/softwares/softwares';
import Pictures from 'app/modules/pictures/pictures';
import CodeBoxs from 'app/modules/code-boxs/code-boxs';
import About from 'app/modules/explain/explain-about';
import Contact from 'app/modules/explain/explain-contact';
import Help from 'app/modules/explain/explain-help';
import Copyright from 'app/modules/explain/explain-copyright';
import Entities from 'app/entities';
import PrivateRoute from 'app/shared/auth/private-route';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import PageNotFound from 'app/shared/error/page-not-found';
import { AUTHORITIES } from 'app/config/constants';
import ArticlesDetail from 'app/modules/articles/article-detail';
import SoftwaresDetail from 'app/modules/softwares/software-detail';
import WallpaperDetail from 'app/modules/pictures/wallpaper-detail';
import CodeBoxsDetail from 'app/modules/code-boxs/key-box-detail';

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
      <ErrorBoundaryRoute path="/" exact component={Home} />
      <ErrorBoundaryRoute path="/articles" exact component={Articles} />
      <ErrorBoundaryRoute exact path={`/articles/:id`} component={ArticlesDetail} />
      <ErrorBoundaryRoute path="/softwares" exact component={Softwares} />
      <ErrorBoundaryRoute exact path={`/softwares/:id`} component={SoftwaresDetail} />
      <ErrorBoundaryRoute path="/pictures" exact component={Pictures} />
      <ErrorBoundaryRoute exact path={`/pictures/:id`} component={WallpaperDetail} />
      <ErrorBoundaryRoute exact path={`/code-boxs/:id`} component={CodeBoxsDetail} />
      <ErrorBoundaryRoute path="/about" exact component={About} />
      <ErrorBoundaryRoute path="/contact" exact component={Contact} />
      <ErrorBoundaryRoute path="/help" exact component={Help} />
      <ErrorBoundaryRoute path="/copyright" exact component={Copyright} />
      <PrivateRoute path="/code-boxs" exact component={CodeBoxs} hasAnyAuthorities={[AUTHORITIES.USER]} />
      <PrivateRoute path="/" component={Entities} hasAnyAuthorities={[AUTHORITIES.USER]} />
      <ErrorBoundaryRoute component={PageNotFound} />
    </Switch>
  </div>
);

export default Routes;
