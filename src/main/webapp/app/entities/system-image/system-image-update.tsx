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
import { getEntity, updateEntity, createEntity, reset } from './system-image.reducer';
import { ISystemImage } from 'app/shared/model/system-image.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISystemImageUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SystemImageUpdate = (props: ISystemImageUpdateProps) => {
  const [userLinkId, setUserLinkId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { systemImageEntity, userLinks, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/system-image');
  };

  useEffect(() => {
    if (!isNew) {
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
        ...systemImageEntity,
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
          <h2 id="cmServiceApp.systemImage.home.createOrEditLabel">
            <Translate contentKey="cmServiceApp.systemImage.home.createOrEditLabel">Create or edit a SystemImage</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : systemImageEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="system-image-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="system-image-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="imageURLLabel" for="system-image-imageURL">
                  <Translate contentKey="cmServiceApp.systemImage.imageURL">Image URL</Translate>
                </Label>
                <AvField
                  id="system-image-imageURL"
                  type="text"
                  name="imageURL"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="imageTypeLabel" for="system-image-imageType">
                  <Translate contentKey="cmServiceApp.systemImage.imageType">Image Type</Translate>
                </Label>
                <AvInput
                  id="system-image-imageType"
                  type="select"
                  className="form-control"
                  name="imageType"
                  value={(!isNew && systemImageEntity.imageType) || 'PNG'}
                >
                  <option value="PNG">{translate('cmServiceApp.ImageType.PNG')}</option>
                  <option value="JPG">{translate('cmServiceApp.ImageType.JPG')}</option>
                  <option value="JPEG">{translate('cmServiceApp.ImageType.JPEG')}</option>
                  <option value="BMP">{translate('cmServiceApp.ImageType.BMP')}</option>
                  <option value="GIF">{translate('cmServiceApp.ImageType.GIF')}</option>
                  <option value="PSD">{translate('cmServiceApp.ImageType.PSD')}</option>
                  <option value="AI">{translate('cmServiceApp.ImageType.AI')}</option>
                  <option value="CDR">{translate('cmServiceApp.ImageType.CDR')}</option>
                  <option value="PCD">{translate('cmServiceApp.ImageType.PCD')}</option>
                  <option value="SVG">{translate('cmServiceApp.ImageType.SVG')}</option>
                  <option value="RAW">{translate('cmServiceApp.ImageType.RAW')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="createUserLabel" for="system-image-createUser">
                  <Translate contentKey="cmServiceApp.systemImage.createUser">Create User</Translate>
                </Label>
                <AvField id="system-image-createUser" type="text" name="createUser" />
              </AvGroup>
              <AvGroup>
                <Label id="creatTimeLabel" for="system-image-creatTime">
                  <Translate contentKey="cmServiceApp.systemImage.creatTime">Creat Time</Translate>
                </Label>
                <AvInput
                  id="system-image-creatTime"
                  type="datetime-local"
                  className="form-control"
                  name="creatTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.systemImageEntity.creatTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updateUserLabel" for="system-image-updateUser">
                  <Translate contentKey="cmServiceApp.systemImage.updateUser">Update User</Translate>
                </Label>
                <AvField id="system-image-updateUser" type="text" name="updateUser" />
              </AvGroup>
              <AvGroup>
                <Label id="updateTimeLabel" for="system-image-updateTime">
                  <Translate contentKey="cmServiceApp.systemImage.updateTime">Update Time</Translate>
                </Label>
                <AvInput
                  id="system-image-updateTime"
                  type="datetime-local"
                  className="form-control"
                  name="updateTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.systemImageEntity.updateTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="system-image-note">
                  <Translate contentKey="cmServiceApp.systemImage.note">Note</Translate>
                </Label>
                <AvField id="system-image-note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label for="system-image-userLink">
                  <Translate contentKey="cmServiceApp.systemImage.userLink">User Link</Translate>
                </Label>
                <AvInput id="system-image-userLink" type="select" className="form-control" name="userLinkId">
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
              <Button tag={Link} id="cancel-save" to="/system-image" replace color="info">
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
  systemImageEntity: storeState.systemImage.entity,
  loading: storeState.systemImage.loading,
  updating: storeState.systemImage.updating,
  updateSuccess: storeState.systemImage.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(SystemImageUpdate);
