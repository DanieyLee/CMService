import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntities as getSoftwareTypes } from 'app/entities/software-type/software-type.reducer';
import { getEntities as getUserLinks } from 'app/entities/user-link/user-link.reducer';
import { getEntity, updateUserEntity, createUserEntity, setBlob, reset } from 'app/entities/software/software.reducer';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';

export interface ISoftwareManagementUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SoftwareManagementUpdate = (props: ISoftwareManagementUpdateProps) => {
  const [softwareTypeId, setSoftwareTypeId] = useState('0');
  const [userLinkId, setUserLinkId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { softwareEntity, softwareTypes, userLinks, loading, updating } = props;

  const { softwareICO, softwareICOContentType } = softwareEntity;

  const handleClose = () => {
    props.history.push('/system/software-management' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getSoftwareTypes();
    props.getUserLinks();
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...softwareEntity,
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
    <div className="content-software-management-edit">
      <div className="content-software-management-edit-title">
        <h2>
          <Translate contentKey="cmServiceApp.software.home.createOrEditLabel">Create or edit a Software</Translate>
        </h2>
      </div>
      {loading ? (
        <p>Loading...</p>
      ) : (
        <AvForm model={isNew ? {} : softwareEntity} onSubmit={saveEntity}>
          <Row>
            {!isNew ? (
              <Col md="1">
                <AvGroup>
                  <Label for="software-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                    :{softwareEntity.id}
                  </Label>
                  <AvInput id="software-id" type="hidden" className="form-control" name="id" required readOnly />
                </AvGroup>
              </Col>
            ) : null}
            <Col md="2">
              <AvGroup check>
                <Label id="starsLabel">
                  <AvInput id="software-stars" type="checkbox" className="form-check-input" name="stars" />
                  <Translate contentKey="cmServiceApp.software.stars">Stars</Translate>
                </Label>
              </AvGroup>
            </Col>
            <Col md="2">
              <AvGroup check>
                <Label id="showLabel">
                  <AvInput id="software-show" type="checkbox" className="form-check-input" name="show" />
                  <Translate contentKey="cmServiceApp.software.show">Show</Translate>
                </Label>
              </AvGroup>
            </Col>
            <Col md="2">
              <AvGroup check>
                <Label id="allowLabel">
                  <AvInput id="software-allow" type="checkbox" className="form-check-input" name="allow" />
                  <Translate contentKey="cmServiceApp.software.allow">Allow</Translate>
                </Label>
              </AvGroup>
            </Col>
            <Col md="2">
              <AvGroup check>
                <Label id="stateLabel">
                  <AvInput id="software-state" type="checkbox" className="form-check-input" name="state" />
                  <Translate contentKey="cmServiceApp.software.state">State</Translate>
                </Label>
              </AvGroup>
            </Col>
            <Col md="6">
              <AvGroup>
                <Label for="software-softwareType">
                  <Translate contentKey="cmServiceApp.software.softwareType">Software Type</Translate>
                </Label>
                <AvInput id="software-softwareType" type="select" className="form-control" name="softwareTypeId">
                  <option value="" key="0" />
                  {softwareTypes
                    ? softwareTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.type}
                      </option>
                    ))
                    : null}
                </AvInput>
              </AvGroup>
            </Col>
            <Col md="6">
              <AvGroup>
                <Label id="applySystemLabel" for="software-applySystem">
                  <Translate contentKey="cmServiceApp.software.applySystem">Apply System</Translate>
                </Label>
                <AvInput
                  id="software-applySystem"
                  type="select"
                  className="form-control"
                  name="applySystem"
                  value={(!isNew && softwareEntity.applySystem) || 'WIN'}
                >
                  <option value="WIN">{translate('cmServiceApp.SystemType.WIN')}</option>
                  <option value="LINUX">{translate('cmServiceApp.SystemType.LINUX')}</option>
                  <option value="MACOS">{translate('cmServiceApp.SystemType.MACOS')}</option>
                  <option value="ANDROID">{translate('cmServiceApp.SystemType.ANDROID')}</option>
                  <option value="IOS">{translate('cmServiceApp.SystemType.IOS')}</option>
                </AvInput>
              </AvGroup>
            </Col>
            <Col md="6">
              <AvGroup>
                <Label id="nameLabel" for="software-name">
                  <Translate contentKey="cmServiceApp.software.name">Name</Translate>
                </Label>
                <AvField
                  id="software-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="downloadUrlLabel" for="software-downloadUrl">
                  <Translate contentKey="cmServiceApp.software.downloadUrl">Download Url</Translate>
                </Label>
                <AvField id="software-downloadUrl" type="text" name="downloadUrl" />
              </AvGroup>
            </Col>
            <Col md="2">
              <AvGroup>
                <Label id="versionLabel" for="software-version">
                  <Translate contentKey="cmServiceApp.software.version">Version</Translate>
                </Label>
                <AvField id="software-version" type="text" name="version" />
              </AvGroup>
              <AvGroup>
                <Label id="sizeLabel" for="software-size">
                  <Translate contentKey="cmServiceApp.software.size">Size</Translate>
                </Label>
                <AvField id="software-size" type="string" className="form-control" name="size" />
              </AvGroup>
            </Col>
            <Col md="4">
              <AvGroup>
                <AvGroup>
                  <Label id="softwareICOLabel" for="softwareICO">
                    <Translate contentKey="cmServiceApp.software.softwareICO">Software ICO</Translate>
                  </Label>
                  <br />
                  <input id="file_softwareICO" type="file" onChange={onBlobChange(true, 'softwareICO')} accept="image/*" />
                  {softwareICO ? (
                    <div>
                      {softwareICOContentType ? (
                        <img src={`data:${softwareICOContentType};base64,${softwareICO}`} style={{ maxHeight: '100px' }} />
                      ) : null}
                      <Button color="danger" onClick={clearBlob('softwareICO')}>
                        <FontAwesomeIcon icon="times-circle" />
                      </Button>
                    </div>
                  ) : null}
                  <AvInput type="hidden" name="softwareICO" value={softwareICO} />
                </AvGroup>
              </AvGroup>
            </Col>
            {!isNew ? (
              <Col md="4">
                <AvGroup>
                  <Label id="scoreLabel" for="software-score">
                    <Translate contentKey="cmServiceApp.software.score">Score</Translate>
                  </Label>
                  <AvField id="software-score" type="string" className="form-control" name="score" />
                </AvGroup>
              </Col>
            ) : null}
            {!isNew ? (
              <Col md="4">
                <AvGroup>
                  <Label id="downloadNumberLabel" for="software-downloadNumber">
                    <Translate contentKey="cmServiceApp.software.downloadNumber">Download Number</Translate>
                  </Label>
                  <AvField id="software-downloadNumber" type="string" className="form-control" name="downloadNumber" />
                </AvGroup>
              </Col>
            ) : null}
            {!isNew ? (
              <Col md="4">
                <AvGroup>
                  <Label id="browseNumberLabel" for="software-browseNumber">
                    <Translate contentKey="cmServiceApp.software.browseNumber">Browse Number</Translate>
                  </Label>
                  <AvField id="software-browseNumber" type="string" className="form-control" name="browseNumber" />
                </AvGroup>
              </Col>
            ) : null}
            <Col md="12">
              <AvGroup>
                <Label id="explainLabel" for="software-explain">
                  <Translate contentKey="cmServiceApp.software.explain">Explain</Translate>
                </Label>
                <AvField id="software-explain" type="textarea" name="explain" />
              </AvGroup>
            </Col>
            <Col md="12">
              <AvGroup>
                <Label id="noteLabel" for="software-note">
                  <Translate contentKey="cmServiceApp.software.note">Note</Translate>
                </Label>
                <AvField id="software-note" type="textarea" name="note" />
              </AvGroup>
            </Col>
            <AvGroup>
              <AvField id="software-createUser" type="hidden" name="createUser" />
              <AvField id="software-creatTime" type="hidden" name="creatTime" />
              <AvField id="software-userLink" type="hidden" name="userLinkId" />
            </AvGroup>
            <Col md="12">
              <Button tag={Link} id="cancel-save" to="/system/software-management" replace color="info">
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
  softwareTypes: storeState.softwareType.entities,
  userLinks: storeState.userLink.entities,
  softwareEntity: storeState.software.entity,
  loading: storeState.software.loading,
  updating: storeState.software.updating,
  updateSuccess: storeState.software.updateSuccess,
});

const mapDispatchToProps = {
  getSoftwareTypes,
  getUserLinks,
  getEntity,
  updateUserEntity,
  setBlob,
  createUserEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SoftwareManagementUpdate);
