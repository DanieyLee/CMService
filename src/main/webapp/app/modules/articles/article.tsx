import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import {
  byteSize,
  Translate,
  ICrudGetAllAction,
  TextFormat,
  getSortState,
  IPaginationBaseState,
  JhiPagination,
  JhiItemCount,
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getPublicEntities } from 'app/entities/article/article.reducer';
import { IArticle } from 'app/shared/model/article.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IArticleProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Article = (props: IArticleProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getPublicEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const { articleList, match, loading, totalItems } = props;
  return (
    <div className="article-home-div">
      <div className="table-responsive">
        {articleList && articleList.length > 0 ? (
            <div>
              <div className="title-content-text-div">
                <h3>
                  <Translate contentKey="cmServiceApp.article.public.title">Title</Translate>
                </h3>
                <div>
                  <Translate contentKey="cmServiceApp.article.public.content.one">Content One</Translate>
                  <a><Translate contentKey="cmServiceApp.article.public.sign.one">Sign One</Translate></a>
                </div>
                <div>
                  <Translate contentKey="cmServiceApp.article.public.content.two">Content Two</Translate>
                  <a><Translate contentKey="cmServiceApp.article.public.sign.two">Sign Two</Translate></a>
                  <Translate contentKey="cmServiceApp.article.public.content.three">Content Three</Translate>
                  <a><Translate contentKey="cmServiceApp.article.public.sign.three">Sign Three</Translate></a>
                  <Translate contentKey="cmServiceApp.article.public.content.four">Content Four</Translate>
                </div>
              </div>
              {articleList.map((article, i) => (
                <div key={`entity-${i}`}>
                  <div className="table-article-list-div-home">
                    <div className="table-article-div-left-top">
                      <Button tag={Link} to={`${match.url}/${article.id}`} color="link" size="sm">
                        <span>{article.title}</span>
                      </Button>
                    </div>
                    <div className="table-article-div-right-top">
                      {article.articleTypeType ? article.articleTypeType : ''}
                    </div>
                    <div className="content-separator-list">
                    <br />
                    </div>
                    <div className="table-article-div-left-down">
                      <img src="content/images/author.svg" alt="author" />
                      <div className="table-article-div-left-down-up">
                        {article.author}
                      </div>
                      <div className="table-article-div-left-down-down">
                        <Button tag={Link} to={`${match.url}/${article.id}`} color="link" size="sm">
                          <span>{article.content}</span>
                        </Button>
                      </div>
                    </div>
                    <div className="table-article-div-right-down">
                      <img src="content/images/look.svg" alt="look" />
                      <span>{article.views > 1000 ? article.views.toString().substring(0,article.views.toString().length-3) + "k" : article.views}</span>
                      <img src="content/images/heart.svg" alt="heart" />
                      <span>{article.likeNumber > 1000? article.likeNumber.toString().substring(0,article.likeNumber.toString().length-3) + "k" : article.likeNumber}</span>
                    </div>
                  </div>
                </div>
              ))}
            </div>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="cmServiceApp.article.home.notFound">No Articles found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={articleList && articleList.length > 0 ? '' : 'd-none'}>
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
    </div>
  );
};

const mapStateToProps = ({ article }: IRootState) => ({
  articleList: article.entities,
  loading: article.loading,
  totalItems: article.totalItems,
});

const mapDispatchToProps = {
  getPublicEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Article);
