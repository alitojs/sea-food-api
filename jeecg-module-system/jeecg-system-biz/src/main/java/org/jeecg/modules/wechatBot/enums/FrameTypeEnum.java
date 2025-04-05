package org.jeecg.modules.wechatBot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 框架type
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum FrameTypeEnum {

    SEND_TEXT_MSG("SendTextMsg", "text", "text","发送文本消息"),
    ;

    // 框架名称
    private String fameName;
    // 企业微信
    private String enterpriseWeChatValue;
    //Linux微信
    private String linuxWeChatValue;

    private String desc;
}
