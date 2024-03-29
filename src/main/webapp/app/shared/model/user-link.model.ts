export interface IUserLink {
  id?: number;
  firstName?: string;
  sex?: boolean;
  age?: number;
  theme?: string;
  passwordKey?: number;
  userLogin?: string;
  userId?: number;
  imageContentType?: string;
  image?: any;
  imgSwitch?: boolean;
  imgName?: string;
}

export const defaultValue: Readonly<IUserLink> = {
  sex: false,
  imgSwitch: false,
};
