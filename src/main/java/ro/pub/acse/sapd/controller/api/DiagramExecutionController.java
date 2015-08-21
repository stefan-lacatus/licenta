package ro.pub.acse.sapd.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ro.pub.acse.sapd.data.DataPoint;
import ro.pub.acse.sapd.diagrams.executor.DiagramExecutionException;
import ro.pub.acse.sapd.diagrams.executor.DiagramExecutor;
import ro.pub.acse.sapd.repository.FunctionalDiagramRepository;

/**
 * Handles adding new data
 */
@RestController
@RequestMapping("/api/diagram/")
public class DiagramExecutionController {
    @Autowired
    FunctionalDiagramRepository diagramRepository;
    @Autowired
    DiagramExecutor diagramExecutor;

    @RequestMapping(value = "{diagramId}", headers = "Accept=application/json")
    @ResponseBody
    public DataPoint addDataPut(@PathVariable Long diagramId) throws DiagramExecutionException {
        return diagramExecutor.execute(diagramRepository.findOne(diagramId));
    }
}
