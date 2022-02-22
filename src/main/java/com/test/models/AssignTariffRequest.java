package com.test.models;

import lombok.Data;

@Data
public class AssignTariffRequest {
    private String shipmentName;
    private String tariffName;
}
