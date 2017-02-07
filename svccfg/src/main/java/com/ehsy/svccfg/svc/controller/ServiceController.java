package com.ehsy.svccfg.svc.controller;

import com.ehsy.common.controller.BaseController;
import com.ehsy.common.msg.MsgUtils;
import com.ehsy.common.msg.response.ResponseMsg;
import com.ehsy.common.utils.Constants;
import com.ehsy.svccfg.model.SvcDescInfo;
import com.ehsy.svccfg.model.SvcSign;
import com.ehsy.svccfg.svc.service.SvcDescInfoService;
import com.ehsy.svccfg.svc.service.SvcSignService;
import com.ehsy.svcreg.model.Svc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alibaba.fastjson.JSON.parseObject;

@Controller
@RequestMapping("/")
public class ServiceController extends BaseController{

    @Autowired
    private SvcDescInfoService svcDescInfoService;

    @Autowired
    private SvcSignService svcSignService;

    @RequestMapping(value="/{component}/services")
    @ResponseBody
    public ResponseEntity<ResponseMsg<List<Svc>>> getServicesByCmp(@PathVariable String component){
        List<Svc> servicesList = svcDescInfoService.getServicesByCmp(component);
        ResponseMsg<List<Svc>> responseMsg = MsgUtils.newSuccessResponseMsg("services", servicesList);
        return new ResponseEntity<>(responseMsg, headers, HttpStatus.OK);
    }

    @RequestMapping(value="/getDescByCode")
    @ResponseBody
    public ResponseEntity<ResponseMsg<String>> getDescByCode(@RequestBody String msg){
//        Description description = new Description();
        String desc = svcDescInfoService.getDescByCode(parseObject(parseObject(msg).getString("body")).getString(Constants.SVC_CFG_DESC_KEY));
//        description.setDescription(desc);
//        ResponseMsg<Description> responseMsg = MsgUtils.newSuccessResponseMsg(description);
        Map<String, String> map = new HashMap();
        map.put("description", desc);
        ResponseMsg<String> responseMsg = MsgUtils.newSuccessResponseMsg("description", desc);
        return new ResponseEntity<>(responseMsg, headers, HttpStatus.OK);
    }

    @RequestMapping(value="/{appID}/sign")
    @ResponseBody
    public ResponseEntity<ResponseMsg<SvcSign>> getSvcSignByAppID(@PathVariable String appID){
        SvcSign svcSign = svcSignService.getSvcSignByAppID(appID);
        ResponseMsg<SvcSign> responseMsg = MsgUtils.newSuccessResponseMsg(Constants.SVC_SIGN_BY_APPID_KEY, svcSign);
        return new ResponseEntity<>(responseMsg, headers, HttpStatus.OK);
    }

    @RequestMapping(value="/descs")
    @ResponseBody
    public ResponseEntity<ResponseMsg<List<SvcDescInfo>>> getDescs(){
        List<SvcDescInfo> svcDescInfoList = svcDescInfoService.getDescs();
        ResponseMsg<List<SvcDescInfo>> responseMsg = MsgUtils.newSuccessResponseMsg(Constants.SVC_DESCS_KEY, svcDescInfoList);
        return new ResponseEntity<>(responseMsg, headers, HttpStatus.OK);
    }

    @RequestMapping(value="/insert1")
    @ResponseBody
    public ResponseEntity<ResponseMsg<Integer>> insert1(){
        ResponseMsg<Integer> responseMsg = MsgUtils.newSuccessResponseMsg(Constants.SVC_INSERT_KEY, 1);
        return new ResponseEntity<>(responseMsg, headers, HttpStatus.OK);
    }

    @RequestMapping(value="/insert2")
    @ResponseBody
    public ResponseEntity<ResponseMsg<Integer>> insert2(){
        throw new RuntimeException("数据库操作异常");
    }
}