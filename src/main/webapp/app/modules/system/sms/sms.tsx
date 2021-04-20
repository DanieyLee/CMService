import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Row, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getUserEntities } from 'app/entities/phone/phone.reducer';
import { APP_DATE_FORMAT_SIMPLE_ZH_CN } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { Page } from 'app/shared/layout/page/page';

export interface ISmsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Sms = (props: ISmsProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getUserEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
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
    <div className="content-sms-management">
      <h2 id="phone-heading">
        <Translate contentKey="cmServiceApp.phone.home.title">Phones</Translate>
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
              </tr>
            </thead>
            <tbody>
              {phoneList.map((phone, i) => (
                <tr key={`entity-${i}`}>
                  <td>{phone.id}</td>
                  <td>{phone.phone}</td>
                  <td>{phone.code}</td>
                  <td>{phone.effectiveTime ? <TextFormat type="date" value={phone.effectiveTime} format={APP_DATE_FORMAT_SIMPLE_ZH_CN} /> : null}</td>
                  <td>{phone.sendTime ? <TextFormat type="date" value={phone.sendTime} format={APP_DATE_FORMAT_SIMPLE_ZH_CN} /> : null}</td>
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

const mapStateToProps = ({ phone }: IRootState) => ({
  phoneList: phone.entities,
  loading: phone.loading,
  totalItems: phone.totalItems,
});

const mapDispatchToProps = {
  getUserEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Sms);
