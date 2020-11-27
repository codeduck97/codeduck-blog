package com.duck.code.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duck.code.commons.entity.blog.BlogArticle;
import com.duck.code.commons.entity.blog.BlogTag;
import com.duck.code.web.mapper.BlogTagMapper;
import com.duck.code.web.service.BlogArticleService;
import com.duck.code.web.service.BlogTagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author Code Duck
 * @since 2020-10-30
 */
@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper, BlogTag> implements BlogTagService {

    @Resource
    private BlogArticleService blogArticleService;

    /**
     * desc: 获取标签的字典集合
     * <p>
     *
     * @param
     * @return key=tagId，value=tagName
     */
    @Override
    public Map<String, String> getTagDic() {
        List<BlogTag> list = super.list();
        HashMap<String, String> tagDic = new HashMap<>();
        list.forEach(i -> {
            tagDic.put(i.getId(), i.getTagName());
        });
        return tagDic;
    }

    /**
     * desc: 获取标签云信息
     * <p>
     *
     * @param
     * @return
     */
    @Override
    public List<BlogTag> getTagCloud() {
        return this.baseMapper.queryAllTagsByBlogId();
    }

    /**
     * desc: 通过标签id获取存在的博文列表
     * <p>
     *
     * @param id
     * @return
     */
    @Override
    public List<BlogArticle> getArticlesByTagId(String id) {
        QueryWrapper<BlogArticle> wrapper = new QueryWrapper<>();
        wrapper.eq("tag_id", id);
        wrapper.eq("published", 1);
        wrapper.select("id","author","title","cover");
        List<BlogArticle> list = blogArticleService.list(wrapper);
        return list;
    }

    /**
     * desc: 获取存在博文的标签列表
     * <p>
     *
     * @param
     * @return
     */
    @Override
    public List<BlogTag> getTagList() {
        return this.baseMapper.queryAllTagsByBlogId();
    }


}
