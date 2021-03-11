import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getPublicEntities } from 'app/entities/wallpaper/wallpaper.reducer';
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
    props.getPublicEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
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
            <div className="title-content-text-div">
              <h3>
                <Translate contentKey="cmServiceApp.wallpaper.public.title">Title</Translate>
              </h3>
              <div>
                <Translate contentKey="cmServiceApp.wallpaper.public.content.one">Content One</Translate>
                <a><Translate contentKey="cmServiceApp.wallpaper.public.sign.one">Sign One</Translate></a>
              </div>
              <div>
                <Translate contentKey="cmServiceApp.wallpaper.public.content.two">Content Two</Translate>
                <a><Translate contentKey="cmServiceApp.wallpaper.public.sign.two">Sign Two</Translate></a>
                <Translate contentKey="cmServiceApp.wallpaper.public.content.three">Content Three</Translate>
                <a><Translate contentKey="cmServiceApp.wallpaper.public.sign.three">Sign Three</Translate></a>
                <Translate contentKey="cmServiceApp.wallpaper.public.content.four">Content Four</Translate>
              </div>
            </div>
            <div className="content-text-div-map">
              {wallpaperList.map((wallpaper, i) => (
                <div key={`entity-${i}`}>
                  <div className="wallpaper-content-text-div-map-img">
                    <img src={`${wallpaper.imageUrl}`} alt={`${wallpaper.imageName}`} />
                    <Button tag={Link} to={`${match.url}/${wallpaper.id}`} color="link" size="sm">
                      <div>{wallpaper.imageName}</div>
                    </Button>
                  </div>
                </div>
              ))}
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
      {props.totalItems ? (
        <div className={wallpaperList && wallpaperList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ wallpaper }: IRootState) => ({
  wallpaperList: wallpaper.entities,
  loading: wallpaper.loading,
  totalItems: wallpaper.totalItems,
});

const mapDispatchToProps = {
  getPublicEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Wallpaper);
