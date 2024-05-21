package com.weixue.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weixue.base.exception.WeiXueException;
import com.weixue.base.model.PageParams;
import com.weixue.base.model.PageResult;
import com.weixue.content.mapper.*;
import com.weixue.content.model.dto.AddCourseDto;
import com.weixue.content.model.dto.CourseBaseInfoDto;
import com.weixue.content.model.dto.EditCourseDto;
import com.weixue.content.model.dto.QueryCourseParamsDto;
import com.weixue.content.model.po.*;
import com.weixue.content.service.CourseBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: Sang
 * @Date: 2024/04/20/12:23
 * @Description:
 */
@Service
@Slf4j
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

    @Autowired
    CourseBaseMapper courseBaseMapper;

    @Autowired
    CourseMarketMapper courseMarketMapper;

    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    @Autowired
    TeachplanMapper teachplanMapper;

    @Autowired
    CourseTeacherMapper teacherMapper;

    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto courseParamsDto) {

        //拼装查询条件
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        //TODO:lambda不太懂
        //根据名称模糊查询
        queryWrapper.like(StringUtils.isNoneEmpty(courseParamsDto.getCourseName()), CourseBase::getName, courseParamsDto.getCourseName());
        //根据课程审核状态查询
        queryWrapper.eq(StringUtils.isNoneEmpty(courseParamsDto.getAuditStatus()), CourseBase::getAuditStatus, courseParamsDto.getAuditStatus());
        //根据课程发布状态查询
        queryWrapper.eq(StringUtils.isNoneEmpty(courseParamsDto.getPublishStatus()), CourseBase::getStatus, courseParamsDto.getPublishStatus());

        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());

        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, queryWrapper);
        List<CourseBase> items = pageResult.getRecords();
        long total = pageResult.getTotal();

        PageResult<CourseBase> courseBasePageResult = new PageResult<CourseBase>(items, total, pageParams.getPageNo(), pageParams.getPageSize());
        return courseBasePageResult;
    }

    @Transactional
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto) {

//        //参数的合法性校验
//        if (StringUtils.isBlank(dto.getName())) {
////            throw new RuntimeException("课程名称为空");
//            WeiXueException.cast("课程名称为空");
//        }
//
//        if (StringUtils.isBlank(dto.getMt())) {
//            throw new RuntimeException("课程分类为空");
//        }
//
//        if (StringUtils.isBlank(dto.getSt())) {
//            throw new RuntimeException("课程分类为空");
//        }
//
//        if (StringUtils.isBlank(dto.getGrade())) {
//            throw new RuntimeException("课程等级为空");
//        }
//
//        if (StringUtils.isBlank(dto.getTeachmode())) {
//            throw new RuntimeException("教育模式为空");
//        }
//
//        if (StringUtils.isBlank(dto.getUsers())) {
//            throw new RuntimeException("适应人群为空");
//        }
//
//        if (StringUtils.isBlank(dto.getCharge())) {
//            throw new RuntimeException("收费规则为空");
//        }

        //向课程基本信息表course_base写入数据
        CourseBase courseBaseNew = new CourseBase();
        //将传入的页面参数放入courseBase中
        BeanUtils.copyProperties(dto, courseBaseNew);
        courseBaseNew.setCompanyId(companyId);
        courseBaseNew.setCreateDate(LocalDateTime.now());
        //审核状态设置为未提交
        courseBaseNew.setAuditStatus("202002");
        //发布状态设置为未发布
        courseBaseNew.setStatus("203001");
        //添加course_base
        int insert = courseBaseMapper.insert(courseBaseNew);
        if (insert <= 0) {
            throw new RuntimeException("添加课程失败");
        }
        //向课程营销表course_market写入数据
        CourseMarket courseMarketNew = new CourseMarket();
        Long courseId = courseBaseNew.getId();
        courseMarketNew.setId(courseId);
        //保存营销信息
        BeanUtils.copyProperties(dto, courseMarketNew);
        saveCourseMarket(courseMarketNew);
        //从数据库查询课程的详细信息，包括两部分
        CourseBaseInfoDto courseBaseInfo = getCourseBaseInfo(courseId);

        return courseBaseInfo;
    }


    //查询课程信息
    @Override
    public CourseBaseInfoDto getCourseBaseInfo(Long courseId) {
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) {
            return null;
        }
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase, courseBaseInfoDto);
        if (courseMarket != null) {
            BeanUtils.copyProperties(courseMarket, courseBaseInfoDto);
        }

        //通过courseCategoryMapper查询分类信息，将分类名称放在courseBaseInfoDto对象
        CourseCategory st = courseCategoryMapper.selectById(courseBase.getSt());
        courseBaseInfoDto.setStName(st.getName());
        CourseCategory mt = courseCategoryMapper.selectById(courseBase.getMt());
        courseBaseInfoDto.setMtName(mt.getName());
        return courseBaseInfoDto;
    }

    @Override
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto editCourseDto) {

        //拿到课程id
        Long courseId = editCourseDto.getId();
        //查询课程信息
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) {
            WeiXueException.cast("课程不存在");
        }
        //数据的合法性校验
        //根据具体的业务逻辑区校验
        //本机构只能修改本机构的校验课程
        if (!companyId.equals(courseBase.getCompanyId())) {
            WeiXueException.cast("本机构只能修改本机构的课程");
        }
        //封装数据
        BeanUtils.copyProperties(editCourseDto, courseBase);
        //修改时间
        courseBase.setChangeDate(LocalDateTime.now());

        //更新数据库
        int i = courseBaseMapper.updateById(courseBase);
        if (i <= 0) {
            WeiXueException.cast("修改课程失败");
        }
        //更新营销信息
        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(editCourseDto, courseMarket);
        saveCourseMarket(courseMarket);
        //查询课程信息
        CourseBaseInfoDto courseBaseInfo = getCourseBaseInfo(courseId);

        return courseBaseInfo;

    }

    @Override
    public void deleteCourseBase(Long companyId, Long courseId) {
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if(!courseBase.getCompanyId().equals(companyId)){
            WeiXueException.cast("只允许删除本机构的课程信息");
        }
        if(!courseBase.getAuditStatus().equals("202002")){
            WeiXueException.cast("仅未提交状态的课程信息可以删除");
        }
        LambdaQueryWrapper<CourseTeacher> teacherLambdaQueryWrapper = new LambdaQueryWrapper<>();
        teacherLambdaQueryWrapper.eq(CourseTeacher::getCourseId,courseId);
        teacherMapper.delete(teacherLambdaQueryWrapper);

        LambdaQueryWrapper<Teachplan> teachplanLambdaQueryWrapper = new LambdaQueryWrapper<>();
        teachplanLambdaQueryWrapper.eq(Teachplan::getCourseId,courseId);
        teachplanMapper.delete(teachplanLambdaQueryWrapper);

        courseMarketMapper.deleteById(courseId);

        courseBaseMapper.deleteById(courseId);

    }

    //单独写一个方法保存营销信息。逻辑：存在则更新，不存在则新增
    private int saveCourseMarket(CourseMarket courseMarketNew) {
        //参数合法性校验
        String charge = courseMarketNew.getCharge();
        if (StringUtils.isEmpty(charge)) {
            throw new RuntimeException("收费规则为空");
        }
        if (charge.equals("201001")) {
            if (courseMarketNew.getPrice() == null || courseMarketNew.getPrice().floatValue() <= 0 || courseMarketNew.getOriginalPrice().floatValue() <= 0) {
//                throw new RuntimeException("课程的价格不能为空并且必须大于等于0");
                WeiXueException.cast("课程的价格不能为空并且必须大于等于0");
            }
        }
        //从数据库查询
        Long id = courseMarketNew.getId();
        CourseMarket courseMarket = courseMarketMapper.selectById(id);
        if (courseMarket == null) {
            int insert = courseMarketMapper.insert(courseMarketNew);
            return insert;
        } else {
            BeanUtils.copyProperties(courseMarketNew, courseMarket);
            courseMarket.setId(courseMarketNew.getId());
            int update = courseMarketMapper.updateById(courseMarket);
            return update;
        }

    }
}
