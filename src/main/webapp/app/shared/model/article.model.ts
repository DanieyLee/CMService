import { Moment } from 'moment';

export interface IArticle {
  id?: number;
  title?: string;
  author?: string;
  content?: any;
  views?: number;
  likeNumber?: number;
  state?: boolean;
  createUser?: string;
  creatTime?: string;
  updateUser?: string;
  updateTime?: string;
  note?: string;
  articleTypeType?: string;
  articleTypeId?: number;
  userLinkFirstName?: string;
  userLinkId?: number;
}

export const defaultValue: Readonly<IArticle> = {
  state: false,
};
