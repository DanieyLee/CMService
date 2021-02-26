import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './phone.reducer';
import { IPhone } from 'app/shared/model/phone.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPhoneDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PhoneDetail = (props: IPhoneDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { phoneEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cmServiceApp.phone.detail.title">Phone</Translate> [<b>{phoneEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="phone">
              <Translate contentKey="cmServiceApp.phone.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{phoneEntity.phone}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="cmServiceApp.phone.code">Code</Translate>
            </span>
          </dt>
          <dd>{phoneEntity.code}</dd>
          <dt>
            <span id="effectiveTime">
              <Translate contentKey="cmServiceApp.phone.effectiveTime">Effective Time</Translate>
            </span>
          </dt>
          <dd>
            {phoneEntity.effectiveTime ? <TextFormat value={phoneEntity.effectiveTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="sendTime">
              <Translate contentKey="cmServiceApp.phone.sendTime">Send Time</Translate>
            </span>
          </dt>
          <dd>{phoneEntity.sendTime ? <TextFormat value={phoneEntity.sendTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createUser">
              <Translate contentKey="cmServiceApp.phone.createUser">Create User</Translate>
            </span>
          </dt>
          <dd>{phoneEntity.createUser}</dd>
          <dt>
            <span id="creatTime">
              <Translate contentKey="cmServiceApp.phone.creatTime">Creat Time</Translate>
            </span>
          </dt>
          <dd>{phoneEntity.creatTime ? <TextFormat value={phoneEntity.creatTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updateUser">
              <Translate contentKey="cmServiceApp.phone.updateUser">Update User</Translate>
            </span>
          </dt>
          <dd>{phoneEntity.updateUser}</dd>
          <dt>
            <span id="updateTime">
              <Translate contentKey="cmServiceApp.phone.updateTime">Update Time</Translate>
            </span>
          </dt>
          <dd>{phoneEntity.updateTime ? <TextFormat value={phoneEntity.updateTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="note">
              <Translate contentKey="cmServiceApp.phone.note">Note</Translate>
            </span>
          </dt>
          <dd>{phoneEntity.note}</dd>
          <dt>
            <Translate contentKey="cmServiceApp.phone.userLink">User Link</Translate>
          </dt>
          <dd>{phoneEntity.userLinkFirstName ? phoneEntity.userLinkFirstName : ''}</dd>
        </dl>
        <Button tag={Link} to="/phone" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/phone/${phoneEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ phone }: IRootState) => ({
  phoneEntity: phone.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PhoneDetail);
