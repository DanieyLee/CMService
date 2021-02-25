import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Row, Col, Alert, Button } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';

export type IHomeProp = StateProps;

export const Home = (props: IHomeProp) => {
  const { account } = props;

  return (
    <Row>
      <Col md="12">
        <div className="home-top-01">
          <img src="content/images/yuoai.png" alt="Logo" />
          <div className="home-top-02">
            <h3>
              <Translate contentKey="home.title">Welcome, Java Hipster!</Translate>
            </h3>
            <p>
              <Translate contentKey="home.subtitle">This is your homepage</Translate>
            </p>
          </div>
        </div>
        {account && account.login ? (
          <div className="home-logged-message-div">
            <Alert color="success">
              <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
                You are logged in as user {account.login}.
              </Translate>
            </Alert>
          </div>
        ) : (
          <Alert className="home-login-div" color="warning">
            <span className="home-login-div-span radius-left">
              <Link to="/login" className="alert-link">
                <Translate contentKey="global.messages.info.authenticated.link"> sign in</Translate>
              </Link>
            </span>
            <span className="home-login-div-span radius-right">
              <Link to="/account/register" className="alert-link">
                <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>
              </Link>
            </span>
          </Alert>
        )}
        <div className="home-login-low">
          <div className="home-login-low-text">本项目使用Jhipster框架开发</div>
          <div className="home-login-low-text-github">项目稍后会在github开源</div>
        </div>
      </Col>
    </Row>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Home);
