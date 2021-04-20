import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getPublicEntity, likeEntity, getNearEntity } from 'app/entities/wallpaper/wallpaper.reducer';
import { AvForm } from 'availity-reactstrap-validation';

export interface IWallpaperDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WallpaperDetail = (props: IWallpaperDetailProps) => {
  useEffect(() => {
    props.getPublicEntity(props.match.params.id);
  }, []);

  useEffect(() => {
    props.history.push(`${props.wallpaperEntity.id}`);
  }, [props.wallpaperEntity.id]);

  const goBack = () => {
    props.history.push("/wallpapers/0");
  }

  const turnPage = (event, id, turn) => {
    props.getNearEntity(id, turn);
  }

  const countDown = (name, time) => {
    const button = document.getElementById(name) as HTMLButtonElement;
    let num = time;
    button.setAttribute("disabled","true");
    const interval = setInterval(() => {
      if (num > 1){
        num--;
      } else {
        button.removeAttribute("disabled");
        clearInterval(interval);
      }
    }, 1000);
  }

  const likeValidSubmit = (event) => {
    countDown("send-submit",3);
    props.likeEntity(props.wallpaperEntity.id);
    event.preventDefault();
  };

  const { wallpaperEntity } = props;
  return (
    <div className="content-wallpaper-details">
      <Row>
        <Col md="11">
          <span>{wallpaperEntity.imagePixel}</span>
          <span>{wallpaperEntity.imageName}</span>
        </Col>
        <Col md="1">
          <Button color="danger" onClick={goBack}>
            <FontAwesomeIcon icon="times-circle" />
          </Button>
        </Col>
        <Col md="12">
          <span>{wallpaperEntity.imageType}</span>
          <span>{wallpaperEntity.note}</span>
          <img src={`${wallpaperEntity.imageUrl}`} alt={`${wallpaperEntity.imageName}`} />
          <Row>
            <Col md="4">
              <AvForm id="send-form" onValidSubmit={likeValidSubmit}>
                <Button id="send-submit" color="primary" type="submit">
                  <img src="content/images/like.svg" alt="like" />
                  <Translate contentKey="cmServiceApp.wallpaper.like">Like</Translate>
                </Button>
              </AvForm>
            </Col>
            <Col md="4">
              <div>
                <Button color="danger" onClick={(event) => {turnPage(event, wallpaperEntity.id,true)}}>
                  <Translate contentKey="cmServiceApp.wallpaper.lastPage">Last Page</Translate>
                </Button>
                <Button color="danger" onClick={(event) => {turnPage(event, wallpaperEntity.id,false)}}>
                  <Translate contentKey="cmServiceApp.wallpaper.nextPage">Next Page</Translate>
                </Button>
              </div>
            </Col>
            <Col md="4" className="content-wallpaper-details-like">
              <FontAwesomeIcon icon={'eye'} />
              <span>{wallpaperEntity.visitorVolume > 1000 ? wallpaperEntity.visitorVolume.toString().substring(0,wallpaperEntity.visitorVolume.toString().length-3) + "k" : wallpaperEntity.visitorVolume}</span>
              <FontAwesomeIcon icon={'heart'} />
              <span>{wallpaperEntity.like > 1000? wallpaperEntity.like.toString().substring(0,wallpaperEntity.like.toString().length-3) + "k" : wallpaperEntity.like}</span>
            </Col>
          </Row>
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = ({ wallpaper }: IRootState) => ({
  wallpaperEntity: wallpaper.entity,
});

const mapDispatchToProps = {
  getPublicEntity,
  likeEntity,
  getNearEntity,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WallpaperDetail);
