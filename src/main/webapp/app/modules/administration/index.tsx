import React from 'react';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import UserManagement from './user-management';
import UserLinkManagement from './user-link-management';
import ArticleManagement from './article-management';
import SoftwareManagement from './software-management';
import WallpaperManagement from './wallpaper-management';
import ArticleTypeManagement from './article-type-management';
import SoftwareTypeManagement from './software-type-management';
import Sms from './sms/sms'
import Logs from './logs/logs';
import Health from './health/health';
import Metrics from './metrics/metrics';
import Configuration from './configuration/configuration';
import Audits from './audits/audits';
import Docs from './docs/docs';

const Routes = ({ match }) => (
  <div>
    <ErrorBoundaryRoute path={`${match.url}/user-management`} component={UserManagement} />
    <ErrorBoundaryRoute path={`${match.url}/user-link-management`} component={UserLinkManagement} />
    <ErrorBoundaryRoute path={`${match.url}/article-management`} component={ArticleManagement} />
    <ErrorBoundaryRoute path={`${match.url}/software-management`} component={SoftwareManagement} />
    <ErrorBoundaryRoute path={`${match.url}/wallpaper-management`} component={WallpaperManagement} />
    <ErrorBoundaryRoute path={`${match.url}/article-type-management`} component={ArticleTypeManagement} />
    <ErrorBoundaryRoute path={`${match.url}/software-type-management`} component={SoftwareTypeManagement} />
    <ErrorBoundaryRoute exact path={`${match.url}/sms`} component={Sms} />
    <ErrorBoundaryRoute exact path={`${match.url}/health`} component={Health} />
    <ErrorBoundaryRoute exact path={`${match.url}/metrics`} component={Metrics} />
    <ErrorBoundaryRoute exact path={`${match.url}/docs`} component={Docs} />
    <ErrorBoundaryRoute exact path={`${match.url}/configuration`} component={Configuration} />
    <ErrorBoundaryRoute exact path={`${match.url}/audits`} component={Audits} />
    <ErrorBoundaryRoute exact path={`${match.url}/logs`} component={Logs} />
  </div>
);

export default Routes;
