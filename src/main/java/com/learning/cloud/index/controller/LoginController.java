package com.learning.cloud.index.controller;

import com.dingtalk.api.response.OapiUserGetResponse;
import com.learning.cloud.bureau.dao.BureauDao;
import com.learning.cloud.bureau.entity.Bureau;
import com.learning.cloud.index.entity.SysUserInfo;
import com.learning.cloud.index.entity.UserInfo;
import com.learning.cloud.school.dao.SchoolDao;
import com.learning.cloud.school.entity.School;
import com.learning.cloud.user.parent.dao.ParentDao;
import com.learning.cloud.user.parent.entity.Parent;
import com.learning.cloud.user.student.dao.StudentDao;
import com.learning.cloud.user.student.entity.Student;
import com.learning.cloud.user.teacher.dao.TeacherDao;
import com.learning.cloud.user.teacher.entity.Teacher;
import com.learning.cloud.user.user.dao.UserDao;
import com.learning.cloud.user.user.entity.User;
import com.learning.domain.JsonResult;
import com.learning.enums.JsonResultEnum;
import com.learning.exception.MyException;
import com.learning.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.learning.cloud.dingCommon.DingUtils.*;

/**
 * 免登
 */
@RestController
public class LoginController {

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private BureauDao bureauDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private ParentDao parentDao;

    @Autowired
    private TeacherDao teacherDao;

    /**
     * 应用管理后台免登
     * 1.配置微应用后台地址
     * 2.获取免登过程中密钥（SSOSecret）
     * 3.1获取免登授权码
     *
     */
    @PostMapping("/oaLogin")
    public JsonResult oaLogin(@RequestParam(value="code",required = true) String code)throws Exception{
        return getAdministrator(code);
    }

    /**
     * 小程序免登
     * 1.前端获取免登授权码authCode（授权码，有效期5分钟，且只能使用一次）
     * userid
     */
    @PostMapping("/miniThirdPartyLogin")
    public JsonResult miniThirdPartyLogin(@RequestParam(value="authCode",required = true) String authCode, @RequestParam(value="corpid",required = true) String corpid) throws Exception{
        return getUserInfo(authCode, corpid);
    }

    /**
     * 扫码登录第三方网站
     * 1.获取appId及appSecret
     * 2.构造扫码登录页面
     * 3.服务端通过临时授权码获取授权用户的个人信息
     * openid
     */
    @PostMapping("/scanCodeThirdPartyLogin")
    public JsonResult scanCodeThirdPartyLogin(@RequestParam(value="authCode",required = true) String authCode,
                                              @RequestParam(value="appId",required = true) String appId,
                                              @RequestParam(value="appSecret",required = true) String appSecret) throws Exception{
        return getUserInfoByCode(authCode, appId, appSecret);
    }

    /**
     * 获取用户在当前系统的角色
     */
    @GetMapping("/getSysUserRole")
    public JsonResult getSysUserRole(String userid, String unionid, String corpid)throws Exception{
        // 返回值
        UserInfo userInfo = new UserInfo();
        List<User> users = new ArrayList<>();
        // 第三方扫码
        if(corpid==null||corpid.isEmpty()){
            if(!unionid.isEmpty()){
                users = userDao.getByUnionId(unionid);
            }else {
                throw new MyException(JsonResultEnum.NO_USER);
            }
        } else {
            if(!userid.isEmpty()){
                users = userDao.getUserByUserIdAndCorpId(userid, corpid);
            }else {
                throw new MyException(JsonResultEnum.NO_USER);
            }
        }
        // 当用户处在多个组织时
        List<SysUserInfo> sysUserInfos = new ArrayList<>();
        for(int i=0;i<users.size();i++){
            User user = users.get(i);
            int userRole = user.getRoleType();
            OapiUserGetResponse userGetResponse = getUserByUserid(user.getUserId(), user.getCorpId());
            if(userGetResponse!=null){
                if(i==0){
                    userInfo.setAvatar(userGetResponse.getAvatar());
                    userInfo.setName(userGetResponse.getName());
                    userInfo.setUnionid(unionid);
                }
                SysUserInfo sysUserInfo = new SysUserInfo();
                sysUserInfo.setCropid(user.getCorpId());
                sysUserInfo.setUserid(user.getUserId());
                sysUserInfo.setAdmin(userGetResponse.getIsAdmin());
                sysUserInfo.setRole(userRole);
                sysUserInfo.setId(user.getId());
                String cropName = "";
                //老师
                School school = new School();
                if(user.getSchoolId()==null||user.getSchoolId()==-1){
                    // 获取教育局id
                    Bureau bureau = bureauDao.getByCorpId(user.getCorpId());
                    sysUserInfo.setBureauId(bureau.getId());
                    cropName = bureau.getBureauName();
                }else {
                    school = schoolDao.getSchoolByCorpId(user.getCorpId());
                    cropName = school.getSchoolName();
                    sysUserInfo.setBureauId(school.getBureauId());
                    sysUserInfo.setSchoolId(user.getSchoolId());
                }
                // 2: 家长  3: 老师 4: 学生 5:其他
                if(userRole==2){
                    Parent parent = parentDao.getByUserId(user.getUserId());
                    sysUserInfo.setClassId(parent.getClassId());
                }
                if(userRole==4){
                    //获取班级id
                    Student student = studentDao.getByUserId(user.getUserId());
                    sysUserInfo.setClassId(student.getClassId().toString());
                }
                else if(userRole==3){
                    //获取老师id
                    Teacher teacher = new Teacher();
                    teacher.setSchoolId(school.getId());
                    teacher = teacherDao.getTeacherInSchool(teacher);
                    sysUserInfo.setTeacherId(teacher.getId());
                }


                sysUserInfo.setCropName(cropName);
                sysUserInfos.add(sysUserInfo);
            }
        }
        userInfo.setSysUserInfos(sysUserInfos);
        return JsonResultUtil.success(userInfo);

    }
}
