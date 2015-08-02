package ro.pub.acse.sapd.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import ro.pub.acse.sapd.model.entities.ApplicationTag;
import ro.pub.acse.sapd.repository.ApplicationTagRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * An implementation of the repository
 */
public class ApplicationTagRepositoryImpl implements ro.pub.acse.sapd.repository.custom.ApplicationTagRepositoryCustom {
    @Autowired
    private ApplicationTagRepository tagRepository;

    @Override
    public Set<ApplicationTag> addTagsFromBindingResult(BindingResult result) {
        HashSet<ApplicationTag> tags = new HashSet<>();
        // attempt to create the tags
        if (result.getFieldErrorCount("tags") > 0) {
            // first attempt to reset all the existing tags
            String[] values = result.getFieldError("tags").getRejectedValue().toString().split(",");
            for (String value : values) {
                try {
                    long tagId = Long.parseLong(value);
                    tags.add(tagRepository.findOne(tagId));
                } catch (NumberFormatException ex) {
                    // if it's not a long then add the tag manually
                    ApplicationTag tag = new ApplicationTag();
                    tag.setName(value);
                    tagRepository.save(tag);
                    tags.add(tag);
                }
            }
        }
        return tags;
    }
}
