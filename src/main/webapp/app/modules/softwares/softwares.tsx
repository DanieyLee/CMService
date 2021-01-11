import './softwares.scss';

import React from 'react';
import { Link, Switch } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import Software from './software';

export type ISoftwaresProp = StateProps;

export const Softwares = (props: ISoftwaresProp) => {

  return (
    <Row>
      <ErrorBoundaryRoute component={Software} />
    </Row>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Softwares);
