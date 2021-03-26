import axios, { AxiosResponse } from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IArticle, defaultValue } from 'app/shared/model/article.model';

export const ACTION_TYPES = {
  FETCH_ARTICLE_LIST: 'article/FETCH_ARTICLE_LIST',
  FETCH_ARTICLE: 'article/FETCH_ARTICLE',
  CREATE_ARTICLE: 'article/CREATE_ARTICLE',
  UPDATE_ARTICLE: 'article/UPDATE_ARTICLE',
  DELETE_ARTICLE: 'article/DELETE_ARTICLE',
  SET_BLOB: 'article/SET_BLOB',
  RESET: 'article/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IArticle>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ArticleState = Readonly<typeof initialState>;

// Reducer

export default (state: ArticleState = initialState, action): ArticleState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ARTICLE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ARTICLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ARTICLE):
    case REQUEST(ACTION_TYPES.UPDATE_ARTICLE):
    case REQUEST(ACTION_TYPES.DELETE_ARTICLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ARTICLE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ARTICLE):
    case FAILURE(ACTION_TYPES.CREATE_ARTICLE):
    case FAILURE(ACTION_TYPES.UPDATE_ARTICLE):
    case FAILURE(ACTION_TYPES.DELETE_ARTICLE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ARTICLE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_ARTICLE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ARTICLE):
    case SUCCESS(ACTION_TYPES.UPDATE_ARTICLE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ARTICLE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/articles';

// Actions

export const getEntities: ICrudGetAllAction<IArticle> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ARTICLE_LIST,
    payload: axios.get<IArticle>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getPublicTypeEntities: (id, page, size, sort) => { payload: Promise<AxiosResponse>; type: string } = (id, page, size, sort) => {
  const requestUrl = `api/public/articles/type/${id}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ARTICLE_LIST,
    payload: axios.get<IArticle>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getTopPublicEntities: ICrudGetAllAction<IArticle> = () => {
  const requestUrl = `api/public/articles/top`;
  return {
    type: ACTION_TYPES.FETCH_ARTICLE_LIST,
    payload: axios.get<IArticle>(requestUrl),
  };
};

export const getEntity: ICrudGetAction<IArticle> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ARTICLE,
    payload: axios.get<IArticle>(requestUrl),
  };
};

export const getPublicEntity: ICrudGetAction<IArticle> = id => {
  const requestUrl = `api/public/articles/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ARTICLE,
    payload: axios.get<IArticle>(requestUrl),
  };
};

export const likeEntity: ICrudGetAction<IArticle> = id => {
  const requestUrl = `api/public/articles/like/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ARTICLE,
    payload: axios.get<IArticle>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IArticle> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ARTICLE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IArticle> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ARTICLE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IArticle> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ARTICLE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
