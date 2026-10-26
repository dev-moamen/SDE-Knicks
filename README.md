# Parkify

JavaFX parking app. 800×800 window, shared navy/teal theme (`app.css`).

**Team:** Moamen Fahmy, Omar Khodir, Matyas Grof-Tisza

## Screens

```
Introduction → Home
                 ├─ My Profile
                 │     ├─ My Cars
                 │     └─ Payment Information
                 ├─ Parking Lot
                 └─ My Reservations
```

- **Introduction** — logo and Get Started
- **Home** — My Profile, Parking Lot, My Reservations
- **My Profile** — cars and payment
- **My Cars** — add a car; list on the right
- **Payment Information** — add a card; list on the right
- **Parking Lot** — spots A1–A4 and B1–B4 (teal = available)
- **My Reservations** — reservation table

Every inner screen has **Back** in the top-left and the screen title centered.

## Run

```bash
mvn javafx:run
```
