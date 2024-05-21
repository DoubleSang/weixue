package com.weixue.content.api;

import com.weixue.content.model.dto.SaveTeachplanDto;
import com.weixue.content.model.dto.TeachplanDto;
import com.weixue.content.model.po.Teachplan;
import com.weixue.content.service.TeachplanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Sang
 * @Date: 2024/05/20/16:30
 * @Description: 课程计划管理相关接口
 */
@RestController
@Api(value = "课程计划编辑接口",tags = "课程计划编辑接口")
public class TeachplanController {

    @Autowired
    TeachplanService teachplanService;

    //查询课程计划
    @ApiOperation("查询课程计划树形结构")
    @GetMapping("/teachplan/{courseId}/tree-nodes")
    public List<TeachplanDto>getTreeNodes(@PathVariable Long courseId){
        return teachplanService.findTeachplanTree(courseId);

    }

    @ApiOperation("课程计划创建或修改")
    @PostMapping("/teachplan")
    public void saveTeachplan(@RequestBody SaveTeachplanDto teachplan){
        teachplanService.saveTeachplan(teachplan);
    }

    @ApiOperation("课程计划删除")
    @DeleteMapping("/teachplan/{teachplanId}")
    public void deleteTeachplan(@PathVariable Long teachplanId){
        teachplanService.deleteTeachplan(teachplanId);
    }

    @ApiOperation("课程计划上/下移")
    @PostMapping("/teachplan/{moveType}/{teachplanId}")
    public void orderByTeachplan(@PathVariable String moveType,@PathVariable Long teachplanId){
        teachplanService.orderByTeachplan(moveType,teachplanId);
    }
}
