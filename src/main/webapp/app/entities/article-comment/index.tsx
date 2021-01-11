import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ArticleComment from './article-comment';
import ArticleCommentDetail from './article-comment-detail';
import ArticleCommentUpdate from './article-comment-update';
import ArticleCommentDeleteDialog from './article-comment-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ArticleCommentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ArticleCommentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ArticleCommentDetail} />
      <ErrorBoundaryRoute path={match.url} component={ArticleComment} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ArticleCommentDeleteDialog} />
  </>
);

export default Routes;
