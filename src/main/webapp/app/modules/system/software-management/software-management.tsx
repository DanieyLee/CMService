import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import {
  openFile,
  byteSize,
  Translate,
  TextFormat,
  getSortState,
  JhiPagination,
  JhiItemCount,
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, updateUserEntity } from 'app/entities/software/software.reducer';
import { APP_DATE_FORMAT_SIMPLE_ZH_CN } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { Page } from 'app/shared/layout/page/page';

export interface ISoftwareManagementProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const SoftwareManagement = (props: ISoftwareManagementProps) => {
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

  useEffect(() => {
    if (props.updateSuccess) {
      getAllEntities();
    }
  }, [props.updateSuccess]);

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

  const toggle = (wallpaper, name, boolean) => () =>
    props.updateUserEntity({
      ...wallpaper,
      [name]: !boolean,
    });

  const { softwareList, match, loading, totalItems } = props;
  return (
    <div className="content-software-management">
      <h2 id="software-heading">
        <Translate contentKey="global.menu.admin.softwareManagement">Software management</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="cmServiceApp.software.home.createLabel">Create new Software</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {softwareList && softwareList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="cmServiceApp.software.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('size')}>
                  <Translate contentKey="cmServiceApp.software.size">Size</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('stars')}>
                  <Translate contentKey="cmServiceApp.software.stars">Stars</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('show')}>
                  <Translate contentKey="cmServiceApp.software.show">Show</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('allow')}>
                  <Translate contentKey="cmServiceApp.software.allow">Allow</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('state')}>
                  <Translate contentKey="cmServiceApp.software.state">State</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="cmServiceApp.software.softwareType">Software Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {softwareList.map((software, i) => (
                <tr key={`entity-${i}`}>
                  <td>{software.id}</td>
                  <td>{software.name}</td>
                  <td>{software.size}</td>
                  <td>
                    {software.stars ? (
                      <Button color="success" onClick={toggle(software, "stars", software.stars)}>
                        <Translate contentKey="cmServiceApp.software.getStars.true">True</Translate>
                      </Button>
                    ) : (
                      <Button color="danger" onClick={toggle(software, "stars", software.stars)}>
                        <Translate contentKey="cmServiceApp.software.getStars.false">False</Translate>
                      </Button>
                    )}
                  </td>
                  <td>
                    {software.show ? (
                      <Button color="success" onClick={toggle(software, "show", software.show)}>
                        <Translate contentKey="cmServiceApp.software.getShow.true">True</Translate>
                      </Button>
                    ) : (
                      <Button color="danger" onClick={toggle(software, "show", software.show)}>
                        <Translate contentKey="cmServiceApp.software.getShow.false">False</Translate>
                      </Button>
                    )}
                  </td>
                  <td>
                    {software.allow ? (
                      <Button color="success" onClick={toggle(software, "allow", software.allow)}>
                        <Translate contentKey="cmServiceApp.software.getAllow.true">True</Translate>
                      </Button>
                    ) : (
                      <Button color="danger" onClick={toggle(software, "allow", software.allow)}>
                        <Translate contentKey="cmServiceApp.software.getAllow.false">False</Translate>
                      </Button>
                    )}
                  </td>
                  <td>
                    {software.state ? (
                      <Button color="success" onClick={toggle(software, "state", software.state)}>
                        <Translate contentKey="cmServiceApp.software.getState.true">True</Translate>
                      </Button>
                    ) : (
                      <Button color="danger" onClick={toggle(software, "state", software.state)}>
                        <Translate contentKey="cmServiceApp.software.getState.false">False</Translate>
                      </Button>
                    )}
                  </td>
                  <td>
                    {software.softwareTypeType ? software.softwareTypeType : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${software.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${software.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="cmServiceApp.software.home.notFound">No Software found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={softwareList && softwareList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ software }: IRootState) => ({
  softwareList: software.entities,
  loading: software.loading,
  totalItems: software.totalItems,
  updateSuccess: software.updateSuccess,
});

const mapDispatchToProps = {
  getEntities,
  updateUserEntity,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SoftwareManagement);
