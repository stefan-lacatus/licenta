package ro.pub.acse.sapd.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicationController {

    @RequestMapping(value = {"/"})
    public String managementHome(Model model) {
        model.addAttribute("current_page", "home");
        return "home";
    }
}
