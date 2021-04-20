import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { AvForm } from 'availity-reactstrap-validation';
import { Row, Col, Button } from 'reactstrap';
import {
  Translate,
  TextFormat
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getPublicEntity, likeEntity } from 'app/entities/article/article.reducer';
import { APP_DATE_FORMAT_SIMPLE_ZH_CN, APP_DATE_FORMAT_SIMPLE_ZH } from 'app/config/constants';
import ArticleComment from './article-comment';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

export interface IArticleDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArticleDetail = (props: IArticleDetailProps) => {

  useEffect(() => {
    props.getPublicEntity(props.match.params.id);
  }, []);

  const countDown = (name, time) => {
    const button = document.getElementById(name) as HTMLButtonElement;
    let num = time;
    button.setAttribute("disabled","true");
    const interval = setInterval(() => {
      if (num > 1){
        num--;
      } else {
        button.removeAttribute("disabled");
        clearInterval(interval);
      }
    }, 1000);
  }

  const likeValidSubmit = (event) => {
    countDown("send-submit",10);
    props.likeEntity(props.match.params.id);
    event.preventDefault();
  };

  const { articleEntity } = props;
  return (
    <div className="content-article-item">
      <h1 className="content-article-item-h1">
        <span>{articleEntity.articleTypeType}</span>
        <p>{articleEntity.title}</p>
      </h1>
      <h2 className="content-article-item-h2">
        <TextFormat value={articleEntity.creatTime} type="date" format={APP_DATE_FORMAT_SIMPLE_ZH} />
        <img src="content/images/author.svg" alt="author" />
        <p>{articleEntity.author}</p>
      </h2>
      <div dangerouslySetInnerHTML = {{ __html: articleEntity.content }} />
      <hr className="content-article-item-hr"/>
      <Row>
        <Col md="9">
          <Translate contentKey="cmServiceApp.article.modify.title" interpolate={{ name: articleEntity.updateUser,time:'' }} />
          <TextFormat value={articleEntity.updateTime} type="date" format={APP_DATE_FORMAT_SIMPLE_ZH_CN} />
          <Translate contentKey="cmServiceApp.article.modify.tail">tail</Translate>
        </Col>
        <Col md="3">
          <FontAwesomeIcon icon={'eye'} />
          <span>{articleEntity.views > 1000 ? articleEntity.views.toString().substring(0,articleEntity.views.toString().length-3) + "k" : articleEntity.views}</span>
          <FontAwesomeIcon icon={'heart'} />
          <span>{articleEntity.likeNumber > 1000? articleEntity.likeNumber.toString().substring(0,articleEntity.likeNumber.toString().length-3) + "k" : articleEntity.likeNumber}</span>
        </Col>
        <Col md="12" >
          <div>{articleEntity.note}</div>
        </Col>
        <AvForm id="send-form" onValidSubmit={likeValidSubmit}>
          <Button id="send-submit" color="primary" type="submit">
            <img src="content/images/like.svg" alt="like" />
            <Translate contentKey="cmServiceApp.wallpaper.like">Like</Translate>
          </Button>
        </AvForm>
      </Row>
      <hr className="content-article-item-hr"/>
      <ErrorBoundaryRoute path={`/articles/detail/:id`} component={ArticleComment} />
    </div>
  );
};

const mapStateToProps = ({ article }: IRootState) => ({
  articleEntity: article.entity,
});

const mapDispatchToProps = {
  getPublicEntity,
  likeEntity,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArticleDetail);
