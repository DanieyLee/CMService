import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Input, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getMyAllEntities } from 'app/entities/key-box/key-box.reducer';
import { IKeyBox } from 'app/shared/model/key-box.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT_ZH, APP_LOCAL_DATE_FORMAT_ZH_CN } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IKeyBoxProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const KeyBox = (props: IKeyBoxProps) => {
  const [filter, setFilter] = useState('');
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getMyAllEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
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

  const changeFilter = evt => setFilter(evt.target.value);

  const filterFn = l => l.explain.toUpperCase().includes(filter.toUpperCase());

  const { keyBoxList, match, loading, totalItems } = props;
  return (
    <div className="table-responsive-div-all-keybos">
      <h2 id="key-box-heading">
        <Translate contentKey="cmServiceApp.keyBox.public.title">Key Boxes</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="cmServiceApp.keyBox.home.createLabel">Create new Key Box</Translate>
        </Link>
      </h2>
      <div className="key-box-heading-title-div">
        <div>
          <Translate contentKey="cmServiceApp.keyBox.public.content.one">Content One</Translate>
          <a><Translate contentKey="cmServiceApp.keyBox.public.sign.one">Sign One</Translate></a>
        </div>
        <div>
          <Translate contentKey="cmServiceApp.keyBox.public.content.two">Content Two</Translate>
          <a><Translate contentKey="cmServiceApp.keyBox.public.sign.two">Sign Two</Translate></a>
          <Translate contentKey="cmServiceApp.keyBox.public.content.three">Content Three</Translate>
          <a><Translate contentKey="cmServiceApp.keyBox.public.sign.three">Sign Three</Translate></a>
          <Translate contentKey="cmServiceApp.keyBox.public.content.four">Content Four</Translate>
        </div>
      </div>
      <div className="table-responsive">
        <div className="table-responsive-filter">
          <Input type="search" value={filter} onChange={changeFilter} name="search" id="search" />
          <div>
            <Translate contentKey="configuration.filter">Filter</Translate>
          </div>
        </div>
        {keyBoxList && keyBoxList.length > 0 ? (
            <div className="table-responsive-div-all-keybos">
              {keyBoxList.filter(filterFn)
                .map((keyBox, i) => (
                <div key={`entity-${i}`} className="table-responsive-div-all-keybos-map-table">
                  <div className="table-responsive-div-all-keybos-time">
                    {keyBox.updateTime ?
                    <TextFormat type="date" value={keyBox.updateTime} format={APP_LOCAL_DATE_FORMAT_ZH_CN} /> : null}
                  </div>
                  <Button className="table-responsive-clear table-responsive-title" tag={Link} to={`${match.url}/${keyBox.id}`} color="link" size="sm">
                    <span>{keyBox.explain}</span>
                  </Button>
                  <div>
                    <Translate contentKey="cmServiceApp.keyBox.userAccount">UserAccount</Translate>{": "}
                    <span>{keyBox.userAccount}</span>
                  </div>
                  <div>
                    <span>
                      <Translate contentKey="cmServiceApp.keyBox.password">Password</Translate>{": "}
                      <span>{keyBox.display ? "******" : keyBox.password}</span>
                    </span>
                    {' '}
                    <span>
                      <Translate contentKey="cmServiceApp.keyBox.secondPassword">SecondPassword</Translate>{": "}
                      <span>{keyBox.display ? "******" : keyBox.secondPassword}</span>
                      <FontAwesomeIcon icon="eye" />
                    </span>
                  </div>
                  <div>
                    <Translate contentKey="cmServiceApp.keyBox.loginAddress">LoginAddress</Translate>{": "}
                    <a className="table-responsive-clear table-responsive-a-address" href={"http://" + keyBox.loginAddress} >{keyBox.loginAddress}</a>
                  </div>
                  <div>
                    <Translate contentKey="cmServiceApp.keyBox.note">Note</Translate>{": "}
                    <span>{keyBox.note}</span>
                  </div>
                  <div className="text-center table-responsive-div-all-keybos-margin">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${keyBox.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="info"
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
                        color="info"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </div>
                </div>
              ))}
            </div>
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
  getMyAllEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(KeyBox);
