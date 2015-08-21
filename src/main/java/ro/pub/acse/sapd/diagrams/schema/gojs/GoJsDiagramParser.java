package ro.pub.acse.sapd.diagrams.schema.gojs;


import ro.pub.acse.sapd.diagrams.executor.DiagramExecutionException;
import ro.pub.acse.sapd.diagrams.schema.DiagramBlock;
import ro.pub.acse.sapd.diagrams.schema.DiagramParser;
import ro.pub.acse.sapd.diagrams.schema.EndChannelBlock;
import ro.pub.acse.sapd.repository.DataChannelRepository;
import ro.pub.acse.sapd.repository.ProcessorBlockRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Parses a diagram saved by goJs
 */
public class GoJsDiagramParser implements DiagramParser {
    private final DataChannelRepository channelRepository;
    private final ProcessorBlockRepository blockRepository;

    public GoJsDiagramParser(DataChannelRepository channelRepository, ProcessorBlockRepository blockRepository) {
        this.channelRepository = channelRepository;
        this.blockRepository = blockRepository;
    }

    @Override
    public Map<Long, DiagramBlock> getKeyBlockMap(FunctionBlockDiagram fbdDiagram) throws DiagramExecutionException {
        Map<Long, DiagramBlock> blocks = new HashMap<>();
        for (NodeDataArray nodeDataArray : fbdDiagram.getNodeDataArray()) {
            if (!Objects.equals(nodeDataArray.getCategory(), "Comment")) {
                if (nodeDataArray.getBlockId() != 0 || Objects.equals(nodeDataArray.getCategory(), "End")) {
                    if (Objects.equals(nodeDataArray.getCategory(), "Input")) {
                        blocks.put(nodeDataArray.getKey(), channelRepository.findOne(nodeDataArray.getBlockId()));
                    } else if (Objects.equals(nodeDataArray.getCategory(), "End")) {
                        blocks.put(nodeDataArray.getKey(), new EndChannelBlock());
                    } else {
                        blocks.put(nodeDataArray.getKey(), blockRepository.findOne(nodeDataArray.getBlockId()));
                    }
                } else {
                    throw new DiagramExecutionException("Failed to parse the processor block list. Block "
                            + nodeDataArray.getKey() + " has a blockId 0 ");
                }
            }
        }
        return blocks;
    }
}
