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

import { IUserLink, defaultValue } from 'app/shared/model/user-link.model';

export const ACTION_TYPES = {
  FETCH_USERLINK_LIST: 'userLink/FETCH_USERLINK_LIST',
  FETCH_USERLINK: 'userLink/FETCH_USERLINK',
  CREATE_USERLINK: 'userLink/CREATE_USERLINK',
  UPDATE_USERLINK: 'userLink/UPDATE_USERLINK',
  DELETE_USERLINK: 'userLink/DELETE_USERLINK',
  RESET: 'userLink/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUserLink>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type UserLinkState = Readonly<typeof initialState>;

// Reducer

export default (state: UserLinkState = initialState, action): UserLinkState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_USERLINK_LIST):
    case REQUEST(ACTION_TYPES.FETCH_USERLINK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_USERLINK):
    case REQUEST(ACTION_TYPES.UPDATE_USERLINK):
    case REQUEST(ACTION_TYPES.DELETE_USERLINK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_USERLINK_LIST):
    case FAILURE(ACTION_TYPES.FETCH_USERLINK):
    case FAILURE(ACTION_TYPES.CREATE_USERLINK):
    case FAILURE(ACTION_TYPES.UPDATE_USERLINK):
    case FAILURE(ACTION_TYPES.DELETE_USERLINK):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERLINK_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_USERLINK):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_USERLINK):
    case SUCCESS(ACTION_TYPES.UPDATE_USERLINK):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_USERLINK):
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

const apiUrl = 'api/user-links';

// Actions

export const getEntities: ICrudGetAllAction<IUserLink> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_USERLINK_LIST,
    payload: axios.get<IUserLink>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IUserLink> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USERLINK,
    payload: axios.get<IUserLink>(requestUrl),
  };
};

export const getUserEntity: ICrudGetAction<IUserLink> = id => {
  const requestUrl = `${apiUrl}/user-id/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USERLINK,
    payload: axios.get<IUserLink>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IUserLink> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_USERLINK,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IUserLink> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_USERLINK,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUserLink> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_USERLINK,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
