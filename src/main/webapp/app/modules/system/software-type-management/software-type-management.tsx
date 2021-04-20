import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, reset } from 'app/entities/software-type/software-type.reducer';
import { APP_DATE_FORMAT_SIMPLE_ZH_CN } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface ISoftwareTypeManagementProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const SoftwareTypeManagement = (props: ISoftwareTypeManagementProps) => {
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

  const { softwareTypeList, match, loading } = props;
  return (
    <div className="content-software-type-management">
      <h2 id="software-type-heading">
        <Translate contentKey="global.menu.admin.softwareTypeManagement">Software type management</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="cmServiceApp.softwareType.home.createLabel">Create new Software Type</Translate>
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
          {softwareTypeList && softwareTypeList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('type')}>
                    <Translate contentKey="cmServiceApp.softwareType.type">Type</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createUser')}>
                    <Translate contentKey="cmServiceApp.softwareType.createUser">Create User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('creatTime')}>
                    <Translate contentKey="cmServiceApp.softwareType.creatTime">Creat Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updateUser')}>
                    <Translate contentKey="cmServiceApp.softwareType.updateUser">Update User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updateTime')}>
                    <Translate contentKey="cmServiceApp.softwareType.updateTime">Update Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('note')}>
                    <Translate contentKey="cmServiceApp.softwareType.note">Note</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {softwareTypeList.map((softwareType, i) => (
                  <tr key={`entity-${i}`}>
                    <td>{softwareType.id}</td>
                    <td>{softwareType.type}</td>
                    <td>{softwareType.createUser}</td>
                    <td>
                      {softwareType.creatTime ? <TextFormat type="date" value={softwareType.creatTime} format={APP_DATE_FORMAT_SIMPLE_ZH_CN} /> : null}
                    </td>
                    <td>{softwareType.updateUser}</td>
                    <td>
                      {softwareType.updateTime ? <TextFormat type="date" value={softwareType.updateTime} format={APP_DATE_FORMAT_SIMPLE_ZH_CN} /> : null}
                    </td>
                    <td>{softwareType.note}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${softwareType.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${softwareType.id}/delete`} color="danger" size="sm">
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
                <Translate contentKey="cmServiceApp.softwareType.home.notFound">No Software Types found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

const mapStateToProps = ({ softwareType }: IRootState) => ({
  softwareTypeList: softwareType.entities,
  loading: softwareType.loading,
  totalItems: softwareType.totalItems,
  links: softwareType.links,
  entity: softwareType.entity,
  updateSuccess: softwareType.updateSuccess,
});

const mapDispatchToProps = {
  getEntities,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SoftwareTypeManagement);
