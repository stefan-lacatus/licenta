package ro.pub.acse.sapd.model.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ro.pub.acse.sapd.model.entities.ProcessorBlock;
import ro.pub.acse.sapd.repository.ProcessorBlockRepository;

/**
 * Convert Id to a ProcessorBlock
 */
@Component
public class IdToProcessorBlockConverter implements Converter<String, ProcessorBlock> {

    @Autowired
    private ProcessorBlockRepository processorBlockRepository;

    @Override
    public ProcessorBlock convert(String id) {
        return processorBlockRepository.findOne(Long.parseLong(id));
    }

}