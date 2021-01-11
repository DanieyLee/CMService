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
import { getEntity, updateEntity, createEntity, reset } from './articl-enclosure.reducer';
import { IArticlEnclosure } from 'app/shared/model/articl-enclosure.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IArticlEnclosureUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArticlEnclosureUpdate = (props: IArticlEnclosureUpdateProps) => {
  const [articleId, setArticleId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { articlEnclosureEntity, articles, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/articl-enclosure');
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
        ...articlEnclosureEntity,
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
          <h2 id="cmServiceApp.articlEnclosure.home.createOrEditLabel">
            <Translate contentKey="cmServiceApp.articlEnclosure.home.createOrEditLabel">Create or edit a ArticlEnclosure</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : articlEnclosureEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="articl-enclosure-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="articl-enclosure-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="enclosureURLLabel" for="articl-enclosure-enclosureURL">
                  <Translate contentKey="cmServiceApp.articlEnclosure.enclosureURL">Enclosure URL</Translate>
                </Label>
                <AvField id="articl-enclosure-enclosureURL" type="text" name="enclosureURL" />
              </AvGroup>
              <AvGroup>
                <Label id="enclosureTypeLabel" for="articl-enclosure-enclosureType">
                  <Translate contentKey="cmServiceApp.articlEnclosure.enclosureType">Enclosure Type</Translate>
                </Label>
                <AvInput
                  id="articl-enclosure-enclosureType"
                  type="select"
                  className="form-control"
                  name="enclosureType"
                  value={(!isNew && articlEnclosureEntity.enclosureType) || 'IMAGE'}
                >
                  <option value="IMAGE">{translate('cmServiceApp.FileType.IMAGE')}</option>
                  <option value="VIDEO">{translate('cmServiceApp.FileType.VIDEO')}</option>
                  <option value="AUDIO">{translate('cmServiceApp.FileType.AUDIO')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="createUserLabel" for="articl-enclosure-createUser">
                  <Translate contentKey="cmServiceApp.articlEnclosure.createUser">Create User</Translate>
                </Label>
                <AvField id="articl-enclosure-createUser" type="text" name="createUser" />
              </AvGroup>
              <AvGroup>
                <Label id="creatTimeLabel" for="articl-enclosure-creatTime">
                  <Translate contentKey="cmServiceApp.articlEnclosure.creatTime">Creat Time</Translate>
                </Label>
                <AvInput
                  id="articl-enclosure-creatTime"
                  type="datetime-local"
                  className="form-control"
                  name="creatTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.articlEnclosureEntity.creatTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updateUserLabel" for="articl-enclosure-updateUser">
                  <Translate contentKey="cmServiceApp.articlEnclosure.updateUser">Update User</Translate>
                </Label>
                <AvField id="articl-enclosure-updateUser" type="text" name="updateUser" />
              </AvGroup>
              <AvGroup>
                <Label id="updateTimeLabel" for="articl-enclosure-updateTime">
                  <Translate contentKey="cmServiceApp.articlEnclosure.updateTime">Update Time</Translate>
                </Label>
                <AvInput
                  id="articl-enclosure-updateTime"
                  type="datetime-local"
                  className="form-control"
                  name="updateTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.articlEnclosureEntity.updateTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="articl-enclosure-note">
                  <Translate contentKey="cmServiceApp.articlEnclosure.note">Note</Translate>
                </Label>
                <AvField id="articl-enclosure-note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label for="articl-enclosure-article">
                  <Translate contentKey="cmServiceApp.articlEnclosure.article">Article</Translate>
                </Label>
                <AvInput id="articl-enclosure-article" type="select" className="form-control" name="articleId">
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
              <Button tag={Link} id="cancel-save" to="/articl-enclosure" replace color="info">
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
  articlEnclosureEntity: storeState.articlEnclosure.entity,
  loading: storeState.articlEnclosure.loading,
  updating: storeState.articlEnclosure.updating,
  updateSuccess: storeState.articlEnclosure.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(ArticlEnclosureUpdate);
