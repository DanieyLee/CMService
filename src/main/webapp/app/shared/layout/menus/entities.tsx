import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="shapes" name={translate('global.menu.entities.main')} id="entity-menu" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="asterisk" to="/user-link">
      <Translate contentKey="global.menu.entities.userLink" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/software">
      <Translate contentKey="global.menu.entities.software" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/software-type">
      <Translate contentKey="global.menu.entities.softwareType" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/software-comments">
      <Translate contentKey="global.menu.entities.softwareComments" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/software-score">
      <Translate contentKey="global.menu.entities.softwareScore" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/article">
      <Translate contentKey="global.menu.entities.article" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/article-enclosure">
      <Translate contentKey="global.menu.entities.articleEnclosure" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/article-type">
      <Translate contentKey="global.menu.entities.articleType" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/article-comment">
      <Translate contentKey="global.menu.entities.articleComment" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/wallpaper">
      <Translate contentKey="global.menu.entities.wallpaper" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/system-image">
      <Translate contentKey="global.menu.entities.systemImage" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/key-box">
      <Translate contentKey="global.menu.entities.keyBox" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/phone">
      <Translate contentKey="global.menu.entities.phone" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
