import java.math.BigDecimal;
import java.time.Instant;

class Deposit extends Operation
{
    private Deposit(Instant date, BigDecimal amount)
    {
        super.date = date ;
        super.amount = amount;
    }

    static Deposit makeDeposit(Instant date, BigDecimal amount)
    {
        return new Deposit(date,amount);
    }


    @Override
    public String toString()
    {
        return "Deposit : " + super.toString() ;
    }

    @Override
    public BigDecimal applyOnBalance(BigDecimal balance)
    {
        return balance.add(amount);
    }
}
