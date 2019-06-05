package com.ytxd.contorller;


import com.ytxd.common.Response;
import com.ytxd.common.Result;
import com.ytxd.pojo.FileList;
import com.ytxd.service.FileListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/file")
@Api(value = "附件表controller",tags = {"附件表接口"})
public class FileListController {
    @Autowired
    private FileListService fileListService;


    /**
     * 根据目录查找
     * @param categoryId
     * @return
     */
    @GetMapping("/selectCategoryId")
    @ApiOperation(value="根据目录查找附件表",notes = "根据目录categoryId查找")
    public Response selectCategoryId(@ApiParam(name = "categortId",value="目录categoryId",required = true)Integer categoryId,
                                     @ApiParam(name = "page", value="页数") Integer page,
                                     @ApiParam(name = "rows", value="条数")Integer rows
                                    ){
        return fileListService.selectCategotyId(categoryId,page,rows);
    }

    /**
     * 添加
     * @param fileList
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value="添加一条附件信息",notes = "添加附件对象")
    public Result add(@RequestBody @ApiParam(name = "附件对象",value = "传入json格式") FileList fileList){
        try {
            fileListService.insert(fileList);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }
    /**
     * 根据id删除
     * @param
     * @return
     */
    @GetMapping("/delete")
    @ApiOperation(value="删除一条附件信息",notes = "根据附表id删除")
    public Result delete(@ApiParam(name = "fileId",value = "附件id删除",required = true)@RequestParam(value = "fileId") String fileId, HttpSession session){
        try {
            fileListService.deleteByPrimaryKey(fileId);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }


}
