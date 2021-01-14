import './explain.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';

export type IHomeProp = StateProps;

export const ExplainHelp = (props: IHomeProp) => {
  const { account } = props;

  return (
    <Row>
      <Col md="12">
        <div className="home-explain-help">
          <h2>
            <Translate contentKey="explain.title.help">Help</Translate>
          </h2>
          <p className="lead">
            <Translate contentKey="explain.subtitle.help">SubtitleHelp</Translate>
          </p>
          <div className="home-explain-help-text">
            <p>
              <Translate contentKey="explain.text.help">TextHelp</Translate>
            </p>
          </div>
          <div className="home-explain-help-text-table">
            <div className="home-explain-help-text-table-div">
              <div>
                <Translate contentKey="explain.keybox.name">Name</Translate>
              </div>
              <div>
                <Translate contentKey="explain.keybox.explain.name">Name</Translate>
              </div>
            </div>
            <div className="home-explain-help-text-table-div">
              <div>
                <Translate contentKey="explain.keybox.sex">Sex</Translate>
              </div>
              <div>
                <Translate contentKey="explain.keybox.explain.sex">Sex</Translate>
              </div>
            </div>
            <div className="home-explain-help-text-table-div">
              <div>
                <Translate contentKey="explain.keybox.age">Age</Translate>
              </div>
              <div>
                <Translate contentKey="explain.keybox.explain.age">Age</Translate>
              </div>
            </div>
            <div className="home-explain-help-text-table-div">
              <div>
                <Translate contentKey="explain.keybox.passwordKey">PasswordKey</Translate>
              </div>
              <div>
                <Translate contentKey="explain.keybox.explain.passwordKey">PasswordKey</Translate>
              </div>
            </div>
            <div className="home-explain-help-text-table-div">
              <div>
                <Translate contentKey="explain.keybox.phone">Phone</Translate>
              </div>
              <div>
                <Translate contentKey="explain.keybox.explain.phone">Phone</Translate>
              </div>
            </div>
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

export default connect(mapStateToProps)(ExplainHelp);
