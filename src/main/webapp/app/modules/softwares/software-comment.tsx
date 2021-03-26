import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Translate, TextFormat, getSortState, translate } from 'react-jhipster';
import { Button } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';

import { IRootState } from 'app/shared/reducers';
import { getPublicSoftwareEntities, replySoftware, reset } from 'app/entities/software-comments/software-comments.reducer';
import { APP_DATE_FORMAT_SIMPLE_ZH_CN } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { Page } from 'app/shared/layout/page/page';

export interface ISoftwareCommentProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SoftwareComment = (props: ISoftwareCommentProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const { softwareCommentEntity, softwareCommentsList, loading, account, updating } = props;

  const getAllEntities = () => {
    props.getPublicSoftwareEntities(props.match.params.id,paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  const handleClose = () => {
    props.getPublicSoftwareEntities(props.match.params.id,paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
    props.history.push(`/softwares/detail/${props.match.params.id}?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`);
    props.reset();
  };

  useEffect(() => {
    props.reset();
  }, []);

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

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const saveEntity = (event, errors, values) => {
    values.createUser = account.id;
    values.softwareId = props.match.params.id;
    values.txTitle = props.match.params.id;
    if (errors.length === 0) {
      const entity = {
        ...softwareCommentEntity,
        ...values,
      };
      props.replySoftware(entity);
    }
  };

  return (
    <div className="content-software-item-comment">
      <div className="table-responsive">
        {softwareCommentsList && softwareCommentsList.length > 0 ? (
          <div>
            {softwareCommentsList.map((softwareComment, i) => (
              <div key={`entity-${i}`}>
                <h5>
                  {softwareComment.createUser}
                  {softwareComment.creatTime ? <TextFormat type="date" value={softwareComment.creatTime} format={APP_DATE_FORMAT_SIMPLE_ZH_CN} /> : null}
                </h5>
                <p>
                  {softwareComment.content}
                </p>
                <hr/>
              </div>
            ))}
          </div>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="cmServiceApp.softwareComments.home.notFound">No Software Comments found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={softwareCommentsList && softwareCommentsList.length > 0 ? '' : 'd-none'}>
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
      <div className="content-software-item-comment-reply">
        {account && account.login ? (
          <div>
            <h2>
              <Translate contentKey="cmServiceApp.softwareComments.sendComment">Send Comment</Translate>
            </h2>
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm onSubmit={saveEntity}>
                <AvGroup>
                  <AvInput
                    id="software-comment-content"
                    type="textarea"
                    name="content"
                    validate={{
                      required: { value: true, errorMessage: translate('cmServiceApp.softwareComment.lengthError') },
                      minLength: { value: 1, errorMessage: translate('cmServiceApp.softwareComment.lengthError') },
                      maxLength: { value: 255, errorMessage: translate('cmServiceApp.softwareComment.lengthError') },
                    }}
                  />
                </AvGroup>
                <div className="content-software-item-comment-reply-button">
                  <Button tag={Link} to="/softwares/0" replace color="info">
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                  </Button>
                  <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <span className="d-none d-md-inline">
                    <Translate contentKey="cmServiceApp.softwareComments.send">Send</Translate>
                  </span>
                  </Button>
                </div>
              </AvForm>
            )}
          </div>
        ) : (
          <div>
            <h2>
              <Translate contentKey="cmServiceApp.softwareComments.sendComment">Send Comment</Translate>
            </h2>
            <div className="content-software-item-comment-reply-not-login">
              <Translate contentKey="cmServiceApp.softwareComments.explain">Explain</Translate>
              <Link to="/login" className="alert-link">
                <Translate contentKey="cmServiceApp.softwareComments.login">Login</Translate>
              </Link>
            </div>
            <div className="content-software-item-comment-reply-button">
              <Button tag={Link} to="/softwares/0" replace color="info">
              <span className="d-none d-md-inline">
                <Translate contentKey="entity.action.back">Back</Translate>
              </span>
              </Button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ softwareComments, authentication }: IRootState) => ({
  softwareCommentEntity: softwareComments.entity,
  softwareCommentsList: softwareComments.entities,
  loading: softwareComments.loading,
  totalItems: softwareComments.totalItems,
  updating: softwareComments.updating,
  account: authentication.account,
  updateSuccess: softwareComments.updateSuccess,
});

const mapDispatchToProps = {
  getPublicSoftwareEntities,
  replySoftware,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SoftwareComment);
