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
import ro.pub.acse.sapd.model.entities.ApplicationUser;
import ro.pub.acse.sapd.model.entities.ProcessorBlock;
import ro.pub.acse.sapd.repository.ApplicationTagRepository;
import ro.pub.acse.sapd.repository.ProcessorBlockRepository;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;

/**
 * Mappings and controller for managing processorBlocks users
 */
@Controller
@RequestMapping(value = "/management")
public class ProcessorBlockController {
    @Loggable
    private Logger log;

    @Autowired
    private ProcessorBlockRepository blocks;
    @Autowired
    private ApplicationTagRepository tags;

    @RequestMapping("/block/new")
    public String newBlock(Model model) {
        model.addAttribute("current_page", "management");
        ProcessorBlock process = new ProcessorBlock();
        process.setActive(true);
        model.addAttribute("block", process);
        model.addAttribute("add_new", true);
        return "management/fragments/block";
    }

    @RequestMapping(value = {"/block/new/save", "/block/{id}/save"}, method = RequestMethod.POST)
    public ModelAndView editBlock(@ModelAttribute("block") ProcessorBlock block, BindingResult result,
                                  Principal principal) {
        // attempt to create the tags
        if (result.getFieldErrorCount("tags") > 0) {
            block.setTags(tags.addTagsFromBindingResult(result));
        }
        ApplicationUser activeUser = (ApplicationSecurityUser) ((Authentication) principal).getPrincipal();
        block.setLastEditedBy(activeUser);
        block.setLastEditedTime(new Date());

        blocks.save(block);
        log.info(String.format("Edited block %s by %s", block.getName(), activeUser.getUsername()));
        return new ModelAndView("redirect:/management/blocks");
    }

    @RequestMapping("/block/{id}")
    public String getBlock(Model model, @PathVariable("id") long id) {
        model.addAttribute("current_page", "management");
        model.addAttribute("block", blocks.findOne(id));
        model.addAttribute("add _new", false);
        return "management/fragments/block";
    }

    @RequestMapping(value = "/block/search/{searchTerm}", method = RequestMethod.GET)
    public String searchBlock(HttpServletRequest request, Model model, Pageable pageable,
                              @PathVariable("searchTerm") String search) {
        final Sort sortOrder = new Sort(new Sort.Order(Sort.Direction.DESC, "active"),
                new Sort.Order(Sort.Direction.ASC, "name"));
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sortOrder);
        PageWrapper<ProcessorBlock> page = new PageWrapper<>(blocks.search(pageRequest, search.toLowerCase()),
                request.getRequestURI());
        model.addAttribute("page", page);
        model.addAttribute("current_page", "management");
        return "management/blocks";
    }

    @RequestMapping(value = "/block/disable", method = RequestMethod.POST)
    public String disableBlock(@RequestParam Long blockId, Principal principal) {
        ApplicationSecurityUser activeUser = (ApplicationSecurityUser) ((Authentication) principal).getPrincipal();
        ProcessorBlock block = blocks.findOne(blockId);
        if (block != null) {
            block.setLastEditedBy(activeUser);
            block.setLastEditedTime(new Date());
            blocks.save(block);
            log.info(String.format("Disabled block %s by user %s",
                    block.getName(), activeUser.getUsername()));
        }
        return "redirect:/management/blocks";
    }

    @RequestMapping(value = "/block/search")
    public String searchPostBlock(@RequestParam(required = false) String search) {
        return "redirect:/management/block/search/" + search;
    }

    @RequestMapping(value = {"/blocks/", "/blocks"}, method = RequestMethod.GET)
    public String listBlocks(HttpServletRequest request, Model model, Pageable pageable) {
        Sort sortOrder = new Sort(new Sort.Order(Sort.Direction.ASC, "name"),
                new Sort.Order(Sort.Direction.ASC, "active"));
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sortOrder);
        PageWrapper<ProcessorBlock> page = new PageWrapper<>(blocks.findAll(pageRequest), "/management/blocks");
        model.addAttribute("page", page);
        model.addAttribute("current_page", "management");
        return "management/blocks";
    }
}
