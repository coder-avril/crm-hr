package com.lding.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lding.domain.*;
import com.lding.service.*;
import com.lding.util.Files;
import com.lding.util.properties.UploadInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Slf4j
public class MainController {
    @Autowired
    private PersonalInfoService personService;
    @Autowired
    private SkillService skillService;
    @Autowired
    private EducationService educationService;
    @Autowired
    private ExperienceService experienceService;
    @Autowired
    private AwardService awardService;
    @Autowired
    private WebsiteService websiteService;
    @Autowired
    private ContactMeService contactMeService;
    @Autowired
    private UploadInfo uploadInfo;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping("/")
    public String initial(Model model) {
        model.addAttribute("personalInfo", this.personService.get(1));
        model.addAttribute("skills", this.skillService.getAll(1));
        model.addAttribute("website", this.websiteService.getAll(1));
        model.addAttribute("awards", this.awardService.getAll(1));
        model.addAttribute("experiences", this.experienceService.getAll(1));
        model.addAttribute("educations", this.educationService.getAll(1));
        return "index";
    }

    @PostMapping("/login")
    @ResponseBody // 一定要加，表示返回数据给前台
    public String login(String email, String password, HttpServletRequest request) throws Exception {
        // 创建返回用数据
        Map<String, Object> result = new HashMap<>();
        // 检查用户名、密码
        PersonalInfo user = new PersonalInfo();
        user.setEmail(email);
        user.setPassword(password);
        user = this.personService.getByUser(user);
        if (user != null) {
            // 将用户信息存入Session中
            request.getSession().setAttribute("user", user);
            result.put("success", true);
        } else {
            result.put("success", false);
            result.put("message", "邮件地址或者密码不正确");
        }
        // 返回json
        return new ObjectMapper().writeValueAsString(result);
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().setAttribute("user", null);
        return "admin/login";
    }

    @GetMapping("/admin/user")
    public String user(Model model, HttpServletRequest request) {
        model.addAttribute("personalInfo",
                this.personService.get(((PersonalInfo)(request.getSession().
                        getAttribute("user"))).getId()));
        return "admin/user";
    }

    @PostMapping("/admin/saveUser")
    @ResponseBody
    public String saveUser(PersonalInfo user, MultipartFile photoFile,
                           HttpServletRequest request) throws IOException {
        user.setPhoto(Files.uploadFile(user.getPhoto(), uploadInfo, photoFile));
        Map<String, Object> result = new HashMap<>();
        // 从Session中拿到密码
        String password = ((PersonalInfo)(request.getSession().getAttribute("user"))).getPassword();
        user.setPassword(password);
        if (this.personService.save(user)) {
            result.put("success", true);
            result.put("message", "用户信息保存成功");
            // 登录成功后 同期Session里面的user情报
            request.getSession().setAttribute("user", user);
        } else {
            result.put("success", false);
            result.put("message", "用户信息保存失败");
        }
        return new ObjectMapper().writeValueAsString(result);
    }

    @GetMapping("/admin/skill")
    public String skill(Model model) {
        model.addAttribute("skills", this.skillService.getAll(1));
        return "admin/skill";
    }

    @PostMapping("/admin/saveSkill")
    @ResponseBody
    public String saveSkill(Skill skill, HttpServletRequest request) throws JsonProcessingException {
        Map<String, Object> result = new HashMap<>();
        // 从Session中拿到用户ID
        Integer userId = ((PersonalInfo)(request.getSession().getAttribute("user"))).getId();
        skill.setUserId(userId);
        if (this.skillService.save(skill)) {
            result.put("success", true);
            result.put("message", "技能信息保存成功");
        } else {
            result.put("success", false);
            result.put("message", "技能信息保存失败");
        }
        return new ObjectMapper().writeValueAsString(result);
    }

    @PostMapping("/admin/removeSkill")
    @ResponseBody
    public String removeSkill(@RequestBody List<Skill> skills) throws JsonProcessingException {
        Map<String, Object> result = new HashMap<>();
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (Skill skill: skills) {
            ids.add(skill.getId());
        }
        if (this.skillService.remove(ids)) {
            result.put("success", true);
            result.put("message", "技能信息删除成功");
        } else {
            result.put("success", false);
            result.put("message", "技能信息删除失败");
        }
        return new ObjectMapper().writeValueAsString(result);
    }

