import java.math.BigDecimal;
import java.time.Instant;

class Withdrawal extends Operation
{
    private Withdrawal(Instant date, BigDecimal amount)
    {
        super.date = date ;
        super.amount = amount;
    }

    static Withdrawal makeWithdrawal(BigDecimal amount, Instant date)
    {
        return new Withdrawal(DateService.getDateTime(date),amount);
    }

    @Override
    public String toString()
    {
        return "Withdrawal : " + super.toString() ;
    }

    @Override
    public BigDecimal applyOnBalance(BigDecimal balance)
    {
        return balance.subtract(amount);
    }
}
