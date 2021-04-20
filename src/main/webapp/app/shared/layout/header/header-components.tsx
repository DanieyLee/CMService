import React from 'react';
import { Translate } from 'react-jhipster';

import { NavItem, NavLink, NavbarBrand } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import appConfig from 'app/config/constants';

export const BrandIcon = props => (
  <div {...props} className="header-icon">
    <img src="content/images/logo.svg" alt="Logo" />
  </div>
);

export const Brand = props => (
  <NavbarBrand tag={Link} to="/" className="header-logo">
    <BrandIcon />
    <span className="header-title">
      <Translate contentKey="global.title">Title</Translate>
    </span>
    <span className="header-version">{appConfig.VERSION}</span>
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
    <NavLink tag={Link} to="/articles/0" className="d-flex align-items-center">
      <span>
        <Translate contentKey="global.menu.articles">Articles</Translate>
      </span>
    </NavLink>
  </NavItem>
);

export const Softwares = props => (
  <NavItem>
    <NavLink tag={Link} to="/softwares/0" className="d-flex align-items-center">
      <span>
        <Translate contentKey="global.menu.softwares">Softwares</Translate>
      </span>
    </NavLink>
  </NavItem>
);

export const Wallpapers = props => (
  <NavItem>
    <NavLink tag={Link} to="/wallpapers/0" className="d-flex align-items-center">
      <span>
        <Translate contentKey="global.menu.wallpapers">Wallpapers</Translate>
      </span>
    </NavLink>
  </NavItem>
);

export const KeyBoxs = props => (
  <NavItem className="dropdown">
    <NavLink tag={Link} to="/key-boxs" className="d-flex align-items-center">
      <FontAwesomeIcon icon={'box'} />
      <span>
        <Translate contentKey="global.menu.key-boxs">KeyBox</Translate>
      </span>
    </NavLink>
  </NavItem>
);
