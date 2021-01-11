import { Moment } from 'moment';

export interface IKeyBox {
  id?: number;
  userAccount?: string;
  password?: string;
  secondPassword?: string;
  loginAddress?: string;
  explain?: string;
  display?: boolean;
  createUser?: string;
  creatTime?: string;
  updateUser?: string;
  updateTime?: string;
  note?: string;
  userLinkFirstName?: string;
  userLinkId?: number;
}

export const defaultValue: Readonly<IKeyBox> = {
  display: false,
};
