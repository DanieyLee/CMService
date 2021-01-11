import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IArticle } from 'app/shared/model/article.model';
import { getEntities as getArticles } from 'app/entities/article/article.reducer';
import { IUserLink } from 'app/shared/model/user-link.model';
import { getEntities as getUserLinks } from 'app/entities/user-link/user-link.reducer';
import { getEntity, updateEntity, createEntity, reset } from './article-comment.reducer';
import { IArticleComment } from 'app/shared/model/article-comment.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IArticleCommentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArticleCommentUpdate = (props: IArticleCommentUpdateProps) => {
  const [articleId, setArticleId] = useState('0');
  const [userLinkId, setUserLinkId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { articleCommentEntity, articles, userLinks, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/article-comment' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getArticles();
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
        ...articleCommentEntity,
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
          <h2 id="cmServiceApp.articleComment.home.createOrEditLabel">
            <Translate contentKey="cmServiceApp.articleComment.home.createOrEditLabel">Create or edit a ArticleComment</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : articleCommentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="article-comment-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="article-comment-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="contentLabel" for="article-comment-content">
                  <Translate contentKey="cmServiceApp.articleComment.content">Content</Translate>
                </Label>
                <AvField id="article-comment-content" type="text" name="content" />
              </AvGroup>
              <AvGroup>
                <Label id="createUserLabel" for="article-comment-createUser">
                  <Translate contentKey="cmServiceApp.articleComment.createUser">Create User</Translate>
                </Label>
                <AvField id="article-comment-createUser" type="text" name="createUser" />
              </AvGroup>
              <AvGroup>
                <Label id="creatTimeLabel" for="article-comment-creatTime">
                  <Translate contentKey="cmServiceApp.articleComment.creatTime">Creat Time</Translate>
                </Label>
                <AvInput
                  id="article-comment-creatTime"
                  type="datetime-local"
                  className="form-control"
                  name="creatTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.articleCommentEntity.creatTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updateUserLabel" for="article-comment-updateUser">
                  <Translate contentKey="cmServiceApp.articleComment.updateUser">Update User</Translate>
                </Label>
                <AvField id="article-comment-updateUser" type="text" name="updateUser" />
              </AvGroup>
              <AvGroup>
                <Label id="updateTimeLabel" for="article-comment-updateTime">
                  <Translate contentKey="cmServiceApp.articleComment.updateTime">Update Time</Translate>
                </Label>
                <AvInput
                  id="article-comment-updateTime"
                  type="datetime-local"
                  className="form-control"
                  name="updateTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.articleCommentEntity.updateTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="article-comment-note">
                  <Translate contentKey="cmServiceApp.articleComment.note">Note</Translate>
                </Label>
                <AvField id="article-comment-note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label for="article-comment-article">
                  <Translate contentKey="cmServiceApp.articleComment.article">Article</Translate>
                </Label>
                <AvInput id="article-comment-article" type="select" className="form-control" name="articleId">
                  <option value="" key="0" />
                  {articles
                    ? articles.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.title}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="article-comment-userLink">
                  <Translate contentKey="cmServiceApp.articleComment.userLink">User Link</Translate>
                </Label>
                <AvInput id="article-comment-userLink" type="select" className="form-control" name="userLinkId">
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
              <Button tag={Link} id="cancel-save" to="/article-comment" replace color="info">
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
  articles: storeState.article.entities,
  userLinks: storeState.userLink.entities,
  articleCommentEntity: storeState.articleComment.entity,
  loading: storeState.articleComment.loading,
  updating: storeState.articleComment.updating,
  updateSuccess: storeState.articleComment.updateSuccess,
});

const mapDispatchToProps = {
  getArticles,
  getUserLinks,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArticleCommentUpdate);
