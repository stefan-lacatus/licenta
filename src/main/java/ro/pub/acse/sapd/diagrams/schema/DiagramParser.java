package ro.pub.acse.sapd.diagrams.schema;

import ro.pub.acse.sapd.model.entities.DataChannel;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Parses a diagram schema to an map where each block has a key, and a DiagramBlock reference
 */
public interface DiagramParser {
    Map<Long, DiagramBlock> getKeyBlockMap() throws DiagramParseException;

    Set<DataChannel> getUsedChannels() throws DiagramParseException;

    Map<DiagramBlock, List<DiagramBlock>> getBlockAsGraph(Map<Long, DiagramBlock> blocks) throws DiagramParseException;

    Map<DiagramBlock, List<DiagramBlock>> getBlockAsGraph() throws DiagramParseException;
}
