import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, reset } from 'app/entities/user-link/user-link.reducer';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IUserLinkManagementProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const UserLinkManagement = (props: IUserLinkManagementProps) => {
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

  const { userLinkList, match, loading } = props;
  return (
    <div className="content-user-link-management">
      <h2 id="user-link-heading">
        <Translate contentKey="global.menu.admin.userLinkManagement">User Link management</Translate>
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
          {userLinkList && userLinkList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('firstName')}>
                    <Translate contentKey="cmServiceApp.userLink.firstName">First Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('sex')}>
                    <Translate contentKey="settings.form.sex">Sex</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('age')}>
                    <Translate contentKey="cmServiceApp.userLink.age">Age</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('theme')}>
                    <Translate contentKey="cmServiceApp.userLink.theme">Theme</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('passwordKey')}>
                    <Translate contentKey="cmServiceApp.userLink.passwordKey">Password Key</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="cmServiceApp.userLink.user">User</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {userLinkList.map((userLink, i) => (
                  <tr key={`entity-${i}`}>
                    <td>{userLink.id}</td>
                    <td>{userLink.firstName}</td>
                    <td>{userLink.sex ? '男(man)' : '女(woman)'}</td>
                    <td>{userLink.age}</td>
                    <td>{userLink.theme}</td>
                    <td>{userLink.passwordKey}</td>
                    <td>{userLink.userLogin ? userLink.userLogin : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${userLink.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
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
                <Translate contentKey="cmServiceApp.userLink.home.notFound">No User Links found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

const mapStateToProps = ({ userLink }: IRootState) => ({
  userLinkList: userLink.entities,
  loading: userLink.loading,
  totalItems: userLink.totalItems,
  links: userLink.links,
  entity: userLink.entity,
  updateSuccess: userLink.updateSuccess,
});

const mapDispatchToProps = {
  getEntities,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserLinkManagement);
