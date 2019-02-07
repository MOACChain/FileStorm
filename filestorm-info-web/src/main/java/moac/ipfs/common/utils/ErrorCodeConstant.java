package moac.ipfs.common.utils;

/**
 * @program: moacipfs
 * @author: GZC
 * @create: 2018-09-12 11:01
 * @description: 错误码
 **/
public class ErrorCodeConstant {
    /**
     * 系统相关
     */
    /**
     * 系统用户密码不能为空 10001
     */
    public static final int PASSWORD_CANNOT_BE_EMPTY = 10001;
    /**
     * 上传文件不能为空 10002
     */
    public static final int UPLOAD_CANNOT_BE_EMPTY = 10002;
    /**
     * 身份证号格式不正确 10003
     */
    public static final int INCORRECT_ID_CARD = 10003;
    /**
     * 上传文件格式不正确 10004
     */
    public static final int UPLOAD_UNIT_FILE_FORMAT_ERROR = 10004;
    /**
     * 上传文件失败 10005
     */
    public static final int UPLOAD_FILE_ERROR = 10005;
    /**
     * 信息不完整 10006
     */
    public static final int INCOMPLETE_INFORMATION = 10006;
    /**
     * 图片验证码不正确 10007
     */
    public static final int WRONG_CAPTCHA = 10007;



    /**
     * 用户相关
     */
    /**
     * 帐号不存在 20001
     */
    public static final int USER_NOT_FOUND = 20001;
    /**
     * 密码错误 20002
     */
    public static final int WRONG_PASSWORD = 20002;
    /**
     * 帐号被禁止使用 20003
     */
    public static final int FROZEN_ACCOUNT = 20003;
    /**
     * 手机验证码类型错误 20004
     */
    public static final int WRONG_TYPE_OF_MOBILE = 20004;
    /**
     * 手机号已经注册 20005
     */
    public static final int MOBILE_ALREADY_EXISTED = 20005;
    /**
     * 手机号验证码不正确 20006
     */
    public static final int MOBILE_CODE_IS_WRONG = 20006;
    /**
     * 获取手机验证码失败 20007
     */
    public static final int FAILED_TO_GET_MOBILE_CODE = 20007;
    /**
     * 获取邮箱验证码失败 20008
     */
    public static final int FAILED_TO_GET_EMAIL_CODE = 20008;
    /**
     * 该邮箱已绑定帐号 20009
     */
    public static final int IS_ALREADY_BIND_EMAIL = 20009;
    /**
     * 该邮箱未绑定帐号 20010
     */
    public static final int EMAIL_IS_NOT_BIND = 20010;
    /**
     * 邮箱验证码不正确 20010
     */
    public static final int EMAIL_CODE_IS_WRONG = 20010;
    /**
     * 手机号不正确 20011
     */
    public static final int MOBILE_IS_WRONG = 20011;
    /**
     * 邮箱不正确 20012
     */
    public static final int EMAIL_IS_WRONG = 20012;
    /**
     * 上传头像失败 20013
     */
    public static final int UPLOADING_AVATAR_FAILED = 20013;
    /**
     * 登录密码错误 20014
     */
    public static final int ORIGINAL_PASSWORD_ERROR = 20014;
    /**
     * 邮箱未绑定 20015
     */
    public static final int NO_BIND_EMAIL = 20015;
    /**
     * 资金密码未设置 20016
     */
    public static final int NO_SETTING_FUND_PASSWORD = 20016;
    /**
     * 资金密码错误 20017
     */
    public static final int FUND_PASSWORD_IS_WRONG = 20017;
    /**
     * 身份认证错误
     */
    public static final int IDENTITY_AUTHENTICATION_ERROR = 20018;

    /**
     * 币种相关
     */
    /**
     * 币种不存在 30001
     */
    public static final int CURRENCY_DOES_NOT_EXIST = 30001;
    /**
     * 币种不可提 30002
     */
    public static final int CURRENCY_CANNOT_BE_MENTIONED = 30002;
    /**
     * 提币数量不能小于单笔最低可提数量 30003
     */
    public static final int WRONG_AMOUNT_OF_COINS = 30003;
    /**
     * 币种资产不足 30004
     */
    public static final int INSUFFICIENT_ASSETS = 30004;
    /**
     * 获取币种新地址失败 30005
     */
    public static final int FAILED_TO_GET_CURRENCY_ADDRESS = 30005;
    /**
     * 币种不可充 30006
     */
    public static final int CURRENCY_CANNOT_BE_CHARGE = 30006;

    /**
     * 项目方相关
     */



}
