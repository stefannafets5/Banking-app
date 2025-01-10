package org.poo.bank;

import java.util.ArrayList;

import org.poo.converter.ConverterJson;
import org.poo.converter.CurrencyConverter;
import org.poo.users.Account;
import org.poo.users.SavingsAccount;
import org.poo.users.User;
import org.poo.users.Card;
import org.poo.fileio.ObjectInput;
import org.poo.fileio.CommandInput;

/**
 * The type Bank.
 */
public final class Bank {
    private static Bank instance;
    private final ArrayList<User> users = new ArrayList<>();
    private final CurrencyConverter moneyConverter;

    /**
     * Gets users.
     *
     * @return the users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Gets money converter.
     *
     * @return the money converter
     */
    public CurrencyConverter getMoneyConverter() {
        return moneyConverter;
    }

    private Bank(final ObjectInput input) {
        for (int i = 0; i < input.getUsers().length; i++) {
            this.users.add(new User(input.getUsers()[i].getFirstName(),
                    input.getUsers()[i].getLastName(),
                    input.getUsers()[i].getEmail()));
        }
        moneyConverter = CurrencyConverter.getInstance(input);
    }

    /**
     * Gets instance.
     *
     * @param input the input
     * @return instance
     */
    public static Bank getInstance(final ObjectInput input) {
        if (instance == null) {
            instance = new Bank(input);
        }
        return instance;
    }

    /**
     * Reset instance.
     */
    public static void resetInstance() {
        instance = null;
    }

    /**
     * Sets alias.
     *
     * @param input the input
     */
    public void setAlias(final CommandInput input) {
        String iban = input.getAccount();
        String email = input.getEmail();
        String alias = input.getAlias();

        for (User user : users) {
            if (user.getEmail().equals(email)) {
                for (Account currentAccount : user.getAccounts()) {
                    if (currentAccount.getIban().equals(iban)) {
                        currentAccount.setAlias(alias);
                    }
                }
            }
        }
    }

    /**
     * Add account.
     *
     * @param input the input
     */
    public void addAccount(final CommandInput input) {
        String email = input.getEmail();

        for (User user : users) {
            if (user.getEmail().equals(email)) {
                user.addAccount(input);
                break;
            }
        }
    }

    /**
     * Delete account int.
     *
     * @param input the input
     * @return int
     */
    public int deleteAccount(final CommandInput input) {
        String iban = input.getAccount();
        String email = input.getEmail();
        int canDelete = 0;

        for (User user : users) {
            if (user.getEmail().equals(email)) {
                for (int i = 0; i < user.getAccounts().size(); i++) {
                    if (user.getAccounts().get(i).getIban().equals(iban)) {
                        if (user.getAccounts().get(i).getBalance() == 0) {
                            user.deleteAccount(i);
                            canDelete = 1;
                        } else {
                            user.addErrorTransaction(input.getTimestamp(),
                                    "Account couldn't be deleted "
                                            + "- there are funds remaining");
                        }
                    }
                }
            }
        }
        return canDelete;
    }

    /**
     * Add founds.
     *
     * @param input the input
     */
    public void addFounds(final CommandInput input) {
        String iban = input.getAccount();

        for (User user : users) {
            for (Account currentAccount : user.getAccounts()) {
                if (currentAccount.getIban().equals(iban)) {
                    currentAccount.addMoney(input.getAmount());
                    break;
                }
            }
        }
    }

    /**
     * Sets minimum balance.
     *
     * @param input the input
     */
    public void setMinimumBalance(final CommandInput input) {
        String iban = input.getAccount();
        double amount = input.getAmount();

        for (User user : users) {
            for (Account currentAccount : user.getAccounts()) {
                if (currentAccount.getIban().equals(iban)) {
                    currentAccount.setMinBalance(amount);
                }
            }
        }
    }

    /**
     * Check card status int.
     *
     * @param input the input
     * @return int
     */
    public int checkCardStatus(final CommandInput input) {
        String cardNr = input.getCardNumber();
        int found = 0;

        for (User user : users) {
            for (Account currentAccount : user.getAccounts()) {
                for (Card currentCard : currentAccount.getCards()) {
                    if (currentCard.getCardNumber().equals(cardNr)) {
                        found = 1;
                        if (currentCard.getStatus().equals("active")
                                && currentAccount.getBalance()
                                <= currentAccount.getMinBalance()) {

                            user.addErrorTransaction(input.getTimestamp(),
                                    "You have reached the minimum amount"
                                            + " of funds, the card will be frozen");
                            currentCard.setStatus("frozen");
                        }
                    }
                }
            }
        }
        return found;
    }

