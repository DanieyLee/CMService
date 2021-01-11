import { Moment } from 'moment';

export interface IArticleComment {
  id?: number;
  content?: string;
  createUser?: string;
  creatTime?: string;
  updateUser?: string;
  updateTime?: string;
  note?: string;
  articleTitle?: string;
  articleId?: number;
  userLinkFirstName?: string;
  userLinkId?: number;
}

export const defaultValue: Readonly<IArticleComment> = {};
