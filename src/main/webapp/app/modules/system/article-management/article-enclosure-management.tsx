import React, { useEffect, useState } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button, Table } from 'reactstrap';
import { getSortState, TextFormat, translate, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, reset } from 'app/entities/article-enclosure/article-enclosure.reducer';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { toast } from 'react-toastify';

export interface IArticleEnclosureManagementProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArticleEnclosureManagement = (props: IArticleEnclosureManagementProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );
  const [sorting, setSorting] = useState(false);
  const { articleEnclosureList, match, loading } = props;

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const resetAll = () => {
    props.reset();
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    props.getEntities();
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      resetAll();
    }
  }, [props.updateSuccess]);

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleClose = () => {
    props.history.push(`${match.url.substr(0,match.url.lastIndexOf("/"))}`);
  };

  const clickCopy = id => () => {
    (document.getElementById(`address${id}`) as HTMLInputElement).select();
    document.execCommand("Copy");
    toast.success(translate('explain.copy.success'));
    window.getSelection().empty()
  }

  const clickDelete = id => () => {
    if(window.confirm('你确定要删除吗？')){
      alert("确定");
      return true;
    }else{
      alert("取消");
      return false;
    }

  }

  return (
    <Modal isOpen toggle={handleClose} className="alert-article-enclosure">
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="cmServiceApp.articleEnclosure.detail.title">Title</Translate>
      </ModalHeader>
      <ModalBody id="cmServiceApp.article.delete.question">
        <div className="table-responsive">
          {articleEnclosureList && articleEnclosureList.length > 0 ? (
            <div className="alert-article-enclosure-media">
              {articleEnclosureList.map((articleEnclosure, i) => (
                <div key={`entity-${i}`} className="alert-article-enclosure-media-num">
                  {articleEnclosure.enclosureType.toString().toUpperCase() === "IMAGE" ? (
                    <img src={articleEnclosure.enclosureURL} />
                  ) : null}
                  {articleEnclosure.enclosureType.toString().toUpperCase() === "VIDEO" ? (
                    <video src={articleEnclosure.enclosureURL} loop autoPlay controls />
                  ) : null}
                  {articleEnclosure.enclosureType.toString().toUpperCase() === "AUDIO" ? (
                    <audio src={articleEnclosure.enclosureURL} loop controls />
                  ) : null}
                  <input id={`address${articleEnclosure.id}`} value={articleEnclosure.enclosureURL} readOnly/>
                  <p>{articleEnclosure.enclosureURL}</p>
                  <Button color="primary"  onClick={clickCopy(`${articleEnclosure.id}`)}>
                    <Translate contentKey="explain.copy">Copy</Translate>
                  </Button>
                  <Button color="danger"  onClick={clickDelete(`${articleEnclosure.id}`)}>
                    <Translate contentKey="entity.action.delete">Delete</Translate>
                  </Button>
                </div>
              ))}
            </div>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="cmServiceApp.articleEnclosure.home.notFound">No Articl Enclosures found</Translate>
              </div>
            )
          )}
        </div>
      </ModalBody>
      <ModalFooter>
        <Button color="primary" onClick={handleClose}>
          <Translate contentKey="cmServiceApp.articleEnclosure.upload">Upload</Translate>
        </Button>
        <Button color="secondary" onClick={handleClose}>
          <Translate contentKey="cmServiceApp.articleEnclosure.close">Close</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ articleEnclosure }: IRootState) => ({
  articleEnclosureList: articleEnclosure.entities,
  updateSuccess: articleEnclosure.updateSuccess,
  links: articleEnclosure.links,
  loading: articleEnclosure.loading,
});

const mapDispatchToProps = {
  getEntities,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArticleEnclosureManagement);
