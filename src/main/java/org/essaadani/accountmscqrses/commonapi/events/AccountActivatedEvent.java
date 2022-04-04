package org.essaadani.accountmscqrses.commonapi.events;

import lombok.Getter;
import org.essaadani.accountmscqrses.commonapi.enums.AccountStatus;

public class AccountActivatedEvent extends BaseEvent<String>{
    /*
    * What to save at the event store?
    * */
    @Getter private AccountStatus accountStatus;
    /*
     * END What to save at the event store?
     * */

    public AccountActivatedEvent(String id, AccountStatus accountStatus) {
        super(id);
        this.accountStatus = accountStatus;
    }
}
