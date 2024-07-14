package com.news.model;

import java.util.List;

public interface NewsDAO {
    public void insert(NewsVO news);
    public void update(NewsVO news);
    public void delete(Integer newsID);
    public NewsVO findByPK(Integer newsID);
    public List<NewsVO> getAll();
}
