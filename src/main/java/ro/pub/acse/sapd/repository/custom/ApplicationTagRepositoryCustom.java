package ro.pub.acse.sapd.repository.custom;

import org.springframework.validation.BindingResult;
import ro.pub.acse.sapd.model.entities.ApplicationTag;

import java.util.Set;

/**
 * Adds the method to create tags from a binding result with errors
 */
public interface ApplicationTagRepositoryCustom {
    Set<ApplicationTag> addTagsFromBindingResult(BindingResult result);
}
