package ro.pub.acse.sapd.controller.web.monitoring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Displays the logs in a webpage
 */
@Controller
@RequestMapping(value = "/monitor")
public class LogsController {
    @RequestMapping(value = {"logs", "logs/"})
    public String managementHome(Model model) {
        model.addAttribute("current_page", "monitor");
        return "monitoring/logs";
    }
}
