package cn.hupig.www.code.cmservice.domain.enumeration;

/**
 * The SystemType enumeration.
 */
public enum SystemType {
    WIN("Windows"),
    LINUX("Linux"),
    MACOS("MacOS"),
    ANDROID("Android"),
    IOS("Apple");

    private final String value;


    SystemType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
