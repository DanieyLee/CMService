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
import { getEntity, updateEntity, createEntity, reset } from './software-comments.reducer';
import { ISoftwareComments } from 'app/shared/model/software-comments.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISoftwareCommentsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SoftwareCommentsUpdate = (props: ISoftwareCommentsUpdateProps) => {
  const [softwareId, setSoftwareId] = useState('0');
  const [userLinkId, setUserLinkId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { softwareCommentsEntity, software, userLinks, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/software-comments' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
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
        ...softwareCommentsEntity,
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
          <h2 id="cmServiceApp.softwareComments.home.createOrEditLabel">
            <Translate contentKey="cmServiceApp.softwareComments.home.createOrEditLabel">Create or edit a SoftwareComments</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : softwareCommentsEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="software-comments-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="software-comments-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="txTitleLabel" for="software-comments-txTitle">
                  <Translate contentKey="cmServiceApp.softwareComments.txTitle">Tx Title</Translate>
                </Label>
                <AvField
                  id="software-comments-txTitle"
                  type="text"
                  name="txTitle"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="contentLabel" for="software-comments-content">
                  <Translate contentKey="cmServiceApp.softwareComments.content">Content</Translate>
                </Label>
                <AvField
                  id="software-comments-content"
                  type="text"
                  name="content"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="createUserLabel" for="software-comments-createUser">
                  <Translate contentKey="cmServiceApp.softwareComments.createUser">Create User</Translate>
                </Label>
                <AvField id="software-comments-createUser" type="text" name="createUser" />
              </AvGroup>
              <AvGroup>
                <Label id="creatTimeLabel" for="software-comments-creatTime">
                  <Translate contentKey="cmServiceApp.softwareComments.creatTime">Creat Time</Translate>
                </Label>
                <AvInput
                  id="software-comments-creatTime"
                  type="datetime-local"
                  className="form-control"
                  name="creatTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.softwareCommentsEntity.creatTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updateUserLabel" for="software-comments-updateUser">
                  <Translate contentKey="cmServiceApp.softwareComments.updateUser">Update User</Translate>
                </Label>
                <AvField id="software-comments-updateUser" type="text" name="updateUser" />
              </AvGroup>
              <AvGroup>
                <Label id="updateTimeLabel" for="software-comments-updateTime">
                  <Translate contentKey="cmServiceApp.softwareComments.updateTime">Update Time</Translate>
                </Label>
                <AvInput
                  id="software-comments-updateTime"
                  type="datetime-local"
                  className="form-control"
                  name="updateTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.softwareCommentsEntity.updateTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="software-comments-note">
                  <Translate contentKey="cmServiceApp.softwareComments.note">Note</Translate>
                </Label>
                <AvField id="software-comments-note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label for="software-comments-software">
                  <Translate contentKey="cmServiceApp.softwareComments.software">Software</Translate>
                </Label>
                <AvInput id="software-comments-software" type="select" className="form-control" name="softwareId">
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
                <Label for="software-comments-userLink">
                  <Translate contentKey="cmServiceApp.softwareComments.userLink">User Link</Translate>
                </Label>
                <AvInput id="software-comments-userLink" type="select" className="form-control" name="userLinkId">
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
              <Button tag={Link} id="cancel-save" to="/software-comments" replace color="info">
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
  softwareCommentsEntity: storeState.softwareComments.entity,
  loading: storeState.softwareComments.loading,
  updating: storeState.softwareComments.updating,
  updateSuccess: storeState.softwareComments.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(SoftwareCommentsUpdate);
