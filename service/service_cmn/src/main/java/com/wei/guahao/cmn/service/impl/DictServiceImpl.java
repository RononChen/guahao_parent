package com.wei.guahao.cmn.service.impl;


import com.atguigu.yygh.model.cmn.Dict;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wei.guahao.cmn.mapper.DictMapper;
import com.wei.guahao.cmn.service.DictService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    public List<Dict> selectChildData(long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id",id);
        List<Dict> dictList = baseMapper.selectList(queryWrapper);
//        遍历dictList 给每一个Dict的hasChildren赋值
        for (Dict dict : dictList) {
            Long id1 = dict.getId();
            boolean hasChildren = this.isHasChildren(id1);
            dict.setHasChildren(hasChildren);
        }
        return dictList;
    }

//    上边是把数据库的字段和Dict做了一个映射。 Dict中的 private boolean hasChildren并没有映射。
//    id下面是否有子节点
    private boolean isHasChildren(long id){
        QueryWrapper<Dict> queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id",id);
        Integer i = baseMapper.selectCount(queryWrapper);
        return i > 0;
    }

}
