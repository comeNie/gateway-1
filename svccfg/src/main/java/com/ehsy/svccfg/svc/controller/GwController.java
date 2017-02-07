package com.ehsy.svccfg.svc.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ehsy.common.controller.BaseController;
import com.ehsy.common.page.Page;
import com.ehsy.svccfg.model.SvcDescInfo;
import com.ehsy.svccfg.svc.model.SvcRelation;
import com.ehsy.svccfg.svc.model.ext.CfgRequest;
import com.ehsy.svccfg.svc.model.ext.CfgResponse;
import com.ehsy.svccfg.svc.model.ext.Result;
import com.ehsy.svccfg.svc.service.SvcDescInfoService;
import com.ehsy.svccfg.svc.service.SvcProviderService;
import com.ehsy.svccfg.svc.service.SvcRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/gw")
public class GwController extends BaseController {

    @Autowired
    private SvcDescInfoService svcDescInfoService;

    @Autowired
    private SvcProviderService svcProviderService;

    @Autowired
    private SvcRelationService svcRelationService;

    @RequestMapping(value = "/index")
    public ModelAndView index(Model model) {
        ModelAndView mv = new ModelAndView("jsp/index");
        return mv;
    }

    @RequestMapping(value = "/result")
    public ModelAndView result(String result) {
        ModelAndView mv = new ModelAndView("result");
        mv.addObject("result", JSON.parseObject(result, Result.class));
        return mv;
    }


