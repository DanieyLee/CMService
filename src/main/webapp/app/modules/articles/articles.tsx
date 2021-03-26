import React from 'react';
import { connect } from 'react-redux';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import Article from './article';

export type IArticlesProp = StateProps;

export const Articles = (props: IArticlesProp) => {
  return (
    <div>
      <ErrorBoundaryRoute component={Article} />
    </div>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Articles);
