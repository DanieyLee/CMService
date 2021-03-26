import React from 'react';
import { NavLink, Button } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { Link } from 'react-router-dom';

export const Article = props => (

  <div className="content-home-article">
    <div className="content-home-article-list">
      {props.articleList && props.articleList.length > 0 ? (
        <div>
          <div className="content-home-article-list-title">
            <p>
              <Translate contentKey="home.article">Title</Translate>
            </p>
          </div>
          {props.articleList.map((article, i) => (
            <div key={`entity-${i}`}>
              <div className="content-home-article-list-name">
                <Button tag={Link} to={`/articles/${article.id}`} color="link" size="sm">
                  <span>{article.title}</span>
                </Button>
              </div>
              <div className="content-home-article-list-content">
                <Button tag={Link} to={`/articles/${article.id}`} color="link" size="sm">
                  <div dangerouslySetInnerHTML = {{ __html: article.content.length > 5000 ? article.content.substr(0,5000) : article.content }} />
                </Button>
              </div>
            </div>
          ))}
        </div>
      ) : (
        !props.loading && (
          <div className="alert alert-warning">
            <Translate contentKey="cmServiceApp.article.home.notFound">No Articles found</Translate>
          </div>
        )
      )}
    </div>
    <hr/>
    <NavLink tag={Link} to="/articles">
      <Translate contentKey="home.more">More</Translate>
    </NavLink>
  </div>
);

export const Frame = props => (
  <div className="content-home-frame">
    <h2>
      <Translate contentKey="home.frame.title">Title</Translate>
    </h2>
    <p>
      <Translate contentKey="home.frame.content">Content</Translate>
    </p>
    <h3>
      <img src="content/images/react.svg" alt="Logo" />
      <Translate contentKey="home.frame.name">Name</Translate>
    </h3>
    <div>
      <img src="content/images/react.svg" alt="Logo" />
      <img src="content/images/sass.svg" alt="Logo" />
      <img src="content/images/typescript.svg" alt="Logo" />
    </div>
    <h3>
      <img src="content/images/spring-boot.svg" alt="Logo" />
      <Translate contentKey="home.frame.name2">Name</Translate>
    </h3>
    <div>
      <img src="content/images/spring-boot.svg" alt="Logo" />
      <img src="content/images/spring.png" alt="Logo" />
      <p>
        <img src="content/images/liquibase.png" alt="Logo" />
      </p>
      <img src="content/images/swagger.png" alt="Logo" />
      <img src="content/images/mysql.svg" alt="Logo" />
      <img src="content/images/hibernate.svg" alt="Logo" />
    </div>
    <h3>
      <Translate contentKey="home.frame.title2">Title</Translate>
    </h3>
    <p>
      <Translate contentKey="home.frame.content2">Content</Translate>
    </p>
  </div>
);
