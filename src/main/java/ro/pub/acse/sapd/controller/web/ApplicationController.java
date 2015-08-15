package ro.pub.acse.sapd.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.pub.acse.sapd.configuration.PageWrapper;
import ro.pub.acse.sapd.model.entities.InputBlock;
import ro.pub.acse.sapd.repository.InputBlockRepository;

@Controller
public class ApplicationController {

    @Autowired
    private InputBlockRepository inputBlockRepository;

    @RequestMapping(value = {"/", ""})
    public String managementHome(Model model) {
        model.addAttribute("current_page", "home");
        return "home";
    }
}
