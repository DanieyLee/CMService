import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './article-enclosure.reducer';
import { IArticleEnclosure } from 'app/shared/model/article-enclosure.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IArticleEnclosureDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArticleEnclosureDetail = (props: IArticleEnclosureDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { articleEnclosureEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cmServiceApp.articleEnclosure.detail.title">articleEnclosure</Translate> [<b>{articleEnclosureEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="enclosureURL">
              <Translate contentKey="cmServiceApp.articleEnclosure.enclosureURL">Enclosure URL</Translate>
            </span>
          </dt>
          <dd>{articleEnclosureEntity.enclosureURL}</dd>
          <dt>
            <span id="enclosureType">
              <Translate contentKey="cmServiceApp.articleEnclosure.enclosureType">Enclosure Type</Translate>
            </span>
          </dt>
          <dd>{articleEnclosureEntity.enclosureType}</dd>
          <dt>
            <span id="createUser">
              <Translate contentKey="cmServiceApp.articleEnclosure.createUser">Create User</Translate>
            </span>
          </dt>
          <dd>{articleEnclosureEntity.createUser}</dd>
          <dt>
            <span id="creatTime">
              <Translate contentKey="cmServiceApp.articleEnclosure.creatTime">Creat Time</Translate>
            </span>
          </dt>
          <dd>
            {articleEnclosureEntity.creatTime ? (
              <TextFormat value={articleEnclosureEntity.creatTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updateUser">
              <Translate contentKey="cmServiceApp.articleEnclosure.updateUser">Update User</Translate>
            </span>
          </dt>
          <dd>{articleEnclosureEntity.updateUser}</dd>
          <dt>
            <span id="updateTime">
              <Translate contentKey="cmServiceApp.articleEnclosure.updateTime">Update Time</Translate>
            </span>
          </dt>
          <dd>
            {articleEnclosureEntity.updateTime ? (
              <TextFormat value={articleEnclosureEntity.updateTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="note">
              <Translate contentKey="cmServiceApp.articleEnclosure.note">Note</Translate>
            </span>
          </dt>
          <dd>{articleEnclosureEntity.note}</dd>
          <dt>
            <Translate contentKey="cmServiceApp.articleEnclosure.article">Article</Translate>
          </dt>
          <dd>{articleEnclosureEntity.articleTitle ? articleEnclosureEntity.articleTitle : ''}</dd>
        </dl>
        <Button tag={Link} to="/article-enclosure" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/article-enclosure/${articleEnclosureEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ articleEnclosure }: IRootState) => ({
  articleEnclosureEntity: articleEnclosure.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArticleEnclosureDetail);
