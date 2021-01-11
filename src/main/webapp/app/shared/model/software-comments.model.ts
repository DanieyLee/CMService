import { Moment } from 'moment';

export interface ISoftwareComments {
  id?: number;
  txTitle?: string;
  content?: string;
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

export const defaultValue: Readonly<ISoftwareComments> = {};
