package ro.pub.acse.sapd.diagrams.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.pub.acse.sapd.blocks.BlockExecutionException;
import ro.pub.acse.sapd.blocks.BlockExecutor;
import ro.pub.acse.sapd.data.DataPoint;
import ro.pub.acse.sapd.data.DataService;
import ro.pub.acse.sapd.diagrams.executor.graph.TopologicalSort;
import ro.pub.acse.sapd.diagrams.executor.graph.TopologicalSortException;
import ro.pub.acse.sapd.diagrams.schema.DiagramBlock;
import ro.pub.acse.sapd.diagrams.schema.DiagramParseException;
import ro.pub.acse.sapd.diagrams.schema.DiagramParser;
import ro.pub.acse.sapd.diagrams.schema.gojs.GoJsDiagramParser;
import ro.pub.acse.sapd.model.entities.DataChannel;
import ro.pub.acse.sapd.model.entities.FunctionalDiagram;
import ro.pub.acse.sapd.model.entities.ProcessorBlock;
import ro.pub.acse.sapd.repository.DataChannelRepository;
import ro.pub.acse.sapd.repository.ProcessorBlockRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Executes a diagram
 */
@Component
public class DiagramExecutor {
    @Autowired
    private BlockExecutor blockExecutor;
    @Autowired
    private ProcessorBlockRepository blockRepository;
    @Autowired
    private DataChannelRepository channelRepository;
    @Autowired
    private DataService dataService;

    public DataPoint execute(FunctionalDiagram diagram) throws DiagramExecutionException {
        try {
            Map<DiagramBlock, List<DiagramBlock>> listMap;
            Map<DiagramBlock, DataPoint> results = new HashMap<>();
            DiagramParser diagramParser = new GoJsDiagramParser(diagram, channelRepository, blockRepository);

            listMap = diagramParser.getBlockAsGraph();
            List<DiagramBlock> blockOrder = TopologicalSort.tSort(listMap);
            for (DiagramBlock block : blockOrder) {
                executeBlock(block, listMap, results);
            }
            // get the result of the block just before the end block
            DataPoint result = results.get(blockOrder.get(blockOrder.size() - 2));
            dataService.addData(diagram.getChannel(), result);
            return result;
        } catch (IOException | TopologicalSortException | BlockExecutionException | DiagramParseException e) {
            throw new DiagramExecutionException(e);
        }
    }

    private void executeBlock(DiagramBlock block, Map<DiagramBlock, List<DiagramBlock>> listMap,
                              Map<DiagramBlock, DataPoint> results) throws
            BlockExecutionException, DiagramExecutionException {
        List<DataPoint> inputs = new ArrayList<>();
        // build the inputs list
        for (DiagramBlock parentBlocks : listMap.get(block)) {
            if (results.containsKey(parentBlocks)) {
                inputs.add(results.get(parentBlocks));
            } else {
                throw new DiagramExecutionException(
                        String.format("Attempting to execute %s before having result of %s",
                                block.toString(), parentBlocks.toString()));
            }
        }
        if (block instanceof ProcessorBlock) {
            results.put(block, blockExecutor.execute(((ProcessorBlock) block), inputs));
        } else if (block instanceof DataChannel) {
            List<DataPoint> points = dataService.getLastDataPoints((DataChannel) block, 1);
            if (points.size() > 0) {
                results.put(block, points.get(0));
            } else {
                throw new DiagramExecutionException("There is no data on input " + ((DataChannel) block).getName());
            }
        }
    }
}
