import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';

export type IExplainProp = StateProps;

export const ExplainCopy = (props: IExplainProp) => {

  return (
    <div className="content-explain-copy">
      <h2>
        <Translate contentKey="explain.title.copy">Copy</Translate>
      </h2>
      <p>
        <Translate contentKey="footer.statement.one">Statement</Translate>
        <Translate contentKey="footer.statement.two">Statement</Translate>
      </p>
    </div>
  );
};

const mapStateToProps = storeState => ({
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(ExplainCopy);
