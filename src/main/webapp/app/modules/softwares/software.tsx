import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {
  Translate,
  TextFormat,
  getSortState,
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getPublicTypeEntities } from 'app/entities/software/software.reducer';
import { getSoftwareTypeEntities } from 'app/entities/software-type/software-type.reducer';
import { APP_DATE_FORMAT_SIMPLE } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { Page } from 'app/shared/layout/page/page';

export interface ISoftwareProps extends StateProps, DispatchProps, RouteComponentProps<{ type: string }> {}

export const Software = (props: ISoftwareProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getSoftwareTypeEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
    props.getPublicTypeEntities(props.match.params.type, paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
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

  const turnPage = id => () =>
    props.getPublicTypeEntities(id, paginationState.activePage - 1, paginationState.itemsPerPage,
      `${paginationState.sort},${paginationState.order}`);

  const { softwareList, softwareTypeList, loading } = props;
  return (
    <div className="content-software">
      {softwareList && softwareList.length > 0 ? (
        <div>
          <div className="content-software-title">
            <h3>
              <Translate contentKey="cmServiceApp.software.public.title">Title</Translate>
            </h3>
            <h4>
              <Translate contentKey="cmServiceApp.software.public.content.one">Content One</Translate>
              <a><Translate contentKey="cmServiceApp.software.public.sign.one">Sign One</Translate></a>
            </h4>
            <h5>
              <Translate contentKey="cmServiceApp.software.public.content.two">Content Two</Translate>
              <a><Translate contentKey="cmServiceApp.software.public.sign.two">Sign Two</Translate></a>
              <Translate contentKey="cmServiceApp.software.public.content.three">Content Three</Translate>
              <a><Translate contentKey="cmServiceApp.software.public.sign.three">Sign Three</Translate></a>
              <Translate contentKey="cmServiceApp.software.public.content.four">Content Four</Translate>
            </h5>
          </div>
          {softwareTypeList && softwareTypeList.length > 0 ? (
            <div className="content-software-type">
              <Button onClick={turnPage(0)}>
                <Translate contentKey="cmServiceApp.software.public.title">Title</Translate>
              </Button>
              {softwareTypeList.map((articleType, i) => (
                <Button key={`entity-${i}`} onClick={turnPage(articleType.id)}>
                  {articleType.type}
                </Button>
              ))}
            </div>
          ) : (
            ""
          )}
          <div className="content-software-item">
            {softwareList.map((software, i) => (
              <div key={`entity-${i}`} className="content-software-item-num">
                <Button tag={Link} to={`/softwares/detail/${software.id}`} color="link" size="sm" >
                  {software.softwareICO ? (
                    <img
                      src={`data:${software.softwareICOContentType};base64,${software.softwareICO}`}
                    />
                    ) : <img src="content/images/image.svg" alt="image" />}
                </Button>
                <div className="content-software-item-num-content">
                  <div>
                    {software.version}
                  </div>
                  <div>
                    {software.stars ? (
                      <FontAwesomeIcon icon={'star'}/>
                    ) : ''}
                    <Button tag={Link} to={`/softwares/detail/${software.id}`} color="link" size="sm">
                      {software.name}
                    </Button>
                  </div>
                  <div>
                    {software.explain}
                  </div>
                </div>
                <div className="content-software-item-num-img">
                  <Row>
                    <Col md="4">
                      <FontAwesomeIcon icon={'eye'} />
                      <span>{software.browseNumber > 1000 ? software.browseNumber.toString().substring(0,software.browseNumber.toString().length-3) + "k" : software.browseNumber}</span>
                    </Col>
                    <Col md="4">
                      <FontAwesomeIcon icon={'download'} />
                      <span>{software.downloadNumber > 1000 ? software.downloadNumber.toString().substring(0,software.downloadNumber.toString().length-3)+"k" : software.downloadNumber}</span>
                    </Col>
                    <Col md="4">
                      <FontAwesomeIcon icon={'clock'} />
                      <span>{software.updateTime ? <TextFormat type="date" value={software.updateTime} format={APP_DATE_FORMAT_SIMPLE} /> : null}</span>
                    </Col>
                  </Row>
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
      {props.totalItems ? (
        <div className={softwareList && softwareList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ software, softwareType }: IRootState) => ({
  softwareList: software.entities,
  softwareTypeList: softwareType.entities,
  loading: software.loading,
  totalItems: software.totalItems,
});

const mapDispatchToProps = {
  getPublicTypeEntities,
  getSoftwareTypeEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Software);
