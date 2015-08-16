package ro.pub.acse.sapd.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.pub.acse.sapd.model.entities.BlockDiagram;
import ro.pub.acse.sapd.model.entities.DataChannel;
import ro.pub.acse.sapd.model.entities.InputBlock;
import ro.pub.acse.sapd.repository.BlockDiagramRepository;
import ro.pub.acse.sapd.repository.InputBlockRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class InputBlockController {
    @Autowired
    private InputBlockRepository inputBlockRepository;
    @Autowired
    private BlockDiagramRepository diagramRepository;

    @RequestMapping(value = "/blocks/getChannels", headers = "Accept=application/json")
    @ResponseBody
    public List<InputChannelJsonResult> getAllTags() {
        List<InputChannelJsonResult> channels = new ArrayList<>();
        for (InputBlock inputBlock : inputBlockRepository.findAll()) {
            channels.addAll(inputBlock.getChannels().stream().map(channel ->
                    new InputChannelJsonResult(inputBlock, channel)).collect(Collectors.toList()));
        }

        // now also add the channels from the diagram outputs
        channels.addAll(diagramRepository.findAll().stream().map
                (InputChannelJsonResult::new).collect(Collectors.toList()));
        return channels;
    }

    private static class InputChannelJsonResult implements Serializable {
        private long id;
        private String name;

        public InputChannelJsonResult(InputBlock block, DataChannel channel) {
            this.id = channel.getId();
            this.name = "I." + block.getName() + "/" + channel.getName();
        }

        public InputChannelJsonResult(BlockDiagram diagram) {
            this.id = diagram.getId();
            this.name = "O." + diagram.getName() + "/" + diagram.getChannel().getName();
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
