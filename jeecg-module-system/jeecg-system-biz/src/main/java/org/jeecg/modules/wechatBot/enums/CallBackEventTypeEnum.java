package org.jeecg.modules.wechatBot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum CallBackEventTypeEnum {

    EVENT_FRIEND_MSG("EventFriendMsg", "私聊消息事件"),
    EVENT_GROUP_MSG("EventGroupMsg", "群消息事件"),
    EVENT_SEND_OUT_MSG("EventSendOutMsg", "公开, 本人发出的消息"),
    EVENT_FRIEND_VERIFY("EventFriendVerify", "好友请求事件"),
    EVENT_CONTACT_CHANGE("EventContactsChange", "朋友变动事件"),
    EVENT_GROUP_MEMBER_ADD("EventGroupMemberAdd", "群成员增加"),
    EVENT_GROUP_MEMBER_DECREASE("EventGroupMemberDecrease", "群成员减少"),
    EVENT_SYS_MSG("EventSysMsg", "系统消息事件"),
    EVENT_SCAN_CASH_MONEY("EventScanCashMoney", "面对面收款"),
    EVENT_RECEIVED_TRANSFER("EventReceivedTransfer", "收到转账事件"),
    EVENT_MODIFY("EventModify", "用户在设置界面调试事件"),
    EVENT_LOGIN("EventLogin", "新的账号登录成功/下线");

    private String value;

    private String desc;
}
