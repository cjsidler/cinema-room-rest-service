package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
public class TheaterController {
    private final Theater theater;

    public TheaterController() {
        this.theater = new Theater(9, 9);
        this.theater.buildSeatsList();
    }

    @GetMapping("/seats")
    public Map<String, Object> getAvailableSeats() {
        Map<String, Object> res = new HashMap<>();

        res.put("total_rows", this.theater.getRows());
        res.put("total_columns", this.theater.getColumns());
        res.put("available_seats", this.theater.getAvailableSeats());

        return res;
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnSeat(@RequestBody TokenRequest tokenRequest) {
        // Send seat back if seat found
        for (Seat seat : this.theater.getAllSeats()) {
            if (seat.getToken() != null && seat.getToken().equals(tokenRequest.getToken())) {
                seat.setPurchased(false);
                seat.setToken(null);
                this.theater.setIncome(this.theater.getIncome() - seat.getPrice());

                Map<String, Seat> res = new HashMap<>();
                res.put("returned_ticket", seat);

                return new ResponseEntity<>(res, HttpStatus.OK);
            }
        }

        // If seat not found, send error saying seat not found
        return new ResponseEntity<>(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSeat(@RequestBody Seat seatToPurchase) {
        // If row or column is < 0 or >= rows or columns, send error saying out of bounds
        if (seatToPurchase.getRow() < 0
                || seatToPurchase.getRow() >= this.theater.getRows()
                || seatToPurchase.getColumn() < 0
                || seatToPurchase.getColumn() >= this.theater.getColumns()) {
            return new ResponseEntity<>(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        }

        // Send seat back if seat found
        for (Seat seat : this.theater.getAvailableSeats()) {
            if (seat.getRow() == seatToPurchase.getRow() && seat.getColumn() == seatToPurchase.getColumn()) {
                seat.setPurchased(true);
                this.theater.setIncome(this.theater.getIncome() + seat.getPrice());

                UUID uuid = UUID.randomUUID();
                seat.setToken(uuid);

                Map<String, Object> res = new HashMap<>();
                res.put("token", uuid);
                res.put("ticket", seat);

                return new ResponseEntity<>(res, HttpStatus.OK);
            }
        }

        // If seat not found, send error saying seat not found
        return new ResponseEntity<>(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam Optional<String> password) {
        // Verify that the password is correct
        if (password.isEmpty() || !password.get().equals("super_secret")) {
            return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }

        Map<String, Integer> res = new HashMap<>();

        int availableSeats = this.theater.getAvailableSeats().size();
        int purchasedTickets = (int) this.theater.getAllSeats().stream().filter(Seat::isPurchased).count();

        res.put("current_income", this.theater.getIncome());
        res.put("number_of_available_seats", availableSeats);
        res.put("number_of_purchased_tickets", purchasedTickets);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
