import React from 'react';
import { Translate, translate } from 'react-jhipster';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Label, Alert, Row, Col } from 'reactstrap';
import { AvForm, AvField, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Link } from 'react-router-dom';

export interface ILoginModalProps {
  showModal: boolean;
  loginError: boolean;
  handleLogin: Function;
  handleClose: Function;
}

class LoginModal extends React.Component<ILoginModalProps> {
  handleSubmit = (event, errors, { username, password, rememberMe }) => {
    const { handleLogin } = this.props;
    handleLogin(username, password, rememberMe);
  };

  render() {
    const { loginError, handleClose } = this.props;

    return (
      <Modal className="content-login" isOpen={this.props.showModal} toggle={handleClose} backdrop="static" id="login-page" autoFocus={false}>
        <AvForm onSubmit={this.handleSubmit}>
          <ModalHeader id="login-title" toggle={handleClose}>
            <Translate contentKey="login.title">Sign in</Translate>
          </ModalHeader>
          <ModalBody>
            <Row>
              <Col md="12">
                {loginError ? (
                  <Alert color="danger">
                    <Translate contentKey="login.messages.error.authentication">
                      <strong>Failed to sign in!</strong> Please check your credentials and try again.
                    </Translate>
                  </Alert>
                ) : null}
              </Col>
              <Col md="12">
                <Link to="/account/reset/request" className="content-login-link">
                  <Translate contentKey="login.password.forgot">Did you forget your password?</Translate>
                </Link>
                <Link to="/account/register"  className="content-login-link">
                  <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>
                </Link>
                <AvField
                  name="username"
                  label={translate('global.form.phone.label')}
                  placeholder={translate('global.form.phone.placeholder')}
                  required
                  autoFocus
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
                <AvField
                  name="password"
                  type="password"
                  label={translate('login.form.password')}
                  placeholder={translate('login.form.password.placeholder')}
                  required
                  validate={{
                    required: { value: true, errorMessage: translate('global.messages.validate.newpassword.required') },
                    minLength: { value: 4, errorMessage: translate('global.messages.validate.newpassword.minlength') },
                    maxLength: { value: 50, errorMessage: translate('global.messages.validate.newpassword.maxlength') },
                  }}
                />
                <AvGroup check inline>
                  <Label>
                    <AvInput type="checkbox" name="rememberMe" value={ true } />
                    <div>
                      <Translate contentKey="login.form.rememberme">Remember me</Translate>
                    </div>
                  </Label>
                </AvGroup>
              </Col>
            </Row>
          </ModalBody>
          <ModalFooter>
            <Button color="secondary" onClick={handleClose} tabIndex="1">
              <Translate contentKey="entity.action.cancel">Cancel</Translate>
            </Button>{' '}
            <Button color="primary" type="submit">
              <Translate contentKey="login.form.button">Sign in</Translate>
            </Button>
          </ModalFooter>
        </AvForm>
      </Modal>
    );
  }
}

export default LoginModal;