    @PostMapping("/admin/savePassword")
    @ResponseBody
    public String savePassword(@RequestBody Password password) throws JsonProcessingException {
        Map<String, Object> result = new HashMap<>();
        // 检查旧密码是否正确
        PersonalInfo user = this.personService.get(1);
        if (!password.getOldPassword().equals(user.getPassword())) {
            result.put("success", false);
            result.put("message", "旧密码不正确");
        } else {
            // 更新新密码
            user.setPassword(password.getNewPassword());
            if (this.personService.save(user)) {
                // 最新化Session里面的用户情报
                result.put("success", true);
                result.put("message", "密码更新成功");
            } else {
                result.put("success", false);
                result.put("message", "密码更新失败");
            };
        }
        return new ObjectMapper().writeValueAsString(result);
    }

    @GetMapping("/admin/contact")
    public String contact(ContactRequest input, Model model) {
        if (input.getPageNo() == null) {
            input.setPageNo(1);
        }
        if (input.getPageSize() == null) {
            input.setPageSize(10);
        }
        ContactResponse response = this.contactMeService.getAll(input);
        model.addAttribute("contacts", response.getContacts());
        model.addAttribute("totalCount", response.getTotalCount());
        model.addAttribute("totalPages", response.getTotalPages());
        model.addAttribute("searchInfo", response.getSearchInfo());
        return "admin/contact";
    }

    @PostMapping("/sendInfo")
    @ResponseBody
    public String sendInfo(ContactMe contactMe) throws JsonProcessingException {
        Map<String, Object> result = new HashMap<>();
        if (contactMe.getName() == null || contactMe.getName().trim().equals("") ||
                contactMe.getEmail() == null || contactMe.getEmail().trim().equals("") ||
                contactMe.getMessage() == null || contactMe.getMessage().equals("")) {
            result.put("success", false);
            result.put("message", "发送失败！请检查姓名、邮件和消息内容，不可以为空！");
        } else {
            contactMe.setUserId("1");
            contactMe.setSendDate(format.format(new Date()));
            if (this.contactMeService.save(contactMe)) {
                result.put("success", true);
                result.put("message", "联系信息发送成功");
            } else {
                result.put("success", false);
                result.put("message", "服务器忙！联系信息发送失败！请稍后再试！");
            }
        }
        return new ObjectMapper().writeValueAsString(result);
    }

    @GetMapping("/admin/education")
    public String education(Model model) {
        model.addAttribute("educations", this.educationService.getAll(1));
        return "admin/education";
    }

    @PostMapping("/admin/saveEducation")
    @ResponseBody
    public String saveEducation(Education education, HttpServletRequest request) throws JsonProcessingException {
        Map<String, Object> result = new HashMap<>();
        // 从Session中拿到用户ID
        Integer userId = ((PersonalInfo)(request.getSession().getAttribute("user"))).getId();
        education.setUserId(userId);
        if (this.educationService.save(education)) {
            result.put("success", true);
            result.put("message", "教育信息保存成功");
        } else {
            result.put("success", false);
            result.put("message", "教育信息保存失败");
        }
        return new ObjectMapper().writeValueAsString(result);
    }

    @PostMapping("/admin/removeEducation")
    @ResponseBody
    public String removeEducation(@RequestBody List<Education> educations) throws JsonProcessingException {
        Map<String, Object> result = new HashMap<>();
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (Education education: educations) {
            ids.add(education.getId());
        }
        if (this.educationService.remove(ids)) {
            result.put("success", true);
            result.put("message", "教育信息删除成功");
        } else {
            result.put("success", false);
            result.put("message", "教育信息删除失败");
        }
        return new ObjectMapper().writeValueAsString(result);
    }

    @GetMapping("/admin/experience")
    public String experience(Model model) {
        model.addAttribute("experiences", this.experienceService.getAll(1));
        return "admin/experience";
    }

