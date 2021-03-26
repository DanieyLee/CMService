import React from 'react';
import { Translate } from 'react-jhipster';

import { NavLink } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';

export const Brand = props => (
  <tbody className="footer-function-title">
    <tr>
      <div className="footer-function-title-logo">
        <img src="content/images/logo.svg" alt="Logo" />
        <Translate contentKey="global.title">CMService</Translate>
      </div>
    </tr>
    <tr>
      <div className="footer-function-title-link">
        <p>QQ - 541091705</p>
        <p>lixin520gj@163.com</p>
      </div>
    </tr>
    <tr>
      <NavLink tag={Link} to="/link">
        <img src="content/images/qq.svg" alt="qq"/>
      </NavLink>
      <NavLink tag={Link} to="/link">
        <img src="content/images/wechat.svg" alt="wechat"/>
      </NavLink>
      <NavLink tag={Link} to="/link">
        <img src="content/images/email.svg" alt="email"/>
      </NavLink>
    </tr>
  </tbody>
);

export const Articles = props => (
  <tbody>
    <tr>
      <div>
        <Translate contentKey="footer.share">Share</Translate>
      </div>
    </tr>
    <tr>
      <NavLink tag={Link} to="/articles/0">
        <Translate contentKey="footer.hot.articles">Hot</Translate>
      </NavLink>
    </tr>
    <tr>
      <NavLink tag={Link} to="/articles/0">
        <Translate contentKey="footer.new.articles">New</Translate>
      </NavLink>
    </tr>
    <tr>
      <NavLink tag={Link} to="/articles/0">
        <span>...</span>
      </NavLink>
    </tr>
  </tbody>
);

export const Softwares = props => (
  <tbody>
    <tr>
      <div>
        <Translate contentKey="footer.software">Software</Translate>
      </div>
    </tr>
    <tr>
      <NavLink tag={Link} to="/softwares/0">
        <Translate contentKey="footer.download.software">Download</Translate>
      </NavLink>
    </tr>
    <tr>
      <NavLink tag={Link} to="/softwares/0">
        <Translate contentKey="footer.new.software">New</Translate>
      </NavLink>
    </tr>
    <tr>
      <NavLink tag={Link} to="/softwares/0">
        <Translate contentKey="footer.stars.software">Stars</Translate>
      </NavLink>
    </tr>
    <tr>
      <NavLink tag={Link} to="/softwares/0">
        <span>...</span>
      </NavLink>
    </tr>
  </tbody>
);

export const Explain = props => (
  <tbody>
    <tr>
      <div className="a">
        <Translate contentKey="footer.explain">Explain</Translate>
      </div>
    </tr>
    <tr>
      <NavLink tag={Link} to="/about">
        <Translate contentKey="footer.about">About</Translate>
      </NavLink>
    </tr>
    <tr>
      <NavLink tag={Link} to="/link">
        <Translate contentKey="footer.link">Link</Translate>
      </NavLink>
    </tr>
    <tr>
      <NavLink tag={Link} to="/hide">
        <Translate contentKey="footer.hide">Hide</Translate>
      </NavLink>
    </tr>
    <tr>
      <NavLink tag={Link} to="/copy">
        <Translate contentKey="footer.copy">Copy</Translate>
      </NavLink>
    </tr>
  </tbody>
);
