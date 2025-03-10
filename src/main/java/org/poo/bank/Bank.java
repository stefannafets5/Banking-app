package org.poo.bank;

import java.util.ArrayList;

import org.poo.commerciants.Commerciant;
import org.poo.converter.ConverterJson;
import org.poo.converter.CurrencyConverter;
import org.poo.users.*;
import org.poo.fileio.ObjectInput;
import org.poo.fileio.CommandInput;

import org.poo.utils.Utils;

/**
 * The type Bank.
 */
public final class Bank {
    private static Bank instance;
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Commerciant> commerciants = new ArrayList<>();
    private ArrayList<SplitPayment> activePayments = new ArrayList<>();
    private final CurrencyConverter moneyConverter;

    /**
     * Gets commerciants.
     *
     * @return the commerciants
     */
    public ArrayList<Commerciant> getCommerciants() {
        return commerciants;
    }

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

    /**
     * Gets active payments.
     *
     * @return the active payments
     */
    public ArrayList<SplitPayment> getActivePayments() {
        return activePayments;
    }

    private Bank(final ObjectInput input) {
        for (int i = 0; i < input.getUsers().length; i++) {
            this.users.add(new User(input.getUsers()[i].getFirstName(),
                    input.getUsers()[i].getLastName(),
                    input.getUsers()[i].getEmail(),
                    input.getUsers()[i].getBirthDate(),
                    input.getUsers()[i].getOccupation()));
        }
        for (int i = 0; i < input.getCommerciants().length; i++) {
            this.commerciants.add(new Commerciant(input.getCommerciants()[i].getCommerciant(),
                    input.getCommerciants()[i].getId(),
                    input.getCommerciants()[i].getAccount(),
                    input.getCommerciants()[i].getType(),
                    input.getCommerciants()[i].getCashbackStrategy()));
        }
        moneyConverter = CurrencyConverter.getInstance(input);
    }

    /**
     * Gets instance.
     *
     * @param input the input
     * @return instance instance
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
     * Gets name by email.
     *
     * @param email the email
     * @return the name by email
     */
    public static String getNameByEmail(final String email) {
        for (User user : instance.getUsers()) {
            if (user.getEmail().equals(email)) {
                return user.getLastName() + " " + user.getFirstName();
            }
        }
        return null;
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
        String to = input.getCurrency();

        for (User user : users) {
            if (user.getEmail().equals(email)) {
                double limit = getMoneyConverter().convert(Utils.LIMIT, "RON", to);
                user.addAccount(input, limit);
                break;
            }
        }
    }

