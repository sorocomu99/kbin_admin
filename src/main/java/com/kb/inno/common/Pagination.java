package com.kb.inno.common;

import lombok.Data;

@Data
public class Pagination {
    private int blockPage;
    private int totalPage;
    private int totalBlock;
    private int nowBlock;
    private int startPage;
    private int endPage;
    private int currentPage;
    private int totalCount;
    private int pageSize;

    public Pagination(int totalCount, int currentPage, int pageSize, int blockPage){
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.blockPage = blockPage;
        this.currentPage = currentPage;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize) == 0 ? 1 : (int) Math.ceil((double) totalCount / pageSize);
        this.totalBlock = (int) Math.ceil((double) totalPage / blockPage);
        this.nowBlock = (int) Math.ceil((double) currentPage / blockPage);
        this.startPage = nowBlock * blockPage - (blockPage - 1);
        if(this.startPage <= 1){
            this.startPage = 1;
        }
        this.endPage = nowBlock * blockPage == 0 ? 1 : nowBlock * blockPage;
        if(this.endPage >= totalPage){
            this.endPage = totalPage;
        }
    }
}
