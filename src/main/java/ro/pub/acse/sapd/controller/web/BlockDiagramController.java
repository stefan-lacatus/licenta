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
import ro.pub.acse.sapd.model.entities.BlockDiagram;
import ro.pub.acse.sapd.model.entities.ProcessorBlock;
import ro.pub.acse.sapd.repository.ApplicationTagRepository;
import ro.pub.acse.sapd.repository.BlockDiagramRepository;
import ro.pub.acse.sapd.repository.ProcessorBlockRepository;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;

/**
 * Mappings and controller for managing processorBlocks users
 */
@Controller
@RequestMapping(value = "/management")
public class BlockDiagramController {
    @Loggable
    private Logger log;

    @Autowired
    private BlockDiagramRepository diagrams;
    @Autowired
    private ApplicationTagRepository tags;

    @RequestMapping("/diagram/new")
    public String newDiagram(Model model) {
        model.addAttribute("current_page", "management");
        model.addAttribute("diagram", new BlockDiagram());
        model.addAttribute("add_new", true);
        model.addAttribute("default_diagram","  {\n" +
                "      \"class\": \"go.GraphLinksModel\",\n" +
                "      \"linkFromPortIdProperty\": \"fromPort\",\n" +
                "      \"linkToPortIdProperty\": \"toPort\",\n" +
                "      \"nodeDataArray\": [{\n" +
                "          \"category\": \"Comment\",\n" +
                "          \"text\": \"Kookie Brittle\",\n" +
                "          \"key\": -13\n" +
                "      }, {\n" +
                "          \"key\": -1,\n" +
                "          \"category\": \"Start\",\n" +
                "          \"text\": \"Start\"\n" +
                "      }, {\n" +
                "          \"key\": 0,\n" +
                "          \"text\": \"Preheat oveF\"\n" +
                "      }, {\n" +
                "          \"key\": 1,\n" +
                "          \"text\": \"In n salt\"\n" +
                "      }, {\n" +
                "          \"key\": 2,\n" +
                "          \"text\": \"Graduallyour\"\n" +
                "      }, {\n" +
                "          \"key\": 3,\n" +
                "          \"text\": \"Mix ls\"\n" +
                "      }, {\n" +
                "          \"key\": 4,\n" +
                "          \"text\": \"Press\"\n" +
                "      }, {\n" +
                "          \"key\": 5,\n" +
                "          \"text\": \"Finely c\"\n" +
                "      }, {\n" +
                "          \"key\": 6,\n" +
                "          \"text\": \"Sprp\"\n" +
                "      }, {\n" +
                "          \"key\": 7,\n" +
                "          \"text\": \"Bakl\"\n" +
                "      }, {\n" +
                "          \"key\": 8,\n" +
                "          \"text\": \"Cut d\"\n" +
                "      }, {\n" +
                "          \"key\": -2,\n" +
                "          \"category\": \"End\",\n" +
                "          \"text\": \"End\"\n" +
                "      }],\n" +
                "      \"linkDataArray\": [{\n" +
                "          \"from\": 1,\n" +
                "          \"to\": 2,\n" +
                "          \"fromPort\": \"OUT\",\n" +
                "          \"toPort\": \"IN\"\n" +
                "      }, {\n" +
                "          \"from\": 2,\n" +
                "          \"to\": 3,\n" +
                "          \"fromPort\": \"OUT\",\n" +
                "          \"toPort\": \"IN\"\n" +
                "      }, {\n" +
                "          \"from\": 3,\n" +
                "          \"to\": 4,\n" +
                "          \"fromPort\": \"OUT\",\n" +
                "          \"toPort\": \"IN\"\n" +
                "      }, {\n" +
                "          \"from\": 4,\n" +
                "          \"to\": 6,\n" +
                "          \"fromPort\": \"OUT\",\n" +
                "          \"toPort\": \"IN\"\n" +
                "      }, {\n" +
                "          \"from\": 6,\n" +
                "          \"to\": 7,\n" +
                "          \"fromPort\": \"OUT\",\n" +
                "          \"toPort\": \"IN\"\n" +
                "      }, {\n" +
                "          \"from\": 7,\n" +
                "          \"to\": 8,\n" +
                "          \"fromPort\": \"OUT\",\n" +
                "          \"toPort\": \"IN\"\n" +
                "      }, {\n" +
                "          \"from\": 8,\n" +
                "          \"to\": -2,\n" +
                "          \"fromPort\": \"OUT\",\n" +
                "          \"toPort\": \"IN\"\n" +
                "      }, {\n" +
                "          \"from\": -1,\n" +
                "          \"to\": 0,\n" +
                "          \"fromPort\": \"OUT\",\n" +
                "          \"toPort\": \"IN\"\n" +
                "      }, {\n" +
                "          \"from\": -1,\n" +
                "          \"to\": 1,\n" +
                "          \"fromPort\": \"OUT\",\n" +
                "          \"toPort\": \"IN\"\n" +
                "      }, {\n" +
                "          \"from\": -1,\n" +
                "          \"to\": 5,\n" +
                "          \"fromPort\": \"OUT\",\n" +
                "          \"toPort\": \"IN\"\n" +
                "      }, {\n" +
                "          \"from\": 5,\n" +
                "          \"to\": 4,\n" +
                "          \"fromPort\": \"OUT\",\n" +
                "          \"toPort\": \"IN\"\n" +
                "      }, {\n" +
                "          \"from\": 0,\n" +
                "          \"to\": 4,\n" +
                "          \"fromPort\": \"OUT\",\n" +
                "          \"toPort\": \"IN\"\n" +
                "      }, {\n" +
                "          \"from\": 2,\n" +
                "          \"to\": 4,\n" +
                "          \"fromPort\": \"OUT\",\n" +
                "          \"toPort\": \"IN\"\n" +
                "      }]\n" +
                "  }");
        return "management/fragments/diagram";
    }

