import './explain.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';

export type IHomeProp = StateProps;

export const ExplainCopyright = (props: IHomeProp) => {
  const { account } = props;

  return (
    <Row>
      <Col md="12">
        <div className="home-explain-copyright">
          <h2>
            <Translate contentKey="explain.title.copyright">Copyright</Translate>
          </h2>
          <div className="home-explain-copyright-text">
            <Translate contentKey="footer.statement.one">Statement</Translate>
            <br />
            <Translate contentKey="footer.statement.two">Statement</Translate>
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

export default connect(mapStateToProps)(ExplainCopyright);
