import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"01.Potato", "02.Fish", "03.Apple", "04.Orange", "05.Milk", "06.Music"};

        System.out.println("Here are our products: ");
        System.out.print(Arrays.toString(products));

        String[] orders = new String[10];
        int[] helpRequests = new int[10];
        int[] cancelRequests = new int[10];
        int availableOrdersIndex = 0;
        int availableHelpIndex = 0;
        int availableCancelIndex = 0;

        while (true) {
            boolean exit = false;
            System.out.println("""
                    ======
                    Menu: Dial only one digit:
                    1 - Create Order
                    2 - List of Your Orders
                    3 - Request Help with an Order
                    4 - Cancel an Order
                    0 - Exit the program
                    """);

            String digitDialed = scanner.nextLine();

            switch (digitDialed) {
                case "1":
                    availableOrdersIndex = createOrder(scanner, products, orders, availableOrdersIndex);
                    break;
                case "2":
                    listOrders(orders);
                    break;
                case "3":
                    availableHelpIndex = requestHelp(scanner, orders, helpRequests, availableHelpIndex);
                    break;
                case "4":
                    availableCancelIndex = cancelOrder(scanner, orders, cancelRequests, availableCancelIndex);
                    break;
                case "0":
                    exitProgram(orders, helpRequests, cancelRequests);
                    exit = true;
                    break;
                default:
                    System.out.println("Wrong command. Please enter a valid command!");
            }

            if (exit) {
                break;
            }
        }
    }

    private static int createOrder(Scanner scanner, String[] products, String[] orders, int availableOrdersIndex) {
        System.out.println("Enter the 2 digit product code or 00 to finish the order");
        System.out.printf("Here are the products and their codes: %s%n", Arrays.toString(products));

        String newOrder = "";
        boolean newOrderIsValid = false;

        while (true) {
            String inputProductCode = scanner.nextLine();

            switch (inputProductCode) {
                case "01":
                    System.out.println("Adding Potato to the order");
                    newOrder += "Potato ";
                    break;
                case "02":
                    System.out.println("Adding Fish to the order");
                    newOrder += "Fish ";
                    break;
                case "03":
                    System.out.println("Adding Apple to the order");
                    newOrder += "Apple ";
                    break;
                case "04":
                    System.out.println("Adding Orange to the order");
                    newOrder += "Orange ";
                    break;
                case "05":
                    System.out.println("Adding Milk to the order");
                    newOrder += "Milk ";
                    break;
                case "06":
                    System.out.println("Adding Music to the order");
                    newOrder += "Music ";
                    break;
                case "00":
                    if (newOrder.length() > 0) {
                        if (availableOrdersIndex == orders.length) {
                            orders = Arrays.copyOf(orders, orders.length * 2);
                        }
                        orders[availableOrdersIndex] = newOrder.strip();
                        availableOrdersIndex++;
                        newOrderIsValid = true;
                    }
                    if (newOrderIsValid) {
                        System.out.println("The order is finished");
                    } else {
                        System.out.println("Can't add an empty order");
                    }
                    return availableOrdersIndex;
                default:
                    System.out.println("Wrong code");
                    continue;
            }
        }
    }

    private static void listOrders(String[] orders) {
        if (orders[0] == null) {
            System.out.println("You have no orders");
        } else {
            System.out.println("Here are your orders:");
            for (int i = 0; i < orders.length; i++) {
                if (orders[i] != null) {
                    System.out.printf("Order #%d: Order: %s%n", i + 1, orders[i]);
                } else {
                    break;
                }
            }
        }
    }

    private static int requestHelp(Scanner scanner, String[] orders, int[] helpRequests, int availableHelpIndex) {
        System.out.println("Please enter the number of your order which you need help with" +
                " and one of our representatives will contact you:");

        int numberOfTheOrder = getOrderNumber(scanner);
        int[] validOrderNumbers = getValidOrderNumbers(orders);

        for (int validOrderNumber : validOrderNumbers) {
            if (numberOfTheOrder == validOrderNumber) {
                if (availableHelpIndex == helpRequests.length) {
                    helpRequests = Arrays.copyOf(helpRequests, helpRequests.length * 2);
                }
                helpRequests[availableHelpIndex] = numberOfTheOrder;
                availableHelpIndex++;
                System.out.println("Your request is received");
                return availableHelpIndex;
            }
        }
        System.out.println("Invalid order number");
        return availableHelpIndex;
    }

    private static int cancelOrder(Scanner scanner, String[] orders, int[] cancelRequests, int availableCancelIndex) {
        System.out.println("Please enter the number of your order which you want to cancel " +
                "and one of our representatives will review and remove it for you:");

        int numberOfTheOrder = getOrderNumber(scanner);
        int[] validOrderNumbers = getValidOrderNumbers(orders);

        for (int validOrderNumber : validOrderNumbers) {
            if (numberOfTheOrder == validOrderNumber) {
                if (availableCancelIndex == cancelRequests.length) {
                    cancelRequests = Arrays.copyOf(cancelRequests, cancelRequests.length * 2);
                }
                cancelRequests[availableCancelIndex] = numberOfTheOrder;
                availableCancelIndex++;
                System.out.println("Your request is received");
                return availableCancelIndex;
            }
        }
        System.out.println("Invalid order number");
        return availableCancelIndex;
    }

    private static void exitProgram(String[] orders, int[] helpRequests, int[] cancelRequests) {
        System.out.println("Thank you for using our program!");
        System.out.println("Summary of your interaction:");

        printSummary("Orders", orders);
        printSummary("Help Requests", helpRequests);
        printSummary("Cancellation Requests", cancelRequests);
    }

    private static int getOrderNumber(Scanner scanner) {
        while (true) {
            String orderNumber = scanner.nextLine();
            if (orderNumber.chars().allMatch(Character::isDigit)) {
                return Integer.parseInt(orderNumber);
            } else {
                System.out.println("Please enter a number");
            }
        }
    }

    private static int[] getValidOrderNumbers(String[] orders) {
        return Arrays.stream(orders)
                .filter(order -> order != null)
                .mapToInt(order -> Arrays.asList(orders).indexOf(order) + 1)
                .toArray();
    }

    private static void printSummary(String title, String[] array) {
        String list = Arrays.stream(array)
                .filter(item -> item != null)
                .reduce("", (acc, item) -> acc + item + ", ");
        if (!list.isEmpty()) {
            list = list.substring(0, list.length() - 2);
        }
        System.out.printf("%s: %s%n", title, list);
    }

    private static void printSummary(String title, int[] array) {
        String list = Arrays.stream(array)
                .filter(item -> item != 0)
                .mapToObj(Integer::toString)
                .reduce("", (acc, item) -> acc + item + ", ");
        if (!list.isEmpty()) {
            list = list.substring(0, list.length() - 2);
        }
        System.out.printf("%s: %s%n", title, list);
    }
}
