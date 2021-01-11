import { Moment } from 'moment';
import { FileType } from 'app/shared/model/enumerations/file-type.model';

export interface IArticleEnclosure {
  id?: number;
  enclosureURL?: string;
  enclosureType?: FileType;
  createUser?: string;
  creatTime?: string;
  updateUser?: string;
  updateTime?: string;
  note?: string;
  articleTitle?: string;
  articleId?: number;
}

export const defaultValue: Readonly<IArticleEnclosure> = {};
