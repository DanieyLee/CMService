import './article-list.scss';

import { NavItem, NavLink, NavbarBrand } from 'reactstrap';
import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row } from 'reactstrap';
import {
  Translate,
  getSortState,
  JhiPagination,
} from 'react-jhipster';

import { IRootState } from 'app/shared/reducers';
import { getTopPublicEntities } from 'app/entities/article/article.reducer';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IArticleProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Article = (props: IArticleProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getTopPublicEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
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
    <div className="article-home-top home-box-background-color">
      <div className="article-home-top-height">
        {articleList && articleList.length > 0 ? (
            <div>
              <div className="article-top-title">
                <p>
                  <Translate contentKey="home.article">Title</Translate>
                </p>
              </div>
              {articleList.map((article, i) => (
                <div key={`entity-${i}`}>
                  <div className="article-list-div-title">
                    <Button tag={Link} to={`/articles/${article.id}`} color="link" size="sm">
                      <span>{article.title}</span>
                    </Button>
                  </div>
                  <div className="article-list-div-content">
                    <Button tag={Link} to={`/articles/${article.id}`} color="link" size="sm">
                      <div className="home-article-list-div-content">
                        <span dangerouslySetInnerHTML = {{ __html: article.content }} />
                      </div>
                    </Button>
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
      <hr className="home-box-top-hr"/>
      <NavLink tag={Link} to="/articles" className="home-box-top-hr-link">
        <Translate contentKey="home.more">More</Translate>
      </NavLink>
    </div>
  );
};

const mapStateToProps = ({ article }: IRootState) => ({
  articleList: article.entities,
  loading: article.loading,
  totalItems: article.totalItems,
});

const mapDispatchToProps = {
  getTopPublicEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Article);
