import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './software.reducer';
import { ISoftware } from 'app/shared/model/software.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISoftwareDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SoftwareDetail = (props: ISoftwareDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { softwareEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cmServiceApp.software.detail.title">Software</Translate> [<b>{softwareEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="stars">
              <Translate contentKey="cmServiceApp.software.stars">Stars</Translate>
            </span>
          </dt>
          <dd>{softwareEntity.stars ? 'true' : 'false'}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="cmServiceApp.software.name">Name</Translate>
            </span>
          </dt>
          <dd>{softwareEntity.name}</dd>
          <dt>
            <span id="explain">
              <Translate contentKey="cmServiceApp.software.explain">Explain</Translate>
            </span>
          </dt>
          <dd>{softwareEntity.explain}</dd>
          <dt>
            <span id="softwareICO">
              <Translate contentKey="cmServiceApp.software.softwareICO">Software ICO</Translate>
            </span>
          </dt>
          <dd>
            {softwareEntity.softwareICO ? (
              <div>
                {softwareEntity.softwareICOContentType ? (
                  <a onClick={openFile(softwareEntity.softwareICOContentType, softwareEntity.softwareICO)}>
                    <img
                      src={`data:${softwareEntity.softwareICOContentType};base64,${softwareEntity.softwareICO}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {softwareEntity.softwareICOContentType}, {byteSize(softwareEntity.softwareICO)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="score">
              <Translate contentKey="cmServiceApp.software.score">Score</Translate>
            </span>
          </dt>
          <dd>{softwareEntity.score}</dd>
          <dt>
            <span id="size">
              <Translate contentKey="cmServiceApp.software.size">Size</Translate>
            </span>
          </dt>
          <dd>{softwareEntity.size}</dd>
          <dt>
            <span id="version">
              <Translate contentKey="cmServiceApp.software.version">Version</Translate>
            </span>
          </dt>
          <dd>{softwareEntity.version}</dd>
          <dt>
            <span id="applySystem">
              <Translate contentKey="cmServiceApp.software.applySystem">Apply System</Translate>
            </span>
          </dt>
          <dd>{softwareEntity.applySystem}</dd>
          <dt>
            <span id="show">
              <Translate contentKey="cmServiceApp.software.show">Show</Translate>
            </span>
          </dt>
          <dd>{softwareEntity.show ? 'true' : 'false'}</dd>
          <dt>
            <span id="allow">
              <Translate contentKey="cmServiceApp.software.allow">Allow</Translate>
            </span>
          </dt>
          <dd>{softwareEntity.allow ? 'true' : 'false'}</dd>
          <dt>
            <span id="downloadUrl">
              <Translate contentKey="cmServiceApp.software.downloadUrl">Download Url</Translate>
            </span>
          </dt>
          <dd>{softwareEntity.downloadUrl}</dd>
          <dt>
            <span id="downloadNumber">
              <Translate contentKey="cmServiceApp.software.downloadNumber">Download Number</Translate>
            </span>
          </dt>
          <dd>{softwareEntity.downloadNumber}</dd>
          <dt>
            <span id="browseNumber">
              <Translate contentKey="cmServiceApp.software.browseNumber">Browse Number</Translate>
            </span>
          </dt>
          <dd>{softwareEntity.browseNumber}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="cmServiceApp.software.state">State</Translate>
            </span>
          </dt>
          <dd>{softwareEntity.state ? 'true' : 'false'}</dd>
          <dt>
            <span id="createUser">
              <Translate contentKey="cmServiceApp.software.createUser">Create User</Translate>
            </span>
          </dt>
          <dd>{softwareEntity.createUser}</dd>
          <dt>
            <span id="creatTime">
              <Translate contentKey="cmServiceApp.software.creatTime">Creat Time</Translate>
            </span>
          </dt>
          <dd>{softwareEntity.creatTime ? <TextFormat value={softwareEntity.creatTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updateUser">
              <Translate contentKey="cmServiceApp.software.updateUser">Update User</Translate>
            </span>
          </dt>
          <dd>{softwareEntity.updateUser}</dd>
          <dt>
            <span id="updateTime">
              <Translate contentKey="cmServiceApp.software.updateTime">Update Time</Translate>
            </span>
          </dt>
          <dd>
            {softwareEntity.updateTime ? <TextFormat value={softwareEntity.updateTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="note">
              <Translate contentKey="cmServiceApp.software.note">Note</Translate>
            </span>
          </dt>
          <dd>{softwareEntity.note}</dd>
          <dt>
            <Translate contentKey="cmServiceApp.software.softwareType">Software Type</Translate>
          </dt>
          <dd>{softwareEntity.softwareTypeType ? softwareEntity.softwareTypeType : ''}</dd>
          <dt>
            <Translate contentKey="cmServiceApp.software.userLink">User Link</Translate>
          </dt>
          <dd>{softwareEntity.userLinkFirstName ? softwareEntity.userLinkFirstName : ''}</dd>
        </dl>
        <Button tag={Link} to="/software" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/software/${softwareEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ software }: IRootState) => ({
  softwareEntity: software.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SoftwareDetail);
