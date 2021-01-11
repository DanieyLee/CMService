import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './article-comment.reducer';
import { IArticleComment } from 'app/shared/model/article-comment.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IArticleCommentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArticleCommentDetail = (props: IArticleCommentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { articleCommentEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cmServiceApp.articleComment.detail.title">ArticleComment</Translate> [<b>{articleCommentEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="content">
              <Translate contentKey="cmServiceApp.articleComment.content">Content</Translate>
            </span>
          </dt>
          <dd>{articleCommentEntity.content}</dd>
          <dt>
            <span id="createUser">
              <Translate contentKey="cmServiceApp.articleComment.createUser">Create User</Translate>
            </span>
          </dt>
          <dd>{articleCommentEntity.createUser}</dd>
          <dt>
            <span id="creatTime">
              <Translate contentKey="cmServiceApp.articleComment.creatTime">Creat Time</Translate>
            </span>
          </dt>
          <dd>
            {articleCommentEntity.creatTime ? (
              <TextFormat value={articleCommentEntity.creatTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updateUser">
              <Translate contentKey="cmServiceApp.articleComment.updateUser">Update User</Translate>
            </span>
          </dt>
          <dd>{articleCommentEntity.updateUser}</dd>
          <dt>
            <span id="updateTime">
              <Translate contentKey="cmServiceApp.articleComment.updateTime">Update Time</Translate>
            </span>
          </dt>
          <dd>
            {articleCommentEntity.updateTime ? (
              <TextFormat value={articleCommentEntity.updateTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="note">
              <Translate contentKey="cmServiceApp.articleComment.note">Note</Translate>
            </span>
          </dt>
          <dd>{articleCommentEntity.note}</dd>
          <dt>
            <Translate contentKey="cmServiceApp.articleComment.article">Article</Translate>
          </dt>
          <dd>{articleCommentEntity.articleTitle ? articleCommentEntity.articleTitle : ''}</dd>
          <dt>
            <Translate contentKey="cmServiceApp.articleComment.userLink">User Link</Translate>
          </dt>
          <dd>{articleCommentEntity.userLinkFirstName ? articleCommentEntity.userLinkFirstName : ''}</dd>
        </dl>
        <Button tag={Link} to="/article-comment" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/article-comment/${articleCommentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ articleComment }: IRootState) => ({
  articleCommentEntity: articleComment.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArticleCommentDetail);
