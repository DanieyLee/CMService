import React from 'react';
import { Translate } from 'react-jhipster';

import { NavItem, NavLink, NavbarBrand } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import appConfig from 'app/config/constants';

export const BrandIcon = props => (
  <div {...props} className="brand-icon">
    <img src="content/images/logo-jhipster.svg" alt="Logo" />
  </div>
);

export const Brand = props => (
  <NavbarBrand tag={Link} to="/" className="brand-logo">
    <BrandIcon />
    <span className="brand-title">
      <Translate contentKey="global.title">CMService</Translate>
    </span>
    <span className="navbar-version">{appConfig.VERSION}</span>
  </NavbarBrand>
);

export const Home = props => (
  <NavItem>
    <NavLink tag={Link} to="/" className="d-flex align-items-center">
      <span>
        <Translate contentKey="global.menu.home">Home</Translate>
      </span>
    </NavLink>
  </NavItem>
);

export const Articles = props => (
  <NavItem>
    <NavLink tag={Link} to="/articles" className="d-flex align-items-center">
      <span>
        <Translate contentKey="global.menu.articles">Articles</Translate>
      </span>
    </NavLink>
  </NavItem>
);

export const Softwares = props => (
  <NavItem>
    <NavLink tag={Link} to="/softwares" className="d-flex align-items-center">
      <span>
        <Translate contentKey="global.menu.softwares">Softwares</Translate>
      </span>
    </NavLink>
  </NavItem>
);

export const Pictures = props => (
  <NavItem>
    <NavLink tag={Link} to="/pictures" className="d-flex align-items-center">
      <span>
        <Translate contentKey="global.menu.pictures">Pictures</Translate>
      </span>
    </NavLink>
  </NavItem>
);

export const CodeBoxs = props => (
  <NavItem>
    <NavLink tag={Link} to="/code-boxs" className="d-flex align-items-center">
      <span>
        <Translate contentKey="global.menu.code-boxs">CodeBoxs</Translate>
      </span>
    </NavLink>
  </NavItem>
);
