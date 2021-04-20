import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteUserEntity } from 'app/entities/wallpaper/wallpaper.reducer';

export interface IWallpaperManagementDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WallpaperManagementDeleteDialog = (props: IWallpaperManagementDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/system/wallpaper-management' + props.location.search);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteUserEntity(props.wallpaperEntity.id);
  };

  const { wallpaperEntity } = props;
  return (
    <Modal isOpen toggle={handleClose} className="content-modal-dialog-alert">
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="cmServiceApp.wallpaper.delete.question">
        <Translate contentKey="cmServiceApp.wallpaper.delete.question" interpolate={{ id: wallpaperEntity.id }}>
          Are you sure you want to delete this Wallpaper?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-wallpaper" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ wallpaper }: IRootState) => ({
  wallpaperEntity: wallpaper.entity,
  updateSuccess: wallpaper.updateSuccess,
});

const mapDispatchToProps = { getEntity, deleteUserEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WallpaperManagementDeleteDialog);
