import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPhone, defaultValue } from 'app/shared/model/phone.model';

export const ACTION_TYPES = {
  FETCH_PHONE_LIST: 'phone/FETCH_PHONE_LIST',
  FETCH_PHONE: 'phone/FETCH_PHONE',
  CREATE_PHONE: 'phone/CREATE_PHONE',
  UPDATE_PHONE: 'phone/UPDATE_PHONE',
  DELETE_PHONE: 'phone/DELETE_PHONE',
  RESET: 'phone/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPhone>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type PhoneState = Readonly<typeof initialState>;

// Reducer

export default (state: PhoneState = initialState, action): PhoneState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PHONE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PHONE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PHONE):
    case REQUEST(ACTION_TYPES.UPDATE_PHONE):
    case REQUEST(ACTION_TYPES.DELETE_PHONE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PHONE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PHONE):
    case FAILURE(ACTION_TYPES.CREATE_PHONE):
    case FAILURE(ACTION_TYPES.UPDATE_PHONE):
    case FAILURE(ACTION_TYPES.DELETE_PHONE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PHONE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PHONE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PHONE):
    case SUCCESS(ACTION_TYPES.UPDATE_PHONE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PHONE):
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

const apiUrl = 'api/phones';

// Actions

export const getEntities: ICrudGetAllAction<IPhone> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PHONE_LIST,
    payload: axios.get<IPhone>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IPhone> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PHONE,
    payload: axios.get<IPhone>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPhone> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PHONE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPhone> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PHONE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPhone> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PHONE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
