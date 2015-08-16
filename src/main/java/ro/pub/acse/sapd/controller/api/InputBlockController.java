package ro.pub.acse.sapd.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.pub.acse.sapd.model.entities.InputBlock;
import ro.pub.acse.sapd.repository.InputBlockRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class InputBlockController {
    @Autowired
    private InputBlockRepository inputBlockRepository;

    @RequestMapping(value = "/blocks/getChannels", headers = "Accept=application/json")
    @ResponseBody
    public List<InputChannelJsonResult> getAllTags() {
        List<InputBlock> inputBlocks = inputBlockRepository.findAll();
        List<InputChannelJsonResult> channels = new ArrayList<>();
        for (InputBlock inputBlock : inputBlocks) {
            channels.addAll(inputBlock.getChannels().stream().map(channel -> new InputChannelJsonResult(channel.getId(),
                    inputBlock.getName() + "/" + channel.getName())).collect(Collectors.toList()));
        }
        return channels;
    }

    private static class InputChannelJsonResult implements Serializable {
        private long id;
        private String name;

        public InputChannelJsonResult(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
