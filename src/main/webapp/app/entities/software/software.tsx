import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import {
  openFile,
  byteSize,
  Translate,
  ICrudGetAllAction,
  TextFormat,
  getSortState,
  IPaginationBaseState,
  JhiPagination,
  JhiItemCount,
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './software.reducer';
import { ISoftware } from 'app/shared/model/software.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface ISoftwareProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Software = (props: ISoftwareProps) => {
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

  const { softwareList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="software-heading">
        <Translate contentKey="cmServiceApp.software.home.title">Software</Translate>
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
                <th className="hand" onClick={sort('stars')}>
                  <Translate contentKey="cmServiceApp.software.stars">Stars</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="cmServiceApp.software.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('explain')}>
                  <Translate contentKey="cmServiceApp.software.explain">Explain</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('softwareICO')}>
                  <Translate contentKey="cmServiceApp.software.softwareICO">Software ICO</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('score')}>
                  <Translate contentKey="cmServiceApp.software.score">Score</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('size')}>
                  <Translate contentKey="cmServiceApp.software.size">Size</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('version')}>
                  <Translate contentKey="cmServiceApp.software.version">Version</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('applySystem')}>
                  <Translate contentKey="cmServiceApp.software.applySystem">Apply System</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('show')}>
                  <Translate contentKey="cmServiceApp.software.show">Show</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('allow')}>
                  <Translate contentKey="cmServiceApp.software.allow">Allow</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('downloadUrl')}>
                  <Translate contentKey="cmServiceApp.software.downloadUrl">Download Url</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('downloadNumber')}>
                  <Translate contentKey="cmServiceApp.software.downloadNumber">Download Number</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('browseNumber')}>
                  <Translate contentKey="cmServiceApp.software.browseNumber">Browse Number</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('state')}>
                  <Translate contentKey="cmServiceApp.software.state">State</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createUser')}>
                  <Translate contentKey="cmServiceApp.software.createUser">Create User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('creatTime')}>
                  <Translate contentKey="cmServiceApp.software.creatTime">Creat Time</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updateUser')}>
                  <Translate contentKey="cmServiceApp.software.updateUser">Update User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updateTime')}>
                  <Translate contentKey="cmServiceApp.software.updateTime">Update Time</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('note')}>
                  <Translate contentKey="cmServiceApp.software.note">Note</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="cmServiceApp.software.softwareType">Software Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="cmServiceApp.software.userLink">User Link</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {softwareList.map((software, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${software.id}`} color="link" size="sm">
                      {software.id}
                    </Button>
                  </td>
                  <td>{software.stars ? 'true' : 'false'}</td>
                  <td>{software.name}</td>
                  <td>{software.explain}</td>
                  <td>
                    {software.softwareICO ? (
                      <div>
                        {software.softwareICOContentType ? (
                          <a onClick={openFile(software.softwareICOContentType, software.softwareICO)}>
                            <img
                              src={`data:${software.softwareICOContentType};base64,${software.softwareICO}`}
                              style={{ maxHeight: '30px' }}
                            />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {software.softwareICOContentType}, {byteSize(software.softwareICO)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{software.score}</td>
                  <td>{software.size}</td>
                  <td>{software.version}</td>
                  <td>
                    <Translate contentKey={`cmServiceApp.SystemType.${software.applySystem}`} />
                  </td>
                  <td>{software.show ? 'true' : 'false'}</td>
                  <td>{software.allow ? 'true' : 'false'}</td>
                  <td>{software.downloadUrl}</td>
                  <td>{software.downloadNumber}</td>
                  <td>{software.browseNumber}</td>
                  <td>{software.state ? 'true' : 'false'}</td>
                  <td>{software.createUser}</td>
                  <td>{software.creatTime ? <TextFormat type="date" value={software.creatTime} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{software.updateUser}</td>
                  <td>{software.updateTime ? <TextFormat type="date" value={software.updateTime} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{software.note}</td>
                  <td>
                    {software.softwareTypeType ? (
                      <Link to={`software-type/${software.softwareTypeId}`}>{software.softwareTypeType}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {software.userLinkFirstName ? <Link to={`user-link/${software.userLinkId}`}>{software.userLinkFirstName}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${software.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
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

const mapStateToProps = ({ software }: IRootState) => ({
  softwareList: software.entities,
  loading: software.loading,
  totalItems: software.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Software);
