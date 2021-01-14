import './explain.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { translate, Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Button, Row, Col, Alert } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';


import { IRootState } from 'app/shared/reducers';

export type IHomeProp = StateProps;

export const ExplainContact = (props: IHomeProp) => {
  const { account } = props;

  const handleValidSubmit = "lixin520gj@163.com"

  const input = document.getElementById("email") as HTMLInputElement;

  const clickCopy = () => {
    input.value = handleValidSubmit; // 修改文本框的内容
    input.select(); // 选中文本
    document.execCommand("Copy");
  }

  return (
    <Row>
      <Col md="12">
        <div className="home-explain-contact">
          <h2>
            <Translate contentKey="explain.title.contact">Contact</Translate>
          </h2>
          <p className="lead">
            <Translate contentKey="explain.subtitle.contact">SubtitleContact</Translate>
          </p>
          <div className="home-explain-contact-text">
            <AvForm className="home-explain-contact-text-email" onValidSubmit={handleValidSubmit}>
            <AvField
              name="email"
              placeholder={translate('global.form.email.placeholder')}
              type="email"
              readOnly
              validate={{
                required: { value: true, errorMessage: translate('global.messages.validate.email.required') },
                minLength: { value: 5, errorMessage: translate('global.messages.validate.email.minlength') },
                maxLength: { value: 254, errorMessage: translate('global.messages.validate.email.maxlength') },
              }}
              value={handleValidSubmit}
            />
              <Button className="home-explain-contact-text-button" color="primary" onClick={clickCopy}>
                <Translate contentKey="explain.copy">Copy</Translate>
              </Button>
            </AvForm>
            <div className="home-explain-contact-qrcode-div">
              <img src="content/images/mywechat.png" alt="QR code" className="home-explain-contact-img"/>
              <img src="content/images/myqq.png" alt="QR code" className="home-explain-contact-img"/>
            </div>
          </div>
        </div>
      </Col>
    </Row>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(ExplainContact);
