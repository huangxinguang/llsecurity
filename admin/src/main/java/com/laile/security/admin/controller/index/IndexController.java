package com.laile.security.admin.controller.index;

import com.laile.security.admin.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by huangxinguang on 2017/9/26 下午3:24.
 */

@Controller
@RequestMapping
public class IndexController extends BaseController {

    @RequestMapping(value = "/toMainPage")
    public ModelAndView toMainPage() {
        ModelAndView mav = getModelAndView();
        mav.setViewName("/index/main");
        return mav;
    }

    @RequestMapping(value = "/index")
    public ModelAndView toIndex() {
        ModelAndView mav = getModelAndView();
        mav.addObject("admin",getSessionUser());
        mav.setViewName("/index");
        return mav;
    }

}
