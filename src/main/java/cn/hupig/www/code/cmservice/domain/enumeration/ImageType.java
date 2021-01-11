package cn.hupig.www.code.cmservice.domain.enumeration;

/**
 * The ImageType enumeration.
 */
public enum ImageType {
    PNG("png"),
    JPG("jpg"),
    JPEG("jpeg"),
    BMP("bmp"),
    GIF("gif"),
    PSD("psd"),
    AI("ai"),
    CDR("cdr"),
    PCD("pcd"),
    SVG("svg"),
    RAW("raw");

    private final String value;


    ImageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
