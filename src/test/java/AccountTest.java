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

    void testDeposit(BigDecimal expectedBalance, BigDecimal amount)
    {
        Account account = new Account();
        List<Operation> operations = new ArrayList<>();
        LocalDateTime dateTime = LocalDateTime.of(2017, Month.JUNE, 15, 13, 39,10,10);
        Instant instant = dateTime.atZone(ZoneId.of("UTC")).toInstant();

        account.makeDeposit(amount, DateService.getDateTime(instant));
        operations.add(Deposit.makeDeposit(amount,DateService.getDateTime(instant)));

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
        LocalDateTime dateTime = LocalDateTime.of(2017, Month.JUNE, 15, 13, 39,10,10);
        Instant instant = dateTime.atZone(ZoneId.of("UTC")).toInstant();

        account.makeDeposit(amount,DateService.getDateTime(instant));
        operations.add(Deposit.makeDeposit(amount,DateService.getDateTime(instant)));

        account.makeWithdrawal(amount, DateService.getDateTime(instant));
        operations.add(Withdrawal.makeWithdrawal(amount, DateService.getDateTime(instant)));

        assertEquals(expectedBalance, account.getBalance());
        assertEquals(operations,account.getListOperations());
    }

    @DisplayName("Test the withdrawal function on an account with negative balance")
    @Test
    void testWithdrawalWithNegativeBalance()
    {
        Account account = new Account();
        LocalDateTime dateTime = LocalDateTime.of(2017, Month.JUNE, 15, 13, 39,10,10);
        Instant instant = dateTime.atZone(ZoneId.of("UTC")).toInstant();

        account.makeDeposit(new BigDecimal(1000), DateService.getDateTime(instant));

        try
        {
            account.makeWithdrawal(new BigDecimal(2000), DateService.getDateTime(instant));
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

        LocalDateTime dateTime = LocalDateTime.of(2017, Month.JUNE, 15, 13, 39,10,10);
        Instant instant = dateTime.atZone(ZoneId.of("UTC")).toInstant();

        account.makeDeposit(depositAmount,  DateService.getDateTime(instant));
        operations.add(Deposit.makeDeposit(depositAmount,  DateService.getDateTime(instant)));

        account.makeWithdrawal(withdrawalAmount,  DateService.getDateTime(instant));
        operations.add(Withdrawal.makeWithdrawal(withdrawalAmount,  DateService.getDateTime(instant)));

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
