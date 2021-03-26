import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Input } from 'reactstrap';
import {
  Translate,
  TextFormat,
  getSortState,
  translate
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getMyAllEntities, showHideEntity } from 'app/entities/key-box/key-box.reducer';
import { APP_LOCAL_DATE_FORMAT_ZH_CN } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { Page } from 'app/shared/layout/page/page';

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

  const showHidePassword = (event,keyBox) => {
    props.showHideEntity(keyBox.id);
    keyBox.display = !keyBox.display;
    event.preventDefault();
  }

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const changeFilter = evt => setFilter(evt.target.value);

  const filterFn = l => l.explain.toUpperCase().includes(filter.toUpperCase());

  const { keyBoxList, match, loading, totalItems } = props;
  return (
    <div className="content-key-box">
      <div className="content-key-box-title">
        <h3>
          <Translate contentKey="cmServiceApp.keyBox.public.title">Key Boxes</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <Translate contentKey="cmServiceApp.keyBox.home.createLabel">Create new Key Box</Translate>
          </Link>
        </h3>
        <h4>
          <Translate contentKey="cmServiceApp.keyBox.public.content.one">Content One</Translate>
          <a><Translate contentKey="cmServiceApp.keyBox.public.sign.one">Sign One</Translate></a>
        </h4>
        <h5>
          <Translate contentKey="cmServiceApp.keyBox.public.content.two">Content Two</Translate>
          <a><Translate contentKey="cmServiceApp.keyBox.public.sign.two">Sign Two</Translate></a>
          <Translate contentKey="cmServiceApp.keyBox.public.content.three">Content Three</Translate>
          <a><Translate contentKey="cmServiceApp.keyBox.public.sign.three">Sign Three</Translate></a>
          <Translate contentKey="cmServiceApp.keyBox.public.content.four">Content Four</Translate>
        </h5>
      </div>
      <div className="content-key-box-item">
        <Input type="search" value={filter} placeholder={translate('configuration.filter')} onChange={changeFilter} name="search" id="search" />
        {keyBoxList && keyBoxList.length > 0 ? (
          <div>
            {keyBoxList.filter(filterFn).map((keyBox, i) => (
              <div key={`entity-${i}`} className="content-key-box-item-num">
                <div className="content-key-box-item-num-time">
                  {keyBox.updateTime ?
                  <TextFormat type="date" value={keyBox.updateTime} format={APP_LOCAL_DATE_FORMAT_ZH_CN} /> : null}
                </div>
                <Button className="content-key-box-item-num-name" tag={Link} to={`${match.url}/${keyBox.id}`} color="link" size="sm">
                  <span>{keyBox.explain}</span>
                </Button>
                <div>
                  <Translate contentKey="cmServiceApp.keyBox.userAccount">UserAccount</Translate>{": "}
                  <span>{keyBox.userAccount}</span>
                </div>
                <div className="content-key-box-item-num-password">
                  <div>
                    <Translate contentKey="cmServiceApp.keyBox.password">Password</Translate>{": "}
                    <span>{keyBox.display ? "******" : keyBox.password}</span>
                  </div>
                  <div>
                    <Translate contentKey="cmServiceApp.keyBox.secondPassword">SecondPassword</Translate>{": "}
                    <span>{keyBox.display ? "******" : keyBox.secondPassword}</span>
                    <Button onClick={(event) => {showHidePassword(event,keyBox)}}>
                      <FontAwesomeIcon icon = { keyBox.display ? "eye" : "eye-slash" } />
                    </Button>
                  </div>
                </div>
                <div className="content-key-box-item-num-address">
                  <Translate contentKey="cmServiceApp.keyBox.loginAddress">LoginAddress</Translate>{": "}
                  <a href={"http://" + keyBox.loginAddress} >{keyBox.loginAddress}</a>
                </div>
                <div className="content-key-box-item-num-note">
                  <Translate contentKey="cmServiceApp.keyBox.note">Note</Translate>{": "}
                  <span>{keyBox.note}</span>
                </div>
                <div className="content-key-box-item-num-button">
                  <Button
                    tag={Link}
                    to={`${match.url}/${keyBox.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                    color="primary"
                    size="sm"
                  >
                    <FontAwesomeIcon icon="pencil-alt" />
                    <span className="d-none d-md-inline">
                      <Translate contentKey="entity.action.edit">Edit</Translate>
                    </span>
                  </Button>
                  <Button
                    tag={Link}
                    to={`key-boxs/${keyBox.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                    color="danger"
                    size="sm"
                  >
                    <FontAwesomeIcon icon="trash" />
                    <span className="d-none d-md-inline">
                      <Translate contentKey="entity.action.delete">Delete</Translate>
                    </span>
                  </Button>
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
          <Page
            activePage={paginationState.activePage}
            onSelect={handlePagination}
            maxButtons={5}
            itemsPerPage={paginationState.itemsPerPage}
            totalItems={props.totalItems}
          />
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
  showHideEntity,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(KeyBox);
