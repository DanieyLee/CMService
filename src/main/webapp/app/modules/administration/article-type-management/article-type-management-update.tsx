import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateUserEntity, createUserEntity, reset } from 'app/entities/article-type/article-type.reducer';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';

export interface IArticleTypeManagementUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArticleTypeManagementUpdate = (props: IArticleTypeManagementUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { articleTypeEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/admin/article-type-management');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }
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
        ...articleTypeEntity,
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
    <div className="content-article-type-management-edit">
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="cmServiceApp.articleType.home.createOrEditLabel">
            <Translate contentKey="cmServiceApp.articleType.home.createOrEditLabel">Create or edit a ArticleType</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : articleTypeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="article-type-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="article-type-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="typeLabel" for="article-type-type">
                  <Translate contentKey="cmServiceApp.articleType.type">Type</Translate>
                </Label>
                <AvField
                  id="article-type-type"
                  type="text"
                  name="type"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <AvField id="article-type-createUser" type="hidden" name="createUser" />
              </AvGroup>
              <AvGroup>
                <AvInput
                  id="article-type-creatTime"
                  type="hidden"
                  className="form-control"
                  name="creatTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.articleTypeEntity.creatTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="article-type-note">
                  <Translate contentKey="cmServiceApp.articleType.note">Note</Translate>
                </Label>
                <AvField id="article-type-note" type="text" name="note" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/admin/article-type-management" replace color="info">
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
  articleTypeEntity: storeState.articleType.entity,
  loading: storeState.articleType.loading,
  updating: storeState.articleType.updating,
  updateSuccess: storeState.articleType.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateUserEntity,
  createUserEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArticleTypeManagementUpdate);
