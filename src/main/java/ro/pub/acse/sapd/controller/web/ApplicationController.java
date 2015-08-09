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

    @RequestMapping(value = {"/", "", "/inputs", "/inputs/"})
    public String managementHome(Model model, Pageable pageable) {
        final Sort sortOrder = new Sort(new Sort.Order(Sort.Direction.DESC, "enabled"),
                new Sort.Order(Sort.Direction.ASC, "lastEditedTime"));
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sortOrder);
        PageWrapper<InputBlock> page = new PageWrapper<>(inputBlockRepository.findAll(pageRequest), "/management/input_blocks");
        model.addAttribute("page", page);
        model.addAttribute("current_page", "inputs");
        return "management/input_blocks";
    }
}
