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
import { getEntity, updateEntity, createEntity, reset } from './article-enclosure.reducer';
import { IArticleEnclosure } from 'app/shared/model/article-enclosure.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IArticleEnclosureUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArticleEnclosureUpdate = (props: IArticleEnclosureUpdateProps) => {
  const [articleId, setArticleId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { articleEnclosureEntity, articles, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/article-enclosure');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getArticles();
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
        ...articleEnclosureEntity,
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
          <h2 id="cmServiceApp.articleEnclosure.home.createOrEditLabel">
            <Translate contentKey="cmServiceApp.articleEnclosure.home.createOrEditLabel">Create or edit a articleEnclosure</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : articleEnclosureEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="article-enclosure-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="article-enclosure-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="enclosureURLLabel" for="article-enclosure-enclosureURL">
                  <Translate contentKey="cmServiceApp.articleEnclosure.enclosureURL">Enclosure URL</Translate>
                </Label>
                <AvField id="article-enclosure-enclosureURL" type="text" name="enclosureURL" />
              </AvGroup>
              <AvGroup>
                <Label id="enclosureTypeLabel" for="article-enclosure-enclosureType">
                  <Translate contentKey="cmServiceApp.articleEnclosure.enclosureType">Enclosure Type</Translate>
                </Label>
                <AvInput
                  id="article-enclosure-enclosureType"
                  type="select"
                  className="form-control"
                  name="enclosureType"
                  value={(!isNew && articleEnclosureEntity.enclosureType) || 'IMAGE'}
                >
                  <option value="IMAGE">{translate('cmServiceApp.FileType.IMAGE')}</option>
                  <option value="VIDEO">{translate('cmServiceApp.FileType.VIDEO')}</option>
                  <option value="AUDIO">{translate('cmServiceApp.FileType.AUDIO')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="createUserLabel" for="article-enclosure-createUser">
                  <Translate contentKey="cmServiceApp.articleEnclosure.createUser">Create User</Translate>
                </Label>
                <AvField id="article-enclosure-createUser" type="text" name="createUser" />
              </AvGroup>
              <AvGroup>
                <Label id="creatTimeLabel" for="article-enclosure-creatTime">
                  <Translate contentKey="cmServiceApp.articleEnclosure.creatTime">Creat Time</Translate>
                </Label>
                <AvInput
                  id="article-enclosure-creatTime"
                  type="datetime-local"
                  className="form-control"
                  name="creatTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.articleEnclosureEntity.creatTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updateUserLabel" for="article-enclosure-updateUser">
                  <Translate contentKey="cmServiceApp.articleEnclosure.updateUser">Update User</Translate>
                </Label>
                <AvField id="article-enclosure-updateUser" type="text" name="updateUser" />
              </AvGroup>
              <AvGroup>
                <Label id="updateTimeLabel" for="article-enclosure-updateTime">
                  <Translate contentKey="cmServiceApp.articleEnclosure.updateTime">Update Time</Translate>
                </Label>
                <AvInput
                  id="article-enclosure-updateTime"
                  type="datetime-local"
                  className="form-control"
                  name="updateTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.articleEnclosureEntity.updateTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="article-enclosure-note">
                  <Translate contentKey="cmServiceApp.articleEnclosure.note">Note</Translate>
                </Label>
                <AvField id="article-enclosure-note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label for="article-enclosure-article">
                  <Translate contentKey="cmServiceApp.articleEnclosure.article">Article</Translate>
                </Label>
                <AvInput id="article-enclosure-article" type="select" className="form-control" name="articleId">
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
              <Button tag={Link} id="cancel-save" to="/article-enclosure" replace color="info">
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
  articleEnclosureEntity: storeState.articleEnclosure.entity,
  loading: storeState.articleEnclosure.loading,
  updating: storeState.articleEnclosure.updating,
  updateSuccess: storeState.articleEnclosure.updateSuccess,
});

const mapDispatchToProps = {
  getArticles,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArticleEnclosureUpdate);
