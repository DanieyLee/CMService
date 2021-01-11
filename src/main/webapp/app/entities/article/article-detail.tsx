import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './article.reducer';
import { IArticle } from 'app/shared/model/article.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IArticleDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArticleDetail = (props: IArticleDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { articleEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cmServiceApp.article.detail.title">Article</Translate> [<b>{articleEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="title">
              <Translate contentKey="cmServiceApp.article.title">Title</Translate>
            </span>
          </dt>
          <dd>{articleEntity.title}</dd>
          <dt>
            <span id="author">
              <Translate contentKey="cmServiceApp.article.author">Author</Translate>
            </span>
          </dt>
          <dd>{articleEntity.author}</dd>
          <dt>
            <span id="content">
              <Translate contentKey="cmServiceApp.article.content">Content</Translate>
            </span>
          </dt>
          <dd>{articleEntity.content}</dd>
          <dt>
            <span id="views">
              <Translate contentKey="cmServiceApp.article.views">Views</Translate>
            </span>
          </dt>
          <dd>{articleEntity.views}</dd>
          <dt>
            <span id="likeNumber">
              <Translate contentKey="cmServiceApp.article.likeNumber">Like Number</Translate>
            </span>
          </dt>
          <dd>{articleEntity.likeNumber}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="cmServiceApp.article.state">State</Translate>
            </span>
          </dt>
          <dd>{articleEntity.state ? 'true' : 'false'}</dd>
          <dt>
            <span id="createUser">
              <Translate contentKey="cmServiceApp.article.createUser">Create User</Translate>
            </span>
          </dt>
          <dd>{articleEntity.createUser}</dd>
          <dt>
            <span id="creatTime">
              <Translate contentKey="cmServiceApp.article.creatTime">Creat Time</Translate>
            </span>
          </dt>
          <dd>{articleEntity.creatTime ? <TextFormat value={articleEntity.creatTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updateUser">
              <Translate contentKey="cmServiceApp.article.updateUser">Update User</Translate>
            </span>
          </dt>
          <dd>{articleEntity.updateUser}</dd>
          <dt>
            <span id="updateTime">
              <Translate contentKey="cmServiceApp.article.updateTime">Update Time</Translate>
            </span>
          </dt>
          <dd>{articleEntity.updateTime ? <TextFormat value={articleEntity.updateTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="note">
              <Translate contentKey="cmServiceApp.article.note">Note</Translate>
            </span>
          </dt>
          <dd>{articleEntity.note}</dd>
          <dt>
            <Translate contentKey="cmServiceApp.article.articleType">Article Type</Translate>
          </dt>
          <dd>{articleEntity.articleTypeType ? articleEntity.articleTypeType : ''}</dd>
          <dt>
            <Translate contentKey="cmServiceApp.article.userLink">User Link</Translate>
          </dt>
          <dd>{articleEntity.userLinkFirstName ? articleEntity.userLinkFirstName : ''}</dd>
        </dl>
        <Button tag={Link} to="/article" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/article/${articleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ article }: IRootState) => ({
  articleEntity: article.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArticleDetail);
