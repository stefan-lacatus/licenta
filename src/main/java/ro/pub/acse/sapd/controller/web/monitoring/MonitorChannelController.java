package ro.pub.acse.sapd.controller.web.monitoring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/monitor")
public class MonitorChannelController {

    @RequestMapping(value = {"channels", "channels/"})
    public String managementHome(Model model) {
        model.addAttribute("current_page", "monitor");
        return "monitor/channel";
    }
}
