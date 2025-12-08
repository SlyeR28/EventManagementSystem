package org.rishabh.eventmanagementsystemadvanced.Domains.Modal;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
   ADMIN,
    STAFF,
    ORGANIZER,
    ATTENDEE;

    @JsonCreator
    public static Role from(String value) {
        if (value == null) return null;
        switch (value.trim().toUpperCase()) {
            case "USER":       return ATTENDEE; // map legacy "USER" to ATTENDEE
            case "ATTENDEE":   return ATTENDEE;
            case "STAFF":      return STAFF;
            case "ADMIN":      return ADMIN;
            case "ORGANIZER":  return ORGANIZER;
            default:
                throw new IllegalArgumentException("Unknown Role: " + value);
        }
    }
}