    /**
     * Delete account int.
     *
     * @param input the input
     * @return int int
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
                    if (currentAccount.getType().equals("business")) {
                        BusinessAccount businessAccount = (BusinessAccount) currentAccount;
                        if (!businessAccount.isOwner(input.getEmail())) {
                            if (businessAccount.isManager(input.getEmail())) {
                                if (!businessAccount.getOutOrderForReportManager().contains(
                                        getNameByEmail(input.getEmail()))) {
                                    businessAccount.getOutOrderForReportManager().
                                            add(getNameByEmail(input.getEmail()));
                                }
                                businessAccount.addTotalDeposited(input.getAmount());
                                businessAccount.recordManagerDeposit(getNameByEmail(
                                        input.getEmail()), input.getAmount());
                                currentAccount.addMoney(input.getAmount());
                            } else if (businessAccount.isEmployee(input.getEmail())
                                    && businessAccount.getDepositLimit() >= input.getAmount()) {
                                if (!businessAccount.getOutOrderForReportEmployee().contains(
                                        getNameByEmail(input.getEmail()))) {
                                    businessAccount.getOutOrderForReportEmployee().
                                            add(getNameByEmail(input.getEmail()));
                                }
                                businessAccount.addTotalDeposited(input.getAmount());
                                businessAccount.recordEmployeeDeposit(getNameByEmail(
                                        input.getEmail()), input.getAmount());
                                currentAccount.addMoney(input.getAmount());
                            }
                            break;
                        }
                    }
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
     * @return int int
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
                        currentAccount.addCard(input, type, email,
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
     * Upgrade plan.
     *
     * @param input the input
     * @param out   the out
     */
    public void upgradePlan(final CommandInput input, final ConverterJson out) {
        String newPlan = input.getNewPlanType();
        String iban = input.getAccount();
        int timestamp = input.getTimestamp();
        int accountFound = 0;

        for (User user : users) {
            for (Account currentAccount : user.getAccounts()) {
                if (currentAccount.getIban().equals(iban)) {
                    String to = currentAccount.getCurrency();
                    accountFound = 1;
                    if (user.getPlan().equals(newPlan)) {
                        user.addErrorTransaction(timestamp,
                                "The user already has the " + user.getPlan() + " plan.");
                        return;
                    } else if (user.getPlan().equals("gold")) {
                        return;
                    } else if (user.getPlan().equals("silver") && !newPlan.equals("gold")) {
                        return;
                    } else if (user.getPlan().equals("silver") && newPlan.equals("gold")) {
                        // upgrade silver -> gold 250 RON
                        double moneyToPay = getMoneyConverter().convert(Utils.SILVER_FEE,
                                "RON", to);
                        if (currentAccount.getBalance() < moneyToPay) {
                            user.addErrorTransaction(timestamp, "Insufficient funds");
                            return;
                        } else {
                            currentAccount.subtractMoney(moneyToPay);
                            user.setPlan("gold");
                            user.addUpgradePlanTransaction(timestamp, iban, newPlan);
                            return;
                        }
                    } else if ((user.getPlan().equals("standard")
                            || user.getPlan().equals("student"))
                                && newPlan.equals("gold")) {
                        // upgrade standard -> gold 350 RON
                        double moneyToPay = getMoneyConverter().convert(Utils.STANDARD_FEE,
                                "RON", to);
                        if (currentAccount.getBalance() < moneyToPay) {
                            user.addErrorTransaction(timestamp, "Insufficient funds");
                            return;
                        } else {
                            currentAccount.subtractMoney(moneyToPay);
                            user.setPlan("gold");
                            user.addUpgradePlanTransaction(timestamp, iban, newPlan);
                            return;
                        }
                    } else if ((user.getPlan().equals("standard")
                            || user.getPlan().equals("student"))
                                && newPlan.equals("silver")) {
                        // upgrade standard -> silver 100 RON
                        double moneyToPay = getMoneyConverter().convert(Utils.FEE, "RON", to);
                        if (currentAccount.getBalance() < moneyToPay) {
                            user.addErrorTransaction(timestamp, "Insufficient funds");
                            return;
                        } else {
                            currentAccount.subtractMoney(moneyToPay);
                            user.setPlan("silver");
                            user.addUpgradePlanTransaction(timestamp, iban, newPlan);
                            return;
                        }
                    }
                }
            }
        }
        if (accountFound == 0) {
            out.printError(timestamp, "upgradePlan", "Account not found");
        }
    }

    /**
     * Add business associate.
     *
     * @param input the input
     */
    public void addBusinessAssociate(final CommandInput input) {
        String ibanContBusiness = input.getAccount();
        String role = input.getRole();
        String email = input.getEmail();
        int userFound = 0;

        for (User user : users) {
            if (user.getEmail().equals(email)) {
                userFound = 1;
                break;
            }
        }
        if (userFound == 0) {
            return;
        }
        for (User user : users) {
            for (Account currentAccount : user.getAccounts()) {
                if (currentAccount.getIban().equals(ibanContBusiness)) {
                    if (currentAccount.getType().equals("business")) {
                        BusinessAccount businessAccount = (BusinessAccount) currentAccount;
                        if (businessAccount.isAssociate(email)) {
                            return;
                        }
                        if (role.equals("manager")) {
                            businessAccount.getManagerEmails().add(email);
                            return;
                        }
                        if (role.equals("employee")) {
                            businessAccount.getEmployeeEmails().add(email);
                            return;
                        }
                    }
                }
            }
        }
    }

