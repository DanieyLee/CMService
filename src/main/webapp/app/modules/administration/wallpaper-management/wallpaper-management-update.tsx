import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntities as getUserLinks } from 'app/entities/user-link/user-link.reducer';
import { getEntity, updateEntity, createEntity, reset } from 'app/entities/wallpaper/wallpaper.reducer';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';

export interface IWallpaperManagementUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WallpaperManagementUpdate = (props: IWallpaperManagementUpdateProps) => {
  const [userLinkId, setUserLinkId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { wallpaperEntity, userLinks, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/admin/wallpaper-management' + props.location.search);
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
        ...wallpaperEntity,
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
    <div className="content-wallpaper-management-edit">
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="cmServiceApp.wallpaper.home.createOrEditLabel">
            <Translate contentKey="cmServiceApp.wallpaper.home.createOrEditLabel">Create or edit a Wallpaper</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : wallpaperEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="wallpaper-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="wallpaper-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="imageNameLabel" for="wallpaper-imageName">
                  <Translate contentKey="cmServiceApp.wallpaper.imageName">Image Name</Translate>
                </Label>
                <AvField id="wallpaper-imageName" type="text" name="imageName" />
              </AvGroup>
              <AvGroup>
                <Label id="imageUrlLabel" for="wallpaper-imageUrl">
                  <Translate contentKey="cmServiceApp.wallpaper.imageUrl">Image Url</Translate>
                </Label>
                <AvField
                  id="wallpaper-imageUrl"
                  type="text"
                  name="imageUrl"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="imagePixelLabel" for="wallpaper-imagePixel">
                  <Translate contentKey="cmServiceApp.wallpaper.imagePixel">Image Pixel</Translate>
                </Label>
                <AvField id="wallpaper-imagePixel" type="text" name="imagePixel" />
              </AvGroup>
              <AvGroup>
                <Label id="imageTypeLabel" for="wallpaper-imageType">
                  <Translate contentKey="cmServiceApp.wallpaper.imageType">Image Type</Translate>
                </Label>
                <AvInput
                  id="wallpaper-imageType"
                  type="select"
                  className="form-control"
                  name="imageType"
                  value={(!isNew && wallpaperEntity.imageType) || 'PNG'}
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
                <Label id="visitorVolumeLabel" for="wallpaper-visitorVolume">
                  <Translate contentKey="cmServiceApp.wallpaper.visitorVolume">Visitor Volume</Translate>
                </Label>
                <AvField id="wallpaper-visitorVolume" type="string" className="form-control" name="visitorVolume" />
              </AvGroup>
              <AvGroup check>
                <Label id="isDownloadLabel">
                  <AvInput id="wallpaper-isDownload" type="checkbox" className="form-check-input" name="isDownload" />
                  <Translate contentKey="cmServiceApp.wallpaper.isDownload">Is Download</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="likeLabel" for="wallpaper-like">
                  <Translate contentKey="cmServiceApp.wallpaper.like">Like</Translate>
                </Label>
                <AvField id="wallpaper-like" type="string" className="form-control" name="like" />
              </AvGroup>
              <AvGroup check>
                <Label id="stateLabel">
                  <AvInput id="wallpaper-state" type="checkbox" className="form-check-input" name="state" />
                  <Translate contentKey="cmServiceApp.wallpaper.state">State</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="createUserLabel" for="wallpaper-createUser">
                  <Translate contentKey="cmServiceApp.wallpaper.createUser">Create User</Translate>
                </Label>
                <AvField id="wallpaper-createUser" type="text" name="createUser" />
              </AvGroup>
              <AvGroup>
                <Label id="creatTimeLabel" for="wallpaper-creatTime">
                  <Translate contentKey="cmServiceApp.wallpaper.creatTime">Creat Time</Translate>
                </Label>
                <AvInput
                  id="wallpaper-creatTime"
                  type="datetime-local"
                  className="form-control"
                  name="creatTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.wallpaperEntity.creatTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updateUserLabel" for="wallpaper-updateUser">
                  <Translate contentKey="cmServiceApp.wallpaper.updateUser">Update User</Translate>
                </Label>
                <AvField id="wallpaper-updateUser" type="text" name="updateUser" />
              </AvGroup>
              <AvGroup>
                <Label id="updateTimeLabel" for="wallpaper-updateTime">
                  <Translate contentKey="cmServiceApp.wallpaper.updateTime">Update Time</Translate>
                </Label>
                <AvInput
                  id="wallpaper-updateTime"
                  type="datetime-local"
                  className="form-control"
                  name="updateTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.wallpaperEntity.updateTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="wallpaper-note">
                  <Translate contentKey="cmServiceApp.wallpaper.note">Note</Translate>
                </Label>
                <AvField id="wallpaper-note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label for="wallpaper-userLink">
                  <Translate contentKey="cmServiceApp.wallpaper.userLink">User Link</Translate>
                </Label>
                <AvInput id="wallpaper-userLink" type="select" className="form-control" name="userLinkId">
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
              <Button tag={Link} id="cancel-save" to="/admin/wallpaper-management" replace color="info">
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
  wallpaperEntity: storeState.wallpaper.entity,
  loading: storeState.wallpaper.loading,
  updating: storeState.wallpaper.updating,
  updateSuccess: storeState.wallpaper.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(WallpaperManagementUpdate);
