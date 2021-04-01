import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, setFileData } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntities as getArticleTypes } from 'app/entities/article-type/article-type.reducer';
import { getEntities as getUserLinks } from 'app/entities/user-link/user-link.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from 'app/entities/article/article.reducer';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';

export interface IArticleManagementUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArticleManagementUpdate = (props: IArticleManagementUpdateProps) => {
  const [articleTypeId, setArticleTypeId] = useState('0');
  const [userLinkId, setUserLinkId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { articleEntity, articleTypes, userLinks, loading, updating } = props;

  const { content } = articleEntity;

  const handleClose = () => {
    props.history.push('/system/article-management' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getArticleTypes();
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
        ...articleEntity,
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
    <div className="content-article-management-edit">
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="cmServiceApp.article.home.createOrEditLabel">
            <Translate contentKey="cmServiceApp.article.home.createOrEditLabel">Create or edit a Article</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : articleEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="article-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="article-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="titleLabel" for="article-title">
                  <Translate contentKey="cmServiceApp.article.title">Title</Translate>
                </Label>
                <AvField
                  id="article-title"
                  type="text"
                  name="title"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="authorLabel" for="article-author">
                  <Translate contentKey="cmServiceApp.article.author">Author</Translate>
                </Label>
                <AvField id="article-author" type="text" name="author" />
              </AvGroup>
              <AvGroup>
                <Label id="contentLabel" for="article-content">
                  <Translate contentKey="cmServiceApp.article.content">Content</Translate>
                </Label>
                <AvInput id="article-content" type="textarea" name="content" />
              </AvGroup>
              <AvGroup>
                <Label id="viewsLabel" for="article-views">
                  <Translate contentKey="cmServiceApp.article.views">Views</Translate>
                </Label>
                <AvField id="article-views" type="string" className="form-control" name="views" />
              </AvGroup>
              <AvGroup>
                <Label id="likeNumberLabel" for="article-likeNumber">
                  <Translate contentKey="cmServiceApp.article.likeNumber">Like Number</Translate>
                </Label>
                <AvField id="article-likeNumber" type="string" className="form-control" name="likeNumber" />
              </AvGroup>
              <AvGroup check>
                <Label id="stateLabel">
                  <AvInput id="article-state" type="checkbox" className="form-check-input" name="state" />
                  <Translate contentKey="cmServiceApp.article.state">State</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="createUserLabel" for="article-createUser">
                  <Translate contentKey="cmServiceApp.article.createUser">Create User</Translate>
                </Label>
                <AvField id="article-createUser" type="text" name="createUser" />
              </AvGroup>
              <AvGroup>
                <Label id="creatTimeLabel" for="article-creatTime">
                  <Translate contentKey="cmServiceApp.article.creatTime">Creat Time</Translate>
                </Label>
                <AvInput
                  id="article-creatTime"
                  type="datetime-local"
                  className="form-control"
                  name="creatTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.articleEntity.creatTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updateUserLabel" for="article-updateUser">
                  <Translate contentKey="cmServiceApp.article.updateUser">Update User</Translate>
                </Label>
                <AvField id="article-updateUser" type="text" name="updateUser" />
              </AvGroup>
              <AvGroup>
                <Label id="updateTimeLabel" for="article-updateTime">
                  <Translate contentKey="cmServiceApp.article.updateTime">Update Time</Translate>
                </Label>
                <AvInput
                  id="article-updateTime"
                  type="datetime-local"
                  className="form-control"
                  name="updateTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.articleEntity.updateTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="article-note">
                  <Translate contentKey="cmServiceApp.article.note">Note</Translate>
                </Label>
                <AvField id="article-note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label for="article-articleType">
                  <Translate contentKey="cmServiceApp.article.articleType">Article Type</Translate>
                </Label>
                <AvInput id="article-articleType" type="select" className="form-control" name="articleTypeId">
                  <option value="" key="0" />
                  {articleTypes
                    ? articleTypes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.type}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="article-userLink">
                  <Translate contentKey="cmServiceApp.article.userLink">User Link</Translate>
                </Label>
                <AvInput id="article-userLink" type="select" className="form-control" name="userLinkId">
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
              <Button tag={Link} id="cancel-save" to="/system/article-management" replace color="info">
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
  articleTypes: storeState.articleType.entities,
  userLinks: storeState.userLink.entities,
  articleEntity: storeState.article.entity,
  loading: storeState.article.loading,
  updating: storeState.article.updating,
  updateSuccess: storeState.article.updateSuccess,
});

const mapDispatchToProps = {
  getArticleTypes,
  getUserLinks,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArticleManagementUpdate);
