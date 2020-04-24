package com.epam.cdp.maksim.katuranau.module12.task1.model;

import javax.validation.constraints.Min;
import java.util.Objects;

public class Status {
    @Min(value = 0, message = "identifier should not be negative")
    private Long statusId;
    private String status;

    public Status(final Long statusId, final String status) {
        this.statusId = statusId;
        this.status = status;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(final Long statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status1 = (Status) o;
        return Objects.equals(statusId, status1.statusId) &&
                Objects.equals(status, status1.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusId, status);
    }
}
