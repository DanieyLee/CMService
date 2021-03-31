import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from 'app/entities/wallpaper/wallpaper.reducer';
import { APP_DATE_FORMAT_SIMPLE_ZH_CN } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { Page } from 'app/shared/layout/page/page';

export interface IWallpaperManagementProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const WallpaperManagement = (props: IWallpaperManagementProps) => {
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

  const { wallpaperList, match, loading, totalItems } = props;
  return (
    <div className="content-wallpaper-management">
      <h2 id="wallpaper-heading">
        <Translate contentKey="cmServiceApp.wallpaper.home.title">Wallpapers</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="cmServiceApp.wallpaper.home.createLabel">Create new Wallpaper</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {wallpaperList && wallpaperList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('imageName')}>
                  <Translate contentKey="cmServiceApp.wallpaper.imageName">Image Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('imageUrl')}>
                  <Translate contentKey="cmServiceApp.wallpaper.imageUrl">Image Url</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('imagePixel')}>
                  <Translate contentKey="cmServiceApp.wallpaper.imagePixel">Image Pixel</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('imageType')}>
                  <Translate contentKey="cmServiceApp.wallpaper.imageType">Image Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('visitorVolume')}>
                  <Translate contentKey="cmServiceApp.wallpaper.visitorVolume">Visitor Volume</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('like')}>
                  <Translate contentKey="cmServiceApp.wallpaper.like">Like</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('state')}>
                  <Translate contentKey="cmServiceApp.wallpaper.state">State</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('note')}>
                  <Translate contentKey="cmServiceApp.wallpaper.note">Note</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {wallpaperList.map((wallpaper, i) => (
                <tr key={`entity-${i}`}>
                  <td>{wallpaper.id}</td>
                  <td>{wallpaper.imageName}</td>
                  <td>{wallpaper.imageUrl}</td>
                  <td>{wallpaper.imagePixel}</td>
                  <td>
                    <Translate contentKey={`cmServiceApp.ImageType.${wallpaper.imageType}`} />
                  </td>
                  <td>{wallpaper.visitorVolume}</td>
                  <td>{wallpaper.like}</td>
                  <td>{wallpaper.state ? 'true' : 'false'}</td>
                  <td>{wallpaper.note ? wallpaper.note.length > 10 ? wallpaper.note.substr(0,10) : wallpaper.note : ""}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${wallpaper.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${wallpaper.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="cmServiceApp.wallpaper.home.notFound">No Wallpapers found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={wallpaperList && wallpaperList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ wallpaper }: IRootState) => ({
  wallpaperList: wallpaper.entities,
  loading: wallpaper.loading,
  totalItems: wallpaper.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WallpaperManagement);
