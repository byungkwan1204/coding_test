package com.example.coding_test.요기요;

import org.junit.jupiter.api.Test;

// 상태 전략 패턴
public class ThirdSolution {

    @Test
    void test() {

    }

    enum StateInfo {
        LOGGED_IN, LOGGED_OUT, SUSPENDED, ERROR
    }

    interface BankAccountState {
        public StateInfo login(String password);
        public StateInfo logout();
        public StateInfo unlock(int resetCode);
        public StateInfo withdrawMoney(int amount);
    }

    class BankAccount {

        private BankAccountState loggedIn;
        private BankAccountState loggedOut;
        private BankAccountState suspended;
        private BankAccountState bankAccountState;  // 모든 상태 사용은 이 필드를 사용함
        private int cashBalance;
        private String password;
        private int passwordRetries;
        private int resetCode;

        public BankAccount(int cashBalance, String password, int resetCode) {
            // YOUR SOLUTION HERE
            this.cashBalance = cashBalance;
            this.password = password;
            this.resetCode = resetCode;

            this.loggedIn = new LoggedIn(this);
            this.loggedOut = new LoggedOut(this);
            this.suspended = new Suspended(this);
            this.bankAccountState = this.loggedOut;
        }

        public void setState(BankAccountState state) {
            this.bankAccountState = state;
        }

        public BankAccountState getState() {
            return this.bankAccountState;
        }

        public BankAccountState getLoggedInState() {
            return this.loggedIn;
        }

        public BankAccountState getLoggedOutState() {
            return this.loggedOut;
        }

        public BankAccountState getSuspendedState() {
            return this.suspended;
        }

        public StateInfo login(String password) {
            return this.bankAccountState.login(password);
        }

        public StateInfo logout() {
            return this.bankAccountState.logout();
        }

        public StateInfo unlock(int resetCode) {
            return this.bankAccountState.unlock(resetCode);
        }

        public StateInfo withdrawMoney(int amount) {
            return this.bankAccountState.withdrawMoney(amount);
        }

        public void setCashBalance(int amount) {
            this.cashBalance = amount;
        }

        public int getCashBalance() {
            return this.cashBalance;
        }

        public String getPassword() {
            return this.password;
        }

        public void setPasswordRetries(int passwordRetries) {
            this.passwordRetries = passwordRetries;
        }

        public int getPasswordRetries() {
            return this.passwordRetries;
        }

        public int getResetCode() {
            return this.resetCode;
        }
    }

    // YOUR SOLUTION HERE
    class LoggedIn implements BankAccountState {

        private final BankAccount bankAccount;

        public LoggedIn(BankAccount bankAccount) {
            this.bankAccount = bankAccount;
        }

        @Override
        public StateInfo login(String password) {
            return StateInfo.LOGGED_IN;
        }

        @Override
        public StateInfo logout() {
            bankAccount.setState(bankAccount.getLoggedOutState());
            return StateInfo.LOGGED_OUT;
        }

        @Override
        public StateInfo unlock(int resetCode) {
            return StateInfo.LOGGED_IN;
        }

        @Override
        public StateInfo withdrawMoney(int amount) {
            // 요청 금액이 잔액보다 작은 경우 차감
            if (amount <= bankAccount.getCashBalance()) {
                bankAccount.setCashBalance(bankAccount.getCashBalance() - amount);
            }
            return StateInfo.LOGGED_IN;
        }
    }

    // YOUR SOLUTION HERE
    class LoggedOut implements BankAccountState{

        private final BankAccount bankAccount;

        public LoggedOut(BankAccount bankAccount) {
            this.bankAccount = bankAccount;
        }

        @Override
        public StateInfo login(String password) {
            
            if (bankAccount.getPassword().equals(password)) {
                bankAccount.setPasswordRetries(0);
                bankAccount.setState(bankAccount.getLoggedInState());
                return StateInfo.LOGGED_IN;

            } else {
                int retry = bankAccount.getPasswordRetries() + 1;
                bankAccount.setPasswordRetries(retry);

                if (retry > 2) {
                    bankAccount.setState(bankAccount.getSuspendedState());
                    return StateInfo.SUSPENDED;
                }
                return StateInfo.LOGGED_OUT;
            }
        }

        @Override
        public StateInfo logout() {
            return StateInfo.LOGGED_OUT;
        }

        @Override
        public StateInfo unlock(int resetCode) {
            return StateInfo.LOGGED_OUT;
        }

        @Override
        public StateInfo withdrawMoney(int amount) {
            return StateInfo.LOGGED_OUT;
        }
    }

    // YOUR SOLUTION HERE
    class Suspended implements BankAccountState{

        private final BankAccount bankAccount;

        public Suspended(BankAccount bankAccount) {
            this.bankAccount = bankAccount;
        }

        @Override
        public StateInfo login(String password) {
            return StateInfo.SUSPENDED;
        }

        @Override
        public StateInfo logout() {
            return StateInfo.SUSPENDED;
        }

        @Override
        public StateInfo unlock(int resetCode) {

            if (bankAccount.getResetCode() == resetCode) {
                bankAccount.setPasswordRetries(0);
                bankAccount.setState(bankAccount.getLoggedOutState());
                return StateInfo.LOGGED_OUT;
            }
            return StateInfo.SUSPENDED;
        }

        @Override
        public StateInfo withdrawMoney(int amount) {
            return StateInfo.SUSPENDED;
        }
    }

}
