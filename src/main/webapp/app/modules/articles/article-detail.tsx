import './article-detail.scss';

import React, { useEffect, useState } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Table } from 'reactstrap';
import {
  Translate,
  ICrudGetAction,
  byteSize,
  TextFormat,
  getSortState,
  JhiItemCount,
  JhiPagination
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getPublicEntity } from 'app/entities/article/article.reducer';
import { getPublicArticleEntities } from 'app/entities/article-comment/article-comment.reducer'
import { IArticle } from 'app/shared/model/article.model';
import { APP_DATE_FORMAT_SIMPLE_ZH_CN, APP_DATE_FORMAT_SIMPLE_ZH, APP_DATE_FORMAT } from 'app/config/constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IArticleDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArticleDetail = (props: IArticleDetailProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  useEffect(() => {
    props.getPublicEntity(props.match.params.id);
    props.getPublicArticleEntities(props.match.params.id,paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  }, []);

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const { articleEntity, articleCommentList, match, loading, totalItems } = props;
  return (
    <Row>
      <Col md="12">
        <dl className="jh-entity-details">
          <div className="jh-entity-details-title">
            <span>{articleEntity.articleTypeType}</span>
            {articleEntity.title}
          </div>
          <div className="jh-entity-details-title-date">
            <TextFormat value={articleEntity.creatTime} type="date" format={APP_DATE_FORMAT_SIMPLE_ZH} />
            <img src="content/images/author.svg" alt="author" />
            {articleEntity.author}
          </div>
          <div className="jh-entity-details-content" dangerouslySetInnerHTML = {{ __html: articleEntity.content }} />
          <hr className="jh-entity-details-content-hr" />
          <div className="jh-entity-table-article-div">
            <div className="jh-entity-table-article-div-left">
              <Translate contentKey="cmServiceApp.article.modify.title" interpolate={{ name: articleEntity.updateUser,time:'' }}>
                Are you sure you want to delete this Article?
              </Translate>
              <TextFormat value={articleEntity.updateTime} type="date" format={APP_DATE_FORMAT_SIMPLE_ZH_CN} />
              <span className="d-none d-md-inline">
                <Translate contentKey="cmServiceApp.article.modify.tail">name</Translate>
              </span>
            </div>
            <div className="jh-entity-table-article-div-right">
              <FontAwesomeIcon icon={'eye'} />
              <span>{articleEntity.views > 1000 ? articleEntity.views.toString().substring(0,articleEntity.views.toString().length-3) + "k" : articleEntity.views}</span>
              <FontAwesomeIcon icon={'heart'} />
              <span>{articleEntity.likeNumber > 1000? articleEntity.likeNumber.toString().substring(0,articleEntity.likeNumber.toString().length-3) + "k" : articleEntity.likeNumber}</span>
            </div>
          </div>
          <div dangerouslySetInnerHTML = {{ __html: articleEntity.note }} />
          <hr className="jh-entity-details-content-hr" />



          <div className="table-responsive">
            {articleCommentList && articleCommentList.length > 0 ? (
              <div>
                {articleCommentList.map((articleComment, i) => (
                  <div key={`entity-${i}`} className="jh-entity-content-comment-div">
                    <div className="jh-entity-content-comment-div-user">
                      {articleComment.createUser}
                      {articleComment.creatTime ? <TextFormat type="date" value={articleComment.creatTime} format={APP_DATE_FORMAT_SIMPLE_ZH_CN} /> : null}
                    </div>
                    <div className="jh-entity-content-comment-div-content">
                      {articleComment.content}
                    </div>
                    <hr className="jh-entity-details-content-hr" />
                  </div>
                ))}
              </div>
            ) : (
              !loading && (
                <Translate contentKey="cmServiceApp.articleComment.home.notFound">No Article Comments found</Translate>
              )
            )}
          </div>
          {props.totalItems ? (
            <div className={articleCommentList && articleCommentList.length > 0 ? '' : 'd-none'}>
              <Row className="justify-content-center">
                <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
              </Row>
              <Row className="justify-content-center">
                <JhiPagination
                  activePage={paginationState.activePage}
                  onSelect={handlePagination}
                  maxButtons={5}
                  itemsPerPage={paginationState.itemsPerPage}
                  totalItems={props.totalItems}
                />
              </Row>
            </div>
          ) : (
            ''
          )}

        </dl>



        <Button tag={Link} to="/articles" replace color="info">
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>

      </Col>
    </Row>
  );
};

const mapStateToProps = ({ article, articleComment }: IRootState) => ({
  articleEntity: article.entity,
  articleCommentList: articleComment.entities,
  loading: articleComment.loading,
  totalItems: articleComment.totalItems,
});

const mapDispatchToProps = {
  getPublicEntity,
  getPublicArticleEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArticleDetail);
