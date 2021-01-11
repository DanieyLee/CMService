import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUserLink } from 'app/shared/model/user-link.model';
import { getEntities as getUserLinks } from 'app/entities/user-link/user-link.reducer';
import { getEntity, updateEntity, createEntity, reset } from './key-box.reducer';
import { IKeyBox } from 'app/shared/model/key-box.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IKeyBoxUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const KeyBoxUpdate = (props: IKeyBoxUpdateProps) => {
  const [userLinkId, setUserLinkId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { keyBoxEntity, userLinks, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/key-box' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

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
        ...keyBoxEntity,
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
          <h2 id="cmServiceApp.keyBox.home.createOrEditLabel">
            <Translate contentKey="cmServiceApp.keyBox.home.createOrEditLabel">Create or edit a KeyBox</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : keyBoxEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="key-box-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="key-box-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="userAccountLabel" for="key-box-userAccount">
                  <Translate contentKey="cmServiceApp.keyBox.userAccount">User Account</Translate>
                </Label>
                <AvField id="key-box-userAccount" type="text" name="userAccount" />
              </AvGroup>
              <AvGroup>
                <Label id="passwordLabel" for="key-box-password">
                  <Translate contentKey="cmServiceApp.keyBox.password">Password</Translate>
                </Label>
                <AvField id="key-box-password" type="text" name="password" />
              </AvGroup>
              <AvGroup>
                <Label id="secondPasswordLabel" for="key-box-secondPassword">
                  <Translate contentKey="cmServiceApp.keyBox.secondPassword">Second Password</Translate>
                </Label>
                <AvField id="key-box-secondPassword" type="text" name="secondPassword" />
              </AvGroup>
              <AvGroup>
                <Label id="loginAddressLabel" for="key-box-loginAddress">
                  <Translate contentKey="cmServiceApp.keyBox.loginAddress">Login Address</Translate>
                </Label>
                <AvField id="key-box-loginAddress" type="text" name="loginAddress" />
              </AvGroup>
              <AvGroup>
                <Label id="explainLabel" for="key-box-explain">
                  <Translate contentKey="cmServiceApp.keyBox.explain">Explain</Translate>
                </Label>
                <AvField id="key-box-explain" type="text" name="explain" />
              </AvGroup>
              <AvGroup check>
                <Label id="displayLabel">
                  <AvInput id="key-box-display" type="checkbox" className="form-check-input" name="display" />
                  <Translate contentKey="cmServiceApp.keyBox.display">Display</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="createUserLabel" for="key-box-createUser">
                  <Translate contentKey="cmServiceApp.keyBox.createUser">Create User</Translate>
                </Label>
                <AvField id="key-box-createUser" type="text" name="createUser" />
              </AvGroup>
              <AvGroup>
                <Label id="creatTimeLabel" for="key-box-creatTime">
                  <Translate contentKey="cmServiceApp.keyBox.creatTime">Creat Time</Translate>
                </Label>
                <AvInput
                  id="key-box-creatTime"
                  type="datetime-local"
                  className="form-control"
                  name="creatTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.keyBoxEntity.creatTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updateUserLabel" for="key-box-updateUser">
                  <Translate contentKey="cmServiceApp.keyBox.updateUser">Update User</Translate>
                </Label>
                <AvField id="key-box-updateUser" type="text" name="updateUser" />
              </AvGroup>
              <AvGroup>
                <Label id="updateTimeLabel" for="key-box-updateTime">
                  <Translate contentKey="cmServiceApp.keyBox.updateTime">Update Time</Translate>
                </Label>
                <AvInput
                  id="key-box-updateTime"
                  type="datetime-local"
                  className="form-control"
                  name="updateTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.keyBoxEntity.updateTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="key-box-note">
                  <Translate contentKey="cmServiceApp.keyBox.note">Note</Translate>
                </Label>
                <AvField id="key-box-note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label for="key-box-userLink">
                  <Translate contentKey="cmServiceApp.keyBox.userLink">User Link</Translate>
                </Label>
                <AvInput id="key-box-userLink" type="select" className="form-control" name="userLinkId">
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
              <Button tag={Link} id="cancel-save" to="/key-box" replace color="info">
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
  userLinks: storeState.userLink.entities,
  keyBoxEntity: storeState.keyBox.entity,
  loading: storeState.keyBox.loading,
  updating: storeState.keyBox.updating,
  updateSuccess: storeState.keyBox.updateSuccess,
});

const mapDispatchToProps = {
  getUserLinks,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(KeyBoxUpdate);
