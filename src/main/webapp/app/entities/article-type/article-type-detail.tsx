import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './article-type.reducer';
import { IArticleType } from 'app/shared/model/article-type.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IArticleTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArticleTypeDetail = (props: IArticleTypeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { articleTypeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cmServiceApp.articleType.detail.title">ArticleType</Translate> [<b>{articleTypeEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="type">
              <Translate contentKey="cmServiceApp.articleType.type">Type</Translate>
            </span>
          </dt>
          <dd>{articleTypeEntity.type}</dd>
          <dt>
            <span id="createUser">
              <Translate contentKey="cmServiceApp.articleType.createUser">Create User</Translate>
            </span>
          </dt>
          <dd>{articleTypeEntity.createUser}</dd>
          <dt>
            <span id="creatTime">
              <Translate contentKey="cmServiceApp.articleType.creatTime">Creat Time</Translate>
            </span>
          </dt>
          <dd>
            {articleTypeEntity.creatTime ? <TextFormat value={articleTypeEntity.creatTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updateUser">
              <Translate contentKey="cmServiceApp.articleType.updateUser">Update User</Translate>
            </span>
          </dt>
          <dd>{articleTypeEntity.updateUser}</dd>
          <dt>
            <span id="updateTime">
              <Translate contentKey="cmServiceApp.articleType.updateTime">Update Time</Translate>
            </span>
          </dt>
          <dd>
            {articleTypeEntity.updateTime ? <TextFormat value={articleTypeEntity.updateTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="note">
              <Translate contentKey="cmServiceApp.articleType.note">Note</Translate>
            </span>
          </dt>
          <dd>{articleTypeEntity.note}</dd>
        </dl>
        <Button tag={Link} to="/article-type" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/article-type/${articleTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ articleType }: IRootState) => ({
  articleTypeEntity: articleType.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArticleTypeDetail);
