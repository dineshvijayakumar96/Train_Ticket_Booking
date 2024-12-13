package dev.dinesh;

import java.util.Arrays;

public class Ticket {

    private int pnrNumber = 0;
    private int[] confirmedSeats;
    private int[] waitingList;
    private int[] cancelledSeats;
    private String stationFrom;
    private String stationTo;
    private boolean bookingStatus;

    public Ticket(int pnrNumber, int[] confirmedSeats, int[] waitingList, int[] cancelledSeats, String stationFrom, String stationTo, boolean bookingStatus) {
        this.pnrNumber = pnrNumber;
        this.confirmedSeats = confirmedSeats;
        this.waitingList = waitingList;
        this.cancelledSeats = cancelledSeats;
        this.stationFrom = stationFrom;
        this.stationTo = stationTo;
        this.bookingStatus = bookingStatus;
    }

    public int getPnrNumber() {
        return pnrNumber;
    }

    public int[] getConfirmedSeats() {
        return confirmedSeats;
    }

    public void setConfirmedSeats(int[] confirmedSeats) {
        this.confirmedSeats = confirmedSeats;
    }

    public int[] getWaitingList() {
        return waitingList;
    }

    public void setWaitingList(int[] waitingList) {
        this.waitingList = waitingList;
    }

    public void setCancelledSeats(int[] cancelledSeats) {
        this.cancelledSeats = cancelledSeats;
    }

    public String getStationFrom() {
        return stationFrom;
    }

    public String getStationTo() {
        return stationTo;
    }

    public void setBookingStatus(boolean bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    @Override
    public String toString() {
        return "PNR " + pnrNumber + ", " + stationFrom + " to " + stationTo
                + ", Seat Nos: " + ((confirmedSeats != null) ? Arrays.toString(confirmedSeats) : "-")
                + ((waitingList != null) && (waitingList.length > 0) ? ((waitingList.length == 1) ? ((waitingList[0] == 1) ? "WL1" : "WL2") : "WL1, WL2") : "")
                + (cancelledSeats != null ? ((confirmedSeats != null) ? " Cancelled Seats: " + Arrays.toString(cancelledSeats) : (confirmedSeats == null) ? " Cancelled Seats: " + Arrays.toString(cancelledSeats) : ((cancelledSeats.length < 2) ? " Cancelled Seats: WL2" : " Cancelled Seats: WL1, WL2")) : "");
    }
}
