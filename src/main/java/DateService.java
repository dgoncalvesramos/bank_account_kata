import java.time.Instant;

public class DateService {

    public static Instant getDateTime(Instant date)
    {
        return date == null ?  Instant.now() : date;
    }
}
