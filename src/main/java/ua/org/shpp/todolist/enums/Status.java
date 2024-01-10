package ua.org.shpp.todolist.enums;

public enum Status {
    PLANNED(1),
    WORK_IN_PROGRESS(2),
    POSTPONED(3),
    NOTIFIED(4),
    SIGNED(5),
    DONE(6),
    CANCELLED(7);

    public static final boolean[][] CONNECTIONS = {
            {true,true,true,true,true,true,true},
            {false,true,true,true,true,true,true},
            {false,true,true,true,true,true,true},
            {false,false,false,true,true,true,true},
            {false,false,false,true,true,true,true},
            {false,false,false,false,false,true,false},
            {false,false,false,false,false,false,true}
    };
    private final int rowCol;

    Status(int rowCol) {
        this.rowCol = rowCol;
    }

    public int getRowCol() {
        return rowCol;
    }
}