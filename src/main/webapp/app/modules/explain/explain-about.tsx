import './explain.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';

export type IHomeProp = StateProps;

export const ExplainAbout = (props: IHomeProp) => {
  const { account } = props;

  return (
    <Row>
      <Col md="12">
        <div className="home-explain-about">
          <h2>
            <Translate contentKey="explain.title.about">About</Translate>
          </h2>
          <p className="lead">
            <Translate contentKey="explain.subtitle.about">SubtitleAbout</Translate>
          </p>
          <div className="home-explain-about-text">
            <p>
            <Translate contentKey="explain.text.about">TextAbout</Translate>
            </p>
          </div>
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

export default connect(mapStateToProps)(ExplainAbout);
