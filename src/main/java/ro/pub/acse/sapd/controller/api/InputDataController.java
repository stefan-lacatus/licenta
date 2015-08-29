package ro.pub.acse.sapd.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.pub.acse.sapd.blocks.BlockExecutionException;
import ro.pub.acse.sapd.data.DataPoint;
import ro.pub.acse.sapd.data.DataService;
import ro.pub.acse.sapd.data.impl.StringDataPoint;
import ro.pub.acse.sapd.diagrams.executor.DiagramExecutionException;
import ro.pub.acse.sapd.model.entities.DataChannel;
import ro.pub.acse.sapd.repository.DataChannelRepository;

import java.util.Date;
import java.util.List;

/**
 * Handles adding new data
 */
@RestController
@RequestMapping("/api/")
public class InputDataController {
    @Autowired
    DataChannelRepository dataRepository;
    @Autowired
    DataService dataService;

    @RequestMapping(value = "put/{inputId}/{channelId}/{data}", method = RequestMethod.PUT)
    public ResponseEntity<String> addDataPut(@PathVariable Long inputId, @PathVariable Long channelId,
                                             @PathVariable String data) throws DiagramExecutionException, BlockExecutionException {
        DataChannel channel = dataRepository.findOne(channelId);
        dataService.addData(channel, new StringDataPoint(data));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "fetch/{channelId}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public List<DataPoint> getData(@PathVariable Long channelId,
                                   @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date from,
                                   @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date to,
                                   @RequestParam(value = "maxItems", required = false) Integer maxItems) {
        DataChannel channel = dataRepository.findOne(channelId);
        if (maxItems == null || maxItems == 0) {
            maxItems = 100;
        }
        return dataService.getDataPoints(channel, from, to, maxItems);
    }

    @RequestMapping(value = "fetch/last/{channelId}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public List<DataPoint> getLastData(@PathVariable Long channelId,
                                       @RequestParam("maxItems") Integer maxItems) {
        DataChannel channel = dataRepository.findOne(channelId);
        return dataService.getLastDataPoints(channel, maxItems);
    }
}
