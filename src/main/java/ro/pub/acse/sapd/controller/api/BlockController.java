package ro.pub.acse.sapd.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.pub.acse.sapd.model.entities.ApplicationTag;
import ro.pub.acse.sapd.model.entities.ProcessorBlock;
import ro.pub.acse.sapd.repository.ApplicationTagRepository;
import ro.pub.acse.sapd.repository.ProcessorBlockRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BlockController {
    @Autowired
    private ProcessorBlockRepository blockRepository;

    @RequestMapping(value = "/blocks/getBlocks", headers = "Accept=application/json")
    @ResponseBody
    public List<ProcessorBlock> getAllTags() {
        return blockRepository.findAll();
    }
}
