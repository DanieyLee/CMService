import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISoftwareType } from 'app/shared/model/software-type.model';
import { getEntities as getSoftwareTypes } from 'app/entities/software-type/software-type.reducer';
import { IUserLink } from 'app/shared/model/user-link.model';
import { getEntities as getUserLinks } from 'app/entities/user-link/user-link.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './software.reducer';
import { ISoftware } from 'app/shared/model/software.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISoftwareUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SoftwareUpdate = (props: ISoftwareUpdateProps) => {
  const [softwareTypeId, setSoftwareTypeId] = useState('0');
  const [userLinkId, setUserLinkId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { softwareEntity, softwareTypes, userLinks, loading, updating } = props;

  const { softwareICO, softwareICOContentType } = softwareEntity;

  const handleClose = () => {
    props.history.push('/software' + props.location.search);
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
    values.creatTime = convertDateTimeToServer(values.creatTime);
    values.updateTime = convertDateTimeToServer(values.updateTime);

    if (errors.length === 0) {
      const entity = {
        ...softwareEntity,
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
          <h2 id="cmServiceApp.software.home.createOrEditLabel">
            <Translate contentKey="cmServiceApp.software.home.createOrEditLabel">Create or edit a Software</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : softwareEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="software-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="software-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup check>
                <Label id="starsLabel">
                  <AvInput id="software-stars" type="checkbox" className="form-check-input" name="stars" />
                  <Translate contentKey="cmServiceApp.software.stars">Stars</Translate>
                </Label>
              </AvGroup>
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
                <Label id="explainLabel" for="software-explain">
                  <Translate contentKey="cmServiceApp.software.explain">Explain</Translate>
                </Label>
                <AvField id="software-explain" type="text" name="explain" />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="softwareICOLabel" for="softwareICO">
                    <Translate contentKey="cmServiceApp.software.softwareICO">Software ICO</Translate>
                  </Label>
                  <br />
                  {softwareICO ? (
                    <div>
                      {softwareICOContentType ? (
                        <a onClick={openFile(softwareICOContentType, softwareICO)}>
                          <img src={`data:${softwareICOContentType};base64,${softwareICO}`} style={{ maxHeight: '100px' }} />
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {softwareICOContentType}, {byteSize(softwareICO)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('softwareICO')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_softwareICO" type="file" onChange={onBlobChange(true, 'softwareICO')} accept="image/*" />
                  <AvInput type="hidden" name="softwareICO" value={softwareICO} />
                </AvGroup>
              </AvGroup>
              <AvGroup>
                <Label id="scoreLabel" for="software-score">
                  <Translate contentKey="cmServiceApp.software.score">Score</Translate>
                </Label>
                <AvField id="software-score" type="string" className="form-control" name="score" />
              </AvGroup>
              <AvGroup>
                <Label id="sizeLabel" for="software-size">
                  <Translate contentKey="cmServiceApp.software.size">Size</Translate>
                </Label>
                <AvField id="software-size" type="string" className="form-control" name="size" />
              </AvGroup>
              <AvGroup>
                <Label id="versionLabel" for="software-version">
                  <Translate contentKey="cmServiceApp.software.version">Version</Translate>
                </Label>
                <AvField id="software-version" type="text" name="version" />
              </AvGroup>
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
              <AvGroup check>
                <Label id="showLabel">
                  <AvInput id="software-show" type="checkbox" className="form-check-input" name="show" />
                  <Translate contentKey="cmServiceApp.software.show">Show</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="allowLabel">
                  <AvInput id="software-allow" type="checkbox" className="form-check-input" name="allow" />
                  <Translate contentKey="cmServiceApp.software.allow">Allow</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="downloadUrlLabel" for="software-downloadUrl">
                  <Translate contentKey="cmServiceApp.software.downloadUrl">Download Url</Translate>
                </Label>
                <AvField id="software-downloadUrl" type="text" name="downloadUrl" />
              </AvGroup>
              <AvGroup>
                <Label id="downloadNumberLabel" for="software-downloadNumber">
                  <Translate contentKey="cmServiceApp.software.downloadNumber">Download Number</Translate>
                </Label>
                <AvField id="software-downloadNumber" type="string" className="form-control" name="downloadNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="browseNumberLabel" for="software-browseNumber">
                  <Translate contentKey="cmServiceApp.software.browseNumber">Browse Number</Translate>
                </Label>
                <AvField id="software-browseNumber" type="string" className="form-control" name="browseNumber" />
              </AvGroup>
              <AvGroup check>
                <Label id="stateLabel">
                  <AvInput id="software-state" type="checkbox" className="form-check-input" name="state" />
                  <Translate contentKey="cmServiceApp.software.state">State</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="createUserLabel" for="software-createUser">
                  <Translate contentKey="cmServiceApp.software.createUser">Create User</Translate>
                </Label>
                <AvField id="software-createUser" type="text" name="createUser" />
              </AvGroup>
              <AvGroup>
                <Label id="creatTimeLabel" for="software-creatTime">
                  <Translate contentKey="cmServiceApp.software.creatTime">Creat Time</Translate>
                </Label>
                <AvInput
                  id="software-creatTime"
                  type="datetime-local"
                  className="form-control"
                  name="creatTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.softwareEntity.creatTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updateUserLabel" for="software-updateUser">
                  <Translate contentKey="cmServiceApp.software.updateUser">Update User</Translate>
                </Label>
                <AvField id="software-updateUser" type="text" name="updateUser" />
              </AvGroup>
              <AvGroup>
                <Label id="updateTimeLabel" for="software-updateTime">
                  <Translate contentKey="cmServiceApp.software.updateTime">Update Time</Translate>
                </Label>
                <AvInput
                  id="software-updateTime"
                  type="datetime-local"
                  className="form-control"
                  name="updateTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.softwareEntity.updateTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="software-note">
                  <Translate contentKey="cmServiceApp.software.note">Note</Translate>
                </Label>
                <AvField id="software-note" type="text" name="note" />
              </AvGroup>
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
              <AvGroup>
                <Label for="software-userLink">
                  <Translate contentKey="cmServiceApp.software.userLink">User Link</Translate>
                </Label>
                <AvInput id="software-userLink" type="select" className="form-control" name="userLinkId">
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
              <Button tag={Link} id="cancel-save" to="/software" replace color="info">
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
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SoftwareUpdate);
