import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './phone.reducer';
import { IPhone } from 'app/shared/model/phone.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IPhoneProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Phone = (props: IPhoneProps) => {
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

  const { phoneList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="phone-heading">
        <Translate contentKey="cmServiceApp.phone.home.title">Phones</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="cmServiceApp.phone.home.createLabel">Create new Phone</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {phoneList && phoneList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('phone')}>
                  <Translate contentKey="cmServiceApp.phone.phone">Phone</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('code')}>
                  <Translate contentKey="cmServiceApp.phone.code">Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('effectiveTime')}>
                  <Translate contentKey="cmServiceApp.phone.effectiveTime">Effective Time</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sendTime')}>
                  <Translate contentKey="cmServiceApp.phone.sendTime">Send Time</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createUser')}>
                  <Translate contentKey="cmServiceApp.phone.createUser">Create User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('creatTime')}>
                  <Translate contentKey="cmServiceApp.phone.creatTime">Creat Time</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updateUser')}>
                  <Translate contentKey="cmServiceApp.phone.updateUser">Update User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updateTime')}>
                  <Translate contentKey="cmServiceApp.phone.updateTime">Update Time</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('note')}>
                  <Translate contentKey="cmServiceApp.phone.note">Note</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="cmServiceApp.phone.userLink">User Link</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {phoneList.map((phone, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${phone.id}`} color="link" size="sm">
                      {phone.id}
                    </Button>
                  </td>
                  <td>{phone.phone}</td>
                  <td>{phone.code}</td>
                  <td>{phone.effectiveTime ? <TextFormat type="date" value={phone.effectiveTime} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{phone.sendTime ? <TextFormat type="date" value={phone.sendTime} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{phone.createUser}</td>
                  <td>{phone.creatTime ? <TextFormat type="date" value={phone.creatTime} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{phone.updateUser}</td>
                  <td>{phone.updateTime ? <TextFormat type="date" value={phone.updateTime} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{phone.note}</td>
                  <td>{phone.userLinkFirstName ? <Link to={`user-link/${phone.userLinkId}`}>{phone.userLinkFirstName}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${phone.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${phone.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${phone.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="cmServiceApp.phone.home.notFound">No Phones found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={phoneList && phoneList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ phone }: IRootState) => ({
  phoneList: phone.entities,
  loading: phone.loading,
  totalItems: phone.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Phone);
