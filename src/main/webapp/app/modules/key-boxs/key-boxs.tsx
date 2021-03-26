import React from 'react';
import { connect } from 'react-redux';

import KeyBox from './key-box';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

export type IKeyBoxsProp = StateProps;

export const KeyBoxs = (props: IKeyBoxsProp) => {

  return (
    <div>
      <ErrorBoundaryRoute component={KeyBox} />
    </div>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(KeyBoxs);
