package service.impl;

import dao.CategoryDao;
import dao.impl.CategoryDaoImpl;
import domain.Category;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisConnectionException;
import service.CategoryService;
import util.JedisUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao dao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        Jedis jedis = null;
        List<Category> list = null;
        try{
            jedis = JedisUtil.getJedis();
            Set<Tuple> categories = jedis.zrangeWithScores("categories", 0, -1);
            // use Redis database cache categories
            if(categories == null || categories.size() <= 0) {
                list = dao.findAll();
                for(Category c : list) {
                    jedis.zadd("categories", c.getCid(), c.getCname());
                }
            } else {
                list = new ArrayList<Category>();
                for(Tuple category : categories) {
                    Category c = new Category();
                    c.setCname(category.getElement());
                    c.setCid((int)category.getScore());
                    list.add(c);
                }
            }
        }catch(JedisConnectionException e) {
            list = dao.findAll();
        } finally {
            if(jedis != null) {
                jedis.close();
            }
            return list;
        }
    }
}
