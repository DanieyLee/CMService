import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './software-comments.reducer';
import { ISoftwareComments } from 'app/shared/model/software-comments.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISoftwareCommentsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SoftwareCommentsDetail = (props: ISoftwareCommentsDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { softwareCommentsEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cmServiceApp.softwareComments.detail.title">SoftwareComments</Translate> [
          <b>{softwareCommentsEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="txTitle">
              <Translate contentKey="cmServiceApp.softwareComments.txTitle">Tx Title</Translate>
            </span>
          </dt>
          <dd>{softwareCommentsEntity.txTitle}</dd>
          <dt>
            <span id="content">
              <Translate contentKey="cmServiceApp.softwareComments.content">Content</Translate>
            </span>
          </dt>
          <dd>{softwareCommentsEntity.content}</dd>
          <dt>
            <span id="createUser">
              <Translate contentKey="cmServiceApp.softwareComments.createUser">Create User</Translate>
            </span>
          </dt>
          <dd>{softwareCommentsEntity.createUser}</dd>
          <dt>
            <span id="creatTime">
              <Translate contentKey="cmServiceApp.softwareComments.creatTime">Creat Time</Translate>
            </span>
          </dt>
          <dd>
            {softwareCommentsEntity.creatTime ? (
              <TextFormat value={softwareCommentsEntity.creatTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updateUser">
              <Translate contentKey="cmServiceApp.softwareComments.updateUser">Update User</Translate>
            </span>
          </dt>
          <dd>{softwareCommentsEntity.updateUser}</dd>
          <dt>
            <span id="updateTime">
              <Translate contentKey="cmServiceApp.softwareComments.updateTime">Update Time</Translate>
            </span>
          </dt>
          <dd>
            {softwareCommentsEntity.updateTime ? (
              <TextFormat value={softwareCommentsEntity.updateTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="note">
              <Translate contentKey="cmServiceApp.softwareComments.note">Note</Translate>
            </span>
          </dt>
          <dd>{softwareCommentsEntity.note}</dd>
          <dt>
            <Translate contentKey="cmServiceApp.softwareComments.software">Software</Translate>
          </dt>
          <dd>{softwareCommentsEntity.softwareName ? softwareCommentsEntity.softwareName : ''}</dd>
          <dt>
            <Translate contentKey="cmServiceApp.softwareComments.userLink">User Link</Translate>
          </dt>
          <dd>{softwareCommentsEntity.userLinkFirstName ? softwareCommentsEntity.userLinkFirstName : ''}</dd>
        </dl>
        <Button tag={Link} to="/software-comments" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/software-comments/${softwareCommentsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ softwareComments }: IRootState) => ({
  softwareCommentsEntity: softwareComments.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SoftwareCommentsDetail);
