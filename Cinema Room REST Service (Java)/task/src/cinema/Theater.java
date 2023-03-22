package cinema;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Theater {
    private int rows;
    private int columns;
    private int income;
    private List<Seat> allSeats;

    public Theater(int rows, int cols) {
        this.rows = rows;
        this.columns = cols;
        this.income = 0;
        this.allSeats = new ArrayList<>();
    }

    public void buildSeatsList() {
        for (int row = 1; row <= this.rows; row++) {
            for (int column = 1; column <= this.columns; column++) {
                int price = row <= 4 ? 10 : 8;
                this.allSeats.add(new Seat(row, column, price));
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public List<Seat> getAllSeats() {
        return allSeats;
    }

    public void setAllSeats(List<Seat> allSeats) {
        this.allSeats = allSeats;
    }

    public List<Seat> getAvailableSeats() {
        return this.allSeats.stream()
                .filter(seat -> !seat.isPurchased())
                .toList();
    }
}
