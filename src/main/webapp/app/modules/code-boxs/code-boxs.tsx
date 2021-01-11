import './code-boxs.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';
import KeyBox from './key-box';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

export type ICodeBoxsProp = StateProps;

export const CodeBoxs = (props: ICodeBoxsProp) => {

  return (
    <Row>
      <ErrorBoundaryRoute component={KeyBox} />
    </Row>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(CodeBoxs);
