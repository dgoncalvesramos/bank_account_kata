import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.math.BigDecimal;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {

    @DisplayName("Test the deposit function on an account")
    @ParameterizedTest
    @CsvSource({"1000,1000","2000,2000","4000,4000"})

    void testDeposit(BigDecimal expectedBalance, BigDecimal amount) throws Exception
    {
        Account account = new Account();
        List<Operation> operations = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.of(2017, Month.JUNE, 15, 13, 39,10,10);
        DateServiceProvider dateServiceProvider = new DateServiceProvider() ;

        account.makeDeposit(dateServiceProvider.getDateTimeFromDate(localDateTime).get(), amount);
        operations.add(Deposit.makeDeposit(dateServiceProvider.getDateTimeFromDate(localDateTime).get(), amount));

        assertEquals(expectedBalance, account.getBalance());
        assertEquals(operations,account.getListOperations());
    }

    @DisplayName("Test the withdrawal function on an account")
    @ParameterizedTest
    @CsvSource({"0,1000","0,2000","0,3000"})

    void testWithdrawal(BigDecimal expectedBalance, BigDecimal amount) throws Exception
    {
        Account account = new Account();
        List<Operation> operations = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.of(2017, Month.JUNE, 15, 13, 39,10,10);
        DateServiceProvider dateServiceProvider = new DateServiceProvider() ;

        account.makeDeposit(dateServiceProvider.getDateTimeFromDate(localDateTime).get(), amount);
        operations.add(Deposit.makeDeposit(dateServiceProvider.getDateTimeFromDate(localDateTime).get(), amount));

        account.makeWithdrawal(dateServiceProvider.getDateTimeFromDate(localDateTime).get(), amount);
        operations.add(Withdrawal.makeWithdrawal(dateServiceProvider.getDateTimeFromDate(localDateTime).get(), amount));

        assertEquals(expectedBalance, account.getBalance());
        assertEquals(operations,account.getListOperations());
    }

    @DisplayName("Test the withdrawal function on an account with negative balance")
    @Test
    void testWithdrawalWithNegativeBalance() throws Exception
    {
        Account account = new Account();
        LocalDateTime localDateTime = LocalDateTime.of(2017, Month.JUNE, 15, 13, 39,10,10);
        DateServiceProvider dateServiceProvider = new DateServiceProvider() ;

        account.makeDeposit(dateServiceProvider.getDateTimeFromDate(localDateTime).get(),new BigDecimal(1000));

        try
        {
            account.makeWithdrawal(dateServiceProvider.getDateTimeFromDate(localDateTime).get(),new BigDecimal(2000));
        } catch (Exception e)
        {
            assertEquals("Could not make withdrawal because of negative balance !", e.getMessage());
        }
    }

    @DisplayName("Test the print history functionality")
    @ParameterizedTest
    @CsvSource({"0,1000,1000","1000,2000,1000","500,3000,2500"})
    void testPrintFunctionality(BigDecimal expectedBalance, BigDecimal depositAmount, BigDecimal withdrawalAmount) throws Exception
    {
        Account account = new Account();
        List<Operation> operations = new ArrayList<>();

        LocalDateTime localDateTime = LocalDateTime.of(2017, Month.JUNE, 15, 13, 39,10,10);
        DateServiceProvider dateServiceProvider = new DateServiceProvider() ;

        account.makeDeposit(dateServiceProvider.getDateTimeFromDate(localDateTime).get(),depositAmount);
        operations.add(Deposit.makeDeposit(dateServiceProvider.getDateTimeFromDate(localDateTime).get(), depositAmount));

        account.makeWithdrawal(dateServiceProvider.getDateTimeFromDate(localDateTime).get(), withdrawalAmount);
        operations.add(Withdrawal.makeWithdrawal(dateServiceProvider.getDateTimeFromDate(localDateTime).get(), withdrawalAmount));

        assertEquals(
                "********* ACCOUNT HISTORY *********\n\n" +
                        "BALANCE " + expectedBalance + " $\n" +
                        "### LIST OF OPERATIONS ###\n\n" +
                        "Deposit : 2017-06-15T13:39:10.000000010Z - amount : " + depositAmount + " $\n" +
                        "Withdrawal : 2017-06-15T13:39:10.000000010Z - amount : " + withdrawalAmount + " $\n" +
                        "**************************************"
                ,
                    account.printHistory());
    }
}
