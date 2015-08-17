package ro.pub.acse.sapd.diagrams.executor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.pub.acse.sapd.blocks.BlockExecutor;
import ro.pub.acse.sapd.data.DataPoint;
import ro.pub.acse.sapd.diagrams.executor.graph.TopologicalSort;
import ro.pub.acse.sapd.diagrams.executor.graph.TopologicalSortException;
import ro.pub.acse.sapd.diagrams.schema.FunctionBlockDiagram;
import ro.pub.acse.sapd.diagrams.schema.LinkDataArray;
import ro.pub.acse.sapd.diagrams.schema.NodeDataArray;
import ro.pub.acse.sapd.model.entities.BlockDiagram;
import ro.pub.acse.sapd.model.entities.DataChannel;
import ro.pub.acse.sapd.model.entities.ProcessorBlock;
import ro.pub.acse.sapd.repository.InputChannelRepository;
import ro.pub.acse.sapd.repository.ProcessorBlockRepository;

import java.io.IOException;
import java.util.*;

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
    private InputChannelRepository channelRepository;

    public DiagramExecutor() {

    }

    private static String createIndent(int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append(' ');
        }
        return sb.toString();
    }

    public <T> DataPoint execute(BlockDiagram diagram, DataPoint<T> output) throws DiagramExecutionException {
        try {
            FunctionBlockDiagram fbdDiagram = mapper.readValue(diagram.getCode(), FunctionBlockDiagram.class);
            Map<Long, ProcessorBlock> blocks = new HashMap<>();
            Map<Long, DataChannel> channels = new HashMap<>();
            for (NodeDataArray nodeDataArray : fbdDiagram.getNodeDataArray()) {
                if (nodeDataArray.getBlockId() != 0) {
                    if (Objects.equals(nodeDataArray.getCategory(), "Input")) {
                        channels.put(nodeDataArray.getKey(), channelRepository.findOne(nodeDataArray.getBlockId()));
                    } else if (!Objects.equals(nodeDataArray.getCategory(), "End")) {
                        blocks.put(nodeDataArray.getKey(), blockRepository.findOne(nodeDataArray.getBlockId()));
                    }
                } else {
                    throw new DiagramExecutionException("Failed to parse the processor block list. Block "
                            + nodeDataArray.getKey() + " has a blockId 0 ");
                }
            }

            Map<Long, String> stringMap = new HashMap<>();
            for (NodeDataArray node : fbdDiagram.getNodeDataArray()) {
                stringMap.put(node.getKey(), node.getText());
            }
            Map<String, List<String>> listMap = new HashMap<>();
            for (LinkDataArray linkDataArray : fbdDiagram.getLinkDataArray()) {
                if (listMap.containsKey(stringMap.get(linkDataArray.getTo()))) {
                    listMap.get(stringMap.get(linkDataArray.getTo())).add(stringMap.get(linkDataArray.getFrom()));
                } else {
                    listMap.put(stringMap.get(linkDataArray.getTo()), new ArrayList<>());
                    listMap.get(stringMap.get(linkDataArray.getTo())).add(stringMap.get(linkDataArray.getFrom()));
                }
                if (!listMap.containsKey(stringMap.get(linkDataArray.getFrom()))) {
                    listMap.put(stringMap.get(linkDataArray.getFrom()), new ArrayList<>());
                }

            }
            List<String> strings = TopologicalSort.tSort(listMap);


        } catch (IOException | TopologicalSortException e) {
            throw new DiagramExecutionException(e);
        }

        return null;
    }
}
