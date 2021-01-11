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
import { getPublicEntities } from 'app/entities/software/software.reducer';
import { ISoftware } from 'app/shared/model/software.model';
import { APP_DATE_FORMAT, APP_DATE_FORMAT_SIMPLE } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface ISoftwareProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Software = (props: ISoftwareProps) => {
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

  const { softwareList, match, loading, totalItems } = props;
  return (
    <div className="div-width-auto">
      <div className="table-responsive">
        {softwareList && softwareList.length > 0 ? (
          <div>
            <div className="title-content-text-div">
              <h3>
                <Translate contentKey="cmServiceApp.software.public.title">Title</Translate>
              </h3>
              <div>
                <Translate contentKey="cmServiceApp.software.public.content.one">Content One</Translate>
                <a><Translate contentKey="cmServiceApp.software.public.sign.one">Sign One</Translate></a>
              </div>
              <div>
                <Translate contentKey="cmServiceApp.software.public.content.two">Content Two</Translate>
                <a><Translate contentKey="cmServiceApp.software.public.sign.two">Sign Two</Translate></a>
                <Translate contentKey="cmServiceApp.software.public.content.three">Content Three</Translate>
                <a><Translate contentKey="cmServiceApp.software.public.sign.three">Sign Three</Translate></a>
                <Translate contentKey="cmServiceApp.software.public.content.four">Content Four</Translate>
              </div>
            </div>
            <div className="content-text-div-map">
            {softwareList.map((software, i) => (
              <div key={`entity-${i}`}>
                  <div className="software-list-div">
                    <Button className="software-list-div-title" tag={Link} to={`${match.url}/${software.id}`} color="link" size="sm" >
                      {software.softwareICO ? (
                          <img
                            src={`data:${software.softwareICOContentType};base64,${software.softwareICO}`}
                          />
                          ) : <img src="content/images/failed.svg" alt="Failed" />}
                    </Button>
                    <div className="software-list-div-content">
                      <div className="software-list-div-content-up">{software.version}</div>
                      <div className="software-list-div-content-center">
                        {software.stars ? (
                          <img src="content/images/stars.svg" alt="Stars" />
                          ) : ''}
                        <Button tag={Link} to={`${match.url}/${software.id}`} color="link" size="sm">
                          {software.name}
                        </Button>
                      </div>
                      <div className="software-list-div-content-down">{software.explain}</div>
                    </div>
                    <div className="software-list-div-footer">
                      <div>
                        <img src="content/images/disk.svg" alt="Disk" />
                        <span>{software.size > 1000 ? software.size.toString().substring(0,software.size.toString().length-3) + "k" : software.size}</span>
                      </div>
                      <div>
                        <img src="content/images/download.svg" alt="Download" />
                        <span>{software.downloadNumber > 1000 ? software.downloadNumber.toString().substring(0,software.downloadNumber.toString().length-3)+"k" : software.downloadNumber}</span>
                      </div>
                      <div>
                        <img src="content/images/time.svg" alt="Time" />
                        <span>{software.updateTime ? <TextFormat type="date" value={software.updateTime} format={APP_DATE_FORMAT_SIMPLE} /> : null}</span>
                      </div>
                    </div>
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
      {props.totalItems ? (
        <div className={softwareList && softwareList.length > 0 ? '' : 'd-none'}>
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
  getPublicEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Software);
