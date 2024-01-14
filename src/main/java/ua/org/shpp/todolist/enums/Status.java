package ua.org.shpp.todolist.enums;

public enum Status {
    PLANNED(0),
    WORK_IN_PROGRESS(1),
    POSTPONED(2),
    NOTIFIED(3),
    SIGNED(4),
    DONE(5),
    CANCELLED(6);

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