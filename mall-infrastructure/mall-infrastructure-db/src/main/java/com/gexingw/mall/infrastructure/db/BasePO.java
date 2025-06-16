package com.gexingw.mall.infrastructure.db;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class BasePO implements Serializable {

    private Long id;

}
