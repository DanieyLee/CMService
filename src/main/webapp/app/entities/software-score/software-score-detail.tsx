import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './software-score.reducer';
import { ISoftwareScore } from 'app/shared/model/software-score.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISoftwareScoreDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SoftwareScoreDetail = (props: ISoftwareScoreDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { softwareScoreEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cmServiceApp.softwareScore.detail.title">SoftwareScore</Translate> [<b>{softwareScoreEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="score">
              <Translate contentKey="cmServiceApp.softwareScore.score">Score</Translate>
            </span>
          </dt>
          <dd>{softwareScoreEntity.score}</dd>
          <dt>
            <span id="createUser">
              <Translate contentKey="cmServiceApp.softwareScore.createUser">Create User</Translate>
            </span>
          </dt>
          <dd>{softwareScoreEntity.createUser}</dd>
          <dt>
            <span id="creatTime">
              <Translate contentKey="cmServiceApp.softwareScore.creatTime">Creat Time</Translate>
            </span>
          </dt>
          <dd>
            {softwareScoreEntity.creatTime ? (
              <TextFormat value={softwareScoreEntity.creatTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updateUser">
              <Translate contentKey="cmServiceApp.softwareScore.updateUser">Update User</Translate>
            </span>
          </dt>
          <dd>{softwareScoreEntity.updateUser}</dd>
          <dt>
            <span id="updateTime">
              <Translate contentKey="cmServiceApp.softwareScore.updateTime">Update Time</Translate>
            </span>
          </dt>
          <dd>
            {softwareScoreEntity.updateTime ? (
              <TextFormat value={softwareScoreEntity.updateTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="note">
              <Translate contentKey="cmServiceApp.softwareScore.note">Note</Translate>
            </span>
          </dt>
          <dd>{softwareScoreEntity.note}</dd>
          <dt>
            <Translate contentKey="cmServiceApp.softwareScore.software">Software</Translate>
          </dt>
          <dd>{softwareScoreEntity.softwareName ? softwareScoreEntity.softwareName : ''}</dd>
          <dt>
            <Translate contentKey="cmServiceApp.softwareScore.userLink">User Link</Translate>
          </dt>
          <dd>{softwareScoreEntity.userLinkFirstName ? softwareScoreEntity.userLinkFirstName : ''}</dd>
        </dl>
        <Button tag={Link} to="/software-score" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/software-score/${softwareScoreEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ softwareScore }: IRootState) => ({
  softwareScoreEntity: softwareScore.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SoftwareScoreDetail);
