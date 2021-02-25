import './home-components.scss'

import React from 'react';
import { Translate } from 'react-jhipster';

import { NavItem, NavLink, NavbarBrand } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import appConfig from 'app/config/constants';

export const Frame = props => (
  <div className="home-frame-div">
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
    <div className="home-frame-div-img-web">
      <img src="content/images/react.svg" alt="Logo" />
      <img src="content/images/sass.svg" alt="Logo" />
      <img src="content/images/typescript.svg" alt="Logo" />
    </div>
    <h3>
      <img src="content/images/spring-boot.svg" alt="Logo" />
      <Translate contentKey="home.frame.name2">Name</Translate>
    </h3>
    <div className="home-frame-div-img-java">
      <img src="content/images/spring-boot.svg" alt="Logo" />
      <img src="content/images/spring.png" alt="Logo" />
      <div className="home-frame-div-img-java-liquibase">
        <img src="content/images/liquibase.png" alt="Logo" />
      </div>
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

export const Service = props => (
  <div>
  </div>
);

