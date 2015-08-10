package zero.fluxodecaixa.app;

public class AppVersion {

    private String major;
    private String minor;
    private String phase;

    public AppVersion(String major, String minor, String phase) {
        this.major = major;
        this.minor = minor;
        this.phase = phase;
    }

    public String getMajor() {
        return major;
    }

    public String getMinor() {
        return minor;
    }

    public String getPhase() {
        return phase;
    }

}
