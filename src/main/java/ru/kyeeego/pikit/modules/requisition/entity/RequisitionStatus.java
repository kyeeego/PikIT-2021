package ru.kyeeego.pikit.modules.requisition.entity;

public enum RequisitionStatus {
    MODERATING,
    EXP_VOTING,
    STUD_VOTING,
    IN_PROGRESS,
    // Для закрытых заявок, не прошедших модерацию
    CLOSED,
    // Для заявок, идеи которых были реализованы
    DONE
}
