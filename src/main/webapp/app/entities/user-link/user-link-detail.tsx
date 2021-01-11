import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-link.reducer';
import { IUserLink } from 'app/shared/model/user-link.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserLinkDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserLinkDetail = (props: IUserLinkDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { userLinkEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cmServiceApp.userLink.detail.title">UserLink</Translate> [<b>{userLinkEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="firstName">
              <Translate contentKey="cmServiceApp.userLink.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{userLinkEntity.firstName}</dd>
          <dt>
            <span id="sex">
              <Translate contentKey="cmServiceApp.userLink.sex">Sex</Translate>
            </span>
          </dt>
          <dd>{userLinkEntity.sex ? 'true' : 'false'}</dd>
          <dt>
            <span id="age">
              <Translate contentKey="cmServiceApp.userLink.age">Age</Translate>
            </span>
          </dt>
          <dd>{userLinkEntity.age}</dd>
          <dt>
            <span id="theme">
              <Translate contentKey="cmServiceApp.userLink.theme">Theme</Translate>
            </span>
          </dt>
          <dd>{userLinkEntity.theme}</dd>
          <dt>
            <span id="passwordKey">
              <Translate contentKey="cmServiceApp.userLink.passwordKey">Password Key</Translate>
            </span>
          </dt>
          <dd>{userLinkEntity.passwordKey}</dd>
          <dt>
            <Translate contentKey="cmServiceApp.userLink.user">User</Translate>
          </dt>
          <dd>{userLinkEntity.userLogin ? userLinkEntity.userLogin : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-link" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-link/${userLinkEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ userLink }: IRootState) => ({
  userLinkEntity: userLink.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserLinkDetail);
