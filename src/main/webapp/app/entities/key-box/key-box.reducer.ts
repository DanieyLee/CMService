import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IKeyBox, defaultValue } from 'app/shared/model/key-box.model';

export const ACTION_TYPES = {
  FETCH_KEYBOX_LIST: 'keyBox/FETCH_KEYBOX_LIST',
  FETCH_KEYBOX: 'keyBox/FETCH_KEYBOX',
  CREATE_KEYBOX: 'keyBox/CREATE_KEYBOX',
  UPDATE_KEYBOX: 'keyBox/UPDATE_KEYBOX',
  DELETE_KEYBOX: 'keyBox/DELETE_KEYBOX',
  RESET: 'keyBox/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IKeyBox>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type KeyBoxState = Readonly<typeof initialState>;

// Reducer

export default (state: KeyBoxState = initialState, action): KeyBoxState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_KEYBOX_LIST):
    case REQUEST(ACTION_TYPES.FETCH_KEYBOX):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_KEYBOX):
    case REQUEST(ACTION_TYPES.UPDATE_KEYBOX):
    case REQUEST(ACTION_TYPES.DELETE_KEYBOX):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_KEYBOX_LIST):
    case FAILURE(ACTION_TYPES.FETCH_KEYBOX):
    case FAILURE(ACTION_TYPES.CREATE_KEYBOX):
    case FAILURE(ACTION_TYPES.UPDATE_KEYBOX):
    case FAILURE(ACTION_TYPES.DELETE_KEYBOX):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_KEYBOX_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_KEYBOX):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_KEYBOX):
    case SUCCESS(ACTION_TYPES.UPDATE_KEYBOX):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_KEYBOX):
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

const apiUrl = 'api/key-boxes';

// Actions

export const getEntities: ICrudGetAllAction<IKeyBox> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_KEYBOX_LIST,
    payload: axios.get<IKeyBox>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getMyAllEntities: ICrudGetAllAction<IKeyBox> = (page, size, sort) => {
  const requestUrl = `api/my-key-boxes${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_KEYBOX_LIST,
    payload: axios.get<IKeyBox>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IKeyBox> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_KEYBOX,
    payload: axios.get<IKeyBox>(requestUrl),
  };
};

export const showHideEntity: ICrudGetAction<IKeyBox> = id => {
  const requestUrl = `api/key-boxes/show-hide/${id}`;
  return {
    type: ACTION_TYPES.FETCH_KEYBOX,
    payload: axios.get<IKeyBox>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IKeyBox> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_KEYBOX,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IKeyBox> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_KEYBOX,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IKeyBox> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_KEYBOX,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
