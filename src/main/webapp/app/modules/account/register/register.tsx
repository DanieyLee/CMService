import './register.scss';

import { toast } from 'react-toastify';
import React, { useState, useEffect } from 'react';
import { Translate, translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { AvForm, AvField } from 'availity-reactstrap-validation';
import { Row, Col, Alert, Button } from 'reactstrap';

import PasswordStrengthBar from 'app/shared/layout/password/password-strength-bar';
import { IRootState } from 'app/shared/reducers';
import { phoneRegister, sendCode, reset } from './register.reducer';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export interface IRegisterProps extends StateProps, DispatchProps {}

export const RegisterPage = (props: IRegisterProps) => {
  const [password, setPassword] = useState('');

  useEffect(
    () => () => {
      props.reset();
    },
    []
  );

  const countDown = (name, time) => {
    const button = document.getElementById(name) as HTMLButtonElement;
    const text = button.innerHTML;
    let num = time;
    button.setAttribute("disabled","false");
    button.innerHTML = num.toString();
    const interval = setInterval(() => {
      if (num > 1){
        num--;
        button.innerHTML = num.toString();
      } else {
        button.innerHTML = text;
        button.removeAttribute("disabled");
        clearInterval(interval);
      }
    }, 1000);
  }

  const handleValidSubmit = (event, values) => {
    const phone = (document.getElementById('phoneNumber') as HTMLInputElement).value;
    if (phone === "") {
      toast.error(translate('global.messages.validate.phone.required'));
    } else {
      props.phoneRegister(phone, values.code, values.firstPassword, props.currentLocale);
      countDown("register-submit", 5);
      event.preventDefault();
    }
  };

  const phoneValidSubmit = (event, values) => {
    props.sendCode(values.phoneNumber);
    countDown("send-submit", 60);
    event.preventDefault();
  };

  const updatePassword = event => setPassword(event.target.value);

  return (
    <div>
      <Row className="justify-content-center register-from-top-div">
        <Col md="10">
          <h1 id="register-title">
            <Translate contentKey="register.title">Registration</Translate>
          </h1>
          <AvForm id="send-form" onValidSubmit={phoneValidSubmit}>
            <Row>
              <Col md="8">
                <AvField
                  name="phoneNumber"
                  className="register-from-text-input-phone"
                  label={translate('global.form.phone.label')}
                  placeholder={translate('global.form.phone.placeholder')}
                  type="phoneNumber"
                  validate={{
                    required: { value: true, errorMessage: translate('global.messages.validate.phone.required') },
                    pattern: {
                      value: '^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$',
                      errorMessage: translate('global.messages.validate.phone.invalid'),
                    },
                    minLength: { value: 8, errorMessage: translate('global.messages.validate.phone.minlength') },
                    maxLength: { value: 11, errorMessage: translate('global.messages.validate.phone.maxlength') },
                  }}
                />
              </Col>
              <Col md="4">
                <Button id="send-submit" className="register-from-text-button-phone" color="primary" type="submit">
                  <Translate contentKey="register.form.send">Send</Translate>
                </Button>
              </Col>
            </Row>
          </AvForm>
          <AvForm id="register-form" onValidSubmit={handleValidSubmit}>
            <AvField
              name="code"
              label={translate('global.form.code.label')}
              placeholder={translate('global.form.code.placeholder')}
              validate={{
                required: { value: true, errorMessage: translate('global.messages.validate.code.required') },
                pattern: {
                  value: '^[0-9]*$',
                  errorMessage: translate('global.messages.validate.code.invalid'),
                },
                minLength: { value: 6, errorMessage: translate('global.messages.validate.code.minlength') },
                maxLength: { value: 6, errorMessage: translate('global.messages.validate.code.maxlength') },
              }}
            />
            <AvField
              name="firstPassword"
              label={translate('global.form.newpassword.label')}
              placeholder={translate('global.form.newpassword.placeholder')}
              type="password"
              onChange={updatePassword}
              validate={{
                required: { value: true, errorMessage: translate('global.messages.validate.newpassword.required') },
                minLength: { value: 4, errorMessage: translate('global.messages.validate.newpassword.minlength') },
                maxLength: { value: 50, errorMessage: translate('global.messages.validate.newpassword.maxlength') },
              }}
            />
            <PasswordStrengthBar password={password} />
            <AvField
              name="secondPassword"
              label={translate('global.form.confirmpassword.label')}
              placeholder={translate('global.form.confirmpassword.placeholder')}
              type="password"
              validate={{
                required: { value: true, errorMessage: translate('global.messages.validate.confirmpassword.required') },
                minLength: { value: 4, errorMessage: translate('global.messages.validate.confirmpassword.minlength') },
                maxLength: { value: 50, errorMessage: translate('global.messages.validate.confirmpassword.maxlength') },
                match: { value: 'firstPassword', errorMessage: translate('global.messages.error.dontmatch') },
              }}
            />
            <Button id="register-submit" className="register-from-text-button" color="primary" type="submit">
              <Translate contentKey="register.form.button">Register</Translate>
            </Button>
            &nbsp;
            <Button className="register-from-text-button" tag={Link} to="/" replace color="info">
              <Translate contentKey="entity.action.cancel">Cancel</Translate>
            </Button>
          </AvForm>
          <p>&nbsp;</p>
          <Alert color="warning">
            <span>
              <Translate contentKey="global.messages.info.authenticated.prefix">If you want to </Translate>
            </span>
            <span> </span>
            <Link className="alert-link" to="/login" >
              <Translate contentKey="global.messages.info.authenticated.link"> sign in</Translate>
            </Link>
          </Alert>
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = ({ locale }: IRootState) => ({
  currentLocale: locale.currentLocale,
});

const mapDispatchToProps = { phoneRegister, sendCode, reset };
type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RegisterPage);