    @RequestMapping(value = {"/diagram/new/save", "/diagram/{id}/save"}, method = RequestMethod.POST)
    public ModelAndView editDiagram(@ModelAttribute("diagram") BlockDiagram diagram, BindingResult result,
                                    Principal principal) {
        // attempt to create the tags
        if (result.getFieldErrorCount("tags") > 0) {
            diagram.setTags(tags.addTagsFromBindingResult(result));
        }
        ApplicationUser activeUser = (ApplicationSecurityUser) ((Authentication) principal).getPrincipal();
        diagram.setLastEditedBy(activeUser);
        diagram.setLastEditedTime(new Date());

        diagrams.save(diagram);
        log.info(String.format("Edited diagram %s by %s", diagram.getName(), activeUser.getUsername()));
        return new ModelAndView("redirect:/management/diagrams");
    }

    @RequestMapping("/diagram/{id}")
    public String getDiagram(Model model, @PathVariable("id") long id) {
        model.addAttribute("current_page", "management");
        model.addAttribute("diagram", diagrams.findOne(id));
        model.addAttribute("add _new", false);
        return "management/fragments/diagram";
    }

    @RequestMapping(value = "/diagram/search/{searchTerm}", method = RequestMethod.GET)
    public String searchDiagram(HttpServletRequest request, Model model, Pageable pageable,
                              @PathVariable("searchTerm") String search) {
        final Sort sortOrder = new Sort(new Sort.Order(Sort.Direction.DESC, "active"),
                new Sort.Order(Sort.Direction.ASC, "name"));
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sortOrder);
        PageWrapper<BlockDiagram> page = new PageWrapper<>(diagrams.search(pageRequest, search.toLowerCase()),
                request.getRequestURI());
        model.addAttribute("page", page);
        model.addAttribute("current_page", "management");
        return "management/diagrams";
    }

    @RequestMapping(value = "/diagram/disable", method = RequestMethod.POST)
    public String disableDiagram(@RequestParam Long diagramId, Principal principal) {
        ApplicationSecurityUser activeUser = (ApplicationSecurityUser) ((Authentication) principal).getPrincipal();
        BlockDiagram diagram = diagrams.findOne(diagramId);
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
        PageWrapper<BlockDiagram> page = new PageWrapper<>(diagrams.findAll(pageRequest), "/management/diagrams");
        model.addAttribute("page", page);
        model.addAttribute("current_page", "management");
        return "management/diagrams";
    }
}
