import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Col, Row, Button } from 'reactstrap';
import { AvForm, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, getUrlParameter } from 'react-jhipster';
import { Link, RouteComponentProps } from 'react-router-dom';

import { handlePasswordReset, reset } from './password-reset.reducer';
import { sendCode } from 'app/modules/account/register/register.reducer';
import PasswordStrengthBar from 'app/shared/layout/password/password-strength-bar';
import { toast } from 'react-toastify';

export interface IPasswordResetFinishProps extends DispatchProps, RouteComponentProps<{ key: string }> {}

export const PasswordResetFinishPage = (props: IPasswordResetFinishProps) => {
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

  const phoneValidSubmit = (event, values) => {
    props.sendCode(values.phoneNumber);
    countDown("send-submit", 60);
    event.preventDefault();
  };

  const handleValidSubmit = (event, values) => {
    const phone = (document.getElementById('phoneNumber') as HTMLInputElement).value;
    if (phone === "") {
      toast.error(translate('global.messages.validate.phone.required'));
    } else {
      props.handlePasswordReset(phone, values.code, values.newPassword);
      countDown("reset-submit", 5);
    }
  }

  const updatePassword = event => setPassword(event.target.value);

  return (
    <div className="content-reset-password">
      <h1>
        <Translate contentKey="reset.finish.title">Reset password</Translate>
      </h1>
      <AvForm id="send-form" onValidSubmit={phoneValidSubmit}>
        <Row>
          <Col md="9">
            <AvField
              name="phoneNumber"
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
          <Col md="3">
            <label htmlFor="phoneNumber">&nbsp;</label>
            <Button id="send-submit" color="primary" type="submit">
              <Translate contentKey="register.form.send">Send</Translate>
            </Button>
          </Col>
        </Row>
      </AvForm>
      <AvForm onValidSubmit={handleValidSubmit}>
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
            required: { value: true, errorMessage: translate('global.messages.validate.confirmpassword.required') },
            minLength: { value: 4, errorMessage: translate('global.messages.validate.confirmpassword.minlength') },
            maxLength: { value: 50, errorMessage: translate('global.messages.validate.confirmpassword.maxlength') },
            match: { value: 'newPassword', errorMessage: translate('global.messages.error.dontmatch') },
          }}
        />
        <Button id="reset-submit" color="success" type="submit">
          <Translate contentKey="reset.finish.form.button">Validate new password</Translate>
        </Button>
        <Button tag={Link} to="/login" replace color="info">
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
      </AvForm>
    </div>
  );
};

const mapDispatchToProps = { handlePasswordReset, sendCode, reset };

type DispatchProps = typeof mapDispatchToProps;

export default connect(null, mapDispatchToProps)(PasswordResetFinishPage);
