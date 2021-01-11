import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './system-image.reducer';
import { ISystemImage } from 'app/shared/model/system-image.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISystemImageDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SystemImageDetail = (props: ISystemImageDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { systemImageEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cmServiceApp.systemImage.detail.title">SystemImage</Translate> [<b>{systemImageEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="imageURL">
              <Translate contentKey="cmServiceApp.systemImage.imageURL">Image URL</Translate>
            </span>
          </dt>
          <dd>{systemImageEntity.imageURL}</dd>
          <dt>
            <span id="imageType">
              <Translate contentKey="cmServiceApp.systemImage.imageType">Image Type</Translate>
            </span>
          </dt>
          <dd>{systemImageEntity.imageType}</dd>
          <dt>
            <span id="createUser">
              <Translate contentKey="cmServiceApp.systemImage.createUser">Create User</Translate>
            </span>
          </dt>
          <dd>{systemImageEntity.createUser}</dd>
          <dt>
            <span id="creatTime">
              <Translate contentKey="cmServiceApp.systemImage.creatTime">Creat Time</Translate>
            </span>
          </dt>
          <dd>
            {systemImageEntity.creatTime ? <TextFormat value={systemImageEntity.creatTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updateUser">
              <Translate contentKey="cmServiceApp.systemImage.updateUser">Update User</Translate>
            </span>
          </dt>
          <dd>{systemImageEntity.updateUser}</dd>
          <dt>
            <span id="updateTime">
              <Translate contentKey="cmServiceApp.systemImage.updateTime">Update Time</Translate>
            </span>
          </dt>
          <dd>
            {systemImageEntity.updateTime ? <TextFormat value={systemImageEntity.updateTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="note">
              <Translate contentKey="cmServiceApp.systemImage.note">Note</Translate>
            </span>
          </dt>
          <dd>{systemImageEntity.note}</dd>
          <dt>
            <Translate contentKey="cmServiceApp.systemImage.userLink">User Link</Translate>
          </dt>
          <dd>{systemImageEntity.userLinkFirstName ? systemImageEntity.userLinkFirstName : ''}</dd>
        </dl>
        <Button tag={Link} to="/system-image" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/system-image/${systemImageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ systemImage }: IRootState) => ({
  systemImageEntity: systemImage.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SystemImageDetail);