    /**
     * Create card.
     *
     * @param input the input
     * @param type  the type
     */
    public void createCard(final CommandInput input, final String type) {
        String email = input.getEmail();
        String iban = input.getAccount();

        for (User user : users) {
            if (user.getEmail().equals(email)) {
                for (Account currentAccount : user.getAccounts()) {
                    if (currentAccount.getIban().equals(iban)) {
                        currentAccount.addCard(input, type,
                                user.getTransactions(), "New card created");
                        break;
                    }
                }
            }
        }
    }

    /**
     * Delete card.
     *
     * @param input the input
     */
    public void deleteCard(final CommandInput input) {
        String cardNr = input.getCardNumber();

        for (User user : users) {
            for (Account currentAccount : user.getAccounts()) {
                for (int i = 0; i < currentAccount.getCards().size(); i++) {
                    if (currentAccount.getCards().get(i).getCardNumber().equals(cardNr)) {
                        currentAccount.removeCard(i, input, user.getEmail(),
                                user.getTransactions(), "The card has been destroyed");
                        break;
                    }
                }
            }
        }
    }

    /**
     * Pay online int.
     *
     * @param input the input
     * @return int
     */
    public int payOnline(final CommandInput input) {
        String cardNr = input.getCardNumber();
        double amount = input.getAmount();
        String from = input.getCurrency();
        int timestamp = input.getTimestamp();
        String commerciant = input.getCommerciant();
        String email = input.getEmail();
        int found = 0;

        for (User user : users) {
            if (user.getEmail().equals(email)) {
                for (Account currentAccount : user.getAccounts()) {
                    for (int i = 0; i < currentAccount.getCards().size(); i++) {
                        Card currentCard = currentAccount.getCards().get(i);
                        if (currentCard.getCardNumber().equals(cardNr)) {
                            found = 1;
                            String to = currentAccount.getCurrency();
                            double amountToBePayed = getMoneyConverter().convert(amount, from, to);
                            if (currentCard.getStatus().equals("frozen")) {
                                user.addErrorTransaction(timestamp, "The card is frozen");
                                return 0;
                            } else if (currentAccount.getBalance() >= amountToBePayed) {
                                currentAccount.subtractMoney(amountToBePayed);
                                user.addCardPaymentTransaction(timestamp, amountToBePayed,
                                        commerciant, currentAccount.getIban());

                                if (currentCard.getType().equals("oneTime")) {
                                    currentAccount.removeCard(i, input, user.getEmail(),
                                            user.getTransactions(), "The card has been destroyed");
                                    currentAccount.addCard(input, "oneTime",
                                            user.getTransactions(), "New card created");
                                }
                                return 1;
                            }
                        }
                    }
                }
            }
        }
        for (User user : users) { // paying failed
            if (user.getEmail().equals(email) && found == 1) {
                user.addPaymentFailedTransaction(timestamp);
            }
        }

        if (found == 0) {
            return 2;
        }
        return 0;
    }

    /**
     * Split payment.
     *
     * @param input the input
     */
    public void splitPayment(final CommandInput input) {
        ArrayList<String> ibanList = new ArrayList<>(input.getAccounts());
        double amount = input.getAmount();
        double eachAccountAmount = amount / ibanList.size();
        String from = input.getCurrency();
        int timestamp = input.getTimestamp();
        int everyoneHasMoney = 0;
        String poor = "nobody";

        for (String iban : ibanList) {
            for (User user : users) { // check if everyone can pay
                for (Account currentAccount : user.getAccounts()) {
                    if (currentAccount.getIban().equals(iban)) {
                        String to = currentAccount.getCurrency();
                        double amountToBePayed =
                                getMoneyConverter().convert(eachAccountAmount, from, to);
                        if (currentAccount.getBalance() >= amountToBePayed) {
                            everyoneHasMoney++;
                        } else {
                            poor = currentAccount.getIban();
                        }
                    }
                }
            }
        }
        for (User user : users) {
            for (Account currentAccount : user.getAccounts()) {
                for (String iban : ibanList) {
                    if (currentAccount.getIban().equals(iban) && !poor.equals("nobody")) {
                        user.addSplitPaymentFailedTransaction(input, poor, iban);
                    }
                }
            }
        }
        if (everyoneHasMoney == ibanList.size()) {
            for (User user : users) {
                for (Account currentAccount : user.getAccounts()) {
                    for (String iban : ibanList) {
                        if (currentAccount.getIban().equals(iban)) {
                            String to = currentAccount.getCurrency();
                            double amountToBePayed =
                                    getMoneyConverter().convert(eachAccountAmount, from, to);
                            currentAccount.subtractMoney(amountToBePayed);
                            user.addSplitCardPaymentTransaction(timestamp,
                                    amountToBePayed, amount, from, ibanList);
                        }
                    }
                }
            }
        }
    }

