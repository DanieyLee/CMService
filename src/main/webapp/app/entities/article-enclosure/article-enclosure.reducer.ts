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

import { IArticleEnclosure, defaultValue } from 'app/shared/model/article-enclosure.model';

export const ACTION_TYPES = {
  FETCH_ARTICLEENCLOSURE_LIST: 'articleEnclosure/FETCH_ARTICLEENCLOSURE_LIST',
  FETCH_ARTICLEENCLOSURE: 'articleEnclosure/FETCH_ARTICLEENCLOSURE',
  CREATE_ARTICLEENCLOSURE: 'articleEnclosure/CREATE_ARTICLEENCLOSURE',
  UPDATE_ARTICLEENCLOSURE: 'articleEnclosure/UPDATE_ARTICLEENCLOSURE',
  DELETE_ARTICLEENCLOSURE: 'articleEnclosure/DELETE_ARTICLEENCLOSURE',
  RESET: 'articleEnclosure/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IArticleEnclosure>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ArticleEnclosureState = Readonly<typeof initialState>;

// Reducer

export default (state: ArticleEnclosureState = initialState, action): ArticleEnclosureState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ARTICLEENCLOSURE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ARTICLEENCLOSURE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ARTICLEENCLOSURE):
    case REQUEST(ACTION_TYPES.UPDATE_ARTICLEENCLOSURE):
    case REQUEST(ACTION_TYPES.DELETE_ARTICLEENCLOSURE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ARTICLEENCLOSURE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ARTICLEENCLOSURE):
    case FAILURE(ACTION_TYPES.CREATE_ARTICLEENCLOSURE):
    case FAILURE(ACTION_TYPES.UPDATE_ARTICLEENCLOSURE):
    case FAILURE(ACTION_TYPES.DELETE_ARTICLEENCLOSURE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ARTICLEENCLOSURE_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_ARTICLEENCLOSURE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ARTICLEENCLOSURE):
    case SUCCESS(ACTION_TYPES.UPDATE_ARTICLEENCLOSURE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ARTICLEENCLOSURE):
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

const apiUrl = 'api/article-enclosures';

// Actions

export const getEntities: ICrudGetAllAction<IArticleEnclosure> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ARTICLEENCLOSURE_LIST,
    payload: axios.get<IArticleEnclosure>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IArticleEnclosure> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ARTICLEENCLOSURE,
    payload: axios.get<IArticleEnclosure>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IArticleEnclosure> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ARTICLEENCLOSURE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IArticleEnclosure> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ARTICLEENCLOSURE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IArticleEnclosure> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ARTICLEENCLOSURE,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
