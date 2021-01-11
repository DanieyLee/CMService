import './footer.scss';

import React, { useState } from 'react';
import { Storage, Translate, TranslatorContext } from 'react-jhipster';
import { Col, Row, Button } from 'reactstrap';

import { Articles, Softwares, Explain, Brand } from './footer-components';

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
          <div className="footer-12-div">
            <Brand />
            <Articles />
            <Softwares />
            <Explain />
          </div>
          <div className="footer-language-div">
            <img src="content/images/language.svg" alt="language" className="footer-image-div"/>
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
              <img src="content/images/cloud.svg" alt="cloud" className="footer-icp-image"/>
              <Translate contentKey="footer.down.cloud">Cloud</Translate>
            </div>
            <Translate contentKey="footer.down.icp">Icp</Translate>
            {/*<img src="content/images/police.svg" alt="police" className="footer-icp-image"/>*/}
            {/*<Translate contentKey="footer.down.copyright">Copyright</Translate>*/}
          </div>
        </Col>
      </Row>
    </div>
  );
};
export default Footer;
