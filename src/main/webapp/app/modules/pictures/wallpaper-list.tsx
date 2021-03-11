import './wallpaper-list.scss';

import { NavItem, NavLink, NavbarBrand } from 'reactstrap';
import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getTopPublicEntities } from 'app/entities/wallpaper/wallpaper.reducer';
import { IWallpaper } from 'app/shared/model/wallpaper.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IWallpaperProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Wallpaper = (props: IWallpaperProps) => {
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

  const { wallpaperList, match, loading, totalItems } = props;
  return (
    <div className="div-width-auto">
      <div className="table-responsive">
        {wallpaperList && wallpaperList.length > 0 ? (
          <div>
            <div className="wallpaper-top-title">
              <p>
                <Translate contentKey="home.wallpaper.name">Title</Translate>
              </p>
              <div>
                <Translate contentKey="home.wallpaper.explain">Explain</Translate>
              </div>
            </div>



            <div className="home-wallpaper-content-text-div-map">
              <Row>
                {wallpaperList.map((wallpaper, i) => (
                  <Col md="6" key={`entity-${i}`}>
                    <div className="home-wallpaper-name-content-div-img">
                      <img src={`${wallpaper.imageUrl}`} alt={`${wallpaper.imageName}`} />
                      <Button tag={Link} to={`/pictures/${wallpaper.id}`} color="link" size="sm">
                        <div>{wallpaper.imageName}</div>
                      </Button>
                    </div>
                  </Col>
                ))}
              </Row>
            </div>
          </div>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="cmServiceApp.wallpaper.home.notFound">No Wallpapers found</Translate>
            </div>
          )
        )}
      </div>
      <div className="home-box-top-wallpaper">
        <NavLink tag={Link} to="/pictures">
          <Translate contentKey="home.more">More</Translate>
        </NavLink>
      </div>
      <hr className="home-box-top-hr"/>
    </div>
  );
};

const mapStateToProps = ({ wallpaper }: IRootState) => ({
  wallpaperList: wallpaper.entities,
  loading: wallpaper.loading,
  totalItems: wallpaper.totalItems,
});

const mapDispatchToProps = {
  getTopPublicEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Wallpaper);
