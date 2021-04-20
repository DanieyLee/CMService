import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

const AccountMenuItemsAuthenticated = props => (
  <div className="header-label-account">
    <div className="header-label-account-logo">
      <img src={props.accountImageUrl} alt="Logo" />
    </div>
    <NavDropdown name={translate('global.menu.account.main')} id="account-menu">
      <MenuItem icon="wrench" to="/account/settings">
        <Translate contentKey="global.menu.account.settings">Settings</Translate>
      </MenuItem>
      <MenuItem icon="lock" to="/account/password">
        <Translate contentKey="global.menu.account.password">Password</Translate>
      </MenuItem>
      <MenuItem icon="sign-out-alt" to="/logout">
        <Translate contentKey="global.menu.account.logout">Sign out</Translate>
      </MenuItem>
    </NavDropdown>
  </div>
);

const accountMenuItems = (
  <div className="header-label-account">
    <div className="header-label-account-logo">
      <img src="content/images/author.svg" alt="Logo" />
    </div>
    <NavDropdown name={translate('global.menu.account.main')} id="account-menu">
      <MenuItem id="login-item" icon="sign-in-alt" to="/login">
        <Translate contentKey="global.menu.account.login">Sign in</Translate>
      </MenuItem>
      <MenuItem icon="fingerprint" to="/account/register">
        <Translate contentKey="global.menu.account.register">Register</Translate>
      </MenuItem>
    </NavDropdown>
  </div>
);

export const AccountMenu = ({ isAuthenticated = false , accountImageUrl }) => (
  <>
    {isAuthenticated ? <AccountMenuItemsAuthenticated accountImageUrl={accountImageUrl}/> : accountMenuItems}
  </>
);

export default AccountMenu;
