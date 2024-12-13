package dev.dinesh;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class TicketBooking {

    Scanner scanner = new Scanner(System.in);

    Ticket ticket;
    private int pnrNumber = 0;
    private final int MAX_SEATS = 8;
    private final int MAX_WAITING_LIST = 2;
    private HashMap<Integer, Ticket> ticketsMap = new HashMap<>();
    private HashMap<Integer, String[]> seatingMap = new HashMap<>();
    private LinkedList<Ticket> waitingListSeats = new LinkedList<>();
    private int waitingList = 0;
    private boolean isWaitingListFull = false;


    public TicketBooking() {
        for (int i = 1; i <= MAX_SEATS; i++) {
            seatingMap.put(i, new String[] { "", "", "", "", "" });
        }
    }

    public void bookTicket() {

        String userInputStationFrom;
        String userInputStationTo;
        int userInputNoOfSeats = 0;
        int[] availableSeats = new int[0];

        System.out.println("Enter the boarding station( A | B | C | D ):");
        System.out.print("-> ");
        userInputStationFrom = scanner.nextLine().toUpperCase();

        System.out.println("Enter the destination station( B | C | D | E ):");
        System.out.print("-> ");
        userInputStationTo = scanner.nextLine().toUpperCase();

        System.out.println("Enter the number of seats(Maximum 8 Seats):");
        System.out.print("-> ");
        userInputNoOfSeats = scanner.nextInt();
        scanner.nextLine();

        if (userInputNoOfSeats == 0) {
            System.out.println("The number of seats should be above 0");
            availableSeats = null;
        } else if (userInputNoOfSeats <= MAX_SEATS && userInputNoOfSeats > 0) {
            availableSeats = checkAvailability(userInputStationFrom, userInputStationTo, userInputNoOfSeats);
        } else {
            System.out.println("The Number of seats exceeds the limit.");
        }

        if (availableSeats != null) {

            for (int i : availableSeats) {

                if (seatingMap.containsKey(i)) {

                    String[] seats = seatingMap.get(i);

                    for (int j = getStationIndex(userInputStationFrom); j < getStationIndex(userInputStationTo); j++) {
                        if (seats[j].equalsIgnoreCase("")) {
                            seats[j] = "X";
                        }
                    }
                }
            }

            ++pnrNumber;
            ticket = new Ticket(pnrNumber, availableSeats, null, null, userInputStationFrom, userInputStationTo, (waitingList == 0) ? true : false);
            ticketsMap.put(ticket.getPnrNumber(), ticket);
            System.out.println("Your ticket has been Booked Successfully");
            System.out.println(ticket.toString());
        } else if (userInputNoOfSeats > 0){
            System.out.println("No seats available");
            if (userInputNoOfSeats <= MAX_WAITING_LIST && !isWaitingListFull
                    && userInputNoOfSeats > waitingList &&
                    ((userInputNoOfSeats - waitingList) == (MAX_WAITING_LIST - waitingList)) &&
                    userInputNoOfSeats == (userInputNoOfSeats - waitingList)) {

                int[] waitingSeatNos = new int[userInputNoOfSeats];

                for (int i = 0; i < userInputNoOfSeats; i++) {
                    waitingSeatNos[i] = i + 1;
                }

                waitingList += userInputNoOfSeats;
                if (waitingList == MAX_WAITING_LIST) {
                    isWaitingListFull = true;
                }

                ticket = new Ticket(++pnrNumber, null, waitingSeatNos, null, userInputStationFrom, userInputStationTo, (this.waitingList == 0) ? false : true);
                waitingListSeats.push(ticket);
                System.out.println("Added to waiting list");
                System.out.println(ticket.toString());
            } else if (!isWaitingListFull && userInputNoOfSeats == waitingList) {

                int[] waitingSeatNos = new int[userInputNoOfSeats];

                waitingSeatNos[0] = waitingList + 1;

                waitingList += userInputNoOfSeats;
                if (waitingList == MAX_WAITING_LIST) {
                    isWaitingListFull = true;
                }

                ticket = new Ticket(++pnrNumber, null, waitingSeatNos, null, userInputStationFrom, userInputStationTo, (this.waitingList == 0) ? false : true);
                waitingListSeats.push(ticket);
                System.out.println("Added to waiting list");
                System.out.println(ticket.toString());
            } else {
                System.out.println("The number of seats exceeds Maximum Waiting List");
            }
        }
    }

    private int[] checkAvailability(String userInputStationFrom, String userInputStationTo, int userInputNoOfSeats) {

        int stationFromIndex = getStationIndex(userInputStationFrom);
        int stationToIndex = getStationIndex(userInputStationTo);
        boolean confirmation = true;
        int confirmedSeats = 0;
        int[] availableSeats = new int[userInputNoOfSeats];

        if (stationFromIndex < 0 || stationFromIndex > 3 || stationToIndex < 1 || stationToIndex > 4) {
            System.out.println("Invalid Station");
            return null;
        }

        for (int i = 1; i <= MAX_SEATS; i++) {

            if (confirmedSeats == userInputNoOfSeats) {
                return availableSeats;
            }

            String[] seating = seatingMap.get(i);

            for (int j = stationFromIndex; j <= stationToIndex; j++) {

                if (seating[j] != "") {
                    confirmation = false;
                    break;
                } else {
                    confirmation = true;
                }
            }

            if (confirmation && confirmedSeats < userInputNoOfSeats) {
                availableSeats[confirmedSeats] = i;
                confirmedSeats++;
            }
        }

        if (!confirmation) {
            return null;
        }

        return availableSeats;
    }

    private int checkAvailability(String userInputStationFrom, String userInputStationTo) {

        int stationFromIndex = getStationIndex(userInputStationFrom);
        int stationToIndex = getStationIndex(userInputStationTo);
        boolean confirmation = true;
        int confirmedSeats = 0;

        if (stationFromIndex < 0 || stationFromIndex > 3 || stationToIndex < 1 || stationToIndex > 4) {
            System.out.println("Invalid Station");
            return 0;
        }

        for (int i = 1; i <= MAX_SEATS; i++) {

            String[] seating = seatingMap.get(i);

            for (int j = stationFromIndex; j <= stationToIndex; j++) {

                if (seating[j] != "") {
                    confirmation = false;
                    break;
                } else {
                    confirmation = true;
                }
            }

            if (confirmation) {
                confirmedSeats++;
            }
        }

        if (!confirmation) {
            return 0;
        }

        return confirmedSeats;
    }

    private int getStationIndex(String userInputStation) {

        int stationIndex;

        switch (userInputStation) {
            case "A" -> stationIndex = 0;
            case "B" -> stationIndex = 1;
            case "C" -> stationIndex = 2;
            case "D" -> stationIndex = 3;
            case "E" -> stationIndex = 4;
            default -> stationIndex = 5;
        }
        return stationIndex;
    }

    public void printChart() {

        System.out.printf("%2S %3S %2S %2S %2S %2S\n", "", "A", "B", "C", "D", "E");
        for (int i = 1; i <= MAX_SEATS; i++) {
            String[] seating = seatingMap.get(i);
            System.out.printf("%3d", i);
            for (int j = 0; j < seating.length; j++) {
                if (seating[j] != "") {
                    System.out.printf("%3S", seating[j]);
                } else {
                    System.out.printf("%3S", " ");
                }
            }
            System.out.println();
        }
    }

    public void cancelTicket() {

        int[] cancelledSeats;
        int userInputPNR;
        int userInputNoOfSeats;

        System.out.println("Enter the PNR number: ");
        System.out.print("-> ");
        userInputPNR = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter the number of seats to Cancel: ");
        System.out.print("-> ");
        userInputNoOfSeats = scanner.nextInt();
        scanner.nextLine();

        if (ticketsMap.containsKey(userInputPNR)) {

            Ticket ticket1 = ticketsMap.get(userInputPNR);
            int[] confirmedSeats = ticket1.getConfirmedSeats();
            int[] cancelledTickets = new int[userInputNoOfSeats];

            if (userInputNoOfSeats < ticket1.getConfirmedSeats().length) {

                int[] updatedSeats = new int[confirmedSeats.length - userInputNoOfSeats];

                for (int i = 0; i < userInputNoOfSeats; i++) {
                    cancelledTickets[i] = confirmedSeats[i];
                }

                System.arraycopy(confirmedSeats, userInputNoOfSeats, updatedSeats, 0, confirmedSeats.length - userInputNoOfSeats);

                ticket1.setConfirmedSeats(updatedSeats);

            } else {

                for (int i = 0; i < userInputNoOfSeats; i++) {
                    cancelledTickets[i] = confirmedSeats[i];
                }

                ticket1.setConfirmedSeats(null);

            }

            ticket1.setCancelledSeats(cancelledTickets);

            for (int i : cancelledTickets) {

                if (seatingMap.containsKey(i)) {

                    String[] seats = seatingMap.get(i);

                    for (int j = getStationIndex(ticket1.getStationFrom()); j < getStationIndex(ticket1.getStationTo()); j++) {
                        if (seats[j].equalsIgnoreCase("X")) {
                            seats[j] = "";
                        }
                    }
                }
            }

            System.out.println("Tickets has been Cancelled");
            System.out.println(ticket1.toString());
            updateWaitingList();
        } else {

            for (int i = 0; i < waitingListSeats.size(); i++) {
                Ticket ticket1 = waitingListSeats.get(i);

                if (ticket1.getPnrNumber() == userInputPNR) {
                    int[] seats = ticket1.getWaitingList();

                    if (userInputNoOfSeats < seats.length) {

                        ticket1.setWaitingList(new int[] { seats.length - userInputNoOfSeats});
                        ticket1.setCancelledSeats(new int[] {seats.length - userInputNoOfSeats});

                    } else {

                        int[] cancelledTickets = new int[userInputNoOfSeats];

                        for (int j = 0; j < userInputNoOfSeats; j++) {
                            cancelledTickets[j] = seats[j];
                        }

                        ticket1.setWaitingList(null);
                        ticket1.setCancelledSeats(cancelledTickets);

                    }

                    System.out.println("Ticket has been Cancelled");
                    System.out.println(ticket1.toString());
                    updateWaitingList();
                }
            }
        }
    }

    private void updateWaitingList() {

        for (int i = waitingListSeats.size() - 1; i >= 0; i--) {

            Ticket ticket1 = waitingListSeats.get(i);

            int[] availableSeats = checkAvailability(ticket1.getStationFrom(), ticket1.getStationTo(), ticket1.getWaitingList().length);

            if (availableSeats != null) {

                for (int j : availableSeats) {

                    if (seatingMap.containsKey(j)) {

                        String[] seats = seatingMap.get(j);

                        for (int k = getStationIndex(ticket1.getStationFrom()); k < getStationIndex(ticket1.getStationTo()); k++) {
                            if (seats[k].equalsIgnoreCase("")) {
                                seats[k] = "X";
                            }
                        }
                    }
                }

                ticket1.setConfirmedSeats(availableSeats);
                waitingList = waitingList - ticket1.getWaitingList().length;
                if (waitingList < MAX_WAITING_LIST) {
                    isWaitingListFull = false;
                }
                ticket1.setWaitingList(null);
                ticket1.setBookingStatus(true);

                ticketsMap.put(ticket1.getPnrNumber(), ticket1);

                waitingListSeats.remove(i);

                System.out.println("Seats of PNR: " + ticket1.getPnrNumber() + " has been confirmed");
                System.out.println(ticket1.toString());
            }
        }
    }

    public void seatAvailability() {

        String userInputStationFrom;
        String userInputStationTo;

        System.out.println("Enter the boarding station( A | B | C | D ):");
        System.out.print("-> ");
        userInputStationFrom = scanner.nextLine().toUpperCase();

        System.out.println("Enter the destination station( B | C | D | E ):");
        System.out.print("-> ");
        userInputStationTo = scanner.nextLine().toUpperCase();

        int availabilitySeats = checkAvailability(userInputStationFrom, userInputStationTo);
        System.out.println("Available Seats: " + availabilitySeats);
    }
}
