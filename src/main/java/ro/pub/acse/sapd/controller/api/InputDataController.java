package ro.pub.acse.sapd.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.pub.acse.sapd.data.impl.StringDataPoint;
import ro.pub.acse.sapd.repository.InputChannelRepository;

/**
 * Created by petrisor on 8/9/15.
 */
@RestController
@RequestMapping("/api/input/")
public class InputDataController {
    @Autowired
    InputChannelRepository dataRepository;

    @RequestMapping(value = "{inputId}/{channelId}/{data}", method = RequestMethod.PUT)
    public
    @ResponseBody
    String addDataPut(@PathVariable Long inputId, @PathVariable Long channelId,
                      @PathVariable String data) {
        StringDataPoint dataPoint = new StringDataPoint(data);
        dataRepository.addDataToTable(inputId, channelId, dataPoint);
        return "ok";
    }
}
