package ro.pub.acse.sapd.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.pub.acse.sapd.model.entities.ApplicationTag;
import ro.pub.acse.sapd.repository.ApplicationTagRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TagsController {
    @Autowired
    private ApplicationTagRepository tagRepository;

    @RequestMapping(value = "/tags/getTags", headers = "Accept=application/json")
    public List<ApplicationTag> getAllTags() {
        return tagRepository.findAll();
    }

    @RequestMapping(value = "/tags/getTagNames", headers = "Accept=application/json")
    public List<String> getAllTagsNames() {
        return tagRepository.findAll().stream().map(ApplicationTag::getName).collect(Collectors.toList());
    }
}
