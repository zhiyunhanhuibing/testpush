package com.ytxd.contorller;

import com.ytxd.common.Response;
import com.ytxd.pojo.Article;
import com.ytxd.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/web")
@Api(value = "文章管理的controller", tags = {"文章管理接口"})
public class ArticleController {
    @Autowired
    ArticleService articleService;

    /**
     * 根据给定内容添加文章对应内容
     *
     * @param article
     * @return
     */
    @RequestMapping(value = "/addArticle", method = RequestMethod.POST)
    @ApiOperation(value = "添加文章", notes = "所属类别，文章名和内容不能为空")
    public Integer addArticle(@ApiParam(name = "文章对象", value = "前台写入的文章") @RequestBody Article article) {

        return articleService.addSelective(article);
    }

    /**
     * 根据给定条件动态查询该条件对应的文章及文章附件
     * 如果参数是文章主键，则会查询到一条文章和对应的所有附件
     * 如果参数是类别，则只查询该类别对应的所有文章
     *
     * @param article
     * @return
     */
    @RequestMapping(value = "/queryArticle", method = RequestMethod.GET)
    @ApiOperation(value = "动态查询文章分页", notes = "文章id，和类别id不能同时为空")
    public Response queryArticle(@ApiParam(name = "article", value = "若文章id不为空，则根据文章id查询，类别id不为空则根据文章id查询，否则查询所有") Article article,
                                 @ApiParam(name = "rows", value = "每页总行数") Integer rows,
                                 @ApiParam(name = "page", value = "当前页数") Integer page) {
        return articleService.queryByCondition(article, rows, page);
    }

    /**
     * 根据给定字段修改文章的对应字段内容
     *
     * @param article
     * @return
     */
    @RequestMapping(value = "/modifyArticle", method = RequestMethod.POST)
    @ApiOperation(value = "修改文章属性", notes = "根据articleId修改文章内容")
    public Integer modifyArticle(@ApiParam(name = "修改的文章对象", value = "修改不为空的属性值") @RequestBody Article article) {

        return articleService.modifyByPrimaryKeySelective(article);
    }

    /**
     * 根据id删除文章
     *
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/removeArticle", method = RequestMethod.GET)
    @ApiOperation(value = "删除文章", notes = "根据主键删除文章")
    public Integer removeArticle(@ApiParam(name = "articleId", value = "文章id") String articleId) {
        if (articleId.isEmpty()) {
            throw new RuntimeException("未选择删除文件异常！");
        }
        return articleService.removeByPrimaryKey(articleId);
    }

}