    /**
     * Change limit.
     *
     * @param input the input
     * @param out   the out
     */
    public void changeLimit(final CommandInput input, final ConverterJson out) {
        String command = input.getCommand();
        String ibanContBusiness = input.getAccount();
        String email = input.getEmail();
        double amount = input.getAmount();
        int timestamp = input.getTimestamp();
        int userFound = 0;

        for (User user : users) {
            if (user.getEmail().equals(email)) {
                userFound = 1;
                break;
            }
        }
        if (userFound == 0) {
            // user not found
            return;
        }
        for (User user : users) {
            for (Account currentAccount : user.getAccounts()) {
                if (currentAccount.getIban().equals(ibanContBusiness)) {
                    if (currentAccount.getType().equals("business")) {
                        BusinessAccount businessAccount = (BusinessAccount) currentAccount;
                        if (businessAccount.isOwner(email)) {
                            if (command.equals("changeSpendingLimit")) {
                                businessAccount.setSpendingLimit(amount);
                                return;
                            } else { // changeDepositLimit
                                businessAccount.setDepositLimit(amount);
                                return;
                            }
                        } else { // not owner
                            if (command.equals("changeSpendingLimit")) {
                                out.printError(timestamp, "changeSpendingLimit",
                                        "You must be owner in order to change spending limit.");
                            } else {
                                out.printError(timestamp, "changeDepositLimit",
                                        "You must be owner in order to change deposit limit");
                            }
                            return;
                        }
                    }
                }
            }
        }
    }

