import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

class Account
{
    private final List<Operation> listOperations = new ArrayList<>();


    public void makeDeposit(Instant date, BigDecimal amount) throws Exception
    {
        if(amount.compareTo(new BigDecimal(0)) <=0 || amount.compareTo(new BigDecimal(0)) == 0)
            throw new Exception("Cannot make negative deposits !");

        listOperations.add(Deposit.makeDeposit(date, amount));
    }

    public void makeWithdrawal(Instant date, BigDecimal amount) throws Exception
    {
        if(getBalance().compareTo(new BigDecimal(0)) <= 0)
            throw new Exception ("Could not make withdrawal because of negative balance !");

        listOperations.add(Withdrawal.makeWithdrawal(date, amount));
    }

    public String printHistory()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("********* ACCOUNT HISTORY *********\n\n");
        stringBuilder.append("BALANCE " + getBalance() + " $\n");
        stringBuilder.append("### LIST OF OPERATIONS ###" + "\n\n");
        Iterator<Operation> ite = listOperations.iterator();
        while(ite.hasNext())
        {
            stringBuilder.append(ite.next().toString());
        }
        stringBuilder.append("**************************************");
        return stringBuilder.toString();
    }

    BigDecimal getBalance()
    {
        return listOperations.stream().reduce(
                BigDecimal.ZERO,
                (balance, operation) -> operation.applyOnBalance(balance),
                (a, b) -> b);
    }

    List<Operation> getListOperations()
    {
        return Collections.unmodifiableList(listOperations);
    }

}