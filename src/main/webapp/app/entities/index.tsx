import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserLink from './user-link';
import Software from './software';
import SoftwareType from './software-type';
import SoftwareComments from './software-comments';
import SoftwareScore from './software-score';
import Article from './article';
import ArticlEnclosure from './articl-enclosure';
import ArticleType from './article-type';
import ArticleComment from './article-comment';
import Wallpaper from './wallpaper';
import SystemImage from './system-image';
import KeyBox from './key-box';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}user-link`} component={UserLink} />
      <ErrorBoundaryRoute path={`${match.url}software`} component={Software} />
      <ErrorBoundaryRoute path={`${match.url}software-type`} component={SoftwareType} />
      <ErrorBoundaryRoute path={`${match.url}software-comments`} component={SoftwareComments} />
      <ErrorBoundaryRoute path={`${match.url}software-score`} component={SoftwareScore} />
      <ErrorBoundaryRoute path={`${match.url}article`} component={Article} />
      <ErrorBoundaryRoute path={`${match.url}articl-enclosure`} component={ArticlEnclosure} />
      <ErrorBoundaryRoute path={`${match.url}article-type`} component={ArticleType} />
      <ErrorBoundaryRoute path={`${match.url}article-comment`} component={ArticleComment} />
      <ErrorBoundaryRoute path={`${match.url}wallpaper`} component={Wallpaper} />
      <ErrorBoundaryRoute path={`${match.url}system-image`} component={SystemImage} />
      <ErrorBoundaryRoute path={`${match.url}key-box`} component={KeyBox} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
