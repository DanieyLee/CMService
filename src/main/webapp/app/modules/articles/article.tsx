import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import {
  Translate,
  getSortState
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getPublicTypeEntities } from 'app/entities/article/article.reducer';
import { getArticleTypeEntities } from 'app/entities/article-type/article-type.reducer'
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { Page } from "app/shared/layout/page/page";

export interface IArticleProps extends StateProps, DispatchProps, RouteComponentProps<{ type: string }> {}

export const Article = (props: IArticleProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );
  const getAllEntities = () => {
    props.getArticleTypeEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
    props.getPublicTypeEntities(props.match.params.type, paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
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

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const turnPage = id => () =>
    props.getPublicTypeEntities(id, paginationState.activePage - 1, paginationState.itemsPerPage,
      `${paginationState.sort},${paginationState.order}`);

  const clearLabel = str => {
    return str.replace(/<[^>]+>/g,"");
  }

  const { articleList, articleTypeList, loading } = props;
  return (
    <div className="content-article">
      {articleList && articleList.length > 0 ? (
        <div>
          <div className="content-article-title">
            <h3>
              <Translate contentKey="cmServiceApp.article.public.title">Title</Translate>
            </h3>
            <h4>
              <Translate contentKey="cmServiceApp.article.public.content.one">Content One</Translate>
              <a><Translate contentKey="cmServiceApp.article.public.sign.one">Sign One</Translate></a>
            </h4>
            <h5>
              <Translate contentKey="cmServiceApp.article.public.content.two">Content Two</Translate>
              <a><Translate contentKey="cmServiceApp.article.public.sign.two">Sign Two</Translate></a>
              <Translate contentKey="cmServiceApp.article.public.content.three">Content Three</Translate>
              <a><Translate contentKey="cmServiceApp.article.public.sign.three">Sign Three</Translate></a>
              <Translate contentKey="cmServiceApp.article.public.content.four">Content Four</Translate>
            </h5>
          </div>
          {articleTypeList && articleTypeList.length > 0 ? (
            <div className="content-article-type">
              <Button onClick={turnPage(0)}>
                <Translate contentKey="cmServiceApp.article.public.title">Title</Translate>
              </Button>
              {articleTypeList.map((articleType, i) => (
                <Button key={`entity-${i}`} onClick={turnPage(articleType.id)}>
                  {articleType.type}
                </Button>
              ))}
            </div>
          ) : (
            ""
          )}
            {articleList.map((article, i) => (
              <Row key={`entity-${i}`}>
                <Col md="9">
                  <h6>
                    {article.articleTypeType == null ? "无" : article.articleTypeType}
                  </h6>
                  <Button tag={Link} to={`/articles/detail/${article.id}`} color="link" size="sm">
                    <span>{article.title}</span>
                  </Button>
                </Col>
                <Col md="3">
                  <FontAwesomeIcon icon={'eye'} />
                  <span>{article.views > 1000 ? article.views.toString().substring(0,article.views.toString().length-3) + "k" : article.views}</span>
                  <FontAwesomeIcon icon={'heart'} />
                  <span>{article.likeNumber > 1000? article.likeNumber.toString().substring(0,article.likeNumber.toString().length-3) + "k" : article.likeNumber}</span>
                </Col>
                <Row>
                  <Col md="1">
                    <img src="content/images/author.svg" alt="author" />
                    <div>{article.author}</div>
                  </Col>
                  <Col md="11">
                    <Button tag={Link} to={`/articles/detail/${article.id}`} color="link" size="sm">
                      <div dangerouslySetInnerHTML = {{ __html: clearLabel(article.content) }} />
                    </Button>
                  </Col>
                </Row>
              </Row>
            ))}
        </div>
      ) : (
        !loading && (
          <div className="alert alert-warning">
            <Translate contentKey="cmServiceApp.article.home.notFound">No Articles found</Translate>
          </div>
        )
      )}
      {props.totalItems ? (
        <div className={articleList && articleList.length > 0 ? '' : 'd-none'}>
          <Page
            activePage={paginationState.activePage}
            onSelect={handlePagination}
            maxButtons={5}
            itemsPerPage={paginationState.itemsPerPage}
            totalItems={props.totalItems}
          />
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ article, articleType }: IRootState) => ({
  articleList: article.entities,
  articleTypeList: articleType.entities,
  loading: article.loading,
  totalItems: article.totalItems,
});

const mapDispatchToProps = {
  getPublicTypeEntities,
  getArticleTypeEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Article);
