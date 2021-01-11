import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISoftware } from 'app/shared/model/software.model';
import { getEntities as getSoftware } from 'app/entities/software/software.reducer';
import { IUserLink } from 'app/shared/model/user-link.model';
import { getEntities as getUserLinks } from 'app/entities/user-link/user-link.reducer';
import { getEntity, updateEntity, createEntity, reset } from './software-score.reducer';
import { ISoftwareScore } from 'app/shared/model/software-score.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISoftwareScoreUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SoftwareScoreUpdate = (props: ISoftwareScoreUpdateProps) => {
  const [softwareId, setSoftwareId] = useState('0');
  const [userLinkId, setUserLinkId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { softwareScoreEntity, software, userLinks, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/software-score');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getSoftware();
    props.getUserLinks();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.creatTime = convertDateTimeToServer(values.creatTime);
    values.updateTime = convertDateTimeToServer(values.updateTime);

    if (errors.length === 0) {
      const entity = {
        ...softwareScoreEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="cmServiceApp.softwareScore.home.createOrEditLabel">
            <Translate contentKey="cmServiceApp.softwareScore.home.createOrEditLabel">Create or edit a SoftwareScore</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : softwareScoreEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="software-score-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="software-score-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="scoreLabel" for="software-score-score">
                  <Translate contentKey="cmServiceApp.softwareScore.score">Score</Translate>
                </Label>
                <AvField
                  id="software-score-score"
                  type="string"
                  className="form-control"
                  name="score"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="createUserLabel" for="software-score-createUser">
                  <Translate contentKey="cmServiceApp.softwareScore.createUser">Create User</Translate>
                </Label>
                <AvField id="software-score-createUser" type="text" name="createUser" />
              </AvGroup>
              <AvGroup>
                <Label id="creatTimeLabel" for="software-score-creatTime">
                  <Translate contentKey="cmServiceApp.softwareScore.creatTime">Creat Time</Translate>
                </Label>
                <AvInput
                  id="software-score-creatTime"
                  type="datetime-local"
                  className="form-control"
                  name="creatTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.softwareScoreEntity.creatTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updateUserLabel" for="software-score-updateUser">
                  <Translate contentKey="cmServiceApp.softwareScore.updateUser">Update User</Translate>
                </Label>
                <AvField id="software-score-updateUser" type="text" name="updateUser" />
              </AvGroup>
              <AvGroup>
                <Label id="updateTimeLabel" for="software-score-updateTime">
                  <Translate contentKey="cmServiceApp.softwareScore.updateTime">Update Time</Translate>
                </Label>
                <AvInput
                  id="software-score-updateTime"
                  type="datetime-local"
                  className="form-control"
                  name="updateTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.softwareScoreEntity.updateTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="software-score-note">
                  <Translate contentKey="cmServiceApp.softwareScore.note">Note</Translate>
                </Label>
                <AvField id="software-score-note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label for="software-score-software">
                  <Translate contentKey="cmServiceApp.softwareScore.software">Software</Translate>
                </Label>
                <AvInput id="software-score-software" type="select" className="form-control" name="softwareId">
                  <option value="" key="0" />
                  {software
                    ? software.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="software-score-userLink">
                  <Translate contentKey="cmServiceApp.softwareScore.userLink">User Link</Translate>
                </Label>
                <AvInput id="software-score-userLink" type="select" className="form-control" name="userLinkId">
                  <option value="" key="0" />
                  {userLinks
                    ? userLinks.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.firstName}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/software-score" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  software: storeState.software.entities,
  userLinks: storeState.userLink.entities,
  softwareScoreEntity: storeState.softwareScore.entity,
  loading: storeState.softwareScore.loading,
  updating: storeState.softwareScore.updating,
  updateSuccess: storeState.softwareScore.updateSuccess,
});

const mapDispatchToProps = {
  getSoftware,
  getUserLinks,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SoftwareScoreUpdate);
