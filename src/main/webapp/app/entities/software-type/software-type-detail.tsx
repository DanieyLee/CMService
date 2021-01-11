import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './software-type.reducer';
import { ISoftwareType } from 'app/shared/model/software-type.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISoftwareTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SoftwareTypeDetail = (props: ISoftwareTypeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { softwareTypeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cmServiceApp.softwareType.detail.title">SoftwareType</Translate> [<b>{softwareTypeEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="type">
              <Translate contentKey="cmServiceApp.softwareType.type">Type</Translate>
            </span>
          </dt>
          <dd>{softwareTypeEntity.type}</dd>
          <dt>
            <span id="createUser">
              <Translate contentKey="cmServiceApp.softwareType.createUser">Create User</Translate>
            </span>
          </dt>
          <dd>{softwareTypeEntity.createUser}</dd>
          <dt>
            <span id="creatTime">
              <Translate contentKey="cmServiceApp.softwareType.creatTime">Creat Time</Translate>
            </span>
          </dt>
          <dd>
            {softwareTypeEntity.creatTime ? <TextFormat value={softwareTypeEntity.creatTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updateUser">
              <Translate contentKey="cmServiceApp.softwareType.updateUser">Update User</Translate>
            </span>
          </dt>
          <dd>{softwareTypeEntity.updateUser}</dd>
          <dt>
            <span id="updateTime">
              <Translate contentKey="cmServiceApp.softwareType.updateTime">Update Time</Translate>
            </span>
          </dt>
          <dd>
            {softwareTypeEntity.updateTime ? (
              <TextFormat value={softwareTypeEntity.updateTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="note">
              <Translate contentKey="cmServiceApp.softwareType.note">Note</Translate>
            </span>
          </dt>
          <dd>{softwareTypeEntity.note}</dd>
        </dl>
        <Button tag={Link} to="/software-type" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/software-type/${softwareTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ softwareType }: IRootState) => ({
  softwareTypeEntity: softwareType.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SoftwareTypeDetail);
