import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Row, Col, Alert, Button } from 'reactstrap';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import { IRootState } from 'app/shared/reducers';
import Articles from 'app/modules/articles/article-list';
import Softwares from 'app/modules/softwares/software-list';
import Wallpaper from 'app/modules/pictures/wallpaper-list';
import { Frame, Service } from './home-components';

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
              <Translate contentKey="home.title">Welcome</Translate>
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
          <div className="home-login-low-text">
            <Translate contentKey="home.like">Like</Translate>
          </div>
          <div className="home-login-low-text-github">
            <Translate contentKey="home.github">Github</Translate>
          </div>
        </div>
        <div className="home-article-and-software">
          <div className="home-as-box">
            <ErrorBoundaryRoute component={Articles} />
          </div>
          <div className="home-as-box">
            <ErrorBoundaryRoute component={Softwares} />
          </div>
        </div>
        <div className="home-picture-top">
          <ErrorBoundaryRoute component={Wallpaper} />
        </div>
        <Frame />
        {/*<br />*/}
        {/*<hr className="home-box-top-hr"/>*/}
        <Service />
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
