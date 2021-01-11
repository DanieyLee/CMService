import { Moment } from 'moment';
import { ImageType } from 'app/shared/model/enumerations/image-type.model';

export interface ISystemImage {
  id?: number;
  imageURL?: string;
  imageType?: ImageType;
  createUser?: string;
  creatTime?: string;
  updateUser?: string;
  updateTime?: string;
  note?: string;
  userLinkFirstName?: string;
  userLinkId?: number;
}

export const defaultValue: Readonly<ISystemImage> = {};
