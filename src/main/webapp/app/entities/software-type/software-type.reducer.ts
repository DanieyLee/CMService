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

import { ISoftwareType, defaultValue } from 'app/shared/model/software-type.model';

export const ACTION_TYPES = {
  FETCH_SOFTWARETYPE_LIST: 'softwareType/FETCH_SOFTWARETYPE_LIST',
  FETCH_SOFTWARETYPE: 'softwareType/FETCH_SOFTWARETYPE',
  CREATE_SOFTWARETYPE: 'softwareType/CREATE_SOFTWARETYPE',
  UPDATE_SOFTWARETYPE: 'softwareType/UPDATE_SOFTWARETYPE',
  DELETE_SOFTWARETYPE: 'softwareType/DELETE_SOFTWARETYPE',
  RESET: 'softwareType/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISoftwareType>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type SoftwareTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: SoftwareTypeState = initialState, action): SoftwareTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SOFTWARETYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SOFTWARETYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SOFTWARETYPE):
    case REQUEST(ACTION_TYPES.UPDATE_SOFTWARETYPE):
    case REQUEST(ACTION_TYPES.DELETE_SOFTWARETYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SOFTWARETYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SOFTWARETYPE):
    case FAILURE(ACTION_TYPES.CREATE_SOFTWARETYPE):
    case FAILURE(ACTION_TYPES.UPDATE_SOFTWARETYPE):
    case FAILURE(ACTION_TYPES.DELETE_SOFTWARETYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SOFTWARETYPE_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_SOFTWARETYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SOFTWARETYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_SOFTWARETYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SOFTWARETYPE):
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

const apiUrl = 'api/software-types';

// Actions

export const getEntities: ICrudGetAllAction<ISoftwareType> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SOFTWARETYPE_LIST,
    payload: axios.get<ISoftwareType>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getSoftwareTypeEntities: ICrudGetAllAction<ISoftwareType> = (page, size, sort) => {
  const requestUrl = `api/public/software-types${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SOFTWARETYPE_LIST,
    payload: axios.get<ISoftwareType>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ISoftwareType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SOFTWARETYPE,
    payload: axios.get<ISoftwareType>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISoftwareType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SOFTWARETYPE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const createUserEntity: ICrudPutAction<ISoftwareType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SOFTWARETYPE,
    payload: axios.post(`${apiUrl}/create`, cleanEntity(entity)),
  });
  return result;
};

export const updateUserEntity: ICrudPutAction<ISoftwareType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SOFTWARETYPE,
    payload: axios.put(`${apiUrl}/update`, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<ISoftwareType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SOFTWARETYPE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISoftwareType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SOFTWARETYPE,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
