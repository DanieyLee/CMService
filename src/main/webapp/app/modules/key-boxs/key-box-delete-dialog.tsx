import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IKeyBox } from 'app/shared/model/key-box.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteUserEntity } from 'app/entities/key-box/key-box.reducer';

export interface IKeyBoxDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const KeyBoxDeleteDialog = (props: IKeyBoxDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/key-boxs' + props.location.search);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteUserEntity(props.keyBoxEntity.id);
  };

  const { keyBoxEntity } = props;
  return (
    <Modal isOpen toggle={handleClose} className="content-modal-dialog-alert">
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="cmServiceApp.keyBox.delete.question">
        <Translate contentKey="cmServiceApp.keyBox.delete.question" interpolate={{ id: keyBoxEntity.explain }}>
          Are you sure you want to delete this KeyBox?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-keyBox" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ keyBox }: IRootState) => ({
  keyBoxEntity: keyBox.entity,
  updateSuccess: keyBox.updateSuccess,
});

const mapDispatchToProps = { getEntity, deleteUserEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(KeyBoxDeleteDialog);
