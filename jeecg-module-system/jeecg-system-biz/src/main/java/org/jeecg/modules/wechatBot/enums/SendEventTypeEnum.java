package org.jeecg.modules.wechatBot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author czc
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum SendEventTypeEnum {

    SEND_TEXT_MSG("SendTextMsg", "text", "text","发送文本消息"),
    SEND_GROUP_MSG_AND_AT("SendGroupMsgAndAt", "","", "发送群消息并艾特"),
    SEND_IMAGE_MSG("SendImageMsg", "image","fileUrl", "发送图片消息"),
    SEND_VOICE_MSG("", "voice","file", "发送语音消息"),
    SEND_VIDEO_MSG("SendVideoMsg", "video","file", "发送视频消息"),
    SEND_FILE_MSG("SendFileMsg", "file","", "发送文件消息"),
    SEND_TEXT_CARD_MSG("", "textcard","", "发送文本卡片消息"),
    SEND_NEWS_MSG("", "news","", "发送图文消息"),
    /*
    mpnews类型的图文消息，跟普通的图文消息一致，唯一的差异是图文内容存储在企业微信。
    多次发送mpnews，会被认为是不同的图文，阅读、点赞的统计会被分开计算。
     */
    SEND_MP_NEWS_MSG("", "mpnews","", "发送图文消息"),
    SEND_MARKDOWN_MSG("", "markdown","", "发送markdown消息"),
    SEND_MINI_APP_MSG("SendMiniAppMsg", "miniprogram_notice","", "发送小程序消息"),
    SEND_TEMPLATE_CARD_MSG("", "template_card","", "发送模板卡片消息"),

    SEND_EMOJI_MSG("SendEmojiMsg", "","", "发送动态表情"),
    SEND_MUSIC_MSG("SendMusicMsg", "","", "发送音乐分享"),
    SEND_CARD_MSG("SendCardMsg", "","", "发送名片消息"),
    SEND_LINK_MSG("SendLinkMsg", "", "","发送分享链接"),
    FORWARD_MSG("ForwardMsg", "", "","转发消息"),
    GET_ROBOT_NAME("GetRobotName", "","", "取登录账号的昵称"),
    GET_ROBOT_HEAD_IMG_URL("GetRobotHeadimgurl", "","", "取登录账号的头像"),
    GET_LOGGED_ACCOUNT_LIST("GetLoggedAccountList", "","", "取登录账号列表"),
    GET_FRIEND_LIST("GetFriendList", "", "","取好友列表"),
    GET_GROUP_LIST("GetGroupList", "", "","取群列表"),
    GET_GROUP_MEMBER_DETAIL_INFO("GetGroupMemberDetailInfo", "","", "取群成员详细"),
    GET_GROUP_MEMBER_LIST("GetGroupMemberList", "","", "取群成员列表"),
    GET_CONTACT_HEAD_IMG_URL("GetContactHeadimgurl", "","", "取联系人头像"),
    ACCEPT_TRANSFER("AcceptTransfer", "", "","接收好友转账"),
    AGREE_GROUP_INVITE("AgreeGroupInvite", "", "","同意群聊邀请"),
    AGREE_FRIEND_VERIFY("AgreeFriendVerify", "","", "同意好友请求"),
    MODIFY_FRIEND_NOTE("ModifyFriendNote", "","", "修改好友备注"),
    DELETE_FRIEND("DeleteFriend", "","", "删除好友"),
    REMOVE_GROUP_MEMBER("RemoveGroupMember", "","", "踢出群成员"),
    MODIFY_GROUP_NAME("ModifyGroupName", "","", "修改群名称"),
    MODIFY_GROUP_NOTICE("ModifyGroupNotice", "","", "修改群公告"),
    BUILDING_GROUP_PLUS("BuildingGroupPlus", "", "","建立新群"),
    QUIT_GROUP("QuitGroup", "","", "退出群聊"),
    INVITE_IN_GROUP("InviteInGroup", "", "","邀请加入群聊"),
    ON_TOP("OnTop", "", "","置顶联系人/取消置顶联系人"),
    GET_FRAME_VERSION("GetFrameVersion", "","", "取框架版本号"),
    GET_APP_DIRECTORY("GetAppDirectory", "","","取应用目录"),
    APPEND_LOGS("AppendLogs", "","", "添加日志");

    // 普通微信
    private String weChatValue;
    // 企业微信
    private String enterpriseWeChatValue;
    //Linux微信
    private String linuxWeChatValue;

    private String desc;
}
