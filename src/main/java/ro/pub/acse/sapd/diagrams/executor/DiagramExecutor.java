package ro.pub.acse.sapd.diagrams.executor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.pub.acse.sapd.blocks.BlockExecutionException;
import ro.pub.acse.sapd.blocks.BlockExecutor;
import ro.pub.acse.sapd.data.DataPoint;
import ro.pub.acse.sapd.data.impl.ObjectDataPoint;
import ro.pub.acse.sapd.diagrams.executor.graph.TopologicalSort;
import ro.pub.acse.sapd.diagrams.executor.graph.TopologicalSortException;
import ro.pub.acse.sapd.diagrams.schema.DiagramBlock;
import ro.pub.acse.sapd.diagrams.schema.DiagramParser;
import ro.pub.acse.sapd.diagrams.schema.gojs.FunctionBlockDiagram;
import ro.pub.acse.sapd.diagrams.schema.gojs.GoJsDiagramParser;
import ro.pub.acse.sapd.diagrams.schema.gojs.LinkDataArray;
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
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private BlockExecutor blockExecutor;
    @Autowired
    private ProcessorBlockRepository blockRepository;
    @Autowired
    private DataChannelRepository channelRepository;
    @Autowired
    private DataChannelRepository dataRepository;

    public DataPoint execute(FunctionalDiagram diagram) throws DiagramExecutionException {
        try {
            Map<DiagramBlock, List<DiagramBlock>> listMap;
            Map<DiagramBlock, DataPoint> results = new HashMap<>();
            FunctionBlockDiagram fbdDiagram = mapper.readValue(diagram.getCode(), FunctionBlockDiagram.class);
            DiagramParser diagramParser = new GoJsDiagramParser(channelRepository, blockRepository);
            Map<Long, DiagramBlock> blocks = diagramParser.getKeyBlockMap(fbdDiagram);

            listMap = getBlockAsGraph(fbdDiagram, blocks);
            List<DiagramBlock> blockOrder = TopologicalSort.tSort(listMap);
            for (DiagramBlock block : blockOrder) {
                executeBlock(block, listMap, results);
            }
            // get the result of the block just before the end block
            DataPoint result = results.get(blockOrder.get(blockOrder.size() - 2));
            dataRepository.addDataToTable(diagram.getChannel().getId(), result);
            return result;
        } catch (IOException | TopologicalSortException | BlockExecutionException e) {
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
            results.put(block, new ObjectDataPoint("1"));
        }
    }

    private Map<DiagramBlock, List<DiagramBlock>> getBlockAsGraph(FunctionBlockDiagram fbdDiagram, Map<Long, DiagramBlock> blocks) {
        Map<DiagramBlock, List<DiagramBlock>> listMap = new HashMap<>();
        for (LinkDataArray linkDataArray : fbdDiagram.getLinkDataArray()) {
            if (listMap.containsKey(blocks.get(linkDataArray.getTo()))) {
                listMap.get(blocks.get(linkDataArray.getTo())).add(blocks.get(linkDataArray.getFrom()));
            } else {
                listMap.put(blocks.get(linkDataArray.getTo()), new ArrayList<>());
                listMap.get(blocks.get(linkDataArray.getTo())).add(blocks.get(linkDataArray.getFrom()));
            }
            if (!listMap.containsKey(blocks.get(linkDataArray.getFrom()))) {
                listMap.put(blocks.get(linkDataArray.getFrom()), new ArrayList<>());
            }

        }
        return listMap;
    }
}
