import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Table, Row, Col, Alert } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export type IExplainProp = StateProps;

export const ExplainHide = (props: IExplainProp) => {

  return (
    <div className="content-explain-hide">
      <h2>
        <Translate contentKey="explain.title.hide">Hide</Translate>
      </h2>
      <h5>
        <Translate contentKey="explain.subtitle.hide">SubtitleHide</Translate>
      </h5>
      <p>
        <Translate contentKey="explain.text.hide">TextHide</Translate>
      </p>
      <Table>
        <tbody>
            <tr>
              <td><Translate contentKey="explain.keybox.name">Name</Translate></td>
              <td><Translate contentKey="explain.keybox.explain.name">Name</Translate></td>
            </tr>
            <tr>
              <td><Translate contentKey="explain.keybox.sex">Sex</Translate></td>
              <td><Translate contentKey="explain.keybox.explain.sex">Sex</Translate></td>
            </tr>
            <tr>
              <td><Translate contentKey="explain.keybox.age">Age</Translate></td>
              <td><Translate contentKey="explain.keybox.explain.age">Age</Translate></td>
            </tr>
            <tr>
              <td><Translate contentKey="explain.keybox.passwordKey">PasswordKey</Translate></td>
              <td><Translate contentKey="explain.keybox.explain.passwordKey">PasswordKey</Translate></td>
            </tr>
            <tr>
              <td><Translate contentKey="explain.keybox.phone">Phone</Translate></td>
              <td><Translate contentKey="explain.keybox.explain.phone">Phone</Translate></td>
            </tr>
        </tbody>
      </Table>
    </div>
  );
};

const mapStateToProps = storeState => ({
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(ExplainHide);
