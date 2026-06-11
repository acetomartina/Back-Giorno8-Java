package acetomartina;

import acetomartina.entities.Customer;
import acetomartina.entities.Order;
import acetomartina.entities.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Application {

    public static void main(String[] args) {

        Product p1 = new Product(1L, "Il signore degli Anelli", "Books", 80.0);
        Product p2 = new Product(2L, "Le cronache del Ghiaccio e del Fuoco", "Books", 130.0);
        Product p3 = new Product(3L, "Camion dei Pompieri", "Baby", 70.0);
        Product p4 = new Product(4L, "Trenino Elettrico", "Baby", 110.0);
        Product p5 = new Product(5L, "Moto da Cross", "Boys", 170.0);
        Product p6 = new Product(6L, "Moto GP Racing", "Boys", 250.0);
        Product p7 = new Product(7L, "Le Notti Bianche", "Books", 150.0);


        List<Product> products = new ArrayList<>(List.of(p1, p2, p3, p4, p5, p6, p7));


        Customer c1 = new Customer(1L, "Mario Rossi", 1);
        Customer c2 = new Customer(2L, "Luigi Mora", 2);
        Customer c3 = new Customer(3L, "Anna Corvi", 2);

        List<Customer> customers = new ArrayList<>(List.of(c1, c2, c3));


        Order o1 = new Order(1L,
                "Consegnato",
                LocalDate.of(2021, 2, 15),
                LocalDate.of(2021, 2, 21),
                new ArrayList<>(List.of(p2, p3)),
                c2
        );

        Order o2 = new Order(
                2L,
                "In preparazione",
                LocalDate.of(2021, 5, 10),
                LocalDate.of(2021, 5, 15),
                new ArrayList<>(List.of(p5, p6)),
                c1
        );

        Order o3 = new Order(
                3L,
                "Consegnato",
                LocalDate.of(2021, 3, 20),
                LocalDate.of(2021, 3, 25),
                new ArrayList<>(List.of(p1, p4)),
                c3
        );

        List<Order> orders = new ArrayList<>(List.of(o1, o2, o3));

        Map<Customer, List<Order>> ordersByCustomer =
                orders.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Order::getCustomer
                                )
                        );

        System.out.println();
        System.out.println("Esercizio 1:");
        ordersByCustomer.forEach((customer, orderList) -> {
            System.out.println(customer);
            orderList.forEach(System.out::println);
        });

        Map<Customer, Double> totalSalesByCustomer =
                orders.stream()
                        .collect(Collectors.groupingBy(
                                        Order::getCustomer,
                                        Collectors.summingDouble(Order::calculateTotal
                                        )
                                )
                        );


        System.out.println();
        System.out.println("Esercizio 2: ");
        totalSalesByCustomer.forEach((customer, total) -> {
            System.out.println(customer);
            System.out.println("Totale speso: " + total + "€");
        });

        List<Product> mostExpensiveProducts = products.stream()
                .sorted(
                        Comparator.comparingDouble(Product::getPrice)
                                .reversed()
                )
                .limit(3)
                .toList();

        System.out.println();
        System.out.println("Esercizio 3: ");
        mostExpensiveProducts.forEach(System.out::println);

        double averageOrderAmount = orders.stream()
                .mapToDouble(Order::calculateTotal)
                .average()
                .orElse(0.0);

        System.out.println("Esercizio 4: ");
        System.out.println("Media importi ordini: " + averageOrderAmount + "€");


    }
}
