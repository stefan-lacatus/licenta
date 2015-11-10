package ro.pub.acse.sapd.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ro.pub.acse.sapd.SapdApplication;
import ro.pub.acse.sapd.blocks.BlockExecutionException;
import ro.pub.acse.sapd.blocks.BlockExecutor;
import ro.pub.acse.sapd.diagrams.executor.DiagramExecutionException;
import ro.pub.acse.sapd.diagrams.executor.DiagramExecutor;
import ro.pub.acse.sapd.logging.Loggable;
import ro.pub.acse.sapd.model.entities.DataChannel;
import ro.pub.acse.sapd.model.entities.FunctionalDiagram;
import ro.pub.acse.sapd.repository.DataChannelRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Adds and gets data from data channels
 */
@Component
public class DataService {
    @Autowired
    DataChannelRepository dataRepository;
    @Autowired
    BlockExecutor blockExecutor;
    @Autowired
    DiagramExecutor diagramExecutor;
    @Loggable
    private Logger LOGGER;
    private Logger eventLogger = LoggerFactory.getLogger(SapdApplication.EVENT_LOGGER);

    public int addData(DataChannel channel, DataPoint data) throws BlockExecutionException, DiagramExecutionException {
        if (channel.getInputPreprocessor() != null) {
            List<DataPoint> pointList = new ArrayList<>();
            pointList.add(data);
            data = blockExecutor.execute(channel.getInputPreprocessor(), pointList);
        }
        int rows = dataRepository.addDataToTable(channel.getId(), data);
        LOGGER.debug(String.format("New data %s on channel %s", channel.getName(), data.getValue()));
        eventLogger.warn(String.format("New data %s on channel %s", channel.getName(), data.getValue()));
        fireNewDataOnChannel(channel);
        return rows;
    }

    @Async
    private void fireNewDataOnChannel(DataChannel channel) throws DiagramExecutionException {
        for (FunctionalDiagram functionalDiagram : channel.getSubscribedDiagrams()) {
            DataPoint result = diagramExecutor.execute(functionalDiagram);
            LOGGER.debug(String.format("Executed diagram %s because of new data on channel %s and got result %s",
                    functionalDiagram.getName(), channel.getName(), result.getValue()));
            eventLogger.debug(String.format("Executed diagram %s because of new data on channel %s and got result %s",
                    functionalDiagram.getName(), channel.getName(), result.getValue()));
        }
    }

    public List<DataPoint> getDataPoints(DataChannel channel, Date startInterval, Date endInterval, Integer maxItems) {
        return dataRepository.getDataOnChannel(channel, startInterval, endInterval, new PageRequest(0, maxItems));
    }

    public List<DataPoint> getLastDataPoints(DataChannel channel, Integer maxItems) {
        return dataRepository.getLastDataOnChannel(channel, new PageRequest(0, maxItems));
    }
}
