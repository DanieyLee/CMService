import './settings.scss'

import React, { useEffect } from 'react';
import { Button, Col, Alert, Row } from 'reactstrap';
import { connect } from 'react-redux';
import { Translate, translate } from 'react-jhipster';
import { AvForm, AvField } from 'availity-reactstrap-validation';

import { locales, languages } from 'app/config/translation';
import { choice, sexs } from 'app/config/sexs';
import { IRootState } from 'app/shared/reducers';
import { getSession } from 'app/shared/reducers/authentication';
import { saveAccountSettings, reset } from './settings.reducer';
import { getUserEntity } from 'app/entities/user-link/user-link.reducer';

export interface IUserSettingsProps extends StateProps, DispatchProps {}

export const SettingsPage = (props: IUserSettingsProps) => {
  useEffect(() => {
    props.getSession();
    props.getUserEntity(props.account.id);
    return () => {
      props.reset();
    };
  }, []);

  const handleValidSubmit = (event, values) => {
    const account = {
      ...props.account,
      ...values,
    };

    props.saveAccountSettings(account);
    event.persist();
  };

  const { userLinkEntity } = props;

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="9">
          <h2 id="settings-title">
            <Translate contentKey="settings.title" interpolate={{ username: props.account.login }}>
              User settings for {props.account.login}
            </Translate>
          </h2>
          <br />
          <AvForm id="settings-form" onValidSubmit={handleValidSubmit}>
            <Row>
              <Col md="4">
                <div className="settings-from-image-portrait">
                  <div>
                    <Translate contentKey="settings.form.image">Image</Translate>
                  </div>
                  <img src="content/images/author.svg" alt="author" />
                </div>
                <Row>
                  <Col md="8">
                    <AvField
                      className="form-control"
                      name="passwordKey"
                      id="passwordKey"
                      placeholder={translate('settings.form.passwordKey.placeholder')}
                      validate={{
                        required: { value: true, errorMessage: translate('settings.messages.validate.passwordKey.required') },
                      }}
                      value={props.userLinkEntity.passwordKey}
                    />
                  </Col>
                  <Col md="4">
                    <Button className="register-from-text-button" color="primary" type="submit">
                      <Translate contentKey="settings.form.button">Save</Translate>
                    </Button>
                  </Col>
                </Row>
              </Col>
              <Col md="8">
                <AvField
                  className="form-control"
                  name="firstName"
                  label={translate('settings.form.firstname')}
                  id="firstName"
                  placeholder={translate('settings.form.firstname.placeholder')}
                  validate={{
                    required: { value: true, errorMessage: translate('settings.messages.validate.firstname.required') },
                    minLength: { value: 1, errorMessage: translate('settings.messages.validate.firstname.minlength') },
                    maxLength: { value: 50, errorMessage: translate('settings.messages.validate.firstname.maxlength') },
                  }}
                  value={props.account.firstName}
                />
                <AvField
                  type="select"
                  id="sex"
                  name="sex"
                  className="form-control"
                  label={translate('settings.form.sex')}
                  value={props.userLinkEntity.sex}
                >
                  {choice.map(locale => (
                    <option value={locale} key={locale}>
                      {sexs[locale].name}
                    </option>
                  ))}
                </AvField>
                <AvField
                  className="form-control"
                  name="age"
                  label={translate('settings.form.age')}
                  id="age"
                  placeholder={translate('settings.form.age.placeholder')}
                  validate={{
                    required: { value: true, errorMessage: translate('settings.messages.validate.age.required') },
                    pattern: {
                      value: '^[0-9]*$',
                      errorMessage: translate('settings.messages.validate.age.invalid'),
                    },
                  }}
                  value={props.userLinkEntity.age}
                />
                <AvField
                  className="form-control"
                  name="passwordKey"
                  label={translate('settings.form.passwordKey')}
                  id="passwordKey"
                  placeholder={translate('settings.form.passwordKey.placeholder')}
                  validate={{
                    required: { value: true, errorMessage: translate('settings.messages.validate.passwordKey.required') },
                  }}
                  value={props.userLinkEntity.passwordKey}
                />
              </Col>
            </Row>
            <AvField
              name="email"
              label={translate('settings.form.email')}
              placeholder={translate('settings.form.email.placeholder')}
              type="email"
              validate={{
                required: { value: true, errorMessage: translate('settings.messages.validate.email.required') },
                minLength: { value: 5, errorMessage: translate('settings.messages.validate.email.minlength') },
                maxLength: { value: 254, errorMessage: translate('settings.messages.validate.email.maxlength') },
              }}
              value={props.account.email}
            />

            <Button className="register-from-text-button" color="primary" type="submit">
              <Translate contentKey="settings.form.button">Save</Translate>
            </Button>
          </AvForm>
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = ({ authentication, userLink }: IRootState) => ({
  account: authentication.account,
  userLinkEntity: userLink.entity,
  isAuthenticated: authentication.isAuthenticated,
});

const mapDispatchToProps = { getSession, getUserEntity, saveAccountSettings, reset };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SettingsPage);
