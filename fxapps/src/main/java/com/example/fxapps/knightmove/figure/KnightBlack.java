package com.example.fxapps.knightmove.figure;

public class KnightBlack implements Figure {

    private Cell position;

    public KnightBlack(final Cell ps) {
        position = ps;
    }

    @Override
    public Cell position() {
        return position;
    }

    @Override
    public Cell[] way(Cell dest) {
        return new Cell[] {
                dest
        };
    }

    @Override
    public Figure copy(Cell dest) {
        return new KnightBlack(dest);
    }

    public void setPosition(Cell position) {
        this.position = position;
    }
}