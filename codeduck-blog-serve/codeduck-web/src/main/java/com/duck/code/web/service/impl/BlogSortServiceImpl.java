package com.duck.code.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duck.code.commons.entity.pojo.BlogArticle;
import com.duck.code.commons.entity.pojo.BlogSort;
import com.duck.code.web.mapper.BlogSortMapper;
import com.duck.code.web.service.BlogArticleService;
import com.duck.code.web.service.BlogSortService;
import com.duck.code.web.vo.BlogSortVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 博客分类表 服务实现类
 * </p>
 *
 * @author Code Duck
 * @since 2020-10-27
 */
@Service
public class BlogSortServiceImpl extends ServiceImpl<BlogSortMapper, BlogSort> implements BlogSortService {

    @Resource
    private BlogArticleService blogArticleService;

    /**
     * desc: 获取分类信息的字典集合
     * <p>
     *
     * @param
     * @return key=sortId，value=sortName
     */
    @Override
    public Map<String, String> getSortDic() {
        List<BlogSort> list = super.list();
        HashMap<String, String> sortDic = new HashMap<>();
        list.forEach(i -> {
            sortDic.put(i.getId(), i.getSortName());
        });
        return sortDic;
    }

    /**
     * desc: 获取分类列表
     * <p>
     *
     * @param
     * @return
     */
    @Override
    public List<BlogSort> getSortList() {
        return this.baseMapper.queryAllSortsByBlogId();
    }

    /**
     * desc: 通过sortId获取相关博文列表
     * <p>
     *
     * @param id
     * @return
     */
    @Override
    public List<BlogArticle> getArticlesBySortId(String id) {
        QueryWrapper<BlogArticle> wrapper = new QueryWrapper<>();
        wrapper.eq("sort_id", id);
        wrapper.eq("published", 1);
        wrapper.select("id","author","title","cover");
        List<BlogArticle> list = blogArticleService.list(wrapper);
        return list;
    }
}
