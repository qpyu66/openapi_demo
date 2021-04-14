package com.ankus.openapi.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class listVO {
    public String addrs;
    public String serviceKey;
    public int numOfRows;
    public int pageNo;
}
