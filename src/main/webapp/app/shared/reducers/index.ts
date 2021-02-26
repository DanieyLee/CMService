import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import userLink, {
  UserLinkState
} from 'app/entities/user-link/user-link.reducer';
// prettier-ignore
import software, {
  SoftwareState
} from 'app/entities/software/software.reducer';
// prettier-ignore
import softwareType, {
  SoftwareTypeState
} from 'app/entities/software-type/software-type.reducer';
// prettier-ignore
import softwareComments, {
  SoftwareCommentsState
} from 'app/entities/software-comments/software-comments.reducer';
// prettier-ignore
import softwareScore, {
  SoftwareScoreState
} from 'app/entities/software-score/software-score.reducer';
// prettier-ignore
import article, {
  ArticleState
} from 'app/entities/article/article.reducer';
// prettier-ignore
import articleEnclosure, {
  ArticleEnclosureState
} from 'app/entities/article-enclosure/article-enclosure.reducer';
// prettier-ignore
import articleType, {
  ArticleTypeState
} from 'app/entities/article-type/article-type.reducer';
// prettier-ignore
import articleComment, {
  ArticleCommentState
} from 'app/entities/article-comment/article-comment.reducer';
// prettier-ignore
import wallpaper, {
  WallpaperState
} from 'app/entities/wallpaper/wallpaper.reducer';
// prettier-ignore
import systemImage, {
  SystemImageState
} from 'app/entities/system-image/system-image.reducer';
// prettier-ignore
import keyBox, {
  KeyBoxState
} from 'app/entities/key-box/key-box.reducer';
// prettier-ignore
import phone, {
  PhoneState
} from 'app/entities/phone/phone.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly userLink: UserLinkState;
  readonly software: SoftwareState;
  readonly softwareType: SoftwareTypeState;
  readonly softwareComments: SoftwareCommentsState;
  readonly softwareScore: SoftwareScoreState;
  readonly article: ArticleState;
  readonly articleEnclosure: ArticleEnclosureState;
  readonly articleType: ArticleTypeState;
  readonly articleComment: ArticleCommentState;
  readonly wallpaper: WallpaperState;
  readonly systemImage: SystemImageState;
  readonly keyBox: KeyBoxState;
  readonly phone: PhoneState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  userLink,
  software,
  softwareType,
  softwareComments,
  softwareScore,
  article,
  articleEnclosure,
  articleType,
  articleComment,
  wallpaper,
  systemImage,
  keyBox,
  phone,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
