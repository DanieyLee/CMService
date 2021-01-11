import { Moment } from 'moment';
import { FileType } from 'app/shared/model/enumerations/file-type.model';

export interface IArticlEnclosure {
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

export const defaultValue: Readonly<IArticlEnclosure> = {};
