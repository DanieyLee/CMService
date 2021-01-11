import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './articl-enclosure.reducer';
import { IArticlEnclosure } from 'app/shared/model/articl-enclosure.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IArticlEnclosureDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArticlEnclosureDetail = (props: IArticlEnclosureDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { articlEnclosureEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cmServiceApp.articlEnclosure.detail.title">ArticlEnclosure</Translate> [<b>{articlEnclosureEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="enclosureURL">
              <Translate contentKey="cmServiceApp.articlEnclosure.enclosureURL">Enclosure URL</Translate>
            </span>
          </dt>
          <dd>{articlEnclosureEntity.enclosureURL}</dd>
          <dt>
            <span id="enclosureType">
              <Translate contentKey="cmServiceApp.articlEnclosure.enclosureType">Enclosure Type</Translate>
            </span>
          </dt>
          <dd>{articlEnclosureEntity.enclosureType}</dd>
          <dt>
            <span id="createUser">
              <Translate contentKey="cmServiceApp.articlEnclosure.createUser">Create User</Translate>
            </span>
          </dt>
          <dd>{articlEnclosureEntity.createUser}</dd>
          <dt>
            <span id="creatTime">
              <Translate contentKey="cmServiceApp.articlEnclosure.creatTime">Creat Time</Translate>
            </span>
          </dt>
          <dd>
            {articlEnclosureEntity.creatTime ? (
              <TextFormat value={articlEnclosureEntity.creatTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updateUser">
              <Translate contentKey="cmServiceApp.articlEnclosure.updateUser">Update User</Translate>
            </span>
          </dt>
          <dd>{articlEnclosureEntity.updateUser}</dd>
          <dt>
            <span id="updateTime">
              <Translate contentKey="cmServiceApp.articlEnclosure.updateTime">Update Time</Translate>
            </span>
          </dt>
          <dd>
            {articlEnclosureEntity.updateTime ? (
              <TextFormat value={articlEnclosureEntity.updateTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="note">
              <Translate contentKey="cmServiceApp.articlEnclosure.note">Note</Translate>
            </span>
          </dt>
          <dd>{articlEnclosureEntity.note}</dd>
          <dt>
            <Translate contentKey="cmServiceApp.articlEnclosure.article">Article</Translate>
          </dt>
          <dd>{articlEnclosureEntity.articleTitle ? articlEnclosureEntity.articleTitle : ''}</dd>
        </dl>
        <Button tag={Link} to="/articl-enclosure" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/articl-enclosure/${articlEnclosureEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ articlEnclosure }: IRootState) => ({
  articlEnclosureEntity: articlEnclosure.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArticlEnclosureDetail);
