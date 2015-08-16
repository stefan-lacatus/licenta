package ro.pub.acse.sapd.controller.web;

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
import ro.pub.acse.sapd.model.entities.InputBlock;
import ro.pub.acse.sapd.repository.ApplicationTagRepository;
import ro.pub.acse.sapd.repository.InputBlockRepository;
import ro.pub.acse.sapd.repository.InputChannelRepository;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;

/**
 * Mappings and controller for managing application inputs
 */
@Controller
@RequestMapping(value = "/management")
public class InputController {
    @Loggable
    private Logger log;

    @Autowired
    private InputBlockRepository inputs;
    @Autowired
    private ApplicationTagRepository tags;
    @Autowired
    private InputChannelRepository channels;

    @RequestMapping("/input/new")
    public String newInput(Model model) {
        model.addAttribute("current_page", "management");
        InputBlock block = new InputBlock();
        block.setActive(true);
        model.addAttribute("input", block);
        model.addAttribute("add_new", true);
        return "management/fragments/input";
    }

    @RequestMapping(value = {"/input/new/save", "/input/{id}/save"}, method = RequestMethod.POST)
    public ModelAndView editInput(@ModelAttribute("input") InputBlock input, BindingResult result, Principal principal) {
        if (result.getFieldErrorCount("tags") > 0) {
            input.setTags(tags.addTagsFromBindingResult(result));
        }
        channels.save(input.getChannels());
        ApplicationSecurityUser activeUser = (ApplicationSecurityUser) ((Authentication) principal).getPrincipal();
        input.setLastEditedBy(activeUser);
        input.setLastEditedTime(new Date());
        inputs.save(input);
        log.info(String.format("Edited input %s by %s", input.getName(), activeUser.getUsername()));
        return new ModelAndView("redirect:/management/inputs");
    }

    @RequestMapping("/input/{id}")
    public String getInput(Model model, @PathVariable("id") long id) {
        model.addAttribute("current_page", "management");
        model.addAttribute("input", inputs.findOne(id));
        model.addAttribute("add _new", false);
        return "management/fragments/input";
    }

    @RequestMapping(value = "/input/search/{searchTerm}", method = RequestMethod.GET)
    public String searchInput(HttpServletRequest request, Model model, Pageable pageable,
                              @PathVariable("searchTerm") String search) {
        final Sort sortOrder = new Sort(new Sort.Order(Sort.Direction.DESC, "active"),
                new Sort.Order(Sort.Direction.ASC, "name"));
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sortOrder);
        PageWrapper<InputBlock> page = new PageWrapper<>(inputs.search(pageRequest, search.toLowerCase()),
                request.getRequestURI());
        model.addAttribute("page", page);
        model.addAttribute("current_page", "management");
        return "management/inputs";
    }

    @RequestMapping(value = "/input/disable", method = RequestMethod.POST)
    public String disableInput(@RequestParam Long inputId, Principal principal) {
        ApplicationSecurityUser lastEditedBy = (ApplicationSecurityUser) ((Authentication) principal).getPrincipal();
        InputBlock input = inputs.findOne(inputId);
        if (input != null) {
            input.setLastEditedBy(lastEditedBy);
            input.setLastEditedTime(new Date());
            input.setActive(false);
            inputs.save(input);
            log.info(String.format("Disabled input %s by input %s",
                    input.getName(), lastEditedBy.getUsername()));
        }
        return "redirect:/management/inputs";
    }

    @RequestMapping(value = "/input/search")
    public String searchPostInput(@RequestParam(required = false) String search) {
        return "redirect:/management/input/search/" + search;
    }

    @RequestMapping(value = {"/inputs/", "/inputs"}, method = RequestMethod.GET)
    public String listInputs(HttpServletRequest request, Model model, Pageable pageable) {
        Sort sortOrder = new Sort(new Sort.Order(Sort.Direction.DESC, "active"),
                new Sort.Order(Sort.Direction.ASC, "name"));
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sortOrder);
        PageWrapper<InputBlock> page = new PageWrapper<>(inputs.findAll(pageRequest), "/management/inputs");
        model.addAttribute("page", page);
        model.addAttribute("current_page", "management");
        return "management/inputs";
    }
}
