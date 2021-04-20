import React from 'react';
import { NavLink, Button } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { Link } from 'react-router-dom';

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
