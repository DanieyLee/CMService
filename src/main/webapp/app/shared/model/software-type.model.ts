import { Moment } from 'moment';

export interface ISoftwareType {
  id?: number;
  type?: string;
  createUser?: string;
  creatTime?: string;
  updateUser?: string;
  updateTime?: string;
  note?: string;
}

export const defaultValue: Readonly<ISoftwareType> = {};
