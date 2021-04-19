import React from 'react';
import { translate, Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { AvForm, AvField } from 'availity-reactstrap-validation';
import { Row, Col, Button } from 'reactstrap';
import { toast } from 'react-toastify';

export type IExplainProp = StateProps;

export const ExplainLink = (props: IExplainProp) => {

  const handleValidSubmit = "lixin520gj@163.com"

  const clickCopy = () => {
    (document.getElementById("email") as HTMLInputElement).select();
    document.execCommand("Copy");
    toast.success(translate('explain.copy.success'));
    window.getSelection().empty()
  }

  return (
    <div className="content-explain-link">
      <h2>
        <Translate contentKey="explain.title.link">Link</Translate>
      </h2>
      <p>
        <Translate contentKey="explain.subtitle.link">SubtitleLink</Translate>
      </p>
      <AvForm onValidSubmit={handleValidSubmit}>
        <Row className="content-explain-link-input">
          <Col md="8">
            <AvField
              name="email"
              placeholder={translate('global.form.email.placeholder')}
              type="email"
              readOnly
              value={handleValidSubmit}
            />
          </Col>
          <Col md="4">
            <Button color="primary" onClick={clickCopy}>
              <Translate contentKey="explain.copy">Copy</Translate>
            </Button>
          </Col>
        </Row>
      </AvForm>
      <Row className="content-explain-link-code">
        <Col md="6">
          <img src="content/images/mywechat.png" alt="mywechat"/>
        </Col>
        <Col md="6">
          <img src="content/images/myqq.png" alt="myqq"/>
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = storeState => ({
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(ExplainLink);
