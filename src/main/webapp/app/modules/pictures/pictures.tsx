import './pictures.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';
import Wallpaper from './wallpaper';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

export type IPicturesProp = StateProps;

export const Pictures = (props: IPicturesProp) => {

  return (
    <Row>
      <ErrorBoundaryRoute component={Wallpaper} />
    </Row>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Pictures);
