package ro.pub.acse.sapd.model.entities;

import org.hibernate.validator.constraints.NotBlank;
import ro.pub.acse.sapd.data.DataType;
import ro.pub.acse.sapd.input.InputPreprocessorBlock;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Represents an input channel that takes data takes data from the outside world and stores in into our database
 */
@Entity
public class InputChannel implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String description;
    private DataType dataType;
    private Integer samplingTime;
    private InputPreprocessorBlock inputPreprocessor;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the final data type of this input channel.
     * Determines how the DataPoints generated by this channel are stored
     * @return dataType of channel
     */
    @NotBlank
    public DataType getDataType() {
        return dataType;
    }

    /**
     * Sets the final data type of this input channel.
     * Determines how the DataPoints generated by this channel are stored
     * @param dataType data type of this channel
     */
    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public Integer getSamplingTime() {
        return samplingTime;
    }

    public void setSamplingTime(Integer samplingTime) {
        this.samplingTime = samplingTime;
    }

    @Transient
    public InputPreprocessorBlock getInputPreprocessor() {
        return inputPreprocessor;
    }

    public void setInputPreprocessor(InputPreprocessorBlock inputPreprocessor) {
        this.inputPreprocessor = inputPreprocessor;
    }
}
