package com.example.program_wx;

/**
 * 全局常量
 * Created by YinTao on 2017/12/5.
 */

public class MyConst
{
    //远程服务器
    public static final String HOST = "http://120.55.182.220/api/"; //主网址
    public static final String URL_AVATAR = HOST + "upload/";
    public static final String URL_REGISTER = HOST + "register";//注册
    public static final String URL_LOGIN = HOST + "login";//登录
    //    public static final String URL_THIRD_LOGIN = HOST + "thirdLogin";//第三方登录
    public static final String URL_FriendList = HOST + "fetchFriends";//获取好友列表
    public static final String URL_Search_User = HOST + "searchUser";//查询好友
    public static final String URL_Get_UserInfo = HOST + "getUserInfo";//获取详情
    public static final String URL_UPDATE = HOST + "update";//更新
    public static final String URL_RESET_PASSWORD = HOST + "resetPassword";//更新密码
    public static final String URL_ADD_FRIEND = HOST + "addFriend"; //添加好友
    public static final String URL_DELETE_FRIEND = HOST + "removeFriend";//删除好友
    public static final String URL_ADD_BLACKLIST = HOST + "addBlackList";//添加黑名单

    public static final String URL_PUBLISH = HOST + "publish";//发布动态
    public static final String URL_SOCIAL = HOST + "fetchTimeline";//获取动态列表
    public static final String URL_SOCIAL_DELETE = HOST + "removeTimeline";//删除动态
    public static final String URL_SOCIAL_FRIEND = HOST + "fetchOtherTimeline";//获取好友朋友圈列表
    public static final String URL_SOCIAL_COMMENT = HOST + "commentTimeline";//朋友圈动态评论
    public static final String URL_SOCIAL_DELETE_COMMENT = HOST + "deleteCommentTimeline";//删除朋友圈动态评论
    public static final String URL_SOCIAL_REPLY_COMMENT = HOST + "replyCommentTimeline";//回复朋友圈动态评论
    public static final String URL_SOCIAL_DELETE_REPLY_COMMENT = HOST + "deleteReplyCommentTimeline";//删除朋友圈动态评论回复
    public static final String URL_SOCIAL_GOOD = HOST + "praiseTimeline";//点赞
    public static final String URL_SOCIAL_GOOD_CANCEL = HOST + "deletePraiseTimeline";//取消点赞
    public static final String URL_SOCIAL_GET_PRAISELIST = HOST + "fetchTimelineParises";//获取赞列表
    public static final String URL_SOCIAL_GET_COMMENTLIST = HOST + "fetchTimelineComments";//获取评论列表
    public static final String URL_SOCIAL_GET_DETAIL = HOST + "dynamicInfo";//获取评论列表

    public static final String URL_CHECK_UPDATE = HOST + "getVersion";    //查询更新
    public static final String URL_UPLOAD_MOMENT_BACKGROUND = HOST + "uploadpic";//上传朋友圈背景图片
    public static final String URL_GET_RECENTLY_PEOPLE = HOST + "getRecentlyUser";//获取最近上线的人
    public static final String URL_SEND_LOCAL_LOGIN_TIME = HOST + "updateLocalTimestamp";//
    public static final String URL_SEND_CONTANCTS = HOST + "filteruser";//上传联系人到服务器

    //群相关接口
    public static final String GROUP_HOST = "http://120.55.182.220/group/";  //116.62.180.69
    public static final String URL_GROUP_CREATE = GROUP_HOST + "groupCreate.php";
    public static final String URL_GROUP_MEMBERS = GROUP_HOST + "mucMembers.php";


    public static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|(147))\\d{8}$";
    public static final String SESSION = "session";//全局的session

    /* 返回数据 */
    public static final String JSON_KEY_NICK = "nick";
    public static final String JSON_KEY_HXID = "userId";
    public static final String JSON_KEY_FXID = "fxid";
    public static final String JSON_KEY_SEX = "sex";
    public static final String JSON_KEY_AVATAR = "avatar";
    public static final String JSON_KEY_CITY = "city";
    public static final String JSON_KEY_PASSWORD = "hx_password";
    public static final String JSON_KEY_PROVINCE = "province";
    public static final String JSON_KEY_TEL = "tel";
    public static final String JSON_KEY_SIGN = "sign";
    public static final String JSON_KEY_ROLE = "role";
    public static final String JSON_KEY_BIGREGIONS = "bigRegions";
    public static final String JSON_KEY_SESSION = "session";

    /*用户信息*/
    public static String SHARED_KEY_USER_INFO = "shared_key_user_info";
}
