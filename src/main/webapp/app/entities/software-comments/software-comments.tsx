import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './software-comments.reducer';
import { ISoftwareComments } from 'app/shared/model/software-comments.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface ISoftwareCommentsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const SoftwareComments = (props: ISoftwareCommentsProps) => {
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

  const { softwareCommentsList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="software-comments-heading">
        <Translate contentKey="cmServiceApp.softwareComments.home.title">Software Comments</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="cmServiceApp.softwareComments.home.createLabel">Create new Software Comments</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {softwareCommentsList && softwareCommentsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('txTitle')}>
                  <Translate contentKey="cmServiceApp.softwareComments.txTitle">Tx Title</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('content')}>
                  <Translate contentKey="cmServiceApp.softwareComments.content">Content</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createUser')}>
                  <Translate contentKey="cmServiceApp.softwareComments.createUser">Create User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('creatTime')}>
                  <Translate contentKey="cmServiceApp.softwareComments.creatTime">Creat Time</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updateUser')}>
                  <Translate contentKey="cmServiceApp.softwareComments.updateUser">Update User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updateTime')}>
                  <Translate contentKey="cmServiceApp.softwareComments.updateTime">Update Time</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('note')}>
                  <Translate contentKey="cmServiceApp.softwareComments.note">Note</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="cmServiceApp.softwareComments.software">Software</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="cmServiceApp.softwareComments.userLink">User Link</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {softwareCommentsList.map((softwareComments, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${softwareComments.id}`} color="link" size="sm">
                      {softwareComments.id}
                    </Button>
                  </td>
                  <td>{softwareComments.txTitle}</td>
                  <td>{softwareComments.content}</td>
                  <td>{softwareComments.createUser}</td>
                  <td>
                    {softwareComments.creatTime ? (
                      <TextFormat type="date" value={softwareComments.creatTime} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{softwareComments.updateUser}</td>
                  <td>
                    {softwareComments.updateTime ? (
                      <TextFormat type="date" value={softwareComments.updateTime} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{softwareComments.note}</td>
                  <td>
                    {softwareComments.softwareName ? (
                      <Link to={`software/${softwareComments.softwareId}`}>{softwareComments.softwareName}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {softwareComments.userLinkFirstName ? (
                      <Link to={`user-link/${softwareComments.userLinkId}`}>{softwareComments.userLinkFirstName}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${softwareComments.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${softwareComments.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${softwareComments.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="cmServiceApp.softwareComments.home.notFound">No Software Comments found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={softwareCommentsList && softwareCommentsList.length > 0 ? '' : 'd-none'}>
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
    </div>
  );
};

const mapStateToProps = ({ softwareComments }: IRootState) => ({
  softwareCommentsList: softwareComments.entities,
  loading: softwareComments.loading,
  totalItems: softwareComments.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SoftwareComments);
