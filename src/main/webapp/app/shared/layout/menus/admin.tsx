import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { NavDropdown } from './menu-components';
import { Translate, translate } from 'react-jhipster';

const adminMenuItems = (
  <>
    <MenuItem icon="user" to="/admin/user-management">
      <Translate contentKey="global.menu.admin.userManagement">User management</Translate>
    </MenuItem>
    <MenuItem icon="user-plus" to="/admin/user-link-management">
      <Translate contentKey="global.menu.admin.userLinkManagement">User Link management</Translate>
    </MenuItem>
    <MenuItem icon="book" to="/admin/article-management">
      <Translate contentKey="global.menu.admin.articleManagement">Article management</Translate>
    </MenuItem>
    <MenuItem icon="coins" to="/admin/software-management">
      <Translate contentKey="global.menu.admin.softwareManagement">Software management</Translate>
    </MenuItem>
    <MenuItem icon="file-image" to="/admin/wallpaper-management">
      <Translate contentKey="global.menu.admin.wallpaperManagement">Wallpaper management</Translate>
    </MenuItem>
    <MenuItem icon="sms" to="/admin/sms">
      <Translate contentKey="global.menu.admin.smsRecord">SMS record</Translate>
    </MenuItem>
    <MenuItem icon="grip-vertical" to="/admin/article-type-management">
      <Translate contentKey="global.menu.admin.articleTypeManagement">Article type management</Translate>
    </MenuItem>
    <MenuItem icon="grip-vertical" to="/admin/software-type-management">
      <Translate contentKey="global.menu.admin.softwareTypeManagement">Software type management</Translate>
    </MenuItem>
    <MenuItem icon="tachometer-alt" to="/admin/metrics">
      <Translate contentKey="global.menu.admin.metrics">Metrics</Translate>
    </MenuItem>
    <MenuItem icon="heart" to="/admin/health">
      <Translate contentKey="global.menu.admin.health">Health</Translate>
    </MenuItem>
    <MenuItem icon="list" to="/admin/configuration">
      <Translate contentKey="global.menu.admin.configuration">Configuration</Translate>
    </MenuItem>
    <MenuItem icon="bell" to="/admin/audits">
      <Translate contentKey="global.menu.admin.audits">Audits</Translate>
    </MenuItem>
    {/* jhipster-needle-add-element-to-admin-menu - JHipster will add entities to the admin menu here */}
    <MenuItem icon="tasks" to="/admin/logs">
      <Translate contentKey="global.menu.admin.logs">Logs</Translate>
    </MenuItem>
  </>
);

const swaggerItem = (
  <MenuItem icon="book" to="/admin/docs">
    <Translate contentKey="global.menu.admin.apidocs">API</Translate>
  </MenuItem>
);

export const AdminMenu = ({ showSwagger }) => (
  <NavDropdown icon="cogs" name={translate('global.menu.admin.main')} style={{ width: '100%' }} id="admin-menu">
    {adminMenuItems}
    {showSwagger && swaggerItem}
  </NavDropdown>
);

export default AdminMenu;
