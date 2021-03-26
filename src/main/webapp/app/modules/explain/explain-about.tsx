import React from 'react';
import { Translate } from 'react-jhipster';
import { connect } from 'react-redux';

export type IExplainProp = StateProps;

export const ExplainAbout = (props: IExplainProp) => {
  return (
    <div className="content-explain-about">
      <h2>
        <Translate contentKey="explain.title.about">About</Translate>
      </h2>
      <h5>
        <Translate contentKey="explain.subtitle.about">SubtitleAbout</Translate>
      </h5>
      <p>
        <Translate contentKey="explain.text.about">TextAbout</Translate>
      </p>
    </div>
  );
};

const mapStateToProps = storeState => ({
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(ExplainAbout);
