package org.essaadani.customermscqrseventsourcing.commonapi.events;

import lombok.Getter;
import org.essaadani.accountmscqrses.commonapi.events.BaseEvent;

import java.util.Date;

public class CustomerCreatedEvent extends BaseEvent<String> {
    /*
     * what to save to event store
     * */

    @Getter
    private String firstName;
    @Getter
    private String lastName;
    @Getter
    private Date birthDate;
    @Getter
    private String cin;
    @Getter
    private Date timestamp;

    public CustomerCreatedEvent(String id, String firstName, String lastName, Date birthDate, String cin, Date timestamp) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.cin = cin;
        this.timestamp = timestamp;
    }
}
