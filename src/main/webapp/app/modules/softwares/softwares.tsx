import React from 'react';
import { connect } from 'react-redux';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import Software from './software';

export type ISoftwaresProp = StateProps;

export const Softwares = (props: ISoftwaresProp) => {

  return (
    <div>
      <ErrorBoundaryRoute component={Software} />
    </div>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Softwares);
