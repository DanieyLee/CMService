import { Moment } from 'moment';
import { SystemType } from 'app/shared/model/enumerations/system-type.model';

export interface ISoftware {
  id?: number;
  stars?: boolean;
  name?: string;
  explain?: string;
  softwareICOContentType?: string;
  softwareICO?: any;
  score?: number;
  size?: number;
  version?: string;
  applySystem?: SystemType;
  show?: boolean;
  allow?: boolean;
  downloadUrl?: string;
  downloadNumber?: number;
  browseNumber?: number;
  state?: boolean;
  createUser?: string;
  creatTime?: string;
  updateUser?: string;
  updateTime?: string;
  note?: string;
  softwareTypeType?: string;
  softwareTypeId?: number;
  userLinkFirstName?: string;
  userLinkId?: number;
}

export const defaultValue: Readonly<ISoftware> = {
  stars: false,
  show: false,
  allow: false,
  state: false,
};
