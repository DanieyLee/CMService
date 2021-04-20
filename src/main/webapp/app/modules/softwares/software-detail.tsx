import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { AvForm } from 'availity-reactstrap-validation';

import { IRootState } from 'app/shared/reducers';
import { getPublicEntity, likeEntity, downloadEntity } from 'app/entities/software/software.reducer';
import { APP_DATE_FORMAT_SIMPLE_ZH_CN } from 'app/config/constants';
import SoftwareComment from './software-comment';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

export interface ISoftwareDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SoftwareDetail = (props: ISoftwareDetailProps) => {
  useEffect(() => {
    props.getPublicEntity(props.match.params.id);
  }, []);

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

  const downloadFile = (event) => {
    countDown("software-download",10);
    props.downloadEntity(props.match.params.id);
    event.preventDefault();
  }

  const likeValidSubmit = (event) => {
    countDown("send-submit",10);
    props.likeEntity(props.match.params.id);
    event.preventDefault();
  };

  const { softwareEntity } = props;
  return (
    <div className="content-software-details">
      <Row>
        <Col md="8">
          <Col md="12" className="content-software-details-title">
            <span>{softwareEntity.softwareTypeType}</span>
            {softwareEntity.stars ? (<FontAwesomeIcon icon={'star'}/>) : ''}
            <span>{softwareEntity.name}</span>
          </Col>
          <Row className="content-software-details-system">
            <Col md="4">
              <Translate contentKey="cmServiceApp.software.applySystem">Apply System</Translate>
              ：{softwareEntity.applySystem}
            </Col>
            <Col md="4">
              <Translate contentKey="cmServiceApp.software.version">Version</Translate>
              ：{softwareEntity.version}
            </Col>
            <Col md="4">
              <Translate contentKey="cmServiceApp.software.size">Size</Translate>
              ：{softwareEntity.size}
            </Col>
          </Row>
          <Col md="12" className="content-software-details-download">
            <AvForm id="send-form" onValidSubmit={downloadFile}>
              <Button id="software-download" className={softwareEntity.allow ? "" : "content-software-details-title-no-allow"} type="submit">
                <Translate contentKey="cmServiceApp.software.clickDownload">Click Download</Translate>
              </Button>
              <span>({softwareEntity.downloadNumber})</span>
            </AvForm>
          </Col>
        </Col>
        <Col md="4" className="content-software-details-ico">
          <div>
            {softwareEntity.softwareICO ? (
              <img
                src={`data:${softwareEntity.softwareICOContentType};base64,${softwareEntity.softwareICO}`}
              />
              ) : <img src="content/images/image.svg" alt="image" />}
          </div>
        </Col>
        <Col md="12" className="content-software-details-explain">
          <div>{softwareEntity.explain}</div>
        </Col>
        <Row className="content-software-details-like">
          <Col md="9">
            <Translate contentKey="cmServiceApp.article.modify.title" interpolate={{ name: softwareEntity.updateUser,time:'' }} />
            <TextFormat value={softwareEntity.updateTime} type="date" format={APP_DATE_FORMAT_SIMPLE_ZH_CN} />
            <Translate contentKey="cmServiceApp.article.modify.tail">tail</Translate>
          </Col>
          <Col md="3">
            <FontAwesomeIcon icon={'eye'} />
            <span>{softwareEntity.browseNumber > 1000 ? softwareEntity.browseNumber.toString().substring(0,softwareEntity.browseNumber.toString().length-3) + "k" : softwareEntity.browseNumber}</span>
            <FontAwesomeIcon icon={'heart'} />
            <span>{softwareEntity.score > 1000? softwareEntity.score.toString().substring(0,softwareEntity.score.toString().length-3) + "k" : softwareEntity.score}</span>
          </Col>
          <Col md="12">
            <div>{softwareEntity.note}</div>
          </Col>
          <AvForm id="send-form" onValidSubmit={likeValidSubmit}>
            <Button id="send-submit" color="primary" type="submit">
              <img src="content/images/like.svg" alt="like" />
              <Translate contentKey="cmServiceApp.wallpaper.like">Like</Translate>
            </Button>
          </AvForm>
        </Row>
      </Row>
      <hr/>
      <ErrorBoundaryRoute path={`/softwares/detail/:id`} component={SoftwareComment} />
    </div>
  );
};

const mapStateToProps = ({ software }: IRootState) => ({
  softwareEntity: software.entity,
});

const mapDispatchToProps = {
  getPublicEntity,
  likeEntity,
  downloadEntity
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SoftwareDetail);
