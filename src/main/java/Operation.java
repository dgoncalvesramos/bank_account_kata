import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

abstract class Operation
{
    Instant date ;
    BigDecimal amount ;

    @Override
    public String toString()
    {
        return date + " - " + "amount : " + amount + " $\n";
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Objects.equals(date, operation.date) &&
                Objects.equals(amount, operation.amount);
    }

    @Override
    public int hashCode()
    {

        return Objects.hash(date, amount);
    }

    public abstract BigDecimal applyOnBalance(BigDecimal balance);
}
