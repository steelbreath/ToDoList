package ua.org.shpp.todolist.enums;

import java.util.Set;

public enum Status {
    PLANNED,
    WORK_IN_PROGRESS,
    POSTPONED,
    NOTIFIED,
    SIGNED,
    DONE,
    CANCELLED;

    public static Set<Status> getAllowedStatuses(Status status){
        return switch (status){
            case PLANNED -> Set.of(PLANNED,WORK_IN_PROGRESS,POSTPONED,CANCELLED);
            case WORK_IN_PROGRESS, POSTPONED -> Set.of(WORK_IN_PROGRESS,POSTPONED,NOTIFIED,SIGNED,CANCELLED);
            case NOTIFIED, SIGNED -> Set.of(NOTIFIED,SIGNED,DONE,CANCELLED);
            case DONE -> Set.of(DONE);
            case CANCELLED -> Set.of(CANCELLED);
        };
    }
}