    /**
     * Send money int.
     *
     * @param input the input
     * @return int
     */
    public int sendMoney(final CommandInput input) {
        int timestamp = input.getTimestamp();
        String iban = input.getAccount();
        double amount = input.getAmount();
        String ibanReceiver = input.getReceiver();
        int receiverExists = 0;
        int hasMoney = 1;
        int found = 0;
        String from = "RON"; // initialization but always  modifies before use

        for (User user : users) {
            for (Account currentAccount : user.getAccounts()) {
                if (currentAccount.getIban().equals(ibanReceiver)) {
                    receiverExists = 1;
                    break;
                }
            }
        }
        if (receiverExists == 1) {

            for (User user : users) {
                for (Account currentAccount : user.getAccounts()) {
                    if (currentAccount.getIban().equals(iban)) {
                        found = 1;
                        from = currentAccount.getCurrency();
                        if (currentAccount.getBalance() < amount) {
                            user.addPaymentFailedTransaction(timestamp);
                            hasMoney = 0;
                        }
                        if (hasMoney == 1) {
                            currentAccount.subtractMoney(amount);
                            user.addMoneyTransferTransaction(input, "sent", from, amount);
                        }
                    }
                }
            }
            for (User user : users) {
                for (Account currentAccount : user.getAccounts()) {
                    if (currentAccount.getIban().equals(ibanReceiver)) {
                        String to = currentAccount.getCurrency();
                        if (found == 1 && hasMoney == 1) {
                            double amountToBePayed = getMoneyConverter().convert(amount, from, to);
                            currentAccount.addMoney(amountToBePayed);
                            user.addMoneyTransferTransaction(input,
                                    "received", to, amountToBePayed);
                            return 1;
                        }
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Print transactions.
     *
     * @param input the input
     * @param out   the out
     */
    public void printTransactions(final CommandInput input, final ConverterJson out) {
        String email = input.getEmail();
        int timestamp = input.getTimestamp();

        for (User user : users) {
            if (user.getEmail().equals(email)) {
                out.printTransactions(user.getTransactions(), timestamp);
                break;
            }
        }
    }

    /**
     * Change interest rate int.
     *
     * @param input the input
     * @return int
     */
    public int changeInterestRate(final CommandInput input) {
        String iban = input.getAccount();
        double interestRate = input.getInterestRate();
        int exists = 0;

        for (User user : users) {
            for (Account currentAccount : user.getAccounts()) {
                if (currentAccount.getIban().equals(iban)) {
                    if (currentAccount.getType().equals("savings")) {
                        exists = 1;
                        SavingsAccount savingsAccount = (SavingsAccount) currentAccount;
                        savingsAccount.setInterestRate(interestRate);
                        user.addChangedInterestTransaction(input.getTimestamp(),
                                "Interest rate of the account changed to "
                                        + interestRate);
                    }
                }
            }
        }
        return exists;
    }

    /**
     * Add interest int.
     *
     * @param input the input
     * @return int
     */
    public int addInterest(final CommandInput input) {
        String iban = input.getAccount();
        int exists = 0;

        for (User user : users) {
            for (Account currentAccount : user.getAccounts()) {
                if (currentAccount.getIban().equals(iban)) {
                    if (currentAccount.getType().equals("savings")) {
                        exists = 1;
                        SavingsAccount savingsAccount = (SavingsAccount) currentAccount;
                        double balance = savingsAccount.getBalance();
                        double rate = savingsAccount.getInterestRate();
                        savingsAccount.addMoney(balance * rate);
                    }
                }
            }
        }
        return exists;
    }

    /**
     * Create report int.
     *
     * @param input the input
     * @param out   the out
     * @param type  the type
     * @return the int
     */
    public int createReport(final CommandInput input, final ConverterJson out, final String type) {
        String iban = input.getAccount();

        for (User user : users) {
            for (Account currentAccount : user.getAccounts()) {
                if (currentAccount.getIban().equals(iban)) {

                    if (currentAccount.getType().equals("savings")
                            && input.getCommand().equals("spendingsReport")) {
                        out.spendingsReportError(input.getTimestamp());
                    } else {
                        out.createReport(user.getTransactions(), input, currentAccount, type);
                    }
                    return 1;
                }
            }
        }
        return 0;
    }
}
