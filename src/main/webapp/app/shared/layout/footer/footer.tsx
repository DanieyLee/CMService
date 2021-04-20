import React from 'react';
import { Storage, Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

import { Articles, Softwares, Explain, Brand } from './footer-components';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export interface IFooterProps {
  onLocaleChange: Function;
}

const Footer = (props: IFooterProps) => {

  const localeEN = () => {
    Storage.session.set('locale', "en");
    props.onLocaleChange("en");
  }
  const localeCN = () => {
    Storage.session.set('locale', "zh-cn");
    props.onLocaleChange("zh-cn");
  }
  const localeJA = () => {
    Storage.session.set('locale', "ja");
    props.onLocaleChange("ja");
  }

  return (
    <div className="footer page-content">
      <Row className="footer-function">
        <Col md="3">
          <Brand />
        </Col>
        <Col md="3">
          <Articles />
        </Col>
        <Col md="3">
          <Softwares />
        </Col>
        <Col md="3">
          <Explain />
        </Col>
      </Row>
      <div className="footer-language">
        <FontAwesomeIcon icon={'globe-americas'}/>
        <a onClick={localeCN}>中文(简体)</a>
        <span>|</span>
        <a onClick={localeEN}>English(US)</a>
        <span>|</span>
        <a onClick={localeJA}>日本語(JP)</a>
      </div>
      <div className="footer-copyright">
        <div>
          <FontAwesomeIcon icon={'cloud'}/>
          <Translate contentKey="footer.down.cloud">Cloud</Translate>
        </div>
        <Translate contentKey="footer.down.icp">Icp</Translate>
        <img src="content/images/police.svg" alt="police"/>
        <Translate contentKey="footer.down.copyright">Copyright</Translate>
      </div>
    </div>
  );
};
export default Footer;
