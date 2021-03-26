import { NavLink } from 'reactstrap';
import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button } from 'reactstrap';
import {
  Translate,
  getSortState,
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

  const { articleList, loading } = props;
  return (
    <div className="content-home-article">
      <div className="content-home-article-list">
        {articleList && articleList.length > 0 ? (
            <div>
              <div className="content-home-article-list-title">
                <p>
                  <Translate contentKey="home.article">Title</Translate>
                </p>
              </div>
              {articleList.map((article, i) => (
                <div key={`entity-${i}`}>
                  <div className="content-home-article-list-name">
                    <Button tag={Link} to={`/articles/detail/${article.id}`} color="link" size="sm">
                      <span>{article.title}</span>
                    </Button>
                  </div>
                  <div className="content-home-article-list-content">
                    <Button tag={Link} to={`/articles/detail/${article.id}`} color="link" size="sm">
                      <div dangerouslySetInnerHTML = {{ __html: article.content.length > 5000 ? article.content.substr(0,5000) : article.content }} />
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
      <hr/>
      <NavLink tag={Link} to="/articles/0">
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
