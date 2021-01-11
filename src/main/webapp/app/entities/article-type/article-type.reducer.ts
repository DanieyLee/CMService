import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction,
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IArticleType, defaultValue } from 'app/shared/model/article-type.model';

export const ACTION_TYPES = {
  FETCH_ARTICLETYPE_LIST: 'articleType/FETCH_ARTICLETYPE_LIST',
  FETCH_ARTICLETYPE: 'articleType/FETCH_ARTICLETYPE',
  CREATE_ARTICLETYPE: 'articleType/CREATE_ARTICLETYPE',
  UPDATE_ARTICLETYPE: 'articleType/UPDATE_ARTICLETYPE',
  DELETE_ARTICLETYPE: 'articleType/DELETE_ARTICLETYPE',
  RESET: 'articleType/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IArticleType>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ArticleTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: ArticleTypeState = initialState, action): ArticleTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ARTICLETYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ARTICLETYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ARTICLETYPE):
    case REQUEST(ACTION_TYPES.UPDATE_ARTICLETYPE):
    case REQUEST(ACTION_TYPES.DELETE_ARTICLETYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ARTICLETYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ARTICLETYPE):
    case FAILURE(ACTION_TYPES.CREATE_ARTICLETYPE):
    case FAILURE(ACTION_TYPES.UPDATE_ARTICLETYPE):
    case FAILURE(ACTION_TYPES.DELETE_ARTICLETYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ARTICLETYPE_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_ARTICLETYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ARTICLETYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_ARTICLETYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ARTICLETYPE):
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

const apiUrl = 'api/article-types';

// Actions

export const getEntities: ICrudGetAllAction<IArticleType> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ARTICLETYPE_LIST,
    payload: axios.get<IArticleType>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IArticleType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ARTICLETYPE,
    payload: axios.get<IArticleType>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IArticleType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ARTICLETYPE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IArticleType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ARTICLETYPE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IArticleType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ARTICLETYPE,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
