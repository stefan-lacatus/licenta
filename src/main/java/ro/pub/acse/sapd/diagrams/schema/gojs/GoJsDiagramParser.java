package ro.pub.acse.sapd.diagrams.schema.gojs;


import com.fasterxml.jackson.databind.ObjectMapper;
import ro.pub.acse.sapd.diagrams.schema.DiagramBlock;
import ro.pub.acse.sapd.diagrams.schema.DiagramParseException;
import ro.pub.acse.sapd.diagrams.schema.DiagramParser;
import ro.pub.acse.sapd.diagrams.schema.EndChannelBlock;
import ro.pub.acse.sapd.model.entities.DataChannel;
import ro.pub.acse.sapd.model.entities.FunctionalDiagram;
import ro.pub.acse.sapd.repository.DataChannelRepository;
import ro.pub.acse.sapd.repository.ProcessorBlockRepository;

import java.io.IOException;
import java.util.*;

/**
 * Parses a diagram saved by goJs
 */
public class GoJsDiagramParser implements DiagramParser {
    private final DataChannelRepository channelRepository;
    private final ProcessorBlockRepository blockRepository;
    private final FunctionBlockDiagram fbdDiagram;

    public GoJsDiagramParser(FunctionalDiagram diagram, DataChannelRepository channelRepository,
                             ProcessorBlockRepository blockRepository) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        fbdDiagram = mapper.readValue(diagram.getCode(), FunctionBlockDiagram.class);
        this.channelRepository = channelRepository;
        this.blockRepository = blockRepository;
    }

    public GoJsDiagramParser(FunctionalDiagram diagram, DataChannelRepository dataChannelRepository) throws IOException {
        this(diagram, dataChannelRepository, null);
    }

    @Override
    public Map<Long, DiagramBlock> getKeyBlockMap() throws DiagramParseException {
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
                    throw new DiagramParseException("Failed to parse the processor block list. Block "
                            + nodeDataArray.getKey() + " has a blockId 0 ");
                }
            }
        }
        return blocks;
    }

    @Override
    public Set<DataChannel> getUsedChannels() throws DiagramParseException {
        Set<DataChannel> channels = new HashSet<>();
        for (NodeDataArray nodeDataArray : fbdDiagram.getNodeDataArray()) {
            if (!Objects.equals(nodeDataArray.getCategory(), "Comment")) {
                if (nodeDataArray.getBlockId() != 0 || Objects.equals(nodeDataArray.getCategory(), "End")) {
                    if (Objects.equals(nodeDataArray.getCategory(), "Input")) {
                        channels.add(channelRepository.findOne(nodeDataArray.getBlockId()));
                    }

                } else {
                    throw new DiagramParseException("Failed to parse the processor block list. Block "
                            + nodeDataArray.getKey() + " has a blockId 0 ");
                }
            }
        }
        return channels;
    }

    public Map<DiagramBlock, List<DiagramBlock>> getBlockAsGraph(Map<Long, DiagramBlock> blocks) {
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

    public Map<DiagramBlock, List<DiagramBlock>> getBlockAsGraph() throws DiagramParseException {
        return getBlockAsGraph(getKeyBlockMap());
    }
}
