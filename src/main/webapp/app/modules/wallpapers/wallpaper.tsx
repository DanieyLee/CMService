import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button } from 'reactstrap';
import { Translate, getSortState, JhiPagination } from 'react-jhipster';

import { IRootState } from 'app/shared/reducers';
import { getPublicEntities } from 'app/entities/wallpaper/wallpaper.reducer';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { Page } from 'app/shared/layout/page/page';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

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

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const { wallpaperList, match, loading, totalItems } = props;
  return (
    <div className="content-wallpaper">
      {wallpaperList && wallpaperList.length > 0 ? (
        <div>
          <div className="content-wallpaper-title">
            <h3>
              <Translate contentKey="cmServiceApp.wallpaper.public.title">Title</Translate>
            </h3>
            <h4>
              <Translate contentKey="cmServiceApp.wallpaper.public.content.one">Content One</Translate>
              <a><Translate contentKey="cmServiceApp.wallpaper.public.sign.one">Sign One</Translate></a>
            </h4>
            <h5>
              <Translate contentKey="cmServiceApp.wallpaper.public.content.two">Content Two</Translate>
              <a><Translate contentKey="cmServiceApp.wallpaper.public.sign.two">Sign Two</Translate></a>
              <Translate contentKey="cmServiceApp.wallpaper.public.content.three">Content Three</Translate>
              <a><Translate contentKey="cmServiceApp.wallpaper.public.sign.three">Sign Three</Translate></a>
              <Translate contentKey="cmServiceApp.wallpaper.public.content.four">Content Four</Translate>
            </h5>
          </div>
          <div className="content-wallpaper-item">
            {wallpaperList.map((wallpaper, i) => (
              <div key={`entity-${i}`} className="content-wallpaper-item-num">
                <Button tag={Link} to={`/wallpapers/detail/${wallpaper.id}`} color="link" size="sm">
                  <img src={`${wallpaper.imageUrl}`} alt={`${wallpaper.imageName}`} />
                  <div>
                    <FontAwesomeIcon icon={'heart'} />
                    <span>{wallpaper.like}</span>
                  </div>
                </Button>
                <Button tag={Link} to={`/wallpapers/detail/${wallpaper.id}`} color="link" size="sm">
                  <div>{wallpaper.imageName}</div>
                  <div>{wallpaper.note}</div>
                </Button>
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
      {props.totalItems ? (
        <div className={wallpaperList && wallpaperList.length > 0 ? '' : 'd-none'}>
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
