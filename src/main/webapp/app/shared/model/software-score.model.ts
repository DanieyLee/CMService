import { Moment } from 'moment';

export interface ISoftwareScore {
  id?: number;
  score?: number;
  createUser?: string;
  creatTime?: string;
  updateUser?: string;
  updateTime?: string;
  note?: string;
  softwareName?: string;
  softwareId?: number;
  userLinkFirstName?: string;
  userLinkId?: number;
}

export const defaultValue: Readonly<ISoftwareScore> = {};
