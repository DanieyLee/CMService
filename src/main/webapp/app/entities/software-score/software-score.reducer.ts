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

import { ISoftwareScore, defaultValue } from 'app/shared/model/software-score.model';

export const ACTION_TYPES = {
  FETCH_SOFTWARESCORE_LIST: 'softwareScore/FETCH_SOFTWARESCORE_LIST',
  FETCH_SOFTWARESCORE: 'softwareScore/FETCH_SOFTWARESCORE',
  CREATE_SOFTWARESCORE: 'softwareScore/CREATE_SOFTWARESCORE',
  UPDATE_SOFTWARESCORE: 'softwareScore/UPDATE_SOFTWARESCORE',
  DELETE_SOFTWARESCORE: 'softwareScore/DELETE_SOFTWARESCORE',
  RESET: 'softwareScore/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISoftwareScore>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type SoftwareScoreState = Readonly<typeof initialState>;

// Reducer

export default (state: SoftwareScoreState = initialState, action): SoftwareScoreState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SOFTWARESCORE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SOFTWARESCORE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SOFTWARESCORE):
    case REQUEST(ACTION_TYPES.UPDATE_SOFTWARESCORE):
    case REQUEST(ACTION_TYPES.DELETE_SOFTWARESCORE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SOFTWARESCORE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SOFTWARESCORE):
    case FAILURE(ACTION_TYPES.CREATE_SOFTWARESCORE):
    case FAILURE(ACTION_TYPES.UPDATE_SOFTWARESCORE):
    case FAILURE(ACTION_TYPES.DELETE_SOFTWARESCORE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SOFTWARESCORE_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_SOFTWARESCORE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SOFTWARESCORE):
    case SUCCESS(ACTION_TYPES.UPDATE_SOFTWARESCORE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SOFTWARESCORE):
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

const apiUrl = 'api/software-scores';

// Actions

export const getEntities: ICrudGetAllAction<ISoftwareScore> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SOFTWARESCORE_LIST,
    payload: axios.get<ISoftwareScore>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ISoftwareScore> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SOFTWARESCORE,
    payload: axios.get<ISoftwareScore>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISoftwareScore> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SOFTWARESCORE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<ISoftwareScore> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SOFTWARESCORE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISoftwareScore> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SOFTWARESCORE,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
