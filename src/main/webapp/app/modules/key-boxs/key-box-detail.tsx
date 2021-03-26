import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from 'app/entities/key-box/key-box.reducer';
import { IKeyBox } from 'app/shared/model/key-box.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IKeyBoxDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const KeyBoxDetail = (props: IKeyBoxDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { keyBoxEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cmServiceApp.keyBox.detail.title">KeyBox</Translate> [<b>{keyBoxEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="userAccount">
              <Translate contentKey="cmServiceApp.keyBox.userAccount">User Account</Translate>
            </span>
          </dt>
          <dd>{keyBoxEntity.userAccount}</dd>
          <dt>
            <span id="password">
              <Translate contentKey="cmServiceApp.keyBox.password">Password</Translate>
            </span>
          </dt>
          <dd>{keyBoxEntity.password}</dd>
          <dt>
            <span id="secondPassword">
              <Translate contentKey="cmServiceApp.keyBox.secondPassword">Second Password</Translate>
            </span>
          </dt>
          <dd>{keyBoxEntity.secondPassword}</dd>
          <dt>
            <span id="loginAddress">
              <Translate contentKey="cmServiceApp.keyBox.loginAddress">Login Address</Translate>
            </span>
          </dt>
          <dd>{keyBoxEntity.loginAddress}</dd>
          <dt>
            <span id="explain">
              <Translate contentKey="cmServiceApp.keyBox.explain">Explain</Translate>
            </span>
          </dt>
          <dd>{keyBoxEntity.explain}</dd>
          <dt>
            <span id="display">
              <Translate contentKey="cmServiceApp.keyBox.display">Display</Translate>
            </span>
          </dt>
          <dd>{keyBoxEntity.display ? 'true' : 'false'}</dd>
          <dt>
            <span id="createUser">
              <Translate contentKey="cmServiceApp.keyBox.createUser">Create User</Translate>
            </span>
          </dt>
          <dd>{keyBoxEntity.createUser}</dd>
          <dt>
            <span id="creatTime">
              <Translate contentKey="cmServiceApp.keyBox.creatTime">Creat Time</Translate>
            </span>
          </dt>
          <dd>{keyBoxEntity.creatTime ? <TextFormat value={keyBoxEntity.creatTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updateUser">
              <Translate contentKey="cmServiceApp.keyBox.updateUser">Update User</Translate>
            </span>
          </dt>
          <dd>{keyBoxEntity.updateUser}</dd>
          <dt>
            <span id="updateTime">
              <Translate contentKey="cmServiceApp.keyBox.updateTime">Update Time</Translate>
            </span>
          </dt>
          <dd>{keyBoxEntity.updateTime ? <TextFormat value={keyBoxEntity.updateTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="note">
              <Translate contentKey="cmServiceApp.keyBox.note">Note</Translate>
            </span>
          </dt>
          <dd>{keyBoxEntity.note}</dd>
          <dt>
            <Translate contentKey="cmServiceApp.keyBox.userLink">User Link</Translate>
          </dt>
          <dd>{keyBoxEntity.userLinkFirstName ? keyBoxEntity.userLinkFirstName : ''}</dd>
        </dl>
        <Button tag={Link} to="/key-boxs" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ keyBox }: IRootState) => ({
  keyBoxEntity: keyBox.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(KeyBoxDetail);
