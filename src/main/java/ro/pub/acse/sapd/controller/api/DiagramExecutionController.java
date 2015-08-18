package ro.pub.acse.sapd.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.pub.acse.sapd.blocks.BlockExecutionException;
import ro.pub.acse.sapd.blocks.BlockExecutor;
import ro.pub.acse.sapd.data.DataPoint;
import ro.pub.acse.sapd.data.impl.StringDataPoint;
import ro.pub.acse.sapd.diagrams.executor.DiagramExecutionException;
import ro.pub.acse.sapd.diagrams.executor.DiagramExecutor;
import ro.pub.acse.sapd.model.entities.DataChannel;
import ro.pub.acse.sapd.repository.BlockDiagramRepository;
import ro.pub.acse.sapd.repository.InputChannelRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles adding new data
 */
@RestController
@RequestMapping("/api/diagram/")
public class DiagramExecutionController {
    @Autowired
    BlockDiagramRepository diagramRepository;
    @Autowired
    DiagramExecutor diagramExecutor;

    @RequestMapping(value = "{diagramId}", headers = "Accept=application/json")
    @ResponseBody
    public DataPoint addDataPut(@PathVariable Long diagramId) throws DiagramExecutionException {
        return diagramExecutor.execute(diagramRepository.findOne(diagramId));
    }
}
