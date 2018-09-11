import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

/**
 * Date service interface 
 * @author dgoncalves
 *
 */
public interface DateService {

    /**
     * Return a particular date (only used for junit tests)
     * @param date the LocalDateTime
     * @return an Optional object either null or feed with the date
     */
    default Optional<Instant> getDateTimeFromDate(Instant date)
    {
        return Optional.ofNullable(date);
    }

    /**
     * Return the current time for an operation
     * @return the current time
     */
    default Instant getDateTime()
    {
        return Instant.now();
    }
}