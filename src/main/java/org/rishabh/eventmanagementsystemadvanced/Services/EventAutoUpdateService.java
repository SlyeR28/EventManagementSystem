package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Event;

public interface EventAutoUpdateService {


    void updateEventStatus(Event event);


    void autoUpdateEventStatuses();
}
