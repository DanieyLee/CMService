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
  <div className="footer-title-div">
    <tbody>
      <tr>
        <NavbarBrand tag={Link} to="/" className="f-flex brand-logo">
          <BrandIcon />
          <span className="footer-title">
            <Translate contentKey="global.title">CMService</Translate>
          </span>
        </NavbarBrand>
      </tr>
      <tr>
        <div className="footer-brand-tr-div">
          <img src="content/images/wechat.svg" alt="wechat" className="footer-image-brand" />
          <span>：DanieyLee</span>
        </div>
      </tr>
      <tr>
        <div className="footer-brand-tr-div">
          <img src="content/images/qq.svg" alt="qq" className="footer-image-brand" />
          <span>：541091705</span>
        </div>
      </tr>
      <tr>
        <div className="footer-brand-tr-div">
          <img src="content/images/email.svg" alt="email" className="footer-image-brand" />
          <span>：lixin520gj@163.com</span>
        </div>
      </tr>
    </tbody>
  </div>
);

export const Articles = props => (
  <div className="footer-div">
    <tbody>
      <tr>
        <NavLink tag={Link} to="/articles" className="f-flex align-items-center">
          <span className="footer-title-span">
            <Translate contentKey="footer.share">Share</Translate>
          </span>
        </NavLink>
      </tr>
      <tr>
        <NavLink tag={Link} to="/articles" className="f-flex align-items-center">
          <span>
            <Translate contentKey="footer.hot.articles">Hot</Translate>
          </span>
        </NavLink>
      </tr>
      <tr>
        <NavLink tag={Link} to="/articles" className="f-flex align-items-center">
          <span>
            <Translate contentKey="footer.new.articles">New</Translate>
          </span>
        </NavLink>
      </tr>
      <tr>
        <NavLink tag={Link} to="/articles" className="f-flex align-items-center">
          <span>...</span>
        </NavLink>
      </tr>
    </tbody>
  </div>
);

export const Softwares = props => (
  <div className="footer-div">
    <tbody>
      <tr>
        <NavLink tag={Link} to="/softwares" className="f-flex align-items-center">
          <span className="footer-title-span">
            <Translate contentKey="footer.software">Software</Translate>
          </span>
        </NavLink>
      </tr>
      <tr>
        <NavLink tag={Link} to="/softwares" className="f-flex align-items-center">
          <span>
            <Translate contentKey="footer.download.software">Download</Translate>
          </span>
        </NavLink>
      </tr>
      <tr>
        <NavLink tag={Link} to="/softwares" className="f-flex align-items-center">
          <span>
            <Translate contentKey="footer.new.software">New</Translate>
          </span>
        </NavLink>
      </tr>
      <tr>
        <NavLink tag={Link} to="/softwares" className="f-flex align-items-center">
          <span>
            <Translate contentKey="footer.stars.software">Stars</Translate>
          </span>
        </NavLink>
      </tr>
      <tr>
        <NavLink tag={Link} to="/softwares" className="f-flex align-items-center">
          <span>...</span>
        </NavLink>
      </tr>
    </tbody>
  </div>
);

export const Explain = props => (
  <div className="footer-div">
    <tbody>
      <tr>
        <NavLink tag={Link} to="/softwares" className="f-flex align-items-center">
          <span className="footer-title-span">
            <Translate contentKey="footer.explain">Explain</Translate>
          </span>
        </NavLink>
      </tr>
      <tr>
        <NavLink tag={Link} to="/softwares" className="f-flex align-items-center">
          <span>
            <Translate contentKey="footer.about">About</Translate>
          </span>
        </NavLink>
      </tr>
      <tr>
        <NavLink tag={Link} to="/softwares" className="f-flex align-items-center">
          <span>
            <Translate contentKey="footer.contact">Contact</Translate>
          </span>
        </NavLink>
      </tr>
      <tr>
        <NavLink tag={Link} to="/softwares" className="f-flex align-items-center">
          <span>
            <Translate contentKey="footer.help">Help</Translate>
          </span>
        </NavLink>
      </tr>
      <tr>
        <NavLink tag={Link} to="/softwares" className="f-flex align-items-center">
          <span>
            <Translate contentKey="footer.copyright">Copyright</Translate>
          </span>
        </NavLink>
      </tr>
    </tbody>
  </div>
);

