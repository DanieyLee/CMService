import './footer.scss';

import React, { useState } from 'react';
import { Storage, Translate, TranslatorContext } from 'react-jhipster';
import { Col, Row, Button } from 'reactstrap';

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
      <Row>
        <Col md="12">
          <Row className="footer-12-div">
            <Col md="4">
              <Brand />
            </Col>
            <Col md="3">
              <Articles />
            </Col>
            <Col md="3">
              <Softwares />
            </Col>
            <Col md="2">
              <Explain />
            </Col>
          </Row>
          <div className="footer-language-div">
            <FontAwesomeIcon icon={'globe-americas'}/>
            <a color="secondary" onClick={localeCN}>
              中文(简体)
            </a>
            <span> </span>
            <a color="secondary" onClick={localeEN}>
              | English(US)
            </a>
            <span> </span>
            <a color="secondary" onClick={localeJA}>
              | 日本語(JP)
            </a>
            <div className="footer-statement-div">
              <h6>
                <Translate contentKey="footer.statement">Statement</Translate>
              </h6>
              <Translate contentKey="footer.statement.one">Statement</Translate>
              <br />
              <Translate contentKey="footer.statement.two">Statement</Translate>
            </div>
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
        </Col>
      </Row>
    </div>
  );
};
export default Footer;
