import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Articles from './home-article';
import Softwares from './home-software';
import Wallpaper from './home-wallpaper';
import { Frame } from './home-components';

export type IHomeProp = StateProps;

export const Home = (props: IHomeProp) => {
  const { account } = props;

  return (
    <div className="content-home">
      <div className="content-home-title">
        <Row>
          <Col md="4">
            <img src="content/images/yuoai.png" alt="Logo" />
          </Col>
          <Col md="8">
            <h3>
              <Translate contentKey="home.title">Welcome</Translate>
            </h3>
            <p>
              <Translate contentKey="home.subtitle">This is your homepage</Translate>
            </p>
          </Col>
        </Row>
        {account && account.login ? (
          <Alert color="success">
            <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
              You are logged in as user {account.login}.
            </Translate>
          </Alert>
        ) : (
          <Alert color="warning">
            <Link to="/login" className="alert-link">
              <Translate contentKey="global.messages.info.authenticated.link"> sign in</Translate>
            </Link>
            <Link to="/account/register" className="alert-link">
              <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>
            </Link>
          </Alert>
        )}
        <div className="content-home-title-github">
          <p>
            <Translate contentKey="home.like">Like</Translate>
          </p>
          <p>
            <Translate contentKey="home.github">Github</Translate>
            {/* eslint-disable-next-line react/jsx-no-target-blank */}
            <a href="https://github.com/DanieyLee/CMService" target='_blank' rel='noopener noreferer'>CMService</a>
          </p>
        </div>
      </div>
      <Row>
        <Col md="6">
          <ErrorBoundaryRoute component={Articles} />
        </Col>
        <Col md="6">
          <ErrorBoundaryRoute component={Softwares} />
        </Col>
      </Row>
      <div>
        <ErrorBoundaryRoute component={Wallpaper} />
      </div>
      <Frame />
    </div>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Home);
