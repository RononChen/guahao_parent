package com.wei.guahao.cmn.service.impl;


import com.alibaba.excel.EasyExcel;
import com.atguigu.yygh.model.cmn.Dict;

import com.atguigu.yygh.vo.cmn.DictEeVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.wei.guahao.cmn.listener.DictListener;
import com.wei.guahao.cmn.mapper.DictMapper;
import com.wei.guahao.cmn.service.DictService;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {




    @Override
    @Cacheable(value = "dict",keyGenerator = "keyGenerator")
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


    //    上边是把数据库的字段和Dict做了一个映射。
//    Dict中的 private boolean hasChildren并没有映射。
//    id下面是否有子节点
    private boolean isHasChildren(long id){
        QueryWrapper<Dict> queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id",id);
        Integer i = baseMapper.selectCount(queryWrapper);
        return i > 0;
    }


    //    导出数据字典
    @Override
    public void exportDate(HttpServletResponse response)  {
//        设置下载信息
//        1、设置下载的文件类型是excel
        response.setContentType("application/vnd.ms-excel");
//        2、设置发送到客户端的响应的字符编码，通知浏览器以utf-8的
//        编码格式解析响应正文(也就是excel文件中的内容)
        response.setCharacterEncoding("utf-8");

//        3、文件下载需要设置响应头信息
        // URLEncoder.encode可以防止文件名称中文乱码
        String fileName = null;
        try {
            fileName = URLEncoder.encode("数据字典", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        String fileName = "niko";
        response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xlsx");

//        5、获取数据字典中多有的内容
        List<Dict> dictList = baseMapper.selectList(null);
        ArrayList<DictEeVo> dictEeVoList = new ArrayList<>();
//        集合中的数据类型是Dict，写入到excel文件中的数据类型是DictEeVo，需要转换
        for (Dict dict : dictList) {
            DictEeVo dictEeVo = new DictEeVo();
//            spring提供的工具类BeanUtils
            BeanUtils.copyProperties(dict,dictEeVo);
            dictEeVoList.add(dictEeVo);
        }
//        4、使用easyexcel导出文件
        try {
            /**
             *  下载文件： 对服务器端而言，是使用输出流，写入到指定的文件中
             *  写的对象类型  DictEeVo
             */
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet().doWrite(dictEeVoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    导入数据字典
    @Override
    public void importData(MultipartFile file) {
        try {
//            对于服务端而言，读取excel文件以输入流的方式传递过来
            /** InputStream inputStream：输入流
             *  Class head： 读取的类型
             *  ReadListener readListener  自定义监听器
             */
            EasyExcel.read(file.getInputStream(),DictEeVo.class,new DictListener(baseMapper))
                    .sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    查询医院等级 或者地址
    @Override
    public String getName(String dictCode, Long value) {
//        如果dictCode为空 我们就根据value查询地址
        if (StringUtils.isEmpty(dictCode)){
            QueryWrapper<Dict> wrapper = new QueryWrapper<>();
            wrapper.eq("value",value);
            Dict dict = baseMapper.selectOne(wrapper);
            return dict.getName();

        }else {
//            如果dictCode不为空 我们就根据dictCode和value查询医院等级
//            1 根据dictCode查询出来这条dict 获取到id
            QueryWrapper<Dict> wrapper = new QueryWrapper<>();
            wrapper.eq("dict_code",dictCode);
            Dict dict = baseMapper.selectOne(wrapper);

//            2 然后根据parent_id 和 value 获取到这条dict 返回name
            Long parent_id = dict.getId();
            QueryWrapper<Dict> wrapperFinal = new QueryWrapper<>();
            wrapperFinal.eq("parent_id",parent_id);
            wrapperFinal.eq("value",value);
            Dict dictFinal = baseMapper.selectOne(wrapperFinal);
            return dictFinal.getName();

        }
    }

//    根据dictCode获取下级节点
    @Override
    public List<Dict> findByDictCode(String dictCode) {

//        1 根据dictCode获取到这一个dict
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_code",dictCode);
        Dict dict = baseMapper.selectOne(wrapper);

//        2 根据dict的ID获取所有的字节点
        List<Dict> dictList = this.selectChildData(dict.getId());

        return dictList;
    }

}
