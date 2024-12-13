package dev.dinesh;

import java.util.Scanner;

public class BookingApplication {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        TicketBooking booking = new TicketBooking();

        while (true) {
            System.out.println("""
                    --------------------------------
                    Train Ticket Booking Application
                    --------------------------------
                    1. Book Ticket
                    2. Cancel Ticket
                    3. Check Availability
                    4. Print Chart
                    5. Exit
                    """);
            System.out.print("-> ");
            int userChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userChoice) {
                case 1 -> booking.bookTicket();
                case 2 -> booking.cancelTicket();
                case 3 -> booking.seatAvailability();
                case 4 -> booking.printChart();
                case 5 -> {
                    System.out.println("Program Exited");
                    scanner.close();
                }
                default -> System.out.println("Invalid input! Try again.");
            }
        }
    }
}
