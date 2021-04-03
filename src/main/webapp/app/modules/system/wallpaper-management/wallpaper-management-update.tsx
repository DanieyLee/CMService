import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, Translate, translate } from 'react-jhipster';
import { IRootState } from 'app/shared/reducers';

import { getEntities as getUserLinks } from 'app/entities/user-link/user-link.reducer';
import { getEntity, updateUserEntity, createUserEntity, setBlobAll, reset } from 'app/entities/wallpaper/wallpaper.reducer';
import { convertDateTimeToServer } from 'app/shared/util/date-utils';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export interface IWallpaperManagementUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WallpaperManagementUpdate = (props: IWallpaperManagementUpdateProps) => {
  const [userLinkId, setUserLinkId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { wallpaperEntity, userLinks, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/system/wallpaper-management' + props.location.search);
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
    if (errors.length === 0) {
      const entity = {
        ...wallpaperEntity,
        ...values,
      };
      if (isNew) {
        props.createUserEntity(entity);
        event.persist();
      } else {
        props.updateUserEntity(entity);
      }
    }
  };

  const onBlobChange = (isAnImage, name) => event => {
    if (event && event.target.files && event.target.files[0]) {
      const imgName = event.target.files[0].name;
      setFileData(event, (contentType, data) => props.setBlobAll(name, imgName, true, data, contentType), isAnImage);
    }
  };

  const clearBlob = name => () => {
    props.setBlobAll(name, undefined, false, undefined, undefined);
  };

  return (
    <div className="content-wallpaper-management-edit">
      <div className="justify-content-center">
        <h2 id="cmServiceApp.wallpaper.home.createOrEditLabel">
          <Translate contentKey="cmServiceApp.wallpaper.home.createOrEditLabel">Create or edit a Wallpaper</Translate>
        </h2>
      </div>
      {loading ? (
        <p>Loading...</p>
      ) : (
        <AvForm model={isNew ? {} : wallpaperEntity} onSubmit={saveEntity}>
          <Row>
            {!isNew ? (
              <Col md="1">
                <AvGroup>
                  <Label for="wallpaper-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                    :{wallpaperEntity.id}
                  </Label>
                  <AvInput id="wallpaper-id" type="hidden" name="id" required readOnly />
                </AvGroup>
              </Col>
            ) : null}
            <Col md="11">
              <AvGroup check>
                <Label id="stateLabel">
                  <AvInput id="wallpaper-state" type="checkbox" className="form-check-input" name="state" />
                  <Translate contentKey="cmServiceApp.wallpaper.state">State</Translate>
                </Label>
              </AvGroup>
            </Col>
            {!isNew ? (
              <Col md="2">
                <AvGroup>
                  <Label id="visitorVolumeLabel" for="wallpaper-visitorVolume">
                    <Translate contentKey="cmServiceApp.wallpaper.visitorVolume">Visitor Volume</Translate>
                  </Label>
                  <AvField id="wallpaper-visitorVolume" type="string" className="form-control" name="visitorVolume" />
                </AvGroup>
              </Col>
            ) : null}
            {!isNew ? (
              <Col md="2">
                <AvGroup>
                  <Label id="likeLabel" for="wallpaper-like">
                    <Translate contentKey="cmServiceApp.wallpaper.like">Like</Translate>
                  </Label>
                  <AvField id="wallpaper-like" type="string" className="form-control" name="like" />
                </AvGroup>
              </Col>
            ) : null}
            <Col md="3">
              <AvGroup>
                <Label id="imagePixelLabel" for="wallpaper-imagePixel">
                  <Translate contentKey="cmServiceApp.wallpaper.imagePixel">Image Pixel</Translate>
                </Label>
                <AvField id="wallpaper-imagePixel" type="text" name="imagePixel" />
              </AvGroup>
            </Col>
            <Col md="2">
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
            </Col>
            <Col md="6">
              <AvGroup>
                <Label id="imageNameLabel" for="wallpaper-imageName">
                  <Translate contentKey="cmServiceApp.wallpaper.imageName">Image Name</Translate>
                </Label>
                <AvField id="wallpaper-imageName" type="text" name="imageName" />
              </AvGroup>
            </Col>
            <Col md="12">
              <AvGroup>
                <Label id="noteLabel" for="wallpaper-note">
                  <Translate contentKey="cmServiceApp.wallpaper.note">Note</Translate>
                </Label>
                <AvField id="wallpaper-note" type="text" name="note" />
              </AvGroup>
            </Col>
            <Col md="12">
              <div className="content-account-settings-portrait">
                {props.wallpaperEntity.imgSwitch ? (
                  <img src={`data:${wallpaperEntity.imageContentType};base64,${wallpaperEntity.image}`} />
                ) : isNew ? <img></img> : <img src={`${wallpaperEntity.imageUrl}`} alt={`${wallpaperEntity.imageName}`} />}
                <div className="content-account-settings-portrait-select">
                  <div>
                    {isNew ? <input id="file" type="file" onChange={onBlobChange(true, 'image')} accept="image/*" /> : null}
                  </div>
                </div>
              </div>
              {props.wallpaperEntity.imgSwitch ? (
                <Button className="content-account-settings-portrait-cancel" color="danger" onClick={clearBlob('image')}>
                  <FontAwesomeIcon icon="times-circle" />
                </Button>
              ):null}
              <AvField type="hidden" name="image" value={wallpaperEntity.image} />
              <AvField type="hidden" name="img-name" value={wallpaperEntity.imgName} />
              <AvField type="hidden" name="img-switch" value={wallpaperEntity.imgSwitch} />
            </Col>
            <AvGroup>
              <AvField id="wallpaper-createUser" type="hidden" name="createUser" />
              <AvField id="wallpaper-creatTime" type="hidden" name="creatTime" />
              <AvField id="wallpaper-imageUrl" type="hidden" name="imageUrl" />
              <AvField id="wallpaper-userLink" type="hidden" name="userLinkId" />
            </AvGroup>
            <Col md="12">
              <Button tag={Link} id="cancel-save" to="/system/wallpaper-management" replace color="info">
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </Col>
          </Row>
        </AvForm>
      )}
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
  setBlobAll,
  updateUserEntity,
  createUserEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WallpaperManagementUpdate);
