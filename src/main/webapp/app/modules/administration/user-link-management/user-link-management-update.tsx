import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from 'app/entities/user-link/user-link.reducer';

export interface IUserLinkManagementUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserLinkManagementUpdate = (props: IUserLinkManagementUpdateProps) => {
  const [userId, setUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { userLinkEntity, users, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/admin/user-link-management');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...userLinkEntity,
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
    <div className="content-user-link-management-edit">
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="cmServiceApp.userLink.home.createOrEditLabel">
            <Translate contentKey="cmServiceApp.userLink.home.createOrEditLabel">Create or edit a UserLink</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : userLinkEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="user-link-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="user-link-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="firstNameLabel" for="user-link-firstName">
                  <Translate contentKey="cmServiceApp.userLink.firstName">First Name</Translate>
                </Label>
                <AvField
                  id="user-link-firstName"
                  type="text"
                  name="firstName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="sexLabel">
                  <AvInput id="user-link-sex" type="checkbox" className="form-check-input" name="sex" />
                  <Translate contentKey="cmServiceApp.userLink.sex">Sex</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="ageLabel" for="user-link-age">
                  <Translate contentKey="cmServiceApp.userLink.age">Age</Translate>
                </Label>
                <AvField id="user-link-age" type="string" className="form-control" name="age" />
              </AvGroup>
              <AvGroup>
                <Label id="themeLabel" for="user-link-theme">
                  <Translate contentKey="cmServiceApp.userLink.theme">Theme</Translate>
                </Label>
                <AvField id="user-link-theme" type="text" name="theme" />
              </AvGroup>
              <AvGroup>
                <Label id="passwordKeyLabel" for="user-link-passwordKey">
                  <Translate contentKey="cmServiceApp.userLink.passwordKey">Password Key</Translate>
                </Label>
                <AvField
                  id="user-link-passwordKey"
                  type="string"
                  className="form-control"
                  name="passwordKey"
                  validate={{
                    min: { value: 6, errorMessage: translate('entity.validation.min', { min: 6 }) },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="user-link-user">
                  <Translate contentKey="cmServiceApp.userLink.user">User</Translate>
                </Label>
                <AvInput id="user-link-user" type="select" className="form-control" name="userId">
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.login}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/admin/user-link-management" replace color="info">
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
  users: storeState.userManagement.users,
  userLinkEntity: storeState.userLink.entity,
  loading: storeState.userLink.loading,
  updating: storeState.userLink.updating,
  updateSuccess: storeState.userLink.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserLinkManagementUpdate);
