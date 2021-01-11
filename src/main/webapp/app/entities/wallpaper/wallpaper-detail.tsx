import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './wallpaper.reducer';
import { IWallpaper } from 'app/shared/model/wallpaper.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWallpaperDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WallpaperDetail = (props: IWallpaperDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { wallpaperEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cmServiceApp.wallpaper.detail.title">Wallpaper</Translate> [<b>{wallpaperEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="imageName">
              <Translate contentKey="cmServiceApp.wallpaper.imageName">Image Name</Translate>
            </span>
          </dt>
          <dd>{wallpaperEntity.imageName}</dd>
          <dt>
            <span id="imageUrl">
              <Translate contentKey="cmServiceApp.wallpaper.imageUrl">Image Url</Translate>
            </span>
          </dt>
          <dd>{wallpaperEntity.imageUrl}</dd>
          <dt>
            <span id="imagePixel">
              <Translate contentKey="cmServiceApp.wallpaper.imagePixel">Image Pixel</Translate>
            </span>
          </dt>
          <dd>{wallpaperEntity.imagePixel}</dd>
          <dt>
            <span id="imageType">
              <Translate contentKey="cmServiceApp.wallpaper.imageType">Image Type</Translate>
            </span>
          </dt>
          <dd>{wallpaperEntity.imageType}</dd>
          <dt>
            <span id="visitorVolume">
              <Translate contentKey="cmServiceApp.wallpaper.visitorVolume">Visitor Volume</Translate>
            </span>
          </dt>
          <dd>{wallpaperEntity.visitorVolume}</dd>
          <dt>
            <span id="isDownload">
              <Translate contentKey="cmServiceApp.wallpaper.isDownload">Is Download</Translate>
            </span>
          </dt>
          <dd>{wallpaperEntity.isDownload ? 'true' : 'false'}</dd>
          <dt>
            <span id="like">
              <Translate contentKey="cmServiceApp.wallpaper.like">Like</Translate>
            </span>
          </dt>
          <dd>{wallpaperEntity.like}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="cmServiceApp.wallpaper.state">State</Translate>
            </span>
          </dt>
          <dd>{wallpaperEntity.state ? 'true' : 'false'}</dd>
          <dt>
            <span id="createUser">
              <Translate contentKey="cmServiceApp.wallpaper.createUser">Create User</Translate>
            </span>
          </dt>
          <dd>{wallpaperEntity.createUser}</dd>
          <dt>
            <span id="creatTime">
              <Translate contentKey="cmServiceApp.wallpaper.creatTime">Creat Time</Translate>
            </span>
          </dt>
          <dd>
            {wallpaperEntity.creatTime ? <TextFormat value={wallpaperEntity.creatTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updateUser">
              <Translate contentKey="cmServiceApp.wallpaper.updateUser">Update User</Translate>
            </span>
          </dt>
          <dd>{wallpaperEntity.updateUser}</dd>
          <dt>
            <span id="updateTime">
              <Translate contentKey="cmServiceApp.wallpaper.updateTime">Update Time</Translate>
            </span>
          </dt>
          <dd>
            {wallpaperEntity.updateTime ? <TextFormat value={wallpaperEntity.updateTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="note">
              <Translate contentKey="cmServiceApp.wallpaper.note">Note</Translate>
            </span>
          </dt>
          <dd>{wallpaperEntity.note}</dd>
          <dt>
            <Translate contentKey="cmServiceApp.wallpaper.userLink">User Link</Translate>
          </dt>
          <dd>{wallpaperEntity.userLinkFirstName ? wallpaperEntity.userLinkFirstName : ''}</dd>
        </dl>
        <Button tag={Link} to="/wallpaper" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/wallpaper/${wallpaperEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ wallpaper }: IRootState) => ({
  wallpaperEntity: wallpaper.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WallpaperDetail);
