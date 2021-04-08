package ru.kyeeego.pikit.modules.requisition.port;

public interface ISetDone {
    void setDone(Long id);
    void setInProgress(Long id);
}
