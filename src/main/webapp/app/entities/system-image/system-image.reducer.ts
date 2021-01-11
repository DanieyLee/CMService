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

import { ISystemImage, defaultValue } from 'app/shared/model/system-image.model';

export const ACTION_TYPES = {
  FETCH_SYSTEMIMAGE_LIST: 'systemImage/FETCH_SYSTEMIMAGE_LIST',
  FETCH_SYSTEMIMAGE: 'systemImage/FETCH_SYSTEMIMAGE',
  CREATE_SYSTEMIMAGE: 'systemImage/CREATE_SYSTEMIMAGE',
  UPDATE_SYSTEMIMAGE: 'systemImage/UPDATE_SYSTEMIMAGE',
  DELETE_SYSTEMIMAGE: 'systemImage/DELETE_SYSTEMIMAGE',
  RESET: 'systemImage/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISystemImage>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type SystemImageState = Readonly<typeof initialState>;

// Reducer

export default (state: SystemImageState = initialState, action): SystemImageState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SYSTEMIMAGE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SYSTEMIMAGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SYSTEMIMAGE):
    case REQUEST(ACTION_TYPES.UPDATE_SYSTEMIMAGE):
    case REQUEST(ACTION_TYPES.DELETE_SYSTEMIMAGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SYSTEMIMAGE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SYSTEMIMAGE):
    case FAILURE(ACTION_TYPES.CREATE_SYSTEMIMAGE):
    case FAILURE(ACTION_TYPES.UPDATE_SYSTEMIMAGE):
    case FAILURE(ACTION_TYPES.DELETE_SYSTEMIMAGE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SYSTEMIMAGE_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_SYSTEMIMAGE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SYSTEMIMAGE):
    case SUCCESS(ACTION_TYPES.UPDATE_SYSTEMIMAGE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SYSTEMIMAGE):
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

const apiUrl = 'api/system-images';

// Actions

export const getEntities: ICrudGetAllAction<ISystemImage> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SYSTEMIMAGE_LIST,
    payload: axios.get<ISystemImage>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ISystemImage> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SYSTEMIMAGE,
    payload: axios.get<ISystemImage>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISystemImage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SYSTEMIMAGE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<ISystemImage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SYSTEMIMAGE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISystemImage> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SYSTEMIMAGE,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
