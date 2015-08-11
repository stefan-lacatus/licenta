package ro.pub.acse.sapd.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.pub.acse.sapd.blocks.BlockExecutionException;
import ro.pub.acse.sapd.blocks.BlockExecutor;
import ro.pub.acse.sapd.data.DataPoint;
import ro.pub.acse.sapd.data.impl.StringDataPoint;
import ro.pub.acse.sapd.model.entities.InputChannel;
import ro.pub.acse.sapd.repository.InputChannelRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles adding new data
 */
@RestController
@RequestMapping("/api/input/")
public class InputDataController {
    @Autowired
    InputChannelRepository dataRepository;
    @Autowired
    BlockExecutor blockExecutor;

    @RequestMapping(value = "{inputId}/{channelId}/{data}", method = RequestMethod.PUT)
    public
    @ResponseBody
    ResponseEntity<String> addDataPut(@PathVariable Long inputId, @PathVariable Long channelId,
                                      @PathVariable String data) throws BlockExecutionException {
        addData(inputId, channelId, data);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void addData(Long inputId, Long channelId, String data) throws BlockExecutionException {
        InputChannel channel = dataRepository.findOne(channelId);
        DataPoint dataPoint;
        if (channel.getInputPreprocessor() != null) {
            List<DataPoint<String>> pointList = new ArrayList<>();
            pointList.add(new StringDataPoint(data));
            dataPoint = blockExecutor.execute(channel.getInputPreprocessor(), pointList);
        } else {
            dataPoint = new StringDataPoint(data);
        }

        dataRepository.addDataToTable(inputId, channelId, dataPoint);
    }
}
