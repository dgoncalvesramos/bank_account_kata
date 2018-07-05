import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

class Account
{
    private final List<Operation> listOperations = new ArrayList<>();

    void makeDeposit(BigDecimal amount, Instant date)
    {
            listOperations.add(Deposit.makeDeposit(amount, date));
    }

    void makeWithdrawal(BigDecimal amount, Instant date) throws Exception
    {
        if(getBalance().compareTo(new BigDecimal(0)) >= 0)
            listOperations.add(Withdrawal.makeWithdrawal(amount, date));
        else
            throw new Exception ("Could not make withdrawal because of negative balance !");
    }

   String printHistory()
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
