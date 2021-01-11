package cn.hupig.www.code.cmservice.domain.enumeration;

/**
 * The FileType enumeration.
 */
public enum FileType {
    IMAGE("Image"),
    VIDEO("Video"),
    AUDIO("Audio");

    private final String value;


    FileType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
