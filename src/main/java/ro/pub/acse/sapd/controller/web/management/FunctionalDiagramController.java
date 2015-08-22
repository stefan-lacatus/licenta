package ro.pub.acse.sapd.controller.web.management;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.pub.acse.sapd.configuration.PageWrapper;
import ro.pub.acse.sapd.configuration.security.ApplicationSecurityUser;
import ro.pub.acse.sapd.diagrams.schema.DiagramParseException;
import ro.pub.acse.sapd.diagrams.schema.DiagramParser;
import ro.pub.acse.sapd.diagrams.schema.gojs.GoJsDiagramParser;
import ro.pub.acse.sapd.logging.Loggable;
import ro.pub.acse.sapd.model.entities.ApplicationUser;
import ro.pub.acse.sapd.model.entities.DataChannel;
import ro.pub.acse.sapd.model.entities.FunctionalDiagram;
import ro.pub.acse.sapd.repository.ApplicationTagRepository;
import ro.pub.acse.sapd.repository.DataChannelRepository;
import ro.pub.acse.sapd.repository.FunctionalDiagramRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;

/**
 * Mappings and controller for managing processorBlocks users
 */
@Controller
@RequestMapping(value = "/management")
public class FunctionalDiagramController {
    @Loggable
    private Logger log;

    @Autowired
    private FunctionalDiagramRepository diagrams;
    @Autowired
    private ApplicationTagRepository tags;
    @Autowired
    private DataChannelRepository dataChannelRepository;

    @RequestMapping("/diagram/new")
    public String newDiagram(Model model) {
        model.addAttribute("current_page", "management");
        FunctionalDiagram diagram = new FunctionalDiagram();
        diagram.setActive(true);
        model.addAttribute("diagram", diagram);
        model.addAttribute("add_new", true);
        model.addAttribute("default_diagram", "{ \"class\": \"go.GraphLinksModel\",\n" +
                "  \"copiesArrays\": true,\n" +
                "  \"copiesArrayObjects\": true,\n" +
                "  \"linkFromPortIdProperty\": \"fromPort\",\n" +
                "  \"linkToPortIdProperty\": \"toPort\",\n" +
                "  \"nodeDataArray\": [ \n" +
                "{\"key\":-2, \"category\":\"End\", \"text\":\"End\", \"loc\":\"243.74999999999966 29.450000000000003\"},\n" +
                "{\"category\":\"Comment\", \"text\":\"Comment\", \"key\":-3, \"loc\":\"521.5 -115\"},\n" +
                "{\"category\":\"Input\", \"text\":\"Channel\", \"key\":-1, \"loc\":\"28 29.450000000000003\"},\n" +
                "{\"text\":\"Function\", \"inputArray\":[ {\"portColor\":\"#425e5c\", \"portId\":\"left0\"} ], \"key\":-4, \"loc\":\"142 29.450000000000003\"}\n" +
                " ],\n" +
                "  \"linkDataArray\": [ \n" +
                "{\"from\":-1, \"to\":-4, \"fromPort\":\"OUT\", \"toPort\":\"left0\", \"points\":[56,51.75,66,51.75,81,51.75,81,29.450000000000003,96,29.450000000000003,106,29.450000000000003]},\n" +
                "{\"from\":-4, \"to\":-2, \"fromPort\":\"OUT\", \"toPort\":\"IN\", \"points\":[171.5,51.75,181.5,51.75,196.75,51.75,196.75,29.450000000000003,212,29.450000000000003,222,29.450000000000003]}\n" +
                " ]}");
        return "management/fragments/diagram";
    }

    @Transactional
    @RequestMapping(value = {"/diagram/new/save", "/diagram/{id}/save"}, method = RequestMethod.POST)
    public ModelAndView editDiagram(@ModelAttribute("diagram") FunctionalDiagram diagram, BindingResult result,
                                    Principal principal) throws IOException, DiagramParseException {
        // attempt to create the tags
        if (result.getFieldErrorCount("tags") > 0) {
            diagram.setTags(tags.addTagsFromBindingResult(result));
        }
        ApplicationUser activeUser = (ApplicationSecurityUser) ((Authentication) principal).getPrincipal();
        diagram.setLastEditedBy(activeUser);
        diagram.setLastEditedTime(new Date());

        diagram = diagrams.save(diagram);
        // parse the diagram to get the channel list
        DiagramParser parser = new GoJsDiagramParser(diagram, dataChannelRepository);
        for (DataChannel dataChannel : parser.getUsedChannels()) {
            dataChannel.addSubscribedDiagram(diagram);
            dataChannelRepository.save(dataChannel);
        }
        log.info(String.format("Edited diagram %s by %s", diagram.getName(), activeUser.getUsername()));
        return new ModelAndView("redirect:/management/diagrams");
    }

    @RequestMapping("/diagram/{id}")
    public String getDiagram(Model model, @PathVariable("id") long id) throws IOException, DiagramParseException {
        model.addAttribute("current_page", "management");
        FunctionalDiagram diagram = diagrams.findOne(id);
        model.addAttribute("diagram", diagram);
        model.addAttribute("add _new", false);
        // remove the subscribed channels
        // parse the diagram to get the channel list
        DiagramParser parser = new GoJsDiagramParser(diagram, dataChannelRepository);
        for (DataChannel dataChannel : parser.getUsedChannels()) {
            dataChannel.removeSubscribedDiagram(diagram);
            dataChannelRepository.save(dataChannel);
        }
        return "management/fragments/diagram";
    }

    @RequestMapping(value = "/diagram/search/{searchTerm}", method = RequestMethod.GET)
    public String searchDiagram(HttpServletRequest request, Model model, Pageable pageable,
                                @PathVariable("searchTerm") String search) {
        final Sort sortOrder = new Sort(new Sort.Order(Sort.Direction.DESC, "active"),
                new Sort.Order(Sort.Direction.ASC, "name"));
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sortOrder);
        PageWrapper<FunctionalDiagram> page = new PageWrapper<>(diagrams.search(pageRequest, search.toLowerCase()),
                request.getRequestURI());
        model.addAttribute("page", page);
        model.addAttribute("current_page", "management");
        return "management/diagrams";
    }

    @RequestMapping(value = "/diagram/disable", method = RequestMethod.POST)
    public String disableDiagram(@RequestParam Long diagramId, Principal principal) {
        ApplicationSecurityUser activeUser = (ApplicationSecurityUser) ((Authentication) principal).getPrincipal();
        FunctionalDiagram diagram = diagrams.findOne(diagramId);
        if (diagram != null) {
            diagram.setLastEditedBy(activeUser);
            diagram.setLastEditedTime(new Date());
            diagrams.save(diagram);
            log.info(String.format("Disabled diagram %s by user %s",
                    diagram.getName(), activeUser.getUsername()));
        }
        return "redirect:/management/diagrams";
    }

    @RequestMapping(value = "/diagram/search")
    public String searchPostDiagram(@RequestParam(required = false) String search) {
        return "redirect:/management/diagram/search/" + search;
    }

    @RequestMapping(value = {"/diagrams/", "/diagrams"}, method = RequestMethod.GET)
    public String listDiagrams(HttpServletRequest request, Model model, Pageable pageable) {
        Sort sortOrder = new Sort(new Sort.Order(Sort.Direction.ASC, "name"),
                new Sort.Order(Sort.Direction.ASC, "active"));
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sortOrder);
        PageWrapper<FunctionalDiagram> page = new PageWrapper<>(diagrams.findAll(pageRequest), "/management/diagrams");
        model.addAttribute("page", page);
        model.addAttribute("current_page", "management");
        return "management/diagrams";
    }
}
