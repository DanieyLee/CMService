import React, { useState } from 'react';
import { Translate } from 'react-jhipster';
import { Navbar, Nav, NavbarToggler, Collapse } from 'reactstrap';

import LoadingBar from 'react-redux-loading-bar';

import { Home, Articles, Softwares, Wallpapers, KeyBoxs, Brand } from './header-components';
import { AdminMenu, EntitiesMenu, AccountMenu, SystemMenu } from '../menus';

export interface IHeaderProps {
  isAuthenticated: boolean;
  isAdmin: boolean;
  ribbonEnv: string;
  isInProduction: boolean;
  isSwaggerEnabled: boolean;
  currentLocale: string;
  onLocaleChange: Function;
  accountImageUrl: string;
}

const Header = (props: IHeaderProps) => {
  const [menuOpen, setMenuOpen] = useState(false);

  const renderDevRibbon = () =>
    props.isInProduction === false ? (
      <div className="ribbon dev">
        <a href="">
          <Translate contentKey={`global.ribbon.${props.ribbonEnv}`} />
        </a>
      </div>
    ) : null;

  const toggleMenu = () => setMenuOpen(!menuOpen);

  return (
    <div id="app-header">
      {renderDevRibbon()}
      <LoadingBar className="loading-bar" />
      <Navbar dark expand="sm" fixed="top" className="header">
        <NavbarToggler aria-label="Menu" onClick={toggleMenu} className="header-button"/>
        <Brand />
        <Collapse isOpen={menuOpen} navbar>
          <Nav id="header-tabs" className="ml-auto header-label" navbar>
            <Home />
            <Articles />
            <Softwares />
            <Wallpapers />
            <div className="header-label-width" />
            {props.isAuthenticated && <KeyBoxs />}
            {props.isAuthenticated && props.isAdmin && <EntitiesMenu />}
            {props.isAuthenticated && props.isAdmin && <AdminMenu showSwagger={props.isSwaggerEnabled} />}
            {props.isAuthenticated && props.isAdmin && <SystemMenu />}
            <AccountMenu isAuthenticated={props.isAuthenticated} accountImageUrl={props.accountImageUrl} />
          </Nav>
        </Collapse>
      </Navbar>
    </div>
  );
};

export default Header;
