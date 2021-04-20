import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, setFileData } from 'react-jhipster';
import { IRootState } from 'app/shared/reducers';

import BraftEditor from 'braft-editor'
import 'braft-editor/dist/index.css'

import { getEntities as getArticleTypes } from 'app/entities/article-type/article-type.reducer';
import { getEntities as getUserLinks } from 'app/entities/user-link/user-link.reducer';
import { getEntity, updateUserEntity, createUserEntity, updateArticleFile, setBlob, reset } from 'app/entities/article/article.reducer';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export interface IArticleManagementUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArticleManagementUpdate = (props: IArticleManagementUpdateProps) => {
  const [articleTypeId, setArticleTypeId] = useState('0');
  const [userLinkId, setUserLinkId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);
  const [editorState, setEditorState] = useState(BraftEditor.createEditorState(null));
  const [warning, setWarning] = useState(true);

  const { articleEntity, articleTypes, userLinks, loading, updating, match } = props;

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

  useEffect(() => {
    setEditorState(BraftEditor.createEditorState(articleEntity.content));
  }, [articleEntity.content]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...articleEntity,
        ...values,
      };
      entity.content = editorState.toHTML();
      if (isNew) {
        props.createUserEntity(entity);
      } else {
        props.updateUserEntity(entity);
      }
    }
  };

  // eslint-disable-next-line no-shadow
  const handleEditorChange = (editorState) => {
    setWarning(editorState.toHTML().indexOf("src=\"data:") < 0);
    setEditorState(editorState);
  }

  return (
    <div className="content-article-management-edit">
      <div className="content-article-management-edit-title">
        <h2>
          <Translate contentKey="cmServiceApp.article.home.createOrEditLabel">Create or edit a Article</Translate>
        </h2>
      </div>
      {loading ? (
        <p>Loading...</p>
      ) : (
        <AvForm model={isNew ? {} : articleEntity} onSubmit={saveEntity}>
          <Row>
            {!isNew ? (
              <Col md="1">
                <AvGroup>
                  <Label for="article-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                    :{articleEntity.id}
                  </Label>
                  <AvInput id="article-id" type="hidden" className="form-control" name="id" required readOnly />
                </AvGroup>
              </Col>
            ) : null}
            <Col md="11">
              <AvGroup check>
                <Label id="stateLabel">
                  <AvInput id="article-state" type="checkbox" className="form-check-input" name="state" />
                  <Translate contentKey="cmServiceApp.article.state">State</Translate>
                </Label>
              </AvGroup>
            </Col>
            <Col md="3">
              <AvGroup>
                <Label for="article-articleType">
                  <Translate contentKey="cmServiceApp.article.articleType">Article Type</Translate>
                </Label>
                <AvInput id="article-articleType" type="select" className="form-control" name="articleTypeId"
                         validate={{
                           required: { value: true, errorMessage: translate('entity.validation.required') },
                         }}>
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
            </Col>
            {!isNew ? (
              <Col md="3">
                <AvGroup>
                  <Label id="viewsLabel" for="article-views">
                    <Translate contentKey="cmServiceApp.article.views">Views</Translate>
                  </Label>
                  <AvField id="article-views" type="string" className="form-control" name="views" />
                </AvGroup>
              </Col>
            ) : null}
            {!isNew ? (
              <Col md="3">
                <AvGroup>
                  <Label id="likeNumberLabel" for="article-likeNumber">
                    <Translate contentKey="cmServiceApp.article.likeNumber">Like Number</Translate>
                  </Label>
                  <AvField id="article-likeNumber" type="string" className="form-control" name="likeNumber" />
                </AvGroup>
              </Col>
            ) : null}
            <Col md="3"/>
            <Col md="8">
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
            </Col>
            <Col md="3">
              <AvGroup>
                <Label id="authorLabel" for="article-author">
                  <Translate contentKey="cmServiceApp.article.author">Author</Translate>
                </Label>
                <AvField
                  id="article-author"
                  type="text"
                  name="author"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
            </Col>
            <Col md="12">
              <Button className="content-article-management-edit-enclosure" tag={Link} to={`${match.url}/enclosure`} color="success" size="sm">
                  <span className="d-none d-md-inline">
                    <Translate contentKey="cmServiceApp.articleEnclosure.detail.title">Title</Translate>
                  </span>
              </Button>
            </Col>
            <Col md="12">
              <BraftEditor
                value={editorState}
                onChange={handleEditorChange}
              />
            </Col>
            {!warning ? (
              <Col md="12" className="content-article-management-edit-warning">
                <Translate contentKey="cmServiceApp.article.warning">Warning</Translate>
              </Col>
            ) : null}
            <Col md="12">
              <AvGroup>
                <Label id="noteLabel" for="article-note">
                  <Translate contentKey="cmServiceApp.article.note">Note</Translate>
                </Label>
                <AvField id="article-note" type="textarea" name="note" />
              </AvGroup>
            </Col>
            <AvGroup>
              <AvField id="article-createUser" type="hidden" name="createUser" />
              <AvField id="article-creatTime" type="hidden" name="creatTime" />
              <AvField id="article-userLink" type="hidden" name="userLinkId" />
            </AvGroup>
            <Col md="12">
              <Button tag={Link} id="cancel-save" to="/system/article-management" replace color="info">
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
  updateUserEntity,
  setBlob,
  createUserEntity,
  reset,
  updateArticleFile,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArticleManagementUpdate);
