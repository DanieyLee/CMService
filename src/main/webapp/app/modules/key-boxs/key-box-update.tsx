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
import { getEntity, updateUserEntity, createUserEntity, reset } from 'app/entities/key-box/key-box.reducer';
import { IKeyBox } from 'app/shared/model/key-box.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IKeyBoxUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const KeyBoxUpdate = (props: IKeyBoxUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { keyBoxEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/key-boxs' + props.location.search);
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
        props.createUserEntity(entity);
      } else {
        props.updateUserEntity(entity);
      }
    }
  };

  return (
    <div className="content-key-box-edit">
      {loading ? (
        <p>Loading...</p>
      ) : (
        <AvForm model={isNew ? {} : keyBoxEntity} onSubmit={saveEntity}>
          {!isNew ? (
              <div className="content-key-box-edit-title">
                <Translate contentKey="cmServiceApp.keyBox.home.editLabel">Create or edit a KeyBox</Translate>
                <AvInput id="key-box-id" type="hidden" className="form-control" name="id" required readOnly />
              </div>
            ) :
            <div className="content-key-box-edit-title">
              <Translate contentKey="cmServiceApp.keyBox.home.createLabel">Create or edit a KeyBox</Translate>
            </div>
          }
          <Row>
            <Col md="6">
              <AvGroup>
                <Label id="explainLabel" for="key-box-explain">
                  <Translate contentKey="cmServiceApp.keyBox.explain">Explain</Translate>
                </Label>
                <AvField id="key-box-explain" type="text" name="explain" />
              </AvGroup>
              <AvGroup>
                <Label id="userAccountLabel" for="key-box-userAccount">
                  <Translate contentKey="cmServiceApp.keyBox.userAccount">User Account</Translate>
                </Label>
                <AvField id="key-box-userAccount" type="text" name="userAccount" />
              </AvGroup>
              <AvGroup>
                <Label id="loginAddressLabel" for="key-box-loginAddress">
                  <Translate contentKey="cmServiceApp.keyBox.loginAddress">Login Address</Translate>
                </Label>
                <AvField id="key-box-loginAddress" type="text" name="loginAddress" />
              </AvGroup>
            </Col>
            <Col md="6">
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
              <AvGroup check>
                <p>
                  <Label className="content-key-box-edit-label"/>
                </p>
                <Label id="displayLabel">
                  <AvInput id="key-box-display" type="checkbox" className="form-check-input" name="display" value={isNew ? true : ""}/>
                  <Translate contentKey="cmServiceApp.keyBox.display">Display</Translate>
                </Label>
              </AvGroup>
            </Col>
          </Row>
          <AvGroup>
            <Label id="noteLabel" for="key-box-note">
              <Translate contentKey="cmServiceApp.keyBox.note">Note</Translate>
            </Label>
            <AvField id="key-box-note" type="textarea" name="note"  validate={{
              maxLength: { value: 255, errorMessage: "" },
            }}/>
          </AvGroup>
          <Button tag={Link} id="cancel-save" to="/key-boxs" replace color="info">
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          <Button color="primary" id="save-entity" type="submit" disabled={updating}>
            <Translate contentKey="entity.action.save">Save</Translate>
          </Button>
        </AvForm>
      )}
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
  updateUserEntity,
  createUserEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(KeyBoxUpdate);
