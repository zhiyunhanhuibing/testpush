package com.ytxd.common;

import lombok.Data;

import java.util.List;

@Data
public class Response {
    /**
     * 总页数
     */
    private Integer totelPage;
    /**
     * 總條數
     */
    private Integer totalSize;
    /**
     * 结果集
     */
    private List resultList;
}
