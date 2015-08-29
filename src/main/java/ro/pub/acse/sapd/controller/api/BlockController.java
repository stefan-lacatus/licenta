package ro.pub.acse.sapd.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.pub.acse.sapd.model.entities.ProcessorBlock;
import ro.pub.acse.sapd.repository.ProcessorBlockRepository;

import java.util.List;

@RestController
public class BlockController {
    @Autowired
    private ProcessorBlockRepository blockRepository;

    @RequestMapping(value = "/blocks/getBlocks", headers = "Accept=application/json")
    public List<ProcessorBlock> getAllTags() {
        return blockRepository.findAll();
    }
}
