package ro.pub.acse.sapd.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.pub.acse.sapd.blocks.BlockExecutionException;
import ro.pub.acse.sapd.data.DataService;
import ro.pub.acse.sapd.data.impl.StringDataPoint;
import ro.pub.acse.sapd.diagrams.executor.DiagramExecutionException;
import ro.pub.acse.sapd.model.entities.DataChannel;
import ro.pub.acse.sapd.repository.DataChannelRepository;

/**
 * Handles adding new data
 */
@RestController
@RequestMapping("/api/input/")
public class InputDataController {
    @Autowired
    DataChannelRepository dataRepository;
    @Autowired
    DataService dataService;

    @RequestMapping(value = "{inputId}/{channelId}/{data}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<String> addDataPut(@PathVariable Long inputId, @PathVariable Long channelId,
                                             @PathVariable String data) throws DiagramExecutionException, BlockExecutionException {
        DataChannel channel = dataRepository.findOne(channelId);
        dataService.addData(channel, new StringDataPoint(data));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
