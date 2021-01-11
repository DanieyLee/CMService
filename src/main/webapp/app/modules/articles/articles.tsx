import './articles.scss';

import React from 'react';
import { Link, Switch } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import ArticleUpdate from 'app/entities/article/article-update';
import ArticleDetail from 'app/entities/article/article-detail';
import Article from './article';
import ArticleDeleteDialog from 'app/entities/article/article-delete-dialog';

export type IArticlesProp = StateProps;

export const Articles = (props: IArticlesProp) => {
  return (
    <Row>
      <ErrorBoundaryRoute component={Article} />
    </Row>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Articles);
