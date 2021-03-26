import { NavLink } from 'reactstrap';
import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';

import { IRootState } from 'app/shared/reducers';
import { getTopPublicEntities } from 'app/entities/wallpaper/wallpaper.reducer';
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

  const { wallpaperList, loading } = props;
  return (
    <div className="content-home-wallpaper">
      {wallpaperList && wallpaperList.length > 0 ? (
        <div>
          <div className="content-home-wallpaper-title">
            <Translate contentKey="home.wallpaper.name">Title</Translate>
            <br />
            <Translate contentKey="home.wallpaper.explain">Explain</Translate>
          </div>
          <Row>
            {wallpaperList.map((wallpaper, i) => (
              <Col md="6" key={`entity-${i}`} className="content-home-wallpaper-item">
                <div>
                  <img src={`${wallpaper.imageUrl}`} alt={`${wallpaper.imageName}`} />
                  <Button tag={Link} to={`/wallpapers/detail/${wallpaper.id}`} color="link" size="sm">
                    {wallpaper.imageName}
                  </Button>
                </div>
              </Col>
            ))}
          </Row>
        </div>
      ) : (
        !loading && (
          <div className="alert alert-warning">
            <Translate contentKey="cmServiceApp.wallpaper.home.notFound">No Wallpapers found</Translate>
          </div>
        )
      )}
      <div className="content-home-wallpaper-more">
        <NavLink tag={Link} to="/wallpapers/0">
          <Translate contentKey="home.more">More</Translate>
        </NavLink>
      </div>
      <hr/>
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
