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
import { getEntity, updateEntity, createEntity, reset } from './phone.reducer';
import { IPhone } from 'app/shared/model/phone.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPhoneUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PhoneUpdate = (props: IPhoneUpdateProps) => {
  const [userLinkId, setUserLinkId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { phoneEntity, userLinks, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/phone' + props.location.search);
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
    values.effectiveTime = convertDateTimeToServer(values.effectiveTime);
    values.sendTime = convertDateTimeToServer(values.sendTime);
    values.creatTime = convertDateTimeToServer(values.creatTime);
    values.updateTime = convertDateTimeToServer(values.updateTime);

    if (errors.length === 0) {
      const entity = {
        ...phoneEntity,
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
          <h2 id="cmServiceApp.phone.home.createOrEditLabel">
            <Translate contentKey="cmServiceApp.phone.home.createOrEditLabel">Create or edit a Phone</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : phoneEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="phone-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="phone-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="phoneLabel" for="phone-phone">
                  <Translate contentKey="cmServiceApp.phone.phone">Phone</Translate>
                </Label>
                <AvField id="phone-phone" type="text" name="phone" />
              </AvGroup>
              <AvGroup>
                <Label id="codeLabel" for="phone-code">
                  <Translate contentKey="cmServiceApp.phone.code">Code</Translate>
                </Label>
                <AvField id="phone-code" type="string" className="form-control" name="code" />
              </AvGroup>
              <AvGroup>
                <Label id="effectiveTimeLabel" for="phone-effectiveTime">
                  <Translate contentKey="cmServiceApp.phone.effectiveTime">Effective Time</Translate>
                </Label>
                <AvInput
                  id="phone-effectiveTime"
                  type="datetime-local"
                  className="form-control"
                  name="effectiveTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.phoneEntity.effectiveTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="sendTimeLabel" for="phone-sendTime">
                  <Translate contentKey="cmServiceApp.phone.sendTime">Send Time</Translate>
                </Label>
                <AvInput
                  id="phone-sendTime"
                  type="datetime-local"
                  className="form-control"
                  name="sendTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.phoneEntity.sendTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="createUserLabel" for="phone-createUser">
                  <Translate contentKey="cmServiceApp.phone.createUser">Create User</Translate>
                </Label>
                <AvField id="phone-createUser" type="text" name="createUser" />
              </AvGroup>
              <AvGroup>
                <Label id="creatTimeLabel" for="phone-creatTime">
                  <Translate contentKey="cmServiceApp.phone.creatTime">Creat Time</Translate>
                </Label>
                <AvInput
                  id="phone-creatTime"
                  type="datetime-local"
                  className="form-control"
                  name="creatTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.phoneEntity.creatTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updateUserLabel" for="phone-updateUser">
                  <Translate contentKey="cmServiceApp.phone.updateUser">Update User</Translate>
                </Label>
                <AvField id="phone-updateUser" type="text" name="updateUser" />
              </AvGroup>
              <AvGroup>
                <Label id="updateTimeLabel" for="phone-updateTime">
                  <Translate contentKey="cmServiceApp.phone.updateTime">Update Time</Translate>
                </Label>
                <AvInput
                  id="phone-updateTime"
                  type="datetime-local"
                  className="form-control"
                  name="updateTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.phoneEntity.updateTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="phone-note">
                  <Translate contentKey="cmServiceApp.phone.note">Note</Translate>
                </Label>
                <AvField id="phone-note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label for="phone-userLink">
                  <Translate contentKey="cmServiceApp.phone.userLink">User Link</Translate>
                </Label>
                <AvInput id="phone-userLink" type="select" className="form-control" name="userLinkId">
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
              <Button tag={Link} id="cancel-save" to="/phone" replace color="info">
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
  phoneEntity: storeState.phone.entity,
  loading: storeState.phone.loading,
  updating: storeState.phone.updating,
  updateSuccess: storeState.phone.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(PhoneUpdate);
