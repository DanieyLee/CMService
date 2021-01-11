import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './key-box.reducer';
import { IKeyBox } from 'app/shared/model/key-box.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IKeyBoxProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const KeyBox = (props: IKeyBoxProps) => {
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

  const { keyBoxList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="key-box-heading">
        <Translate contentKey="cmServiceApp.keyBox.home.title">Key Boxes</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="cmServiceApp.keyBox.home.createLabel">Create new Key Box</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {keyBoxList && keyBoxList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('userAccount')}>
                  <Translate contentKey="cmServiceApp.keyBox.userAccount">User Account</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('password')}>
                  <Translate contentKey="cmServiceApp.keyBox.password">Password</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('secondPassword')}>
                  <Translate contentKey="cmServiceApp.keyBox.secondPassword">Second Password</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('loginAddress')}>
                  <Translate contentKey="cmServiceApp.keyBox.loginAddress">Login Address</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('explain')}>
                  <Translate contentKey="cmServiceApp.keyBox.explain">Explain</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('display')}>
                  <Translate contentKey="cmServiceApp.keyBox.display">Display</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createUser')}>
                  <Translate contentKey="cmServiceApp.keyBox.createUser">Create User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('creatTime')}>
                  <Translate contentKey="cmServiceApp.keyBox.creatTime">Creat Time</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updateUser')}>
                  <Translate contentKey="cmServiceApp.keyBox.updateUser">Update User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updateTime')}>
                  <Translate contentKey="cmServiceApp.keyBox.updateTime">Update Time</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('note')}>
                  <Translate contentKey="cmServiceApp.keyBox.note">Note</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="cmServiceApp.keyBox.userLink">User Link</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {keyBoxList.map((keyBox, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${keyBox.id}`} color="link" size="sm">
                      {keyBox.id}
                    </Button>
                  </td>
                  <td>{keyBox.userAccount}</td>
                  <td>{keyBox.password}</td>
                  <td>{keyBox.secondPassword}</td>
                  <td>{keyBox.loginAddress}</td>
                  <td>{keyBox.explain}</td>
                  <td>{keyBox.display ? 'true' : 'false'}</td>
                  <td>{keyBox.createUser}</td>
                  <td>{keyBox.creatTime ? <TextFormat type="date" value={keyBox.creatTime} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{keyBox.updateUser}</td>
                  <td>{keyBox.updateTime ? <TextFormat type="date" value={keyBox.updateTime} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{keyBox.note}</td>
                  <td>{keyBox.userLinkFirstName ? <Link to={`user-link/${keyBox.userLinkId}`}>{keyBox.userLinkFirstName}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${keyBox.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${keyBox.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${keyBox.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="cmServiceApp.keyBox.home.notFound">No Key Boxes found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={keyBoxList && keyBoxList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ keyBox }: IRootState) => ({
  keyBoxList: keyBox.entities,
  loading: keyBox.loading,
  totalItems: keyBox.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(KeyBox);
