package com.weixue.content.service.impl;

import com.weixue.content.mapper.CourseCategoryMapper;
import com.weixue.content.model.dto.CourseCategoryTreeDto;
import com.weixue.content.service.CourseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: Sang
 * @Date: 2024/05/01/19:05
 * @Description:
 */
@Service
@Slf4j
public class CourseCategoryServiceImp implements CourseCategoryService {

    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        //调用mapper查询分类信息
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);
        //创建一个空list，存储最终节点信息
        List<CourseCategoryTreeDto> courseCategoryList = new ArrayList<>();

        //先将list转成map，key就是节点的id，value就是CourseCategoryDto对象，目的就是便于查找,filter(item->!id.equals(item.getId()))排除根节点
        Map<String, CourseCategoryTreeDto> mapTemp = courseCategoryTreeDtos.stream().filter(item -> !id.equals(item.getId())).collect(Collectors.toMap(key -> key.getId(), value -> value, (key1, key2) -> key2));
        //从头遍历 List<CourseCategoryTreeDto>，一边遍历一边找子结点放在父结点的childrenTreeNodes
        courseCategoryTreeDtos.stream().filter(item -> !id.equals(item.getId())).forEach(item -> {
            //向resultCourseCategoryTree写入元素
            if (item.getParentid().equals(id)) {
                courseCategoryList.add(item);
            }
            //找到当前节点的父结点
            CourseCategoryTreeDto courseCategoryTreeDto = mapTemp.get(item.getParentid());
            if(courseCategoryTreeDto!=null){
                if(courseCategoryTreeDto.getChildrenTreeNodes()==null){
                    courseCategoryTreeDto.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
                }
                //找到每个子结点，放到父结点下
                courseCategoryTreeDto.getChildrenTreeNodes().add(item);
            }

        });
        return courseCategoryList;
    }
}
