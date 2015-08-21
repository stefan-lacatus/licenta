package ro.pub.acse.sapd.diagrams.schema;

import ro.pub.acse.sapd.diagrams.executor.DiagramExecutionException;
import ro.pub.acse.sapd.diagrams.schema.gojs.FunctionBlockDiagram;

import java.util.Map;

/**
 * Parses a diagram schema to an map where each block has a key, and a DiagramBlock reference
 */
public interface DiagramParser {
    Map<Long, DiagramBlock> getKeyBlockMap(FunctionBlockDiagram fbdDiagram) throws DiagramExecutionException;
}
