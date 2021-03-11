import './software-list.scss';

import { NavItem, NavLink, NavbarBrand } from 'reactstrap';
import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button } from 'reactstrap';
import {
  Translate,
  getSortState,
} from 'react-jhipster';

import { IRootState } from 'app/shared/reducers';
import { getTopPublicEntities } from 'app/entities/software/software.reducer';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface ISoftwareProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Software = (props: ISoftwareProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getTopPublicEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
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
    <div className="software-home-top home-box-background-color">
      <div className="software-home-top-height">
        {softwareList && softwareList.length > 0 ? (
          <div>
            <div className="software-top-title">
              <p>
                <Translate contentKey="home.software">Title</Translate>
              </p>
            </div>
            <div className="content-text-div-map">
            {softwareList.map((software, i) => (
              <div key={`entity-${i}`} className="software-top-down-list">
                <Button className="software-home-top-list-button" tag={Link} to={`/softwares/${software.id}`} color="link" size="sm" >
                  {software.softwareICO ? (
                      <img
                        src={`data:${software.softwareICOContentType};base64,${software.softwareICO}`}
                      />
                      ) : <img src="content/images/failed.svg" alt="Failed" />}
                </Button>
                <div className="software-home-top-list-div">
                    <Button tag={Link} to={`/softwares/${software.id}`} color="link" size="sm">
                      {software.name}
                    </Button>
                </div>
              </div>
            ))}
            </div>
          </div>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="cmServiceApp.software.home.notFound">No Software found</Translate>
            </div>
          )
        )}
      </div>
      <hr className="home-box-top-hr"/>
      <NavLink tag={Link} to="/softwares" className="home-box-top-hr-link">
        <Translate contentKey="home.more">More</Translate>
      </NavLink>
    </div>
  );
};

const mapStateToProps = ({ software }: IRootState) => ({
  softwareList: software.entities,
  loading: software.loading,
  totalItems: software.totalItems,
});

const mapDispatchToProps = {
  getTopPublicEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Software);