    @PostMapping("/admin/saveExperience")
    @ResponseBody
    public String saveExperience(Experience experience, HttpServletRequest request) throws JsonProcessingException {
        Map<String, Object> result = new HashMap<>();
        // 从Session中拿到用户ID
        Integer userId = ((PersonalInfo)(request.getSession().getAttribute("user"))).getId();
        experience.setUserId(userId);
        if (this.experienceService.save(experience)) {
            result.put("success", true);
            result.put("message", "教育信息保存成功");
        } else {
            result.put("success", false);
            result.put("message", "教育信息保存失败");
        }
        return new ObjectMapper().writeValueAsString(result);
    }

    @PostMapping("/admin/removeExperience")
    @ResponseBody
    public String removeExperience(@RequestBody List<Experience> experiences) throws JsonProcessingException {
        Map<String, Object> result = new HashMap<>();
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (Experience experience: experiences) {
            ids.add(experience.getId());
        }
        if (this.experienceService.remove(ids)) {
            result.put("success", true);
            result.put("message", "教育信息删除成功");
        } else {
            result.put("success", false);
            result.put("message", "教育信息删除失败");
        }
        return new ObjectMapper().writeValueAsString(result);
    }

    @GetMapping("/admin/award")
    public String award(Model model) {
        model.addAttribute("awards", this.awardService.getAll(1));
        return "admin/award";
    }

    @PostMapping("/admin/saveAward")
    @ResponseBody
    public String saveAward(Award award, MultipartFile pictureFile, HttpServletRequest request) throws Exception {
        award.setPicture(Files.uploadFile(award.getPicture(), uploadInfo, pictureFile));
        Map<String, Object> result = new HashMap<>();
        // 从Session中拿到用户ID
        Integer userId = ((PersonalInfo)(request.getSession().getAttribute("user"))).getId();
        award.setUserId(userId);
        if (this.awardService.save(award)) {
            result.put("success", true);
            result.put("message", "获奖信息保存成功");
        } else {
            result.put("success", false);
            result.put("message", "获奖信息保存失败");
        }
        return new ObjectMapper().writeValueAsString(result);
    }

    @PostMapping("/admin/removeAward")
    @ResponseBody
    public String removeAward(@RequestBody List<Award> awards) throws JsonProcessingException {
        Map<String, Object> result = new HashMap<>();
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (Award award: awards) {
            ids.add(award.getId());
        }
        if (this.awardService.remove(ids)) {
            result.put("success", true);
            result.put("message", "获奖信息删除成功");
        } else {
            result.put("success", false);
            result.put("message", "获奖信息删除失败");
        }
        return new ObjectMapper().writeValueAsString(result);
    }

    @GetMapping("/admin/website")
    public String website(Model model) {
        model.addAttribute("website", this.websiteService.getAll(1));
        return "admin/website";
    }

    @PostMapping("/admin/saveWebsite")
    @ResponseBody
    public String saveWebsite(Website website, HttpServletRequest request) throws JsonProcessingException {
        Map<String, Object> result = new HashMap<>();
        // 从Session中拿到用户ID
        Integer userId = ((PersonalInfo)(request.getSession().getAttribute("user"))).getId();
        website.setUserId(userId);
        if (this.websiteService.save(website)) {
            result.put("success", true);
            result.put("message", "网站信息保存成功");
        } else {
            result.put("success", false);
            result.put("message", "网站信息保存失败");
        }
        return new ObjectMapper().writeValueAsString(result);
    }

    @PostMapping("/admin/removeWebsite")
    @ResponseBody
    public String removeWebsite(@RequestBody Website website) throws JsonProcessingException {
        Map<String, Object> result = new HashMap<>();
        if (this.websiteService.remove(website.getId())) {
            result.put("success", true);
            result.put("message", "网站信息删除成功");
        } else {
            result.put("success", false);
            result.put("message", "网站信息删除失败");
        }
        return new ObjectMapper().writeValueAsString(result);
    }

    @GetMapping("/download")
    public void downloadFile(HttpServletResponse response) throws Exception {
        // 设置响应头
        response.setHeader("Content-Disposition", "attachment; filename=lding_hr.png");
        // 读取文件
        try (InputStream is = new ClassPathResource("templates/lding_hr.png").getInputStream()) {
            // 将文件数据利用response写回客户端
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}
