import axios, { AxiosResponse } from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISoftwareComments, defaultValue } from 'app/shared/model/software-comments.model';

export const ACTION_TYPES = {
  FETCH_SOFTWARECOMMENTS_LIST: 'softwareComments/FETCH_SOFTWARECOMMENTS_LIST',
  FETCH_SOFTWARECOMMENTS: 'softwareComments/FETCH_SOFTWARECOMMENTS',
  CREATE_SOFTWARECOMMENTS: 'softwareComments/CREATE_SOFTWARECOMMENTS',
  UPDATE_SOFTWARECOMMENTS: 'softwareComments/UPDATE_SOFTWARECOMMENTS',
  DELETE_SOFTWARECOMMENTS: 'softwareComments/DELETE_SOFTWARECOMMENTS',
  RESET: 'softwareComments/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISoftwareComments>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type SoftwareCommentsState = Readonly<typeof initialState>;

// Reducer

export default (state: SoftwareCommentsState = initialState, action): SoftwareCommentsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SOFTWARECOMMENTS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SOFTWARECOMMENTS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SOFTWARECOMMENTS):
    case REQUEST(ACTION_TYPES.UPDATE_SOFTWARECOMMENTS):
    case REQUEST(ACTION_TYPES.DELETE_SOFTWARECOMMENTS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SOFTWARECOMMENTS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SOFTWARECOMMENTS):
    case FAILURE(ACTION_TYPES.CREATE_SOFTWARECOMMENTS):
    case FAILURE(ACTION_TYPES.UPDATE_SOFTWARECOMMENTS):
    case FAILURE(ACTION_TYPES.DELETE_SOFTWARECOMMENTS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SOFTWARECOMMENTS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SOFTWARECOMMENTS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SOFTWARECOMMENTS):
    case SUCCESS(ACTION_TYPES.UPDATE_SOFTWARECOMMENTS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SOFTWARECOMMENTS):
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

const apiUrl = 'api/software-comments';

// Actions

export const getEntities: ICrudGetAllAction<ISoftwareComments> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SOFTWARECOMMENTS_LIST,
    payload: axios.get<ISoftwareComments>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getPublicSoftwareEntities: (id, page, size, sort) => { payload: Promise<AxiosResponse>; type: string } = (id, page, size, sort) => {
  const requestUrl = `api/public/software-comments/${id}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SOFTWARECOMMENTS_LIST,
    payload: axios.get<ISoftwareComments>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ISoftwareComments> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SOFTWARECOMMENTS,
    payload: axios.get<ISoftwareComments>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISoftwareComments> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SOFTWARECOMMENTS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const replySoftware: ICrudPutAction<ISoftwareComments> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SOFTWARECOMMENTS,
    payload: axios.post(`${apiUrl}/reply`, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISoftwareComments> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SOFTWARECOMMENTS,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISoftwareComments> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SOFTWARECOMMENTS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
