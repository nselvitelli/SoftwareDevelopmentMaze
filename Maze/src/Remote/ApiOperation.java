package Remote;

/**
 * The available API operations in the Player-Referee protocol.
 */
public enum ApiOperation {
    setup, take_turn, win;

    @Override
    public String toString() {
        return super.toString().replace("_", "-");
    }

    public static ApiOperation fromString(String value) {
        value = value.replace("-", "_");
        return ApiOperation.valueOf(value);
    }
}
