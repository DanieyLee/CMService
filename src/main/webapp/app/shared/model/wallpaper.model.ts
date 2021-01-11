import { Moment } from 'moment';
import { ImageType } from 'app/shared/model/enumerations/image-type.model';

export interface IWallpaper {
  id?: number;
  imageName?: string;
  imageUrl?: string;
  imagePixel?: string;
  imageType?: ImageType;
  visitorVolume?: number;
  isDownload?: boolean;
  like?: number;
  state?: boolean;
  createUser?: string;
  creatTime?: string;
  updateUser?: string;
  updateTime?: string;
  note?: string;
  userLinkFirstName?: string;
  userLinkId?: number;
}

export const defaultValue: Readonly<IWallpaper> = {
  isDownload: false,
  state: false,
};
