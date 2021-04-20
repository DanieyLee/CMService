import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { NavDropdown } from './menu-components';
import { Translate, translate } from 'react-jhipster';

const SystemMenuItems = (
  <>
    <MenuItem icon="user" to="/system/user-management">
      <Translate contentKey="global.menu.admin.userManagement">User management</Translate>
    </MenuItem>
    <MenuItem icon="user-plus" to="/system/user-link-management">
      <Translate contentKey="global.menu.admin.userLinkManagement">User Link management</Translate>
    </MenuItem>
    <MenuItem icon="book" to="/system/article-management">
      <Translate contentKey="global.menu.admin.articleManagement">Article management</Translate>
    </MenuItem>
    <MenuItem icon="coins" to="/system/software-management">
      <Translate contentKey="global.menu.admin.softwareManagement">Software management</Translate>
    </MenuItem>
    <MenuItem icon="file-image" to="/system/wallpaper-management">
      <Translate contentKey="global.menu.admin.wallpaperManagement">Wallpaper management</Translate>
    </MenuItem>
    <MenuItem icon="sms" to="/system/sms">
      <Translate contentKey="global.menu.admin.smsRecord">SMS record</Translate>
    </MenuItem>
    <MenuItem icon="grip-vertical" to="/system/article-type-management">
      <Translate contentKey="global.menu.admin.articleTypeManagement">Article type management</Translate>
    </MenuItem>
    <MenuItem icon="th" to="/system/software-type-management">
      <Translate contentKey="global.menu.admin.softwareTypeManagement">Software type management</Translate>
    </MenuItem>
  </>
);

export const SystemMenu = () => (
  <NavDropdown icon="cogs" name={translate('global.menu.admin.system')} style={{ width: '100%' }} id="admin-menu">
    {SystemMenuItems}
  </NavDropdown>
);

export default SystemMenu;
