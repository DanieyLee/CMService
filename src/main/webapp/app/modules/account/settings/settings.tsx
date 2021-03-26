import React, { useEffect } from 'react';
import { Button, Col, Row } from 'reactstrap';
import { connect } from 'react-redux';
import { Translate, translate, openFile, setFileData, byteSize } from 'react-jhipster';
import { AvForm, AvField } from 'availity-reactstrap-validation';

import { choice, sexs } from 'app/config/sexs';
import { IRootState } from 'app/shared/reducers';
import { getSession } from 'app/shared/reducers/authentication';
import { saveAccountSettingsImage, reset } from './settings.reducer';
import { getUserEntity, setBlob } from 'app/entities/user-link/user-link.reducer';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link } from 'react-router-dom';

export interface IUserSettingsProps extends StateProps, DispatchProps {}

export const SettingsPage = (props: IUserSettingsProps) => {
  useEffect(() => {
    props.getSession();
    props.getUserEntity(props.account.id);
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

  const onBlobChange = (isAnImage, name) => event => {
    props.account.lastName = event.target.files[0].name;
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
    props.account.lastName = undefined;
  };

  useEffect(() => {
    if (props.updateSuccess) {
      clearBlob("image");
    }
  }, [props.updateSuccess]);

  const handleValidSubmit = (event, values) => {
    countDown("settings-form-submit", 60);
    const account = {
      ...props.account,
      ...values,
    };
    props.saveAccountSettingsImage(account);
    event.persist();
  };

  const { userLinkEntity } = props;

  return (
    <div className="content-account-settings">
      <h2>
        <Translate contentKey="settings.title" interpolate={{ username: props.account.login }}>
          User settings for {props.account.login}
        </Translate>
      </h2>
      <AvForm id="settings-form" onValidSubmit={handleValidSubmit}>
        <Row>
          <Col md="4">
            <div className="content-account-settings-portrait">
              {userLinkEntity.imageContentType ? (
                <img src={`data:${userLinkEntity.imageContentType};base64,${userLinkEntity.image}`} />
              ) : <img src={props.account.imageUrl} alt="imageURL"/>}
              <div className="content-account-settings-portrait-select">
                <div>
                  <Translate contentKey="settings.form.revise.portrait">Portrait</Translate>
                  <input id="file" type="file" onChange={onBlobChange(true, 'image')} accept="image/*" />
                </div>
              </div>
            </div>
            {userLinkEntity.imageContentType? (
              <Button className="content-account-settings-portrait-cancel" color="danger" onClick={clearBlob('image')}>
                <FontAwesomeIcon icon="times-circle" />
              </Button>
            ):null}
            <AvField type="hidden" name="image" value={userLinkEntity.image} />
          </Col>
          <Col md="8">
            <AvField
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
              label={translate('settings.form.sex')}
              value={userLinkEntity.sex}
            >
              {choice.map(locale => (
                <option value={locale} key={locale}>
                  {sexs[locale].name}
                </option>
              ))}
            </AvField>
            <AvField
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
              value={userLinkEntity.age}
            />
            <AvField
              name="passwordKey"
              label={translate('settings.form.passwordKey')}
              id="passwordKey"
              placeholder={translate('settings.form.passwordKey.placeholder')}
              validate={{
                required: { value: true, errorMessage: translate('settings.messages.validate.passwordKey.required') },
              }}
              value={userLinkEntity.passwordKey}
            />
          </Col>
          <Col md="12">
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
            <Button id="settings-form-submit" color="primary" type="submit">
              <Translate contentKey="settings.form.button">Save</Translate>
            </Button>
            <Button tag={Link} to="/" replace color="info">
              <Translate contentKey="entity.action.cancel">Cancel</Translate>
            </Button>
          </Col>
        </Row>
      </AvForm>
    </div>
  );
};

const mapStateToProps = ({ authentication, userLink, settings }: IRootState) => ({
  account: authentication.account,
  userLinkEntity: userLink.entity,
  isAuthenticated: authentication.isAuthenticated,
  updateSuccess: settings.updateSuccess,
});

const mapDispatchToProps = {
  getSession,
  getUserEntity,
  saveAccountSettingsImage,
  setBlob,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SettingsPage);
