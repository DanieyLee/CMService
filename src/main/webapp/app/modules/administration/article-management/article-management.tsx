import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Table } from 'reactstrap';
import {
  Translate,
  TextFormat,
  getSortState,
  JhiItemCount,
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from 'app/entities/article/article.reducer';
import { APP_DATE_FORMAT_SIMPLE_ZH_CN } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { Page } from 'app/shared/layout/page/page';

export interface IArticleManagementProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ArticleManagement = (props: IArticleManagementProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
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
    <div className="content-article-management">
      <h2 id="article-heading">
        <Translate contentKey="cmServiceApp.article.home.title">Articles</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="cmServiceApp.article.home.createLabel">Create new Article</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {articleList && articleList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('title')}>
                  <Translate contentKey="cmServiceApp.article.title">Title</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('author')}>
                  <Translate contentKey="cmServiceApp.article.author">Author</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('content')}>
                  <Translate contentKey="cmServiceApp.article.content">Content</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('views')}>
                  <Translate contentKey="cmServiceApp.article.views">Views</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('likeNumber')}>
                  <Translate contentKey="cmServiceApp.article.likeNumber">Like Number</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('state')}>
                  <Translate contentKey="cmServiceApp.article.state">State</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createUser')}>
                  <Translate contentKey="cmServiceApp.article.createUser">Create User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('creatTime')}>
                  <Translate contentKey="cmServiceApp.article.creatTime">Creat Time</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updateUser')}>
                  <Translate contentKey="cmServiceApp.article.updateUser">Update User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updateTime')}>
                  <Translate contentKey="cmServiceApp.article.updateTime">Update Time</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('note')}>
                  <Translate contentKey="cmServiceApp.article.note">Note</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="cmServiceApp.article.articleType">Article Type</Translate>
                </th>
                <th>
                  <Translate contentKey="cmServiceApp.article.userLink">User Link</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {articleList.map((article, i) => (
                <tr key={`entity-${i}`}>
                  <td>{article.id}</td>
                  <td>{article.title.length > 10 ? article.title.substr(0,10) : article.title}</td>
                  <td>{article.author}</td>
                  <td>{article.content.length > 10 ? article.content.substr(0,10) : article.content}</td>
                  <td>{article.views}</td>
                  <td>{article.likeNumber}</td>
                  <td>{article.state ? 'true' : 'false'}</td>
                  <td>{article.createUser}</td>
                  <td>{article.creatTime ? <TextFormat type="date" value={article.creatTime} format={APP_DATE_FORMAT_SIMPLE_ZH_CN} /> : null}</td>
                  <td>{article.updateUser}</td>
                  <td>{article.updateTime ? <TextFormat type="date" value={article.updateTime} format={APP_DATE_FORMAT_SIMPLE_ZH_CN} /> : null}</td>
                  <td>{article.note ? article.note.length > 10 ? article.note.substr(0,10) : article.note : null }</td>
                  {article.articleTypeType ? <td>{article.articleTypeType}</td> : ''}
                  {article.userLinkFirstName ? <td>{article.userLinkFirstName}</td> : ''}
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${article.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${article.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
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
            <Page
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
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArticleManagement);