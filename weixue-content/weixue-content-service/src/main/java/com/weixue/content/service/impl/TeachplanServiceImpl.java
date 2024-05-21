package com.weixue.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weixue.base.exception.WeiXueException;
import com.weixue.content.mapper.TeachplanMapper;
import com.weixue.content.mapper.TeachplanMediaMapper;
import com.weixue.content.model.dto.SaveTeachplanDto;
import com.weixue.content.model.dto.TeachplanDto;
import com.weixue.content.model.po.Teachplan;
import com.weixue.content.model.po.TeachplanMedia;
import com.weixue.content.service.TeachplanService;
import lombok.extern.java.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Sang
 * @Date: 2024/05/20/18:28
 * @Description:
 */
@Service
public class TeachplanServiceImpl implements TeachplanService {

    @Autowired
    TeachplanMapper teachplanMapper;
    @Autowired
    TeachplanMediaMapper teachplanMediaMapper;


    @Override
    public List<TeachplanDto> findTeachplanTree(Long courseId) {
        List<TeachplanDto> teachplanDtos = teachplanMapper.selectTreeNodes(courseId);
        return teachplanDtos;
    }

    @Override
    public void saveTeachplan(SaveTeachplanDto saveTeachplanDto) {
        //通过课程计划id判断是新增还是修改
        Long teachplanId = saveTeachplanDto.getId();
        if (teachplanId == null) {
            //新增

            Teachplan teachplan = new Teachplan();
            BeanUtils.copyProperties(saveTeachplanDto, teachplan);
            //确定排序顺序，找到同级节点总个数，然后+1
            Long parentid = saveTeachplanDto.getParentid();
            Long courseId = saveTeachplanDto.getCourseId();
            int count = getTeachplanCount(courseId, parentid);
            teachplan.setOrderby(count + 1);
            teachplanMapper.insert(teachplan);
        } else {
            //修改
            Teachplan teachplan = teachplanMapper.selectById(teachplanId);
            BeanUtils.copyProperties(saveTeachplanDto, teachplan);
            teachplanMapper.updateById(teachplan);
        }
    }

    @Override
    public void deleteTeachplan(Long teachplanId) {
        /**
         * 删除第一级别的大章节时要求大章节下边没有小章节时方可删除。
         * 删除第二级别的小章节的同时需要将teachplan_media表关联的信息也删除。
         * 要求
         * 1、删除大章节，大章节下有小章节时不允许删除。
         * 2、删除大章节，大单节下没有小章节时可以正常删除。
         * 3、删除小章节，同时将关联的信息进行删除。
         */
        Teachplan teachplan = teachplanMapper.selectById(teachplanId);
        if (teachplan == null) {
            WeiXueException.cast("课程计划id为空");
        }
        Long courseId = teachplan.getCourseId();
        Long parentId = teachplan.getParentid();
        //判断是否含有子结点
        int childrenNum = getTeachplanCount(courseId, teachplanId);
        if (childrenNum != 0) {
            WeiXueException.cast("课程计划信息还有子级信息，无法操作");
        } else {
            LambdaQueryWrapper<TeachplanMedia> mediaLambdaQueryWrapper = new LambdaQueryWrapper<>();
            LambdaQueryWrapper<TeachplanMedia> eq = mediaLambdaQueryWrapper.eq(TeachplanMedia::getTeachplanId, teachplanId);
            TeachplanMedia teachplanMedia = teachplanMediaMapper.selectOne(eq);
            teachplanMapper.deleteById(teachplanId);
            teachplanMediaMapper.deleteById(teachplanMedia);
        }
    }

    @Override
    public void orderByTeachplan(String moveType, Long teachplanId) {
        Teachplan teachplan = teachplanMapper.selectById(teachplanId);
        Long courseId = teachplan.getCourseId();//当前课程
        Integer grade = teachplan.getGrade();//当前级别
        Integer orderby = teachplan.getOrderby();//当前排序id
        if (moveType.equals("movedown")) {//向下移动，两两交换
            /**
             * SELECT
             * 	*
             * FROM
             * 	teachplan
             * WHERE
             * 	course_id = 117
             * 	AND grade = 1
             * 	AND orderby > 6
             * ORDER BY
             * 	orderby ASC
             * 	LIMIT 1
             */
            LambdaQueryWrapper<Teachplan> downQueryWrapper = new LambdaQueryWrapper<>();
            downQueryWrapper.eq(Teachplan::getCourseId, courseId)
                    .eq(Teachplan::getGrade, grade)
                    .gt(Teachplan::getOrderby, orderby)
                    .orderByAsc(Teachplan::getOrderby)
                    .last("LIMIT 1");
            Teachplan down = teachplanMapper.selectOne(downQueryWrapper);
            swapNode(teachplan, down);
        }
        if (moveType.equals("moveup")) {//向上移动，两两交换
            /**
             * SELECT
             * 	*
             * FROM
             * 	teachplan
             * WHERE
             * 	course_id = 117
             * 	AND grade = 1
             * 	AND orderby < 6
             * ORDER BY
             * 	orderby DESC
             * 	LIMIT 1
             */
            LambdaQueryWrapper<Teachplan> upQueryWrapper = new LambdaQueryWrapper<>();
            upQueryWrapper.eq(Teachplan::getCourseId, courseId)
                    .eq(Teachplan::getGrade, grade)
                    .lt(Teachplan::getOrderby, orderby)
                    .orderByDesc(Teachplan::getOrderby)
                    .last("LIMIT 1");
            Teachplan up = teachplanMapper.selectOne(upQueryWrapper);
            swapNode(teachplan,up);
        }
    }

    private void swapNode(Teachplan t1, Teachplan t2) {
        if (t2 == null) {
            WeiXueException.cast("无法再移动！");
        }
        Integer orderby1 = t1.getOrderby();
        Integer orderby2 = t2.getOrderby();
        t1.setOrderby(orderby2);
        t2.setOrderby(orderby1);
        teachplanMapper.updateById(t1);
        teachplanMapper.updateById(t2);
    }


    private int getTeachplanCount(Long courseId, Long parentId) {
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Teachplan> eq = queryWrapper.eq(Teachplan::getCourseId, courseId).eq(Teachplan::getParentid, parentId);

        return teachplanMapper.selectCount(eq);

    }
}
