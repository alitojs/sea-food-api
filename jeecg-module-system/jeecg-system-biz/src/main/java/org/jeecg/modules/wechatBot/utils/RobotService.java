package org.jeecg.modules.wechatBot.utils;

import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.alibaba.fastjson.JSONObject;
import com.xkcoding.http.support.Http;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.util.CommonUtils;
import org.jeecg.modules.wechatBot.enums.SendEventTypeEnum;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @Description: 微信机器人API接口封装类
 * @Author: hyl
 * @Date: 2024-01-17
 * @Version: V1.0
 */
@Component
public class RobotService {

 //   @Value("${robot.api-url}")
    private String robotApiUrl;
  //  @Value("${robot.linuxApi-url}")
    private String robotLinuxApiUrl;

//    @Resource
//    private FileConfig fileConfig;


    /**
     * HTTP请求的封装方法
     *
     * @param ip
     * @param data
     * @param method
     * @return
     */
    private String sendHttp(String ip, JSONObject data, Method method) {
        String url = CommonConstant.HTTP_PROTOCOL + ip + robotApiUrl;
        HttpRequest request = new HttpRequest(url);
        if (method == Method.POST) {
            request.method(Method.POST);
        } else {
            request.method(Method.GET);
        }
        request.body(data.toJSONString()); // 使用form方法发送表单数据
        HttpResponse response = request.execute();
        return response.body();
    }
    /**
     * HTTP请求的封装方法(Linux)
     *
     * @param data
     * @param method
     * @return
     */
    private String sendHttpByLinux(JSONObject data, Method method) {
        HttpRequest request = new HttpRequest(robotLinuxApiUrl);
        if (method == Method.POST) {
            request.method(Method.POST);
        } else {
            request.method(Method.GET);
        }
        request.body(data.toJSONString()); // 使用form方法发送表单数据
        HttpResponse response = request.execute();
        return response.body();
    }