    @RequestMapping(value = "/stop")
    @ResponseBody
    public ResponseEntity<Integer> stop(@RequestParam(required = true) String code) {
        int flg = svcDescInfoService.stop(code);
        if (flg > 0) {
            return new ResponseEntity<>(flg, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(flg, headers, HttpStatus.BAD_REQUEST);
        }
    }



    @RequestMapping(value = "/gateway")
    public ModelAndView gateway(Model model) {
        ModelAndView mv = new ModelAndView("jsp/gateway");
        return mv;
    }

    @RequestMapping(value = "/registry")
    public ModelAndView registry(Model model) {
        ModelAndView mv = new ModelAndView("jsp/registry");
        return mv;
    }

    @RequestMapping(value = "cfg/create")
    public ModelAndView configuration() {
        ModelAndView mv = new ModelAndView("cfg/create");
        mv.addObject("providers", svcProviderService.getProviders());
        return mv;
    }

    @RequestMapping(value = "cfg/save")
    public ModelAndView cfgSave(SvcDescInfo svcDescInfo) {
        int flg = svcDescInfoService.insert(svcDescInfo);
        Result result = new Result();
        if (flg > 0) {
            result.setFlg(Result.SUCCESS);
            result.setMsg("配置服务描述文件成功");
        } else {
            result.setFlg(Result.FAILED);
            result.setMsg("配置服务描述文件失败");
        }
        ModelAndView mv = new ModelAndView("redirect:/gw/result");
        result.setUrl("/service-config/gw/cfg/list");
        mv.addObject("result", result.toString());
        return mv;
    }

    @RequestMapping(value = "cfg/list")
    public ModelAndView cfgList(Model model) {
        ModelAndView mv = new ModelAndView("cfg/list");
        mv.addObject("providers", svcProviderService.getProviders());
        return mv;
    }

    @RequestMapping(value = "cfg/getCfgList")
    @ResponseBody
    public ResponseEntity<Page<SvcDescInfo>> getCfgList(SvcDescInfo svcDescInfo,
                                                        @RequestParam(value = "pageNo", required = false, defaultValue="1") int pageNo) {
        Page<SvcDescInfo> page = svcDescInfoService.list(svcDescInfo, pageNo);
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "cfg/getCfgRelationList")
    @ResponseBody
    public ResponseEntity<Page<SvcDescInfo>> getCfgRelationList(SvcDescInfo svcDescInfo,
                                                        @RequestParam(value = "pageNo", required = false, defaultValue="1") int pageNo) {
        svcDescInfo.setCategory(SvcDescInfo.SVC_DESC_CATEGORY_COM);
        Page<SvcDescInfo> page = svcDescInfoService.list(svcDescInfo, pageNo);
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "cfg/del")
    @ResponseBody
    public ResponseEntity<Boolean> cfgDel(String id) {
        int flg = svcDescInfoService.del(id);
        if (flg > 0) {
            return new ResponseEntity<>(true, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, headers, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "cfg/getDescriptionByCode")
    @ResponseBody
    public ResponseEntity<String> getDescriptionByCode(String code) {
        String description = svcDescInfoService.getDescByCode(code);
        return new ResponseEntity<>(description, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "cfg/edit")
    public ModelAndView cfgEdit(String id, Model model) {
        ModelAndView mv = new ModelAndView("cfg/edit");
        mv.addObject("providers", svcProviderService.getProviders());
        SvcDescInfo svcDescInfo = svcDescInfoService.getSvcDescInfoById(id);
        mv.addObject("svcDescInfo", svcDescInfo);
        if(null != svcDescInfo && null != svcDescInfo.getComponentMsg()){
            CfgRequest cfgRequest = new CfgRequest();
            cfgRequest.setRequestMsg(svcDescInfo.getComponentMsg().getRequestMsg());
            CfgResponse cfgResponse = new CfgResponse();
            cfgResponse.setResponseMsg(svcDescInfo.getComponentMsg().getResponseMsg());
            mv.addObject("requestMsg", JSONObject.toJSONString(cfgRequest));
            mv.addObject("responseMsg", JSONObject.toJSONString(cfgResponse));
        }
        return mv;
    }

    @RequestMapping(value = "cfg/postEdit")
    @ResponseBody
    public ModelAndView postEdit(SvcDescInfo svcDescInfo) {
        int flg = svcDescInfoService.postEdit(svcDescInfo);
        Result result = new Result();
        if (flg > 0) {
            result.setFlg(Result.SUCCESS);
            result.setMsg("编辑成功");
        } else {
            result.setFlg(Result.FAILED);
            result.setMsg("编辑失败");
        }
        ModelAndView mv = new ModelAndView("redirect:/gw/result");
        result.setUrl("/service-config/gw/cfg/list");
        mv.addObject("result", result.toString());
        return mv;
    }

    @RequestMapping(value = "cfg/checkCodeExist")
    @ResponseBody
    public ResponseEntity<Boolean> checkCodeExist(String code, String id) {
        boolean isExist = svcDescInfoService.checkSvcIsExist(code, StringUtils.isEmpty(id)? null:id);
            return new ResponseEntity<>(!isExist, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "cfg/checkAliasExist")
    @ResponseBody
    public ResponseEntity<Boolean> checkAliasExist(String alias, String id) {
        boolean isExist = svcDescInfoService.checkAliasExist(alias, StringUtils.isEmpty(id)? null:id);
        return new ResponseEntity<>(!isExist, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "cfg/relation")
    public ModelAndView relation(String id, Model model) {
        ModelAndView mv = new ModelAndView("cfg/relation");
        SvcDescInfo svcDescInfo = svcDescInfoService.getSvcDescInfoById(id);
        if(null != svcDescInfo){
            List<SvcRelation> svcRelations = svcRelationService.getRelations(svcDescInfo.getCode());
            mv.addObject("svcRelations", svcRelations);
        }
        mv.addObject("providers", svcProviderService.getProviders());
        mv.addObject("svcDescInfo", svcDescInfo);
        return mv;
    }

    @RequestMapping(value = "cfg/getRelations")
    @ResponseBody
    public ResponseEntity<List<SvcRelation>> getRelations(String svcCodeMaster) {
        List<SvcRelation> svcRelations = svcRelationService.getRelations(svcCodeMaster);
        return new ResponseEntity<>(svcRelations, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "cfg/setRelation")
    @ResponseBody
    public ResponseEntity<Boolean> setRelation(SvcRelation svcRelation) {
        int flg = svcRelationService.setRelation(svcRelation);
        if (flg > 0) {
            return new ResponseEntity<>(true, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, headers, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "cfg/updateRelation")
    @ResponseBody
    public ResponseEntity<Boolean> updateRelation(SvcRelation svcRelation) {
        int flg = svcRelationService.updateRelation(svcRelation);
        if (flg > 0) {
            return new ResponseEntity<>(true, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, headers, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "cfg/delRelation")
    @ResponseBody
    public ResponseEntity<Boolean> delRelation(SvcRelation svcRelation) {
        int flg = svcRelationService.delRelation(svcRelation);
        if (flg > 0) {
            return new ResponseEntity<>(true, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, headers, HttpStatus.BAD_REQUEST);
        }
    }
}