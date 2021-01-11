import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, reset } from './articl-enclosure.reducer';
import { IArticlEnclosure } from 'app/shared/model/articl-enclosure.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IArticlEnclosureProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ArticlEnclosure = (props: IArticlEnclosureProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const resetAll = () => {
    props.reset();
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    props.getEntities();
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      resetAll();
    }
  }, [props.updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    props.reset();
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
    setSorting(true);
  };

  const { articlEnclosureList, match, loading } = props;
  return (
    <div>
      <h2 id="articl-enclosure-heading">
        <Translate contentKey="cmServiceApp.articlEnclosure.home.title">Articl Enclosures</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="cmServiceApp.articlEnclosure.home.createLabel">Create new Articl Enclosure</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          pageStart={paginationState.activePage}
          loadMore={handleLoadMore}
          hasMore={paginationState.activePage - 1 < props.links.next}
          loader={<div className="loader">Loading ...</div>}
          threshold={0}
          initialLoad={false}
        >
          {articlEnclosureList && articlEnclosureList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('enclosureURL')}>
                    <Translate contentKey="cmServiceApp.articlEnclosure.enclosureURL">Enclosure URL</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('enclosureType')}>
                    <Translate contentKey="cmServiceApp.articlEnclosure.enclosureType">Enclosure Type</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createUser')}>
                    <Translate contentKey="cmServiceApp.articlEnclosure.createUser">Create User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('creatTime')}>
                    <Translate contentKey="cmServiceApp.articlEnclosure.creatTime">Creat Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updateUser')}>
                    <Translate contentKey="cmServiceApp.articlEnclosure.updateUser">Update User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updateTime')}>
                    <Translate contentKey="cmServiceApp.articlEnclosure.updateTime">Update Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('note')}>
                    <Translate contentKey="cmServiceApp.articlEnclosure.note">Note</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="cmServiceApp.articlEnclosure.article">Article</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {articlEnclosureList.map((articlEnclosure, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${articlEnclosure.id}`} color="link" size="sm">
                        {articlEnclosure.id}
                      </Button>
                    </td>
                    <td>{articlEnclosure.enclosureURL}</td>
                    <td>
                      <Translate contentKey={`cmServiceApp.FileType.${articlEnclosure.enclosureType}`} />
                    </td>
                    <td>{articlEnclosure.createUser}</td>
                    <td>
                      {articlEnclosure.creatTime ? (
                        <TextFormat type="date" value={articlEnclosure.creatTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{articlEnclosure.updateUser}</td>
                    <td>
                      {articlEnclosure.updateTime ? (
                        <TextFormat type="date" value={articlEnclosure.updateTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{articlEnclosure.note}</td>
                    <td>
                      {articlEnclosure.articleTitle ? (
                        <Link to={`article/${articlEnclosure.articleId}`}>{articlEnclosure.articleTitle}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${articlEnclosure.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${articlEnclosure.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${articlEnclosure.id}/delete`} color="danger" size="sm">
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
                <Translate contentKey="cmServiceApp.articlEnclosure.home.notFound">No Articl Enclosures found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

const mapStateToProps = ({ articlEnclosure }: IRootState) => ({
  articlEnclosureList: articlEnclosure.entities,
  loading: articlEnclosure.loading,
  totalItems: articlEnclosure.totalItems,
  links: articlEnclosure.links,
  entity: articlEnclosure.entity,
  updateSuccess: articlEnclosure.updateSuccess,
});

const mapDispatchToProps = {
  getEntities,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArticlEnclosure);