    /**
     * 发送文本消息（好友或者群组）
     *
     * @param ipAddr  机器人IP地址
     * @param robWxid 登录账号ID，用来发送这条消息的账号
     * @param toWxid  对方的ID，可以是群或者好友ID
     * @param msg     消息内容
     * @return JSON格式的字符串响应
     */
    public String sendTextMsg(String ipAddr, String robWxid, String toWxid, String msg,String frameType) throws Exception {
        if(frameType.equals(11)){
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.SEND_TEXT_MSG.getWeChatValue()); // API类型
        data.put("msg", msg); // 编码消息内容
        data.put("to_wxid", toWxid); // 目标ID
        data.put("robot_wxid", robWxid); // 发送者账号ID
        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
        }else {
            String url = "?wxid="+robWxid;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type","sendText");
            JSONObject data = new JSONObject();
            data.put("wxid",toWxid);
            data.put("msg",msg);
            jsonObject.put("data",data);
            String body = HttpUtil.createPost(url).body(JSONObject.toJSONString(jsonObject)).execute().body();
            return body;
        }
    }

    /**
     * 发送群消息并艾特某人
     *
     * @param ipAddr     机器人IP地址
     * @param robWxid    账户ID，用哪个账号去发送这条消息
     * @param groupWxid  群ID
     * @param memberWxid 艾特的ID，群成员的ID
     * @param memberName 艾特的昵称，群成员的昵称
     * @param msg        消息内容
     * @return JSON格式的字符串响应
     */
    public String sendGroupMsgAndAt(String ipAddr, String robWxid, String groupWxid, String memberWxid, String memberName, String msg) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.SEND_GROUP_MSG_AND_AT.getWeChatValue()); // API类型
        String value = URLEncodeUtil.encode(msg);
        data.put("msg", value); // 编码消息内容
        data.put("group_wxid", groupWxid); // 群ID
        data.put("member_wxid", memberWxid); // 艾特的ID
        data.put("member_name", memberName); // 艾特的昵称
        data.put("robot_wxid", robWxid); // 发送者账号ID

        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * 发送图片消息
     *
     * @param ipAddr  机器人IP地址
     * @param robWxid 登录账号ID，用来发送这条消息的账号
     * @param toWxid  对方的ID，可以是群或者好友ID
     * @param url    图片的绝对路径
     * @return JSON格式的字符串响应
     */
    public String sendImageMsg(String ipAddr, String robWxid, String toWxid, String url) throws IOException {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.SEND_IMAGE_MSG.getWeChatValue()); // API类型
        JSONObject msgJson = new JSONObject();

        msgJson.put("name", CommonUtils.getFileNameByUrl(url));
        msgJson.put("url",url);
        data.put("msg", msgJson); // 图片的绝对路径
        data.put("to_wxid", toWxid); // 目标ID
        data.put("robot_wxid", robWxid); // 发送者账号ID
        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * 发送视频消息
     *
     * @param ipAddr  机器人IP地址
     * @param robWxid 登录账号ID，用来发送这条消息的账号
     * @param toWxid  对方的ID，可以是群或者好友ID
     * @param url    视频的绝对路径
     * @return JSON格式的字符串响应
     */
    public String sendVideoMsg(String ipAddr, String robWxid, String toWxid, String url) throws IOException {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.SEND_VIDEO_MSG.getWeChatValue()); // API类型
        JSONObject msgJson = new JSONObject();

        msgJson.put("name", CommonUtils.getFileNameByUrl(url));
        msgJson.put("url",url);
        data.put("msg", msgJson); // 视频的绝对路径
        data.put("to_wxid", toWxid); // 目标ID
        data.put("robot_wxid", robWxid); // 发送者账号ID

        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * 发送文件消息
     *
     * @param ipAddr  机器人IP地址
     * @param robWxid 登录账号ID，用来发送这条消息的账号
     * @param toWxid  对方的ID，可以是群或者好友ID
     * @param url    文件的绝对路径
     * @return JSON格式的字符串响应
     */
    public String sendFileMsg(String ipAddr, String robWxid, String toWxid, String url) throws IOException {
        // 封装请求数据
        JSONObject data = new JSONObject();

        data.put("event", SendEventTypeEnum.SEND_FILE_MSG.getWeChatValue()); // API类型
        JSONObject msgJson = new JSONObject();

        msgJson.put("name", CommonUtils.getFileNameByUrl(url));
        msgJson.put("url",url);
        data.put("msg", msgJson); // 文件的绝对路径
        data.put("to_wxid", toWxid); // 目标ID
        data.put("robot_wxid", robWxid); // 发送者账号ID
        // 文本型, , 非本地文件时的文件名
        // data.put("file", "file");

        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * 发送动态表情
     *
     * @param ipAddr  机器人IP地址
     * @param robWxid 登录账号ID，用来发送这条消息的账号
     * @param toWxid  对方的ID，可以是群或者好友ID
     * @param url    动态表情文件（通常是gif）的绝对路径
     * @return JSON格式的字符串响应
     */
    public String sendEmojiMsg(String ipAddr, String robWxid, String toWxid, String url) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.SEND_EMOJI_MSG.getWeChatValue()); // API类型
        data.put("msg", url); // 动态表情的绝对路径
        data.put("to_wxid", toWxid); // 目标ID
        data.put("robot_wxid", robWxid); // 发送者账号ID
        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * 发送名片消息
     *
     * @param ipAddr     机器人IP地址
     * @param robWxid    登录账号ID，用来发送这条消息的账号
     * @param toWxid     对方的ID，可以是群或者好友ID
     * @param friendWxid 要发送的好友/公众hao的wxid
     * @return JSON格式的字符串响应
     */
    public String sendCardMsg(String ipAddr, String robWxid, String toWxid, String friendWxid) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.SEND_CARD_MSG.getWeChatValue()); // API类型
        data.put("friend_wxid", friendWxid); //  要发送的好友/公众hao的wxid
        data.put("to_wxid", toWxid); // 目标ID
        data.put("robot_wxid", robWxid); // 发送者账号ID
        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }


    /**
     * 发送分享链接
     *
     * @param ipAddr    机器人IP地址
     * @param robWxid   账户ID，用来发送这条消息的账号
     * @param toWxid    对方的ID，可以是群或者好友ID
     * @param targetUrl 跳转链接
     * @param picUrl    图片链接
     * @return JSON格式的字符串响应
     */
    public String sendLinkMsg(String ipAddr, String robWxid, String toWxid,String targetUrl, String picUrl) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        JSONObject msgJson = new JSONObject();
        data.put("event", SendEventTypeEnum.SEND_LINK_MSG.getWeChatValue()); // API类型// 发送的分享链接结构体
        msgJson.put("target_url",targetUrl);
        msgJson.put("pic_url",picUrl);
        data.put("msg",msgJson );
        data.put("to_wxid", toWxid); // 目标ID
        data.put("robot_wxid", robWxid); // 发送者账号ID
        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * 发送小程序消息
     *
     * @param ipAddr     机器人IP地址
     * @param robWxid    账户ID，用来发送这条消息的账号
     * @param toWxid     对方的ID，可以是群或者好友ID
     * @param xmlContent 小程序消息的xml内容
     * @return JSON格式的字符串响应
     */
    public String sendMiniAppMsg(String ipAddr, String robWxid, String toWxid, String xmlContent) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.SEND_MINI_APP_MSG.getWeChatValue()); // API类型// 发送的分享链接结构体
        data.put("xmlContent", xmlContent);
        data.put("to_wxid", toWxid); // 目标ID
        data.put("robot_wxid", robWxid); // 发送者账号ID
        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }


    /**
     * 发送音乐分享
     *
     * @param ipAddr  机器人IP地址
     * @param robWxid 登录账号ID，用来发送这条消息的账号
     * @param toWxid  对方的ID，可以是群或者好友ID
     * @param name    歌曲名字
     * @param type    0 网易云音乐 / 1 酷狗音乐 / 2 QQ音乐
     * @return JSON格式的字符串响应
     */
    public String sendMusicMsg(String ipAddr, String robWxid, String toWxid, String name) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.SEND_MUSIC_MSG.getWeChatValue()); // API类型
        //构造信息主体
        JSONObject msg = new JSONObject();
        msg.put("name",name);//音乐名称
        msg.put("type",0);//网易
        data.put("msg", msg); // 发送信息
        data.put("to_wxid", toWxid); // 目标ID
        data.put("robot_wxid", robWxid); // 发送者账号ID
        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * 转发消息
     *
     * @param ipAddr  机器人IP地址
     * @param robWxid 登录账号ID，用来发送这条消息的账号
     * @param toWxid  对方的ID，可以是群或者好友ID
     * @param msgId   原来的消息id
     * @return JSON格式的字符串响应
     */
    public String forwardMsg(String ipAddr, String robWxid, String toWxid, String msgId) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.FORWARD_MSG.getWeChatValue()); // API类型
        data.put("msg_id", msgId);
        data.put("to_wxid", toWxid); // 目标ID
        data.put("robot_wxid", robWxid); // 发送者账号ID
        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }


    /**
     * 获取指定登录账号的昵称
     *
     * @param ipAddr  机器人IP地址
     * @param robWxid 账户ID
     * @return 账号昵称
     */
    public String getRobotName(String ipAddr, String robWxid) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.GET_ROBOT_NAME.getWeChatValue()); // API类型
        data.put("robot_wxid", robWxid); // 账户ID

        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * 获取指定登录账号的头像URL
     *
     * @param ipAddr  机器人IP地址
     * @param robWxid 账户ID
     * @return 头像的HTTP地址
     */
    public String getRobotHeadImgUrl(String ipAddr, String robWxid) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.GET_ROBOT_HEAD_IMG_URL.getWeChatValue()); // API类型
        data.put("robot_wxid", robWxid); // 账户ID

        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * 获取登录账号列表
     *
     * @param ipAddr 机器人IP地址
     * @return 当前框架已登录的账号信息列表
     */
    public String getLoggedAccountList(String ipAddr) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.GET_LOGGED_ACCOUNT_LIST.getWeChatValue()); // API类型
        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * 获取好友列表
     *
     * @param ipAddr    机器人IP地址
     * @param robWxid   账户ID（可选，如果为空字符串，则获取所有登录账号的好友列表）
     * @param isRefresh 是否刷新列表，0 从缓存获取 / 1 刷新并获取
     * @return 当前框架已登录的账号的好友信息列表
     */
    public String getFriendList(String ipAddr, String robWxid, int isRefresh) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.GET_FRIEND_LIST.getWeChatValue()); // API类型
        data.put("robot_wxid", robWxid); // 账户ID
        data.put("is_refresh", isRefresh); // 是否刷新列表

        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * 获取群聊列表
     *
     * @param ipAddr    机器人IP地址
     * @param robWxid   账户ID（可选，如果为空字符串，则获取所有登录账号的群聊列表）
     * @param isRefresh 是否刷新列表，0 从缓存获取 / 1 刷新并获取
     * @return 当前框架已登录的账号的群聊信息列表
     */
    public String getGroupList(String ipAddr, String robWxid, int isRefresh) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.GET_GROUP_LIST.getWeChatValue()); // API类型
        data.put("robot_wxid", robWxid); // 账户ID
        data.put("is_refresh", isRefresh); // 是否刷新列表

        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * 获取群成员列表
     *
     * @param ipAddr    机器人IP地址
     * @param robWxid   账户ID
     * @param groupWxid 群ID
     * @param isRefresh 是否刷新列表，0 从缓存获取 / 1 刷新并获取
     * @return 当前框架已登录的账号的群成员信息列表
     */
    public String getGroupMemberList(String ipAddr, String robWxid, String groupWxid, int isRefresh) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.GET_GROUP_MEMBER_LIST.getWeChatValue()); // API类型
        data.put("robot_wxid", robWxid); // 账户ID
        data.put("group_wxid", groupWxid); // 群ID
        data.put("is_refresh", isRefresh); // 是否刷新列表

        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * 取群成员详细
     *
     * @param ipAddr     机器人IP地址
     * @param robWxid    账户ID
     * @param groupWxid  群ID
     * @param memberWxid 群成员ID
     * @param isRefresh  是否刷新列表，0 从缓存获取 / 1 刷新并获取
     * @return 群成员的资料（JSON格式字符串）
     */
    public String getGroupMember(String ipAddr, String robWxid, String groupWxid, String memberWxid, int isRefresh) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.GET_GROUP_MEMBER_DETAIL_INFO.getWeChatValue()); // API类型
        data.put("robot_wxid", robWxid); // 账户ID
        data.put("group_wxid", groupWxid); // 群ID
        data.put("member_wxid", memberWxid); // 群成员ID

        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * 接收好友转账
     *
     * @param ipAddr     机器人IP地址
     * @param robWxid    账户ID
     * @param friendWxid 朋友ID
     * @param jsonString 转账事件原消息
     * @return 操作结果（JSON格式字符串）
     */
    public String acceptTransfer(String ipAddr, String robWxid, String friendWxid, String jsonString) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.ACCEPT_TRANSFER.getWeChatValue()); // API类型
        data.put("robot_wxid", robWxid); // 账户ID
        data.put("friend_wxid", friendWxid); // 朋友ID
        data.put("json_msg", jsonString); // 转账事件原消息

        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * 同意群聊邀请
     *
     * @param ipAddr     机器人IP地址
     * @param robWxid    账户ID
     * @param jsonString 同步消息事件中群聊邀请原消息
     * @return 操作结果（JSON格式字符串）
     */
    public String agreeGroupInvite(String ipAddr, String robWxid, String jsonString) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.AGREE_GROUP_INVITE.getWeChatValue()); // API类型
        data.put("robot_wxid", robWxid); // 账户ID
        data.put("json_msg", jsonString); // 群聊邀请原消息

        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * 同意好友请求
     *
     * @param ipAddr     机器人IP地址
     * @param robWxid    账户ID
     * @param jsonString 好友请求事件中的原消息
     * @return 操作结果（JSON格式字符串）
     */
    public String agreeFriendVerify(String ipAddr, String robWxid, String jsonString) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.AGREE_FRIEND_VERIFY.getWeChatValue()); // API类型
        data.put("robot_wxid", robWxid); // 账户ID
        data.put("json_msg", jsonString); // 好友请求原消息

        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * 修改好友备注
     *
     * @param ipAddr     机器人IP地址
     * @param robWxid    账户ID
     * @param friendWxid 好友ID
     * @param note       新备注（空字符串表示删除备注）
     * @return 操作结果（JSON格式字符串）
     */
    public String modifyFriendNote(String ipAddr, String robWxid, String friendWxid, String note) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.MODIFY_FRIEND_NOTE.getWeChatValue()); // API类型
        data.put("robot_wxid", robWxid); // 账户ID
        data.put("friend_wxid", friendWxid); // 好友ID
        data.put("note", note); // 新备注（空字符串表示删除备注）

        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * 删除好友
     *
     * @param ipAddr     机器人IP地址
     * @param robWxid    账户ID
     * @param friendWxid 好友ID
     * @return 操作结果（JSON格式字符串）
     */
    public String deleteFriend(String ipAddr, String robWxid, String friendWxid) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.DELETE_FRIEND.getWeChatValue()); // API类型
        data.put("robot_wxid", robWxid); // 账户ID
        data.put("friend_wxid", friendWxid); // 好友ID

        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * 踢出群成员
     *
     * @param ipAddr     机器人IP地址
     * @param robWxid    账户ID
     * @param groupWxid  群ID
     * @param memberWxid 群成员ID
     * @return 操作结果（JSON格式字符串）
     */
    public String removeGroupMember(String ipAddr, String robWxid, String groupWxid, String memberWxid) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.REMOVE_GROUP_MEMBER.getWeChatValue()); // API类型
        data.put("robot_wxid", robWxid); // 账户ID
        data.put("group_wxid", groupWxid); // 群ID
        data.put("member_wxid", memberWxid); // 群成员ID

        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * 修改群名称
     *
     * @param ipAddr    机器人IP地址
     * @param robWxid   账户ID
     * @param groupWxid 群ID
     * @param groupName 新群名
     * @return 操作结果（JSON格式字符串）
     */
    public String modifyGroupName(String ipAddr, String robWxid, String groupWxid, String groupName) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.MODIFY_GROUP_NAME.getWeChatValue()); // API类型
        data.put("robot_wxid", robWxid); // 账户ID
        data.put("group_wxid", groupWxid); // 群ID
        data.put("group_name", groupName); // 新群名

        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * 修改群公告
     *
     * @param ipAddr    机器人IP地址
     * @param robWxid   账户ID
     * @param groupWxid 群ID
     * @param content   新公告
     * @return 操作结果（JSON格式字符串）
     */
    public String modifyGroupNotice(String ipAddr, String robWxid, String groupWxid, String content) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.MODIFY_GROUP_NOTICE.getWeChatValue()); // API类型
        data.put("robot_wxid", robWxid); // 账户ID
        data.put("group_wxid", groupWxid); // 群ID
        data.put("content", content); // 新公告

        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * 建立新群
     *
     * @param ipAddr    机器人IP地址
     * @param robWxid   账户ID
     * @param friendArr 好友ID数组
     * @return 操作结果（JSON格式字符串）
     */
    public String buildNewGroup(String ipAddr, String robWxid, List<String> friendArr) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.BUILDING_GROUP_PLUS.getWeChatValue()); // API类型
        data.put("robot_wxid", robWxid); // 账户ID
        data.put("friendArr", friendArr); // 好友ID数组

        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * 退出群聊
     *
     * @param ipAddr    机器人IP地址
     * @param robWxid   账户ID
     * @param groupWxid 群ID
     * @return 操作结果（JSON格式字符串）
     */
    public String quitGroup(String ipAddr, String robWxid, String groupWxid) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.QUIT_GROUP.getWeChatValue()); // API类型
        data.put("robot_wxid", robWxid); // 账户ID
        data.put("group_wxid", groupWxid); // 群ID

        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * 邀请加入群聊
     *
     * @param ipAddr     机器人IP地址
     * @param robWxid    账户ID
     * @param groupWxid  群ID
     * @param friendWxid 好友ID
     * @return 操作结果（JSON格式字符串）
     */
    public String inviteInGroup(String ipAddr, String robWxid, String groupWxid, String friendWxid) {
        // 封装请求数据
        JSONObject data = new JSONObject();
        data.put("event", SendEventTypeEnum.INVITE_IN_GROUP.getWeChatValue()); // API类型
        data.put("robot_wxid", robWxid); // 账户ID
        data.put("group_wxid", groupWxid); // 群ID
        data.put("friend_wxid", friendWxid); // 好友ID

        // 发起POST请求
        return sendHttp(ipAddr, data, Method.POST);
    }

    /**
     * Linux微信机器人推消息--发文字或文件外链, 外链会解析成图片或者文件
     *
     * @param toWx    消息接收方,传入String 默认是发给昵称（群名同理）, 传入Object 结构支持发给备注过的人，比如：{alias: '备注名'}，群名不支持备注名
     * @param isRoom  是否群聊消息，true表示是，false表示否
     * @param type    消息类型，text表示文本消息，fileUrl表示图片消息
     * @param content 消息内容
     * @return 操作结果（JSON格式字符串）
     */
    public String sendTextOrLink(String toWx, Boolean isRoom, String type, String content) {
        // 封装请求数据
        JSONObject payload = new JSONObject();
        payload.put("to", toWx);
        payload.put("isRoom", isRoom);
        JSONObject data = new JSONObject();
        data.put("type", type);
        data.put("content", content);
        payload.put("data", data);
        return sendHttpByLinux(payload, Method.POST);
    }

    /**
     * Linux微信机器人推消息--读文件暂时只支持单条发送
     *
     * @param toWx    消息接收方,传入String 默认是发给昵称（群名同理）, 传入Object 结构支持发给备注过的人，比如：{alias: '备注名'}，群名不支持备注名
     * @param isRoom  是否群聊消息，1表示是，0表示否
     * @param content 文件，本地文件一次只能发一个，多个文件手动调用多次
     * @return 操作结果（JSON格式字符串）
     */
    public String sendFile(String toWx, String isRoom, String content) {
        // 封装请求数据
        JSONObject payload = new JSONObject();
        payload.put("to", toWx);
        payload.put("isRoom", isRoom);
        payload.put("content", content);
        return sendHttpByLinux(payload, Method.POST);
    }




}
