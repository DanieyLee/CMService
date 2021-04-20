import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Translate, TextFormat, getSortState, translate } from 'react-jhipster';
import { Button } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';

import { IRootState } from 'app/shared/reducers';
import { getPublicArticleEntities, replyArticle, reset } from 'app/entities/article-comment/article-comment.reducer';
import { APP_DATE_FORMAT_SIMPLE_ZH_CN } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { Page } from 'app/shared/layout/page/page';

export interface IArticleCommentProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArticleComment = (props: IArticleCommentProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getPublicArticleEntities(props.match.params.id,paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
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

  const { articleCommentEntity, articleCommentList, loading, account, updating } = props;

  const saveEntity = (event, errors, values) => {
    values.createUser = account.id;
    values.articleId = props.match.params.id;
    if (errors.length === 0) {
      const entity = {
        ...articleCommentEntity,
        ...values,
      };
      props.replyArticle(entity);
    }
  };

  return (
    <div className="content-article-item-comment">
      <div className="table-responsive">
        {articleCommentList && articleCommentList.length > 0 ? (
          <div>
            {articleCommentList.map((articleComment, i) => (
              <div key={`entity-${i}`}>
                <h5>
                  {articleComment.createUser}
                  {articleComment.creatTime ? <TextFormat type="date" value={articleComment.creatTime} format={APP_DATE_FORMAT_SIMPLE_ZH_CN} /> : null}
                </h5>
                <p>
                  {articleComment.content}
                </p>
                <hr/>
              </div>
            ))}
          </div>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="cmServiceApp.articleComment.home.notFound">No Article Comments found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={articleCommentList && articleCommentList.length > 0 ? '' : 'd-none'}>
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
      <div className="content-article-item-comment-reply">
        {account && account.login ? (
          <div>
            <h2>
              <Translate contentKey="cmServiceApp.articleComment.sendComment">Send Comment</Translate>
            </h2>
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm onSubmit={saveEntity}>
                <AvGroup>
                  <AvInput
                    id="article-comment-content"
                    type="textarea"
                    name="content"
                    validate={{
                      required: { value: true, errorMessage: translate('cmServiceApp.articleComment.lengthError') },
                      minLength: { value: 1, errorMessage: translate('cmServiceApp.articleComment.lengthError') },
                      maxLength: { value: 255, errorMessage: translate('cmServiceApp.articleComment.lengthError') },
                    }}
                  />
                </AvGroup>
                <div className="content-article-item-comment-reply-button">
                  <Button tag={Link} to="/articles/0" replace color="info">
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                  </Button>
                  <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <span className="d-none d-md-inline">
                    <Translate contentKey="cmServiceApp.articleComment.send">Send</Translate>
                  </span>
                  </Button>
                </div>
              </AvForm>
            )}
          </div>
        ) : (
          <div>
            <h2>
              <Translate contentKey="cmServiceApp.articleComment.sendComment">Send Comment</Translate>
            </h2>
            <div className="content-article-item-comment-reply-not-login">
              <Translate contentKey="cmServiceApp.articleComment.explain">Explain</Translate>
              <Link to="/login" className="alert-link">
                <Translate contentKey="cmServiceApp.articleComment.login">Login</Translate>
              </Link>
            </div>
            <div className="content-article-item-comment-reply-button">
              <Button tag={Link} to="/articles/0" replace color="info">
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

const mapStateToProps = ({ articleComment, authentication }: IRootState) => ({
  articleCommentEntity: articleComment.entity,
  articleCommentList: articleComment.entities,
  loading: articleComment.loading,
  totalItems: articleComment.totalItems,
  updating: articleComment.updating,
  account: authentication.account,
  updateSuccess: articleComment.updateSuccess,
});

const mapDispatchToProps = {
  getPublicArticleEntities,
  replyArticle,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArticleComment);
