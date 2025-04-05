package org.jeecg.modules.wechatBot.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.product.entity.ProductInfo;
import org.jeecg.modules.product.entity.ProductSpecification;
import org.jeecg.modules.product.entity.vo.ProductInfoPage;
import org.jeecg.modules.product.service.IProductInfoService;
import org.jeecg.modules.product.service.IProductSpecificationService;
import org.jeecg.modules.wechatBot.utils.RobotService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @Description: 微信机器人
 * @Author: jeecg-boot
 * @Date: 2024-08-04
 * @Version: V1.0
 */
@Api(tags = "微信机器人")
@RestController
@RequestMapping("/wechatBot")
@Slf4j
public class WechatBotController {
    @Autowired
    private RobotService robotService;

    /**
     * 微信消息回调
     *
     * @param json 请求体
     * @param req  请求
     * @return
     */
    @AutoLog(value = "微信管家-聊天信息记录-微信消息回调")
    @ApiOperation(value = "微信管家-聊天信息记录-微信消息回调", notes = "微信管家-聊天信息记录-微信消息回调")
    @PostMapping(value = "/wxMsgCallBack")
    public Result<?> wxMsgCallBack(@RequestBody JSONObject json,
                                   HttpServletRequest req) throws Exception {
        //todo 剑哥插件
//        String msg = json.getString("msg");
//        String fromWxid = json.getString("from_wxid");
//        String finalFromWxid = json.getString("final_from_wxid");
//        String msgid = json.getString("msgid");
//        String event = json.getString("event");
//        Integer type = json.getInteger("type");
//        String fromName = json.getString("from_name");
//        String robotWxid = json.getString("robot_wxid");
//        String finalFromName = json.getString("final_from_name");
//        String toWxid = json.getString("to_wxid");
//        //不响应机器人本身消息，防止死循环
//        if(finalFromWxid.equals(robotWxid)){
//            return Result.OK();
//        }
//        if(StringUtils.isNotBlank(msg)) {
//            if (msg.startsWith("听歌")) {
//                robotService.sendMusicMsg("127.0.0.1:8897", robotWxid, fromWxid, msg.replaceFirst("听歌", ""));
//            }
//        }
        //千寻
        String robotWxid = json.getString("wxid");
        JSONObject data = json.getJSONObject("data").getJSONObject("data");
        //来源类型：1|私聊 2|群聊 3|公众号
        String fromType = data.getString("fromType");
        //消息类型：1|文本 3|图片 34|语音 42|名片 43|视频 47|动态表情 48|地理位置 49|分享链接或附件 2001|红包 2002|小程序 2003|群邀请 10000|系统消息
        String msgType = data.getString("msgType");
        //消息来源：0|别人发送 1|自己发送
        String msgSource = data.getString("msgSource");
        //	fromType=1时为好友wxid，fromType=2时为群wxid，fromType=3时公众号wxid
        String fromWxid = data.getString("fromWxid");
        //	仅fromType=2时有效，为群内发言人wxid
        String finalFromWxid = data.getString("finalFromWxid");
        //	仅fromType=2时有效，为消息中艾特人wxid列表
        JSONArray atWxidList = data.getJSONArray("atWxidList");
        // 消息内容
        String msg = data.getString("msg");
        //消息ID
        String msgId = data.getString("msgId");
        //消息内容的Base64
        String msgBase64 = data.getString("msgBase64");
        if(msgSource.equals(1)){
            return Result.OK();
        }
        return Result.OK();

    }

    /**
     * 微信消息回调
     *
     * @param json 请求体
     * @param req  请求
     * @return
     */
    @AutoLog(value = "微信管家-聊天信息记录-微信消息回调")
    @ApiOperation(value = "微信管家-聊天信息记录-微信消息回调", notes = "微信管家-聊天信息记录-微信消息回调")
    @PostMapping(value = "/sendMessage")
    public Result<?> sendMessage(@RequestBody JSONObject json,
                                   HttpServletRequest req) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type","sendText");
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("wxid","wxid_cxyq8vjum42c22");

        String body1 = HttpUtil.createGet("https://api.vvhan.com/api/text/love").execute().body();
        jsonObject1.put("msg",body1);
        jsonObject.put("data",jsonObject1);
        String body = HttpUtil.createPost("http://127.0.0.1:7777/qianxun/httpapi").header("wxid","wxid_cxyq8vjum42c22").body(JSONObject.toJSONString(jsonObject)).execute().body();
        System.out.println(body);
        return Result.OK();

    }
    public static void main(String[] args) {


    }

}