    /**
     * Pay online int.
     *
     * @param input the input
     * @param out   the out
     * @return int int
     */
    public int payOnline(final CommandInput input, final ConverterJson out) {
        String cardNr = input.getCardNumber();
        double amount = input.getAmount();
        String from = input.getCurrency();
        int timestamp = input.getTimestamp();
        String commerciant = input.getCommerciant();
        String email = input.getEmail();
        int commerciantSpendingThreshold = 0;
        int found = 0;
        int isBusiness = 0;

        if (amount == 0) {
            return 0;
        }

        for (Commerciant currentCommerciant : commerciants) {
            if (currentCommerciant.getName().equals(commerciant)) {
                if (currentCommerciant.getCashbackStrategy().equals("spendingThreshold")) {
                    commerciantSpendingThreshold = 1;
                }
            }
        }

        for (User user : users) {
            for (Account currentAccount : user.getAccounts()) {
                int currentAccountBusiness = 0;
                if (currentAccount.getType().equals("business")) {
                    currentAccountBusiness = 1;
                }
                for (int i = 0; i < currentAccount.getCards().size(); i++) {
                    Card currentCard = currentAccount.getCards().get(i);
                    if (currentCard.getCardNumber().equals(cardNr) && currentAccountBusiness == 1) {
                        isBusiness = 1;
                        break;
                    }
                }
            }
        }
        if (isBusiness == 1) {
            return payOnlineBusiness(input, commerciantSpendingThreshold);
        }
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                for (Account currentAccount : user.getAccounts()) {
                    for (int i = 0; i < currentAccount.getCards().size(); i++) {
                        Card currentCard = currentAccount.getCards().get(i);
                        if (currentCard.getCardNumber().equals(cardNr)) {
                            found = 1;
                            String to = currentAccount.getCurrency();
                            double amountToBePayed = getMoneyConverter().convert(amount, from, to);
                            double ronSpent = getMoneyConverter().convert(amount, from, "RON");
                            double commision = user.checkCommission(amountToBePayed, ronSpent);
                            if (currentCard.getStatus().equals("frozen")) {
                                user.addErrorTransaction(timestamp, "The card is frozen");
                                return 0;
                            } else if (currentAccount.getBalance()
                                    >= (amountToBePayed + commision)) {
                                if (commerciantSpendingThreshold == 1) {
                                    currentAccount.addMoneySpent(ronSpent);
                                }
                                double cashback = currentAccount.checkForCashback(commerciant,
                                        getCommerciants(), amountToBePayed,
                                        user.getPlan(), currentAccount);
                                currentAccount.subtractMoney(cashback + commision);
                                currentAccount.addCommerciantTransaction();
                                user.addCardPaymentTransaction(timestamp, amountToBePayed,
                                        commerciant, currentAccount.getIban());
                                if (ronSpent >= Utils.RON_FOR_UPGRADE) {
                                    user.add300RonPayment();
                                }
                                if (user.getRon300Payment() >= Utils.NUM_FOR_UPGRADE
                                        && user.getPlan().equals("silver")) {
                                    user.setPlan("gold");
                                    user.addUpgradePlanTransaction(timestamp,
                                            currentAccount.getIban(), "gold");
                                }
                                if (currentCard.getType().equals("oneTime")) {
                                    currentAccount.removeCard(i, input, user.getEmail(),
                                            user.getTransactions(), "The card has been destroyed");
                                    currentAccount.addCard(input, "oneTime", email,
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
     * Pay online business int.
     *
     * @param input                        the input
     * @param commerciantSpendingThreshold the commerciant spending threshold
     * @return the int
     */
    public int payOnlineBusiness(final CommandInput input, final int commerciantSpendingThreshold) {
        String cardNr = input.getCardNumber();
        double amount = input.getAmount();
        String from = input.getCurrency();
        int timestamp = input.getTimestamp();
        String commerciant = input.getCommerciant();
        String email = input.getEmail();
        int found = 0;
        String name = getNameByEmail(email);

        for (User user : users) {
            for (Account currentAccount : user.getAccounts()) {
                if (currentAccount.getType().equals("business")) {
                    BusinessAccount businessAccount = (BusinessAccount) currentAccount;
                    for (int i = 0; i < currentAccount.getCards().size(); i++) {
                        Card currentCard = currentAccount.getCards().get(i);
                        if (currentCard.getCardNumber().equals(cardNr)
                                && businessAccount.isAssociate(email)) {
                            found = 1;
                        }
                    }
                }
            }
        }
        if (found == 0) {
            return 2;
        }

        for (User user : users) {
            for (Account currentAccount : user.getAccounts()) {
                if (currentAccount.getType().equals("business")) {
                    BusinessAccount businessAccount = (BusinessAccount) currentAccount;
                    double limit = businessAccount.getSpendingLimit();
                    boolean isEmployee = businessAccount.isEmployee(email);
                    boolean isManagerOrOwner = (businessAccount.isManager(email)
                            || businessAccount.isOwner(email));

                    for (int i = 0; i < currentAccount.getCards().size(); i++) {
                        Card currentCard = currentAccount.getCards().get(i);
                        if (currentCard.getCardNumber().equals(cardNr)) {
                            double ronSpent = getMoneyConverter().convert(amount, from, "RON");
                            if (isManagerOrOwner || (isEmployee && ronSpent <= limit)) {
                                String to = currentAccount.getCurrency();
                                double amountToBePayed = getMoneyConverter().convert(
                                        amount, from, to);
                                double commision = user.checkCommission(amountToBePayed, ronSpent);
                                if (currentCard.getStatus().equals("frozen")) {
                                    user.addErrorTransaction(timestamp, "The card is frozen");
                                    return 0;
                                } else if (currentAccount.getBalance()
                                        >= (amountToBePayed + commision)) {
                                    if (commerciantSpendingThreshold == 1) {
                                        currentAccount.addMoneySpent(ronSpent);
                                    }
                                    double cashback = currentAccount.checkForCashback(commerciant,
                                            getCommerciants(), amountToBePayed,
                                            user.getPlan(), currentAccount);
                                    currentAccount.subtractMoney(cashback + commision);
                                    if (!businessAccount.isOwner(email)) {
                                        ((BusinessAccount) currentAccount).
                                                addTotalSpent(amountToBePayed);
                                        if (businessAccount.isManager(email)) {
                                            for (Commerciant com : getCommerciants()) {
                                                if (com.getName().equals(commerciant)
                                                        && !com.getManagerList().contains(name)) {
                                                    com.getManagerList().add(name);
                                                }
                                            }
                                            if (!businessAccount.getOutOrderForReportManager().
                                                    contains(name)) {
                                                businessAccount.getOutOrderForReportManager().
                                                        add(name);
                                            }
                                            businessAccount.recordManagerSpending(name,
                                                    amountToBePayed);
                                        } else if (businessAccount.isEmployee(email)) {
                                            for (Commerciant com : getCommerciants()) {
                                                if (com.getName().equals(commerciant)
                                                        && !com.getEmployeeList().contains(name)) {
                                                    com.getEmployeeList().add(name);
                                                }
                                            }
                                            if (!businessAccount.getOutOrderForReportEmployee().
                                                    contains(name)) {
                                                businessAccount.getOutOrderForReportEmployee().
                                                        add(name);
                                            }
                                            businessAccount.recordEmployeeSpending(name,
                                                    amountToBePayed);
                                        }
                                    }
                                    for (Commerciant com : getCommerciants()) {
                                        if (com.getName().equals(commerciant)) {
                                            com.setBusinessMoneySpent(com.getBusinessMoneySpent()
                                                    + amountToBePayed);
                                        }
                                    }
                                    currentAccount.addCommerciantTransaction();
                                    user.addCardPaymentTransaction(timestamp, amountToBePayed,
                                            commerciant, currentAccount.getIban());
                                    if (currentCard.getType().equals("oneTime")) {
                                        currentAccount.removeCard(i, input, user.getEmail(),
                                                user.getTransactions(),
                                                "The card has been destroyed");
                                        currentAccount.addCard(input, "oneTime", email,
                                                user.getTransactions(), "New card created");
                                    }
                                    return 1;
                                }
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Withdraw savings.
     *
     * @param input the input
     */
    public void withdrawSavings(final CommandInput input) {
        String iban = input.getAccount();
        double amount = input.getAmount();
        String from = input.getCurrency();
        int timestamp = input.getTimestamp();
        String ibanClassicAccount = " ";
        String userEmail = " ";
        int accountFound = 0;
        int typeIsSavings = 0;
        int hasMoney = 0;
        double amountToPay = 0;

        if (amount == 0) {
            return;
        }

        for (User user : users) {
            for (Account currentAccount : user.getAccounts()) {
                if (currentAccount.getIban().equals(iban)) {
                    accountFound = 1;
                    userEmail = user.getEmail();
                    String to = currentAccount.getCurrency();
                    amountToPay = getMoneyConverter().convert(amount, from, to);
                    if (amountToPay <= currentAccount.getBalance()) {
                        hasMoney = 1;
                    }
                    if (currentAccount.getType().equals("savings")) {
                        typeIsSavings = 1;
                    }
                    if (user.verifyAge21(user.getBirthDate()) == 0) {
                        user.addErrorTransaction(timestamp,
                                "You don't have the minimum age required.");
                        return;
                    }
                }
            }
        }
        if (accountFound == 0) {
            return;
        }

        getOut:
        for (User user : users) {
            if (user.getEmail().equals(userEmail)) {
                for (Account currentAccount : user.getAccounts()) {
                    if (currentAccount.getType().equals("classic")
                            && currentAccount.getCurrency().equals(from)) {
                        ibanClassicAccount = currentAccount.getIban();
                        break getOut;
                    }
                }
            }
        }

        if (ibanClassicAccount.equals(" ")) {
            for (User user : users) {
                if (user.getEmail().equals(userEmail)) {
                    user.addErrorTransaction(timestamp, "You do not have a classic account.");
                    return;
                }
            }
        }

        getOut2:
        for (User user : users) {
            if (user.getEmail().equals(userEmail)) {
                for (Account currentAccount : user.getAccounts()) {
                    if (currentAccount.getIban().equals(ibanClassicAccount)) {
                        if (typeIsSavings == 0) {
                            return; // given account is not of type savings
                        }
                        if (hasMoney == 0) {
                            return;
                        }
                        currentAccount.addMoney(amount);
                        user.addSavingsWithdrawalTransaction(timestamp, "Savings withdrawal",
                                amount, ibanClassicAccount, iban);
                        break getOut2;
                    }
                }
            }
        }
        for (User user : users) {
            for (Account currentAccount : user.getAccounts()) {
                if (currentAccount.getIban().equals(iban)) {
                    user.addSavingsWithdrawalTransaction(timestamp, "Savings withdrawal",
                            amount, ibanClassicAccount, iban);
                    currentAccount.subtractMoney(amountToPay);
                }
            }
        }
    }

    /**
     * Cash withdrawal.
     *
     * @param input the input
     * @param out   the out
     */
    public void cashWithdrawal(final CommandInput input, final ConverterJson out) {
        String cardNr = input.getCardNumber();
        String email = input.getEmail();
        double amount = input.getAmount();
        int timestamp = input.getTimestamp();
        int userFound = 0;
        int cardFound = 0;

        if (amount == 0) {
            return;
        }

        for (User user : users) {
            if (user.getEmail().equals(email)) {
                userFound = 1;
            }
        }
        if (userFound == 0) {
            out.printError(timestamp, "cashWithdrawal", "User not found");
            return;
        }

        for (User user : users) {
            if (user.getEmail().equals(email)) {
                for (Account currentAccount : user.getAccounts()) {
                    for (Card currentCard : currentAccount.getCards()) {
                        if (currentCard.getCardNumber().equals(cardNr)) {
                            cardFound = 1;
                            if (currentCard.getStatus().equals("frozen")) {
                                return;
                            }
                            String to = currentAccount.getCurrency();
                            double amountToBePayed = getMoneyConverter().convert(amount, "RON", to);
                            double commision = user.checkCommission(amountToBePayed, amount);
                            if (currentAccount.getBalance() >= (amountToBePayed + commision)) {
                                currentAccount.subtractMoney(amountToBePayed + commision);
                                user.addCashWithdrawalTransaction(timestamp, amount);
                                return;
                            } else {
                                user.addErrorTransaction(timestamp, "Insufficient funds");
                                return;
                            }
                        }
                    }
                }
            }
        }
        if (cardFound == 0) {
            out.printError(timestamp, "cashWithdrawal", "Card not found");
        }
    }

    /**
     * Split payment.
     *
     * @param input the input
     */
    public void splitPayment(final CommandInput input) {
        getActivePayments().add(new SplitPayment(input));
    }

    /**
     * Accept split payment.
     *
     * @param input the input
     * @param out   the out
     */
    public void acceptSplitPayment(final CommandInput input, final ConverterJson out) {
        String email = input.getEmail();
        int timestamp = input.getTimestamp();
        boolean found = false;

        for (SplitPayment paymentToAccept : getActivePayments()) {
            if (!paymentToAccept.getIsCompleted()
                    && paymentToAccept.getStatusForEmail(email, getUsers()) == null) {
                paymentToAccept.acceptPayment(email, getUsers(), getMoneyConverter());
                found = true;
                break;
            }
        }
        if (!found) {
            out.printError(timestamp, "acceptSplitPayment", "User not found");
        }
    }

    /**
     * Reject split payment.
     *
     * @param input the input
     * @param out   the out
     */
    public void rejectSplitPayment(final CommandInput input, final ConverterJson out) {
        String email = input.getEmail();
        int timestamp = input.getTimestamp();
        boolean found = false;

        for (SplitPayment paymentToReject : getActivePayments()) {
            if (!paymentToReject.getIsCompleted()
                    && paymentToReject.getStatusForEmail(email, getUsers()) == null) {
                // One user rejected the payment
                paymentToReject.rejectPayment(email, getUsers());
                found = true;
                break;
            }
        }
        if (!found) {
            out.printError(timestamp, "rejectSplitPayment", "User not found");
        }
    }

    /**
     * Send money int.
     *
     * @param input the input
     * @param out   the out
     * @return int int
     */
    public int sendMoney(final CommandInput input, final ConverterJson out) {
        int timestamp = input.getTimestamp();
        String iban = input.getAccount();
        double amount = input.getAmount();
        String ibanReceiver = input.getReceiver();
        String commerciantName = "";
        int receiverExists = 0;
        int receiverIsCommerciant = 0;
        int commerciantSpendingThreshold = 0;
        int hasMoney = 1;
        int found = 0;
        int ibanIsValid = 0;
        String from = "RON"; // initialization but always  modifies before use

        if (amount == 0) {
            return 0;
        }
        if (iban.contains("POO")) {
            ibanIsValid = 1;
        }

        for (User user : users) {
            for (Account currentAccount : user.getAccounts()) {
                if (currentAccount.getIban().equals(ibanReceiver)) {
                    receiverExists = 1;
                    break;
                }
            }
        }

        for (Commerciant commerciant : commerciants) {
            if (commerciant.getIban().equals(ibanReceiver)) {
                receiverExists = 1;
                break;
            }
        }

        for (User user : users) {
            for (Account currentAccount : user.getAccounts()) {
                if (currentAccount.getIban().equals(iban)) {
                    found = 1;
                    break;
                }
            }
        }
        if (receiverExists == 1 && found == 1) {

            for (Commerciant commerciant : commerciants) {
                if (commerciant.getIban().equals(ibanReceiver)) {
                    receiverIsCommerciant = 1;
                    commerciantName = commerciant.getName();
                    if (commerciant.getCashbackStrategy().equals("spendingThreshold")) {
                        commerciantSpendingThreshold = 1;
                    }
                }
            }

            for (User user : users) {
                for (Account currentAccount : user.getAccounts()) {
                    if (currentAccount.getIban().equals(iban)) {
                        from = currentAccount.getCurrency();
                        double ronSpent = getMoneyConverter().convert(amount, from, "RON");
                        double commision = user.checkCommission(amount, ronSpent);
                        if (currentAccount.getBalance() < (amount + commision)) {
                            user.addPaymentFailedTransaction(timestamp);
                            hasMoney = 0;
                        }
                        if (hasMoney == 1) {
                            int planUpgrade = 0;
                            if (receiverIsCommerciant == 0) {
                                currentAccount.subtractMoney(amount + commision);
                            } else {
                                if (commerciantSpendingThreshold == 1) {
                                    currentAccount.addMoneySpent(ronSpent);
                                }
                                double cashback = currentAccount.checkForCashback(commerciantName,
                                        getCommerciants(), amount, user.getPlan(), currentAccount);
                                currentAccount.subtractMoney(cashback + commision);
                                currentAccount.addCommerciantTransaction();
                                if (ronSpent >= Utils.RON_FOR_UPGRADE) {
                                    user.add300RonPayment();
                                }
                                if (user.getRon300Payment() >= Utils.NUM_FOR_UPGRADE
                                        && user.getPlan().equals("silver")) {
                                    user.setPlan("gold");
                                    planUpgrade = 1;
                                }
                            }
                            user.addMoneyTransferTransaction(input, "sent", from, amount);
                            if (planUpgrade == 1) {
                                user.addUpgradePlanTransaction(timestamp,
                                        currentAccount.getIban(), "gold");
                            }
                        }
                    }
                }
            }
            for (User user : users) {
                for (Account currentAccount : user.getAccounts()) {
                    if (currentAccount.getIban().equals(ibanReceiver)) {
                        String to = currentAccount.getCurrency();
                        if (hasMoney == 1) {
                            double amountToBePayed = getMoneyConverter().convert(amount, from, to);
                            currentAccount.addMoney(amountToBePayed);
                            user.addMoneyTransferTransaction(input,
                                    "received", to, amountToBePayed);
                            return 1;
                        }
                    }
                }
            }
        } else if (ibanIsValid == 1) {
            out.printError(timestamp, "sendMoney", "User not found");
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
     * @return int int
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
     * @return int int
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
                        user.addInterestTransaction(input.getTimestamp(), balance * rate,
                                currentAccount.getCurrency());
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
                        out.createReport(user.getTransactions(),
                                getCommerciants(), input, currentAccount, type);
                    }
                    return 1;
                }
            }
        }
        return 0;
    }
}
