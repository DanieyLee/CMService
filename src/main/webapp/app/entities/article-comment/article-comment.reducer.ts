import axios, { AxiosResponse } from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IArticleComment, defaultValue } from 'app/shared/model/article-comment.model';

export const ACTION_TYPES = {
  FETCH_ARTICLECOMMENT_LIST: 'articleComment/FETCH_ARTICLECOMMENT_LIST',
  FETCH_ARTICLECOMMENT: 'articleComment/FETCH_ARTICLECOMMENT',
  CREATE_ARTICLECOMMENT: 'articleComment/CREATE_ARTICLECOMMENT',
  UPDATE_ARTICLECOMMENT: 'articleComment/UPDATE_ARTICLECOMMENT',
  DELETE_ARTICLECOMMENT: 'articleComment/DELETE_ARTICLECOMMENT',
  RESET: 'articleComment/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IArticleComment>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ArticleCommentState = Readonly<typeof initialState>;

// Reducer

export default (state: ArticleCommentState = initialState, action): ArticleCommentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ARTICLECOMMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ARTICLECOMMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ARTICLECOMMENT):
    case REQUEST(ACTION_TYPES.UPDATE_ARTICLECOMMENT):
    case REQUEST(ACTION_TYPES.DELETE_ARTICLECOMMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ARTICLECOMMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ARTICLECOMMENT):
    case FAILURE(ACTION_TYPES.CREATE_ARTICLECOMMENT):
    case FAILURE(ACTION_TYPES.UPDATE_ARTICLECOMMENT):
    case FAILURE(ACTION_TYPES.DELETE_ARTICLECOMMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ARTICLECOMMENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_ARTICLECOMMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ARTICLECOMMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_ARTICLECOMMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ARTICLECOMMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/article-comments';

// Actions

export const getEntities: ICrudGetAllAction<IArticleComment> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ARTICLECOMMENT_LIST,
    payload: axios.get<IArticleComment>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getPublicArticleEntities: (id, page, size, sort) => { payload: Promise<AxiosResponse>; type: string } = (id, page, size, sort) => {
  const requestUrl = `api/public/article-comments/${id}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ARTICLECOMMENT_LIST,
    payload: axios.get<IArticleComment>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IArticleComment> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ARTICLECOMMENT,
    payload: axios.get<IArticleComment>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IArticleComment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ARTICLECOMMENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IArticleComment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ARTICLECOMMENT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IArticleComment> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ARTICLECOMMENT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
