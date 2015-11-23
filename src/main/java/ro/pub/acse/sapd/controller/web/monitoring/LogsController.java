package ro.pub.acse.sapd.controller.web.monitoring;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ro.pub.acse.sapd.configuration.PageWrapper;
import ro.pub.acse.sapd.logging.Loggable;
import ro.pub.acse.sapd.model.entities.logback.LoggingEvent;
import ro.pub.acse.sapd.repository.LoggingEventRepository;

import javax.servlet.http.HttpServletRequest;

/**
 * Display the logs
 */
@Controller
@RequestMapping(value = {"/monitor", "/monitor/logs"})
public class LogsController {
    @Loggable
    private Logger log;

    @Autowired
    private LoggingEventRepository repository;

    @RequestMapping(value = "/logs/search/{searchTerm}", method = RequestMethod.GET)
    public String searchUser(HttpServletRequest request, Model model, Pageable pageable,
                             @PathVariable("searchTerm") String search) {
        final Sort sortOrder = new Sort(new Sort.Order(Sort.Direction.DESC, "timestmp"));
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sortOrder);
        PageWrapper<LoggingEvent> page = new PageWrapper<>(repository.search(pageRequest, search.toLowerCase()),
                request.getRequestURI());
        model.addAttribute("page", page);
        model.addAttribute("current_page", "monitor");
        return "monitor/logs";
    }

    @RequestMapping(value = "/logs/search")
    public String searchPostUser(@RequestParam(required = false) String search) {
        return "redirect:/monitor/logs/search/" + search;
    }

    @RequestMapping(value = {"/logs/", "/logs"}, method = RequestMethod.GET)
    public String listUsers(HttpServletRequest request, Model model, Pageable pageable) {
        Sort sortOrder = new Sort(new Sort.Order(Sort.Direction.DESC, "timestmp"));
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sortOrder);
        PageWrapper<LoggingEvent> page = new PageWrapper<>(repository.findAll(pageRequest), "/monitor/logs");
        model.addAttribute("page", page);
        model.addAttribute("current_page", "monitor");
        return "monitor/logs";
    }
}