package ro.pub.acse.sapd.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.pub.acse.sapd.configuration.PageWrapper;
import ro.pub.acse.sapd.configuration.security.ApplicationSecurityUser;
import ro.pub.acse.sapd.logging.Loggable;
import ro.pub.acse.sapd.model.entities.ApplicationTag;
import ro.pub.acse.sapd.model.entities.ApplicationUser;
import ro.pub.acse.sapd.repository.ApplicationTagRepository;
import ro.pub.acse.sapd.repository.ApplicationUserRepository;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;
import java.util.HashSet;

/**
 * Mappings and controller for managing application users
 */
@Controller
@RequestMapping(value = "/management")
public class ApplicationUserController {
    @Loggable
    private Logger log;

    @Autowired
    private ApplicationUserRepository users;
    @Autowired
    private ApplicationTagRepository tags;

    @RequestMapping("/user/new")
    public String newUser(Model model) {
        model.addAttribute("current_page", "management");
        model.addAttribute("user", new ApplicationUser());
        model.addAttribute("add_new", true);
        return "management/fragments/user";
    }

    @RequestMapping(value = {"/user/new/save", "/user/{id}/save"}, method = RequestMethod.POST)
    public ModelAndView editUser(@ModelAttribute("user") ApplicationUser user, BindingResult result,
                                 Principal principal) {
        // attempt to create the tags
        if (result.getFieldErrorCount("tags") > 0) {
            user.setTags(tags.addTagsFromBindingResult(result));
        }
        ApplicationUser activeUser = (ApplicationSecurityUser) ((Authentication) principal).getPrincipal();
        user.setLastEditedBy(activeUser);
        user.setLastEditedTime(new Date());
        users.save(user);
        log.info(String.format("Edited user %s by %s", user.getUsername(), activeUser.getUsername()));
        return new ModelAndView("redirect:/management/users");
    }

    @RequestMapping("/user/{id}")
    public String getUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("current_page", "management");
        model.addAttribute("user", users.findOne(id));
        model.addAttribute("add _new", false);
        return "management/fragments/user";
    }

    @RequestMapping(value = "/user/search/{searchTerm}", method = RequestMethod.GET)
    public String searchUser(HttpServletRequest request, Model model, Pageable pageable,
                             @PathVariable("searchTerm") String search) {
        final Sort sortOrder = new Sort(new Sort.Order(Sort.Direction.DESC, "active"),
                new Sort.Order(Sort.Direction.ASC, "lastName"));
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sortOrder);
        PageWrapper<ApplicationUser> page = new PageWrapper<>(users.search(pageRequest, search.toLowerCase()),
                request.getRequestURI());
        model.addAttribute("page", page);
        model.addAttribute("current_page", "management");
        return "management/users";
    }

    @RequestMapping(value = "/user/disable", method = RequestMethod.POST)
    public String disableUser(@RequestParam Long userId, Principal principal) {
        ApplicationSecurityUser activeUser = (ApplicationSecurityUser) ((Authentication) principal).getPrincipal();
        ApplicationUser user = users.findOne(userId);
        if (user != null) {
            user.setLastEditedBy(activeUser);
            user.setLastEditedTime(new Date());
            user.setActive(false);
            users.save(user);
            log.info(String.format("Disabled user %s by user %s",
                    user.getUsername(), activeUser.getUsername()));
        }
        return "redirect:/management/users";
    }

    @RequestMapping(value = "/user/search")
    public String searchPostUser(@RequestParam(required = false) String search) {
        return "redirect:/management/user/search/" + search;
    }

    @RequestMapping(value = {"/users/", "/users"}, method = RequestMethod.GET)
    public String listUsers(HttpServletRequest request, Model model, Pageable pageable) {
        Sort sortOrder = new Sort(new Sort.Order(Sort.Direction.ASC, "username"),
                new Sort.Order(Sort.Direction.ASC, "active"));
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sortOrder);
        PageWrapper<ApplicationUser> page = new PageWrapper<>(users.findAll(pageRequest), "/management/users");
        model.addAttribute("page", page);
        model.addAttribute("current_page", "management");
        return "management/users";
    }
}
