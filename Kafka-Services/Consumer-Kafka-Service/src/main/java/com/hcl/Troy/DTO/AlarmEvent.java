package com.hcl.Troy.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlarmEvent {

    private String eventId;
    private String eventType;
    private String severity;
    private String message;
}