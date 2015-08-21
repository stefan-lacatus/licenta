package ro.pub.acse.sapd.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.pub.acse.sapd.blocks.BlockExecutionException;
import ro.pub.acse.sapd.blocks.BlockExecutor;
import ro.pub.acse.sapd.data.DataPoint;
import ro.pub.acse.sapd.data.impl.StringDataPoint;
import ro.pub.acse.sapd.model.entities.DataChannel;
import ro.pub.acse.sapd.repository.DataChannelRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles adding new data
 */
@RestController
@RequestMapping("/api/input/")
public class InputDataController {
    @Autowired
    DataChannelRepository dataRepository;
    @Autowired
    BlockExecutor blockExecutor;

    @RequestMapping(value = "{inputId}/{channelId}/{data}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<String> addDataPut(@PathVariable Long inputId, @PathVariable Long channelId,
                                             @PathVariable String data) throws BlockExecutionException {
        addData(channelId, data);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void addData(Long channelId, String data) throws BlockExecutionException {
        DataChannel channel = dataRepository.findOne(channelId);
        DataPoint dataPoint;
        if (channel.getInputPreprocessor() != null) {
            List<DataPoint> pointList = new ArrayList<>();
            pointList.add(new StringDataPoint(data));
            dataPoint = blockExecutor.execute(channel.getInputPreprocessor(), pointList);
        } else {
            dataPoint = new StringDataPoint(data);
        }

        dataRepository.addDataToTable(channelId, dataPoint);
    }
}
