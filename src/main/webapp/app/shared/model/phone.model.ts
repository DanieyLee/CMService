import { Moment } from 'moment';

export interface IPhone {
  id?: number;
  phone?: string;
  code?: number;
  effectiveTime?: string;
  sendTime?: string;
  createUser?: string;
  creatTime?: string;
  updateUser?: string;
  updateTime?: string;
  note?: string;
  userLinkFirstName?: string;
  userLinkId?: number;
}

export const defaultValue: Readonly<IPhone> = {};
