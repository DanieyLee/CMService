import './password.scss'

import React, { useState, useEffect } from 'react';
import { Translate, translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { AvForm, AvField } from 'availity-reactstrap-validation';
import { Row, Col, Button } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';
import { getSession } from 'app/shared/reducers/authentication';
import PasswordStrengthBar from 'app/shared/layout/password/password-strength-bar';
import { savePassword, reset } from './password.reducer';

export interface IUserPasswordProps extends StateProps, DispatchProps {}

export const PasswordPage = (props: IUserPasswordProps) => {
  const [password, setPassword] = useState('');

  useEffect(() => {
    props.reset();
    props.getSession();
    return () => {
      props.reset();
    };
  }, []);

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
    props.savePassword(values.currentPassword, values.newPassword);
    countDown("login-re-password-submit", 5);
    event.preventDefault();
  };

  const updatePassword = event => setPassword(event.target.value);

  return (
    <div>
      <Row className="justify-content-center-password">
        <Col md="12">
          <h2 id="password-title">
            <Translate contentKey="password.title" interpolate={{ username: props.account.firstName }}>
              Password for {props.account.firstName}
            </Translate>
          </h2>
          <AvForm id="password-form" onValidSubmit={handleValidSubmit}>
            <AvField
              name="currentPassword"
              label={translate('global.form.currentpassword.label')}
              placeholder={translate('global.form.currentpassword.placeholder')}
              type="password"
              validate={{
                required: { value: true, errorMessage: translate('global.messages.validate.newpassword.required') },
              }}
            />
            <AvField
              name="newPassword"
              label={translate('global.form.newpassword.label')}
              placeholder={translate('global.form.newpassword.placeholder')}
              type="password"
              validate={{
                required: { value: true, errorMessage: translate('global.messages.validate.newpassword.required') },
                minLength: { value: 4, errorMessage: translate('global.messages.validate.newpassword.minlength') },
                maxLength: { value: 50, errorMessage: translate('global.messages.validate.newpassword.maxlength') },
              }}
              onChange={updatePassword}
            />
            <PasswordStrengthBar password={password} />
            <AvField
              name="confirmPassword"
              label={translate('global.form.confirmpassword.label')}
              placeholder={translate('global.form.confirmpassword.placeholder')}
              type="password"
              validate={{
                required: {
                  value: true,
                  errorMessage: translate('global.messages.validate.confirmpassword.required'),
                },
                minLength: {
                  value: 4,
                  errorMessage: translate('global.messages.validate.confirmpassword.minlength'),
                },
                maxLength: {
                  value: 50,
                  errorMessage: translate('global.messages.validate.confirmpassword.maxlength'),
                },
                match: {
                  value: 'newPassword',
                  errorMessage: translate('global.messages.error.dontmatch'),
                },
              }}
            />
            <Button id="login-re-password-submit" className="register-from-text-button" color="success" type="submit">
              <Translate contentKey="password.form.button">Save</Translate>
            </Button>
          </AvForm>
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = ({ authentication }: IRootState) => ({
  account: authentication.account,
  isAuthenticated: authentication.isAuthenticated,
});

const mapDispatchToProps = { getSession, savePassword, reset };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PasswordPage);
