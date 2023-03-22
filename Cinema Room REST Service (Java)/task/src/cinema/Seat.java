package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public class Seat {
    private int row;
    private int column;
    private int price;
    @JsonIgnore
    private boolean purchased;
    @JsonIgnore
    private UUID token;

    public Seat(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
        this.purchased = false;
        this.token = null;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}
