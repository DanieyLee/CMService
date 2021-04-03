import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IWallpaper, defaultValue } from 'app/shared/model/wallpaper.model';

export const ACTION_TYPES = {
  FETCH_WALLPAPER_LIST: 'wallpaper/FETCH_WALLPAPER_LIST',
  FETCH_WALLPAPER: 'wallpaper/FETCH_WALLPAPER',
  CREATE_WALLPAPER: 'wallpaper/CREATE_WALLPAPER',
  UPDATE_WALLPAPER: 'wallpaper/UPDATE_WALLPAPER',
  DELETE_WALLPAPER: 'wallpaper/DELETE_WALLPAPER',
  SET_BLOB: 'wallpaper/SET_BLOB',
  SET_BLOB_ALL: 'wallpaper/SET_BLOB_ALL',
  RESET: 'wallpaper/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IWallpaper>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type WallpaperState = Readonly<typeof initialState>;

// Reducer

export default (state: WallpaperState = initialState, action): WallpaperState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_WALLPAPER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_WALLPAPER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_WALLPAPER):
    case REQUEST(ACTION_TYPES.UPDATE_WALLPAPER):
    case REQUEST(ACTION_TYPES.DELETE_WALLPAPER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_WALLPAPER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_WALLPAPER):
    case FAILURE(ACTION_TYPES.CREATE_WALLPAPER):
    case FAILURE(ACTION_TYPES.UPDATE_WALLPAPER):
    case FAILURE(ACTION_TYPES.DELETE_WALLPAPER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_WALLPAPER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_WALLPAPER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_WALLPAPER):
    case SUCCESS(ACTION_TYPES.UPDATE_WALLPAPER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_WALLPAPER):
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
    case ACTION_TYPES.SET_BLOB_ALL: {
      const { name, imgName, imgSwitch, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          ['imgName']: imgName,
          ['imgSwitch']: imgSwitch,
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

const apiUrl = 'api/wallpapers';

// Actions

export const getEntities: ICrudGetAllAction<IWallpaper> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_WALLPAPER_LIST,
    payload: axios.get<IWallpaper>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getPublicEntities: ICrudGetAllAction<IWallpaper> = (page, size, sort) => {
  const requestUrl = `api/public/wallpapers${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_WALLPAPER_LIST,
    payload: axios.get<IWallpaper>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getTopPublicEntities: ICrudGetAllAction<IWallpaper> = () => {
  const requestUrl = `api/public/wallpapers/top`;
  return {
    type: ACTION_TYPES.FETCH_WALLPAPER_LIST,
    payload: axios.get<IWallpaper>(requestUrl),
  };
};

export const getNearEntity = (id, near) => {
  const requestUrl = `api/public/wallpapers/near/${id}&${near}`;
  return {
    type: ACTION_TYPES.FETCH_WALLPAPER,
    payload: axios.get<IWallpaper>(requestUrl),
  };
};

export const getEntity: ICrudGetAction<IWallpaper> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_WALLPAPER,
    payload: axios.get<IWallpaper>(requestUrl),
  };
};

export const getPublicEntity: ICrudGetAction<IWallpaper> = id => {
  const requestUrl = `api/public/wallpapers/${id}`;
  return {
    type: ACTION_TYPES.FETCH_WALLPAPER,
    payload: axios.get<IWallpaper>(requestUrl),
  };
};

export const likeEntity: ICrudGetAction<IWallpaper> = id => {
  const requestUrl = `api/public/wallpapers/like/${id}`;
  return {
    type: ACTION_TYPES.FETCH_WALLPAPER,
    payload: axios.get<IWallpaper>(requestUrl),
  };
};

export const createUserEntity: ICrudPutAction<IWallpaper> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_WALLPAPER,
    payload: axios.post(`${apiUrl}/create`, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const createEntity: ICrudPutAction<IWallpaper> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_WALLPAPER,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateUserEntity: ICrudPutAction<IWallpaper> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_WALLPAPER,
    payload: axios.put(`${apiUrl}/update`, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IWallpaper> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_WALLPAPER,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteUserEntity: ICrudDeleteAction<IWallpaper> = id => async dispatch => {
  const requestUrl = `${apiUrl}/delete/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_WALLPAPER,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IWallpaper> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_WALLPAPER,
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
export const setBlobAll = (name, imgName, imgSwitch, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB_ALL,
  payload: {
    name,
    imgName,
    imgSwitch,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
