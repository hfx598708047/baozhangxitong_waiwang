package com.stylefeng.guns.modular.system.controller;

import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Constants;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.state.ManagerStatus;
import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.common.exception.InvalidKaptchaException;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.log.LogManager;
import com.stylefeng.guns.core.log.factory.LogTaskFactory;
import com.stylefeng.guns.core.node.MenuNode;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.util.ApiMenuFilter;
import com.stylefeng.guns.core.util.KaptchaUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.api.SmsStub;
import com.stylefeng.guns.modular.support.model.Dic;
import com.stylefeng.guns.modular.support.service.DicService;
import com.stylefeng.guns.modular.system.factory.UserFactory;
import com.stylefeng.guns.modular.system.model.User;
import com.stylefeng.guns.modular.system.service.IMenuService;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.system.transfer.UserDto;
import org.apache.axis2.AxisFault;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.stylefeng.guns.core.support.HttpKit.getIp;

/**
 * 登录控制器
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午8:25:24
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IUserService userService;

    public static SmsStub stub = null;

    static{
        try {
            stub = new SmsStub("https://api.ums86.com:9600/sms_hb/services/Sms?wsdl");//高并发时注意使用单实例
            stub._getServiceClient().getOptions().setProperty(HTTPConstants.REUSE_HTTP_CLIENT, true);//高并发时设置成true
        } catch (AxisFault e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转到主页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        //获取菜单列表
        List<String> roleList = ShiroKit.getUser().getRoleList();
        if (roleList == null || roleList.size() == 0) {
            ShiroKit.getSubject().logout();
            model.addAttribute("tips", "该用户没有角色，无法登陆");
            return "/login.html";
        }
        List<MenuNode> menus = menuService.getMenusByRoleIds(roleList);
        List<MenuNode> titles = MenuNode.buildTitle(menus);
        titles = ApiMenuFilter.build(titles);

        model.addAttribute("titles", titles);

        //获取用户头像
        String id = ShiroKit.getUser().getId();
        User user = userService.selectById(id);
        String avatar = user.getAvatar();
        model.addAttribute("avatar", avatar);

        return "/index.html";
    }

    /**
     * 跳转到登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
            return REDIRECT + "/";
        } else {
            return "/login.html";
        }
    }

    /**
     * 点击登录执行的动作
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginVali() {

        String username = super.getPara("username").trim();
        String password = super.getPara("password").trim();
        String remember = super.getPara("remember");
        //验证验证码是否正确
        if (KaptchaUtil.getKaptchaOnOff()) {
            String kaptcha = super.getPara("kaptcha").trim();
            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
                throw new InvalidKaptchaException();
            }
        }

        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());

        if ("on".equals(remember)) {
            token.setRememberMe(true);
        } else {
            token.setRememberMe(false);
        }

        currentUser.login(token);

        ShiroUser shiroUser = ShiroKit.getUser();
        super.getSession().setAttribute("shiroUser", shiroUser);
        super.getSession().setAttribute("username", shiroUser.getAccount());

        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), getIp()));
        ShiroKit.getSession().setAttribute("sessionFlag", true);
        return REDIRECT + "/";
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut() {
        LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroKit.getUser().getId(), getIp()));
        ShiroKit.getSubject().logout();
        deleteAllCookie();
        return REDIRECT + "/login";
    }


    /**
     * 点击注册执行的动作
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String updateHouse(User user) {
        // 判断账号是否重复
        User theUser = userService.getByAccount(user.getAccount());
        if (theUser != null) {
            return "1";
        }
        // 完善账号信息
        //long nowDate = new Date().getTime();
        //user.setId(nowDate);
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(ShiroKit.md5(user.getPassword(), user.getSalt()));
        user.setStatus(ManagerStatus.OK.getCode());
        //设置角色
        user.setRoleid("b2a11fd7b4404632b114c6ed5a57d995");
        //设置部门
        user.setDeptid("410cfd1bce0241b2bdfc639dc29b4684");
        //设置启用
        user.setStatus("1");
        user.setCreatetime(new Date());
        this.userService.registerUser(user);
        return "2";
    }

    /**
     * 发送验证码的动作
     */
    @RequestMapping(value = "/verificationCode", method = RequestMethod.POST)
    @ResponseBody
    public String verificationCode(String phone) {
        //产生随机数验证码
        Random random = new Random();
        String randomNum = "";
        for (int i=0;i<6;i++) {
            randomNum+=random.nextInt(10);
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        //发送接口,高并发请采用多线程提交
        SmsStub.Sms sms0 = new SmsStub.Sms();
        sms0.setIn0("103908");//企业编号
        sms0.setIn1("sy_zfcx");//登录名
        sms0.setIn2("sanyaZJJ2018");//密码
        sms0.setIn3("您的验证码为："+randomNum);//短信内容
        sms0.setIn4(phone);//手机号码
        sms0.setIn5("000"+format.format(new Date()));
        sms0.setIn6("");
        sms0.setIn7("1");
        sms0.setIn8("");
        sms0.setIn9("");
        sms0.setIn10("");
        SmsStub.SmsResponse resp;
        try {
            resp = stub.Sms(sms0);
            stub.cleanup();//使用完后cleanup
            System.out.println(resp.getOut());
            return randomNum;
        } catch (RemoteException e) {
            e.printStackTrace();
            return "error";
        }

    }


}
