package com.ytxd.contorller;


import com.ytxd.common.Response;
import com.ytxd.common.Result;
import com.ytxd.pojo.Cooperate;
import com.ytxd.pojo.CooperateReq;
import com.ytxd.service.CooperateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/coop")
@Api(value = "合作controller", tags = {"合作关系接口"})
public class CooperateController {
    @Autowired
    private CooperateService cooperateService;

    /**
     * 查询所有数据
     *
     * @return
     */
    @ApiOperation(value = "获取合作关系信息", notes = "根据公司名称模糊查询")
    @PostMapping("/selectAll")
    public Response selectAll(@RequestBody CooperateReq req) {
        return cooperateService.selectAll(req.getMohu(), req.getRows(), req.getPage());
    }

    /**
     * 提交
     *
     * @param record
     */
    @ApiOperation(value = "提交合作信息", notes = "姓名、联系电话、邮箱是必填信息、")
    @PostMapping("/add")
    public Result add(@RequestBody @ApiParam(name = "合作对象", value = "传入json格式") Cooperate record) {
        try {
            cooperateService.insert(record);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 根据主键删除
     *
     * @param coopId
     */
    @ApiOperation(value = "删除合作信息", notes = "根据id删除")
    @PostMapping("/delete")
    public Result delete(@ApiParam(name = "coopId", value = "合作coopId", required = true) String coopId) {
        try {
            cooperateService.deleteByPrimaryKey(coopId);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

}